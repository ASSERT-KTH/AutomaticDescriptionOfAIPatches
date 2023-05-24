There has been a change in the condition of an if statement, in the section of code starting on line nr 106.
  
The change is in an if clause, in the method ```onListItemClick```, in the class ```NewsFragment```.
  
The following changes have been made:  
Inserted binary operator ```(issue != null) && (!com.github.mobile.core.issue.IssueUtils.isPullRequest(issue))``` on line 106.  
Moved unary operator ```!com.github.mobile.core.issue.IssueUtils.isPullRequest(issue)``` from line 106 to line 106.  
