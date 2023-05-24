package AST_analysis_code;

// Class for storing changes
public class Changes{
    String operation; // Type of edit operation (move, insert, delete, update)
    String type; // Type of node being changed
    String name; // Name of node 
    String nameNew; // Name of new node if changed
    String lineNr; // Line number of corresponding code line
    String lineNrTo; // Line that code has been moved to

    public Changes(){
        
    }

    // Creates an object to store the changes in
    public Changes(String operation,String type,String name,String nameNew,String lineNr,String lineNrTo){
        this.operation = operation;
        this.type=type;
        this.name=name;
        this.nameNew=nameNew;
        this.lineNr = lineNr;
        this.lineNrTo = lineNrTo;
    }


    
}