package AST_analysis_code;
import java.io.*;

// Creates a file and writes a string to the file
public class CreateFile{

  public CreateFile(String text, String filePath){
    try{
      // Creates file
      File file = new File(filePath);
      if(!file.exists()){
        file.createNewFile();
      }

      // Writes the string text 
      FileWriter writer = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(writer);
      bw.write(text);
      bw.flush();
      bw.close();
    }
    catch(IOException e){
      System.out.println("Could not create the file");
      e.printStackTrace();
    }
  }
}
