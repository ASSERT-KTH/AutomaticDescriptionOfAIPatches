This change to the HashCalculatorFragment class replaces the getColumnIndex() method call on the cursor object with the getColumnIndexOrThrow() method call. The getColumnIndex() method returns the zero-based index for the named column, or -1 if the column does not exist. On the other hand, the getColumnIndexOrThrow() method also returns the index for the named column, but it throws an IllegalArgumentException if the column does not exist. In this case, the change is likely intended to catch any potential errors caused by passing an invalid column name to getColumnIndex().