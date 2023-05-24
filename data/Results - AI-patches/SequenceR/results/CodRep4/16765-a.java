+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/16765.java	2018-10-25 14:41:36.989551326 +0200
@@ -1,62 +1,62 @@
 /*
  * Licensed to ElasticSearch and Shay Banon under one
  * or more contributor license agreements.  See the NOTICE file
  * distributed with this work for additional information
  * regarding copyright ownership. ElasticSearch licenses this
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
 
 package org.elasticsearch.index.fielddata.fieldcomparator;
 
 import org.apache.lucene.search.FieldComparator;
 import org.apache.lucene.search.SortField;
 import org.elasticsearch.common.Nullable;
 import org.elasticsearch.index.fielddata.IndexFieldData;
 import org.elasticsearch.index.fielddata.IndexNumericFieldData;
 
 import java.io.IOException;
 
 /**
  */
 public class DoubleValuesComparatorSource extends IndexFieldData.XFieldComparatorSource {
 
     private final IndexNumericFieldData indexFieldData;
     private final Object missingValue;
 
     public DoubleValuesComparatorSource(IndexNumericFieldData indexFieldData, @Nullable Object missingValue) {
         this.indexFieldData = indexFieldData;
         this.missingValue = missingValue;
     }
 
     @Override
     public SortField.Type reducedType() {
         return SortField.Type.DOUBLE;
     }
 
     @Override
     public FieldComparator<?> newComparator(String fieldname, int numHits, int sortPos, boolean reversed) throws IOException {
         assert fieldname.equals(indexFieldData.getFieldNames().indexName());
 
         double dMissingValue;
         if (missingValue == null || "_last".equals(missingValue)) {
             dMissingValue = reversed ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
         } else if ("_first".equals(missingValue)) {
             dMissingValue = reversed ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
         } else {
             dMissingValue = missingValue instanceof Number ? ((Number) missingValue).doubleValue() : Double.parseDouble(missingValue.toString());
         }
 
+        return new DoubleValuesComparator(indexFieldData, dMissingValue, numHits, reversed);
     }
 }
