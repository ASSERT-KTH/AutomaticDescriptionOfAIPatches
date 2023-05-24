This difference file shows changes made to a class named MerlinActivity. The onDestroy() method has been removed, and its contents have been moved to the onPause() method.

Previously, the onDestroy() method was setting the merlin variable to null and calling the superclass method. Now, the merlin.unbind() method is called in onPause() instead.
