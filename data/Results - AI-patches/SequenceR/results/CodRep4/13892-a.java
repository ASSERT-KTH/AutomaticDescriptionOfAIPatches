+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/13892.java	2018-10-25 14:41:16.709354911 +0200
@@ -1,112 +1,112 @@
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
 
 package org.elasticsearch.index.query;
 
 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.Iterables;
 import org.apache.lucene.search.ConstantScoreQuery;
 import org.apache.lucene.search.Query;
 import org.elasticsearch.common.inject.Inject;
 import org.elasticsearch.common.xcontent.XContentParser;
 import org.elasticsearch.index.search.UidFilter;
 
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.List;
 
 /**
  *
  */
 public class IdsQueryParser implements QueryParser {
 
     public static final String NAME = "ids";
 
     @Inject
     public IdsQueryParser() {
     }
 
     @Override
     public String[] names() {
         return new String[]{NAME};
     }
 
     @Override
     public Query parse(QueryParseContext parseContext) throws IOException, QueryParsingException {
         XContentParser parser = parseContext.parser();
 
         List<String> ids = new ArrayList<String>();
         Collection<String> types = null;
         String currentFieldName = null;
         float boost = 1.0f;
         XContentParser.Token token;
         while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
             if (token == XContentParser.Token.FIELD_NAME) {
                 currentFieldName = parser.currentName();
             } else if (token == XContentParser.Token.START_ARRAY) {
                 if ("values".equals(currentFieldName)) {
                     while ((token = parser.nextToken()) != XContentParser.Token.END_ARRAY) {
                         String value = parser.textOrNull();
                         if (value == null) {
                             throw new QueryParsingException(parseContext.index(), "No value specified for term filter");
                         }
                         ids.add(value);
                     }
                 } else if ("types".equals(currentFieldName) || "type".equals(currentFieldName)) {
                     types = new ArrayList<String>();
                     while ((token = parser.nextToken()) != XContentParser.Token.END_ARRAY) {
                         String value = parser.textOrNull();
                         if (value == null) {
                             throw new QueryParsingException(parseContext.index(), "No type specified for term filter");
                         }
                         types.add(value);
                     }
                 } else {
                     throw new QueryParsingException(parseContext.index(), "[ids] query does not support [" + currentFieldName + "]");
                 }
             } else if (token.isValue()) {
                 if ("type".equals(currentFieldName) || "_type".equals(currentFieldName)) {
                     types = ImmutableList.of(parser.text());
                 } else if ("boost".equals(currentFieldName)) {
                     boost = parser.floatValue();
                 } else {
                     throw new QueryParsingException(parseContext.index(), "[ids] query does not support [" + currentFieldName + "]");
                 }
             }
         }
 
         if (ids.size() == 0) {
             throw new QueryParsingException(parseContext.index(), "[ids] query, no ids values provided");
         }
 
         if (types == null || types.isEmpty()) {
             types = parseContext.queryTypes();
         } else if (types.size() == 1 && Iterables.getFirst(types, null).equals("_all")) {
             types = parseContext.mapperService().types();
         }
 
+        UidFilter filter = new UidFilter(types, ids);
         // no need for constant score filter, since we don't cache the filter, and it always takes deletes into account
         ConstantScoreQuery query = new ConstantScoreQuery(filter);
         query.setBoost(boost);
         return query;
     }
 }
 
