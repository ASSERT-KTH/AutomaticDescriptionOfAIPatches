--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/8533.java	2018-10-25 14:41:40.381833002 +0200
@@ -1,35 +1,35 @@
 package com.github.mobile.android.issue;
 
 import android.os.Bundle;
 import android.support.v4.view.ViewPager;
 
 import com.github.mobile.android.R.id;
 import com.github.mobile.android.R.layout;
 import com.github.mobile.android.R.string;
 import com.viewpagerindicator.TitlePageIndicator;
 
 import roboguice.activity.RoboFragmentActivity;
 import roboguice.inject.InjectView;
 
 /**
  * Dashboard activity for issues
  */
 public class IssueDashboardActivity extends RoboFragmentActivity {
 
     @InjectView(id.tpi_header)
     private TitlePageIndicator indicator;
 
     @InjectView(id.vp_pages)
     private ViewPager pager;
 
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(layout.issue_dashboard);
 
-        setTitle(getString(string.dashboard_issues_title));
 
         pager.setAdapter(new IssueDashboardPagerAdapter(getApplicationContext(), getSupportFragmentManager()));
         indicator.setViewPager(pager);
     }
 }
