--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/12671.java	2018-10-25 14:41:47.370792096 +0200
@@ -1,311 +1,311 @@
 /*
  * Licensed to Elastic Search and Shay Banon under one
  * or more contributor license agreements.  See the NOTICE file
  * distributed with this work for additional information
  * regarding copyright ownership. Elastic Search licenses this
  * file to you under the Apache License, Version 2.0 (the
  * "License"); you may not use this file except in compliance
  * with the License.  You may obtain a copy of the License at
  *
  *    http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing,
  * software distributed under the License is distributed on an
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  * KIND, either express or implied.  See the License for the
  * specific language governing permissions and limitations
  * under the License.
  */
 
 package org.elasticsearch.index.shard.recovery;
 
 import org.apache.lucene.store.IndexInput;
 import org.elasticsearch.ElasticSearchException;
 import org.elasticsearch.common.StopWatch;
 import org.elasticsearch.common.collect.Lists;
 import org.elasticsearch.common.collect.Sets;
 import org.elasticsearch.common.component.AbstractComponent;
 import org.elasticsearch.common.inject.Inject;
 import org.elasticsearch.common.settings.Settings;
 import org.elasticsearch.common.unit.ByteSizeUnit;
 import org.elasticsearch.common.unit.ByteSizeValue;
 import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
 import org.elasticsearch.index.engine.Engine;
 import org.elasticsearch.index.shard.IllegalIndexShardStateException;
 import org.elasticsearch.index.shard.IndexShardClosedException;
 import org.elasticsearch.index.shard.IndexShardState;
 import org.elasticsearch.index.shard.service.InternalIndexShard;
 import org.elasticsearch.index.store.StoreFileMetaData;
 import org.elasticsearch.index.translog.Translog;
 import org.elasticsearch.indices.IndicesService;
 import org.elasticsearch.indices.recovery.throttler.RecoveryThrottler;
 import org.elasticsearch.threadpool.ThreadPool;
 import org.elasticsearch.transport.BaseTransportRequestHandler;
 import org.elasticsearch.transport.TransportChannel;
 import org.elasticsearch.transport.TransportService;
 import org.elasticsearch.transport.VoidTransportResponseHandler;
 
 import java.io.IOException;
 import java.util.List;
 import java.util.Set;
 import java.util.concurrent.CountDownLatch;
 import java.util.concurrent.atomic.AtomicLong;
 import java.util.concurrent.atomic.AtomicReference;
 
 import static org.elasticsearch.common.unit.TimeValue.*;
 
 /**
  * The source recovery accepts recovery requests from other peer shards and start the recovery process from this
  * source shard to the target shard.
  *
  * @author kimchy (shay.banon)
  */
 public class RecoverySource extends AbstractComponent {
 
     public static class Actions {
         public static final String START_RECOVERY = "index/shard/recovery/startRecovery";
     }
 
     private final ThreadPool threadPool;
 
     private final TransportService transportService;
 
     private final IndicesService indicesService;
 
     private final RecoveryThrottler recoveryThrottler;
 
 
     private final ByteSizeValue fileChunkSize;
 
     @Inject public RecoverySource(Settings settings, ThreadPool threadPool, TransportService transportService, IndicesService indicesService,
                                   RecoveryThrottler recoveryThrottler) {
         super(settings);
         this.threadPool = threadPool;
         this.transportService = transportService;
         this.indicesService = indicesService;
         this.recoveryThrottler = recoveryThrottler;
 
         this.fileChunkSize = componentSettings.getAsBytesSize("file_chunk_size", new ByteSizeValue(100, ByteSizeUnit.KB));
 
         transportService.registerHandler(Actions.START_RECOVERY, new StartRecoveryTransportRequestHandler());
     }
 
     private RecoveryResponse recover(final StartRecoveryRequest request) {
         if (!recoveryThrottler.tryRecovery(request.shardId(), "peer recovery source")) {
             RecoveryResponse retry = new RecoveryResponse();
             retry.retry = true;
             return retry;
         }
         final InternalIndexShard shard = (InternalIndexShard) indicesService.indexServiceSafe(request.shardId().index().name()).shardSafe(request.shardId().id());
         try {
             logger.trace("starting recovery to {}, mark_as_relocated {}", request.targetNode(), request.markAsRelocated());
             final RecoveryResponse response = new RecoveryResponse();
             shard.recover(new Engine.RecoveryHandler() {
                 @Override public void phase1(final SnapshotIndexCommit snapshot) throws ElasticSearchException {
                     long totalSize = 0;
                     long existingTotalSize = 0;
                     try {
                         StopWatch stopWatch = new StopWatch().start();
 
                         for (String name : snapshot.getFiles()) {
                             StoreFileMetaData md = shard.store().metaDataWithMd5(name);
                             boolean useExisting = false;
                             if (request.existingFiles.containsKey(name)) {
                                 if (md.md5().equals(request.existingFiles.get(name).md5())) {
                                     response.phase1ExistingFileNames.add(name);
                                     response.phase1ExistingFileSizes.add(md.sizeInBytes());
                                     existingTotalSize += md.sizeInBytes();
                                     useExisting = true;
                                     if (logger.isTraceEnabled()) {
                                         logger.trace("[{}][{}] recovery [phase1] to {}: not recovering [{}], exists in local store and has md5 [{}]", request.shardId().index().name(), request.shardId().id(), request.targetNode(), name, md.md5());
                                     }
                                 }
                             }
                             if (!useExisting) {
                                 if (request.existingFiles.containsKey(name)) {
                                     logger.trace("[{}][{}] recovery [phase1] to {}: recovering [{}], exists in local store, but has different md5: remote [{}], local [{}]", request.shardId().index().name(), request.shardId().id(), request.targetNode(), name, request.existingFiles.get(name).md5(), md.md5());
                                 } else {
                                     logger.trace("[{}][{}] recovery [phase1] to {}: recovering [{}], does not exists in remote", request.shardId().index().name(), request.shardId().id(), request.targetNode(), name);
                                 }
                                 response.phase1FileNames.add(name);
                                 response.phase1FileSizes.add(md.sizeInBytes());
                                 totalSize += md.sizeInBytes();
                             }
                         }
                         response.phase1TotalSize = totalSize;
                         response.phase1ExistingTotalSize = existingTotalSize;
 
                         final AtomicLong throttlingWaitTime = new AtomicLong();
 
                         logger.trace("[{}][{}] recovery [phase1] to {}: recovering_files [{}] with total_size [{}], reusing_files [{}] with total_size [{}]", request.shardId().index().name(), request.shardId().id(), request.targetNode(), response.phase1FileNames.size(), new ByteSizeValue(totalSize), response.phase1ExistingFileNames.size(), new ByteSizeValue(existingTotalSize));
 
                         final CountDownLatch latch = new CountDownLatch(response.phase1FileNames.size());
                         final AtomicReference<Exception> lastException = new AtomicReference<Exception>();
                         for (final String name : response.phase1FileNames) {
                             threadPool.cached().execute(new Runnable() {
                                 @Override public void run() {
                                     IndexInput indexInput = null;
                                     try {
                                         long throttlingStartTime = System.currentTimeMillis();
                                         while (!recoveryThrottler.tryStream(request.shardId(), name)) {
                                             if (shard.state() == IndexShardState.CLOSED) { // check if the shard got closed on us
                                                 throw new IndexShardClosedException(shard.shardId());
                                             }
                                             Thread.sleep(recoveryThrottler.throttleInterval().millis());
                                         }
                                         throttlingWaitTime.addAndGet(System.currentTimeMillis() - throttlingStartTime);
 
                                         final int BUFFER_SIZE = (int) fileChunkSize.bytes();
                                         byte[] buf = new byte[BUFFER_SIZE];
                                         indexInput = snapshot.getDirectory().openInput(name);
                                         long len = indexInput.length();
                                         long readCount = 0;
                                         while (readCount < len) {
                                             if (shard.state() == IndexShardState.CLOSED) { // check if the shard got closed on us
                                                 throw new IndexShardClosedException(shard.shardId());
                                             }
                                             int toRead = readCount + BUFFER_SIZE > len ? (int) (len - readCount) : BUFFER_SIZE;
                                             long position = indexInput.getFilePointer();
                                             indexInput.readBytes(buf, 0, toRead, false);
                                             transportService.submitRequest(request.targetNode(), RecoveryTarget.Actions.FILE_CHUNK, new RecoveryFileChunkRequest(request.shardId(), name, position, len, buf, toRead), VoidTransportResponseHandler.INSTANCE).txGet();
                                             readCount += toRead;
                                         }
                                         indexInput.close();
                                     } catch (Exception e) {
                                         lastException.set(e);
                                     } finally {
                                         recoveryThrottler.streamDone(request.shardId(), name);
                                         if (indexInput != null) {
                                             try {
                                                 indexInput.close();
                                             } catch (IOException e) {
                                                 // ignore
                                             }
                                         }
                                         latch.countDown();
                                     }
                                 }
                             });
                         }
 
                         latch.await();
 
                         if (lastException.get() != null) {
                             throw lastException.get();
                         }
 
                         // now, set the clean files request
                         Set<String> snapshotFiles = Sets.newHashSet(snapshot.getFiles());
                         transportService.submitRequest(request.targetNode(), RecoveryTarget.Actions.CLEAN_FILES, new RecoveryCleanFilesRequest(shard.shardId(), snapshotFiles), VoidTransportResponseHandler.INSTANCE).txGet();
 
                         stopWatch.stop();
                         logger.trace("[{}][{}] recovery [phase1] to {}: took [{}], throttling_wait [{}]", request.shardId().index().name(), request.shardId().id(), request.targetNode(), stopWatch.totalTime(), timeValueMillis(throttlingWaitTime.get()));
                         response.phase1Time = stopWatch.totalTime().millis();
                     } catch (Throwable e) {
                         throw new RecoverFilesRecoveryException(request.shardId(), response.phase1FileNames.size(), new ByteSizeValue(totalSize), e);
                     }
                 }
 
                 @Override public void phase2(Translog.Snapshot snapshot) throws ElasticSearchException {
                     if (shard.state() == IndexShardState.CLOSED) {
                         throw new IndexShardClosedException(request.shardId());
                     }
                     logger.trace("[{}][{}] recovery [phase2] to {}: sending transaction log operations", request.shardId().index().name(), request.shardId().id(), request.targetNode());
                     StopWatch stopWatch = new StopWatch().start();
 
                     transportService.submitRequest(request.targetNode(), RecoveryTarget.Actions.PREPARE_TRANSLOG, new RecoveryPrepareForTranslogOperationsRequest(request.shardId()), VoidTransportResponseHandler.INSTANCE).txGet();
 
                     int totalOperations = sendSnapshot(snapshot);
 
                     stopWatch.stop();
                     logger.trace("[{}][{}] recovery [phase2] to {}: took [{}]", request.shardId().index().name(), request.shardId().id(), request.targetNode(), stopWatch.totalTime());
                     response.phase2Time = stopWatch.totalTime().millis();
                     response.phase2Operations = totalOperations;
                 }
 
                 @Override public void phase3(Translog.Snapshot snapshot) throws ElasticSearchException {
                     if (shard.state() == IndexShardState.CLOSED) {
                         throw new IndexShardClosedException(request.shardId());
                     }
                     logger.trace("[{}][{}] recovery [phase3] to {}: sending transaction log operations", request.shardId().index().name(), request.shardId().id(), request.targetNode());
                     StopWatch stopWatch = new StopWatch().start();
                     int totalOperations = sendSnapshot(snapshot);
                     transportService.submitRequest(request.targetNode(), RecoveryTarget.Actions.FINALIZE, new RecoveryFinalizeRecoveryRequest(request.shardId()), VoidTransportResponseHandler.INSTANCE).txGet();
                     if (request.markAsRelocated()) {
                         // TODO what happens if the recovery process fails afterwards, we need to mark this back to started
                         try {
                             shard.relocated();
                         } catch (IllegalIndexShardStateException e) {
                             // we can ignore this exception since, on the other node, when it moved to phase3
                             // it will also send shard started, which might cause the index shard we work against
                             // to move be closed by the time we get to the the relocated method
                         }
                     }
                     stopWatch.stop();
                     logger.trace("[{}][{}] recovery [phase3] to {}: took [{}]", request.shardId().index().name(), request.shardId().id(), request.targetNode(), stopWatch.totalTime());
                     response.phase3Time = stopWatch.totalTime().millis();
                     response.phase3Operations = totalOperations;
                 }
 
                 private int sendSnapshot(Translog.Snapshot snapshot) throws ElasticSearchException {
                     int translogBatchSize = 10; // TODO make this configurable
                     int counter = 0;
                     int totalOperations = 0;
                     List<Translog.Operation> operations = Lists.newArrayList();
                     while (snapshot.hasNext()) {
                         if (shard.state() == IndexShardState.CLOSED) {
                             throw new IndexShardClosedException(request.shardId());
                         }
                         operations.add(snapshot.next());
                         totalOperations++;
                         if (++counter == translogBatchSize) {
                             RecoveryTranslogOperationsRequest translogOperationsRequest = new RecoveryTranslogOperationsRequest(request.shardId(), operations);
                             transportService.submitRequest(request.targetNode(), RecoveryTarget.Actions.TRANSLOG_OPS, translogOperationsRequest, VoidTransportResponseHandler.INSTANCE).txGet();
                             counter = 0;
                             operations = Lists.newArrayList();
                         }
                     }
                     // send the leftover
                     if (!operations.isEmpty()) {
                         RecoveryTranslogOperationsRequest translogOperationsRequest = new RecoveryTranslogOperationsRequest(request.shardId(), operations);
                         transportService.submitRequest(request.targetNode(), RecoveryTarget.Actions.TRANSLOG_OPS, translogOperationsRequest, VoidTransportResponseHandler.INSTANCE).txGet();
                     }
                     return totalOperations;
                 }
             });
             return response;
         } finally {
             recoveryThrottler.recoveryDone(request.shardId(), "peer recovery source");
         }
     }
 
     class StartRecoveryTransportRequestHandler extends BaseTransportRequestHandler<StartRecoveryRequest> {
 
         @Override public StartRecoveryRequest newInstance() {
             return new StartRecoveryRequest();
         }
 
         @Override public void messageReceived(final StartRecoveryRequest request, final TransportChannel channel) throws Exception {
             // we don't spawn, but we execute the expensive recovery process on a cached thread pool
             threadPool.cached().execute(new Runnable() {
                 @Override public void run() {
                     try {
                         RecoveryResponse response = recover(request);
                         channel.sendResponse(response);
                     } catch (Exception e) {
                         try {
                             channel.sendResponse(e);
-                        } catch (IOException e1) {
                             // ignore
                         }
                     }
                 }
             });
         }
 
         @Override public boolean spawn() {
             return false;
         }
     }
 }
 
