There has been a change in the class TenantEngineManager, in the section of code starting on line nr 387.
  
The change is in the class ```TenantEngineManager```.
  
The following changes have been made:  
Changed literal from ```"Tenant API not available yet. Tenant will be queued again."``` to ```"Tenant API not available yet (%s). Tenant will be queued again."``` on line 387.  
Inserted invocation ```java.lang.String.format("Tenant API not available yet (%s). Tenant will be queued again.", e.getMessage())``` on line 387.  
Moved literal ```"Tenant API not available yet. Tenant will be queued again."``` from line 387 to line 387.  
