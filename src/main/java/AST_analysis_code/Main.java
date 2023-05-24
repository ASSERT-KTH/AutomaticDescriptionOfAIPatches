package AST_analysis_code;

public class Main
{
    public static void main( String[] args ){

      String pathBefore=""; // Specify path to file before change
      String pathAfter=""; // Specify path to file after change
      String pathDesc=""; // Specify path for file containing description

      DiffFinder df = new DiffFinder(pathBefore, pathAfter);

      if(df.diff.toString().equals("no AST change")){ // If we have an empty diff, there is no change between the ASTs
        System.out.println("No AST change between the files.");
        new CreateFile("No AST change between the files.", pathDesc);
      }
      else{
        DescriptionGenerator dg = new DescriptionGenerator(df);
        // Generates a description and prints it to a file
        df.identifyChanges();
        dg.addSummary();
        dg.addLineNumber();
        dg.sb.append("  \n");
        dg.addPathToChangedNode();
        dg.sb.append("  \n");
        dg.sb.append("The following changes have been made:");
        dg.sb.append("  \n");
        dg.addChangeDescription();
        new CreateFile(dg.sb.toString(), pathDesc);
    }
  }
}


