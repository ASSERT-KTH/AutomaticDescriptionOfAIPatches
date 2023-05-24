package AST_analysis_code;

import java.lang.*;
import java.util.*;

public class DescriptionGenerator {
    StringBuilder sb = new StringBuilder();
    DiffFinder diffFinder;
    LinkedList<String[]> path;

    public DescriptionGenerator(DiffFinder diffFinder){
        // Gets changes found by the DiffFinder stage
        this.diffFinder = diffFinder;
        path = diffFinder.pathFromChangedNodeToRoot();
    }

    public void addLineNumber(){
        // Gets line number of first change
        String lineNumber = diffFinder.getLineNumber();

        sb.append(", in the section of code starting on line nr ");
        sb.append(lineNumber);
        sb.append(".\n");
    }

    public void addPathToChangedNode(){
        // Writes the path to the changed node

        sb.append("The change is");

        for(String[] nodeInfo:path){

            if(nodeInfo.length<1){
                System.out.println(nodeInfo);
                continue;
            }
            else{

            if(nodeInfo[0].equals("Class")||nodeInfo[0].equals("Interface")||nodeInfo[0].equals("Method")||nodeInfo[0].equals("Invocation")||nodeInfo[0].equals("ConstructorCall")||nodeInfo[0].equals("Annotation")){
                addPathText(nodeInfo,"in the","",true);
            }
            else if(nodeInfo[0].equals("Constructor")){
                addPathText(nodeInfo,"in the","",false);
            }

            else if(nodeInfo[0].equals("Literal")||nodeInfo[0].equals("LocalVariable")){
                addPathText(nodeInfo,"in a","",false);
            }

            else if(nodeInfo[0].equals("ArrayWrite")||nodeInfo[0].equals("Assignment")||nodeInfo[0].equals("FieldWrite")||nodeInfo[0].equals("VariableWrite")||nodeInfo[0].equals("OperatorAssignment")){
                sb.append(" in an assignment,"); 

            }

            else if(nodeInfo[0].equals("While")||nodeInfo[0].equals("For")||nodeInfo[0].equals("ForEach")||nodeInfo[0].equals("Do")||nodeInfo[0].equals("Conditional")){
                nodeInfo[0] = splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","statement",false);

            }

            else if(nodeInfo[0].equals("If")||nodeInfo[0].equals("else")){
                addPathText(nodeInfo,"in an","clause",false);

            }

            else if(nodeInfo[0].equals("then")){
                addPathText(nodeInfo,"in a","clause",false);

            }

            else if(nodeInfo[0].equals("Break")||nodeInfo[0].equals("Continue")){
                addPathText(nodeInfo,"in a","statement",false);

            }

            else if(nodeInfo[0].equals("TryWithResource")||nodeInfo[0].equals("Try")||nodeInfo[0].equals("Catch")){
                // Change TryWithResoource to just try
                nodeInfo[0] = splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","statement",false);

            }

            else if(nodeInfo[0].equals("Throw")){
                addPathText(nodeInfo,"in the","statement",true);

            }

            else if(nodeInfo[0].equals("Lambda")){
                addPathText(nodeInfo,"in a","expression",false);

            }

            else if(nodeInfo[0].equals("Return")){
                addPathText(nodeInfo,"in a","statement",false);

            }

            else if(nodeInfo[0].equals("Field")||nodeInfo[0].equals("NewArray")||nodeInfo[0].equals("NewClass")){
                sb.append(" in an initialization,"); 

            }

            else if(nodeInfo[0].equals("ArrayRead")||nodeInfo[0].equals("FieldRead")||nodeInfo[0].equals("VariableRead")){
                sb.append(" in retriving a value,"); 

            }

            else if(nodeInfo[0].equals("TextBlock")||nodeInfo[0].equals("Block")){
                nodeInfo[0] = splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","",false);

            }

            else if(nodeInfo[0].equals("Assert")){
                addPathText(nodeInfo,"in an","statement",false);

            }

            else if(nodeInfo[0].equals("Synchronized")){
                addPathText(nodeInfo,"in a","method",false);

            }

            else if(nodeInfo[0].equals("Switch")||nodeInfo[0].equals("Case")||nodeInfo[0].equals("SwitchExpression")){
                nodeInfo[0] = splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","",false);

            }

            else if(nodeInfo[0].equals("BinaryOperator")||nodeInfo[0].equals("UnaryOperator")){
                nodeInfo[0]=splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","",false);

            }

            else if(nodeInfo[0].equals("ExecutableReferenceExpression")||nodeInfo[0].equals("JavaDoc")){
                nodeInfo[0]=splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","",false);

            }

            else if(nodeInfo[0].equals("SuperAccess")||nodeInfo[0].equals("ThisAccess")||nodeInfo[0].equals("TypeAccess")||nodeInfo[0].equals("TypePattern")){
                nodeInfo[0]=splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","",false);

            }

            else if(nodeInfo[0].equals("YieldStatement")||nodeInfo[0].equals("Record")){
                nodeInfo[0]=splitName(nodeInfo[0]);
                addPathText(nodeInfo,"in a","",false);

            }

            else{
               System.out.println(nodeInfo[0]);

            }

        }

        }

        // Deletes the last comma from the string builder, if there is one
        if(sb.charAt(sb.length()-1) == ','){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(".\n");


    }


    public void addSummary(){
        // Finds the most local method or statement to describe the general location of the change
        LinkedList<String[]> reversePath = diffFinder.reverseList(path);

        for(int i = 0; i<reversePath.size();i++){

             if(reversePath.get(i).length<1){
                 System.out.println(reversePath.get(i));
                 continue;
             }
             else{
                if(reversePath.get(i)[0].equals("For")||reversePath.get(i)[0].equals("ForEach")){
                    sb.append("There has been a change in a for loop");
                    break;
                }
                else if(reversePath.get(i)[0].equals("If")){
                    if(i>0 && reversePath.get(i-1)[0].equals("then")){
                        sb.append("There has been a change in the body of an if statement");
                    }
                    else{
                        sb.append("There has been a change in the condition of an if statement");
                    }
                    break;
                }
                else if(reversePath.get(i)[0].equals("else")){
                    sb.append("There has been a change in an else statement");
                    break;
                }
                else if(reversePath.get(i)[0].equals("While")||reversePath.get(i)[0].equals("Do")){
                    sb.append("There has been a change in a while loop");
                    break;
                }
                else if(reversePath.get(i)[0].equals("Method")){
                    sb.append("There has been a change in the method ");
                    sb.append(reversePath.get(i)[1]);
                    break;

                }
                else if(reversePath.get(i)[0].equals("Constructor")){
                    sb.append("There has been a change in the constructor");
                    break;
                }
                else if(reversePath.get(i)[0].equals("Class")){
                    sb.append("There has been a change in the class ");
                    sb.append(reversePath.get(i)[1]);
                    break;
                }

             }

        }

    }

    public String splitName(String name){
        // Splits names of nodes on capital letter
        String[] splitName = name.split("(?=\\p{Lu})");
        String temp="";
        for(String word:splitName){
            temp += word;
            temp += " ";
        }
        return temp.trim();
    }

    public void addPathText(String[] nodeInfo,String textBefore,String textAfter,boolean name){
        // Method to write the information about the nodes in the path
        if(textBefore.length()>0){
            sb.append(" ");
            sb.append(textBefore);
            sb.append(" ");
        }

        sb.append(nodeInfo[0].toLowerCase());

        if(name){
            sb.append(" ");
            sb.append("```");
            sb.append(nodeInfo[1]);
            sb.append("```");
        }

        if(textAfter.length()>0){
            sb.append(" ");
            sb.append(textAfter);
        }
        sb.append(",");

    }

    public void addChange(Changes change, String suffix){
        // Lists the changes made
        sb.append(change.operation);
        sb.append(suffix);
        sb.append(" ");
        sb.append(splitName(change.type).toLowerCase());
        sb.append(" ");

        lengthCheck(change);

        if (change.nameNew!=null){
            sb.append("from ");
            sb.append(change.name);
            sb.append("```");
            sb.append(" to ");
            sb.append(change.nameNew);
            sb.append("```");
        }
        else{
            sb.append(change.name);
            sb.append("```");

        }
        sb.append(" ");

        if (change.lineNrTo!=null){
          sb.append("from line ");
          sb.append(change.lineNr);
          sb.append(" to line ");
          sb.append(change.lineNrTo);
        }
        else if (change.lineNr != null){
          sb.append("on line ");
          sb.append(change.lineNr);
        }
        sb.append(".  \n");
    }


    private void lengthCheck(Changes change){
        // Checks if code snippet is longer than one line, if that is the case, only part of the code snippet is used
        for(int i = 0;i<2;i++){
            String name = change.name;

            if (i==1 && change.nameNew != null){
                name=change.nameNew;
            }
            else if(i==1 && change.nameNew == null){
                break;
            }
            if(name.contains("\n")){
                String firstLine = name.split("\n")[0];
                String tag = "";
                if(firstLine.charAt(0)=='@'){
                    tag += "``` with the tag ```@";
                    String[] tempTag = firstLine.split("\\.");
                    tag += tempTag[tempTag.length-1];
                    firstLine = name.split("\n")[1];
                }

                String[] check = firstLine.split(" ");
                StringBuilder tempSb = new StringBuilder();
                for (String c:check){
                    if (c.startsWith("org.") || c.startsWith("io.")|| c.startsWith("com.")|| c.startsWith("@org.")|| c.startsWith("@com")){
                        String[] noPackage = (c.split("\\(")[0]).split("\\.");
                        tempSb.append(noPackage[noPackage.length-1]);
                    }
                    else{
                        tempSb.append(c);
                    }
                    tempSb.append(" ");
                }
                firstLine = tempSb.toString().trim();

                if(change.type.equals("Method")||change.type.equals("Class")||change.type.equals("Constructor")){
                    if (firstLine.contains("{")){
                        firstLine = firstLine.substring(0,firstLine.indexOf("{"));
                    }
                    if (i==0){
                        change.name = "```" + firstLine.trim()+tag;
                    }
                    else{
                        change.nameNew = "```" + firstLine.trim()+tag;
                    }
                }
                else if(change.type.equals("If")||change.type.equals("While")||change.type.equals("For")){
                    String condition =  firstLine.substring(firstLine.indexOf("(")+1,firstLine.lastIndexOf(")"));
                    if (i==0){
                        change.name = "statement with condition ```" + condition+tag;
                    }
                    else{
                        change.nameNew = "statement with condition ```" + condition+tag;
                    }
                }
                else if(change.type.equals("Do")){
                    String[] lines = change.name.split("\n");
                    String lastLine = lines[lines.length-1];
                    String condition =  lastLine.substring(lastLine.indexOf("(")+1,lastLine.lastIndexOf(")"));

                    if (i==0){
                        change.name = "statement with while condition ```" + condition+tag;
                    }
                    else{
                        change.nameNew = "statement with while condition ```" + condition+tag;
                    }
                }
                else{
                    if(i==0){
                        change.name = "starting with ```" + firstLine.trim()+tag;
                    }
                    else{
                        change.nameNew = "starting with ```" + firstLine.trim()+tag;
                    }
                }
            }
            else{
                if(i==0){
                    String[] temp = (change.name.split("\\(")[0]).split("\\.");
                    if (temp[0].equals("io")||temp[0].equals("org")||temp[0].equals("com")||temp[0].equals("@org")||temp[0].equals("@com")){
                        change.name = temp[temp.length-1];
                    }
                    change.name = "```" + change.name;

                }
                else{
                    String[] temp = (change.nameNew.split("\\(")[0]).split("\\.");
                    if (temp[0].equals("java")||temp[0].equals("io")||temp[0].equals("org")||temp[0].equals("com")||temp[0].equals("@org")||temp[0].equals("@com")){
                        change.nameNew = temp[temp.length-1];
                    }
                    change.nameNew = "```" + change.nameNew;
                }
            }
        }
    }

    public void addChangeDescription(){
        // Loops through the changes and writes them
        Changes[] changes = diffFinder.insertDeleteCheck();

        for (Changes change:changes){

            if (change != null){


                if(change.operation.equals("Insert")){
                    addChange(change,"ed");
                }
                else if (change.operation.equals("Delete")||change.operation.equals("Move")){
                    change.nameNew= null;
                    addChange(change,"d");
                }
                else if (change.operation.equals("Update")){
                    change.operation= "Change";
                    addChange(change,"d");
                }
            }
        }
    }








}
