--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/8531.java	2018-10-25 14:42:03.710090802 +0200
@@ -1,21 +1,21 @@
 package com.github.mobile.android.issue;
 
 import android.os.Bundle;
 
 import com.github.mobile.android.R.layout;
 import com.github.mobile.android.R.string;
 
 import roboguice.activity.RoboFragmentActivity;
 
 /**
  * Activity to browse a list of bookmarked {@link IssueFilter} items
  */
 public class FilterBrowseActivity extends RoboFragmentActivity {
 
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
-        setTitle(getString(string.saved_filters_title));
         setContentView(layout.issue_filter_list);
     }
 }
