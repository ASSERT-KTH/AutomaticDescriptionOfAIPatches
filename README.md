# Automatic descriptions of AI patches using AST analysis
# Bachelor thesis by Sofia Edvardsson and Lovisa Strange
  
In the folder **src** is the main files for our thesis, including  
* **DiffFinder** that uses ASTs to find the change between two java files, and where that change has been made.   
* **DescriptionGenerator** that uses DiffFinder to write a description of the change.  
* **Main** that generates a file with the description.  
* Also contains help classes for these classes.  
  
In the folder **data** there are two subfolders  
* **open-source-projects** contains pull requests from different open source projects (file before, file after and diff-file), along with the description for the commit written by a human (i-j.txt). It also contains our descriptions of the patches (i-j-desc.md)   
* **AI-patches** that contains patches from research papers on AI-generated patches, as well as our descriptions for them.     
