package AST_analysis_code;

import gumtree.spoon.diff.Diff;
import gumtree.spoon.AstComparator;
import spoon.reflect.cu.SourcePosition;
import gumtree.spoon.diff.operations.Operation;


import spoon.reflect.declaration.CtElement;

import java.util.*;

import javax.xml.transform.Source;

import java.io.*;



public class DiffFinder{

    Diff diff;


    public DiffFinder(String filePathBefore, String filePathAfter){
        try{
            diff = new AstComparator().compare(new File(filePathBefore), new File(filePathAfter)); // Takes in two files to compare
        }

        catch(Exception e){
            System.out.println(e);
        }
    }

    public LinkedList<String[]> pathFromChangedNodeToRoot(){
        // In: Diff
        // Returns: A linked list of the path from the root to the changed node

        // Make a linked list
        LinkedList<String[]> pathAsLinkedList = new LinkedList<String[]>();

        // Get common ancestor
        CtElement node = diff.commonAncestor();

        Set<String> kSet;

        // Going through the tree from the common ancestor to the root node
        while (node != null){
          // Get the metadata key for each node
          kSet = node.getMetadataKeys();
          String[] k = kSet.toArray(new String[kSet.size()]);
          for (String key:k){
            // Check if there is any metadata
            if(node.getMetadata(key)!= null){
              // Add the metadata to the linked list
              String[] temp = node.getMetadata(key).toString().replace("[0,0]", "").split(": ");
              for(int i=0;i<temp.length;i++){ // Deletes trailing whitespaces
                temp[i] = temp[i].trim();
              }
              pathAsLinkedList.add(temp);

            }
          }
          // Go to the parent node
          node = node.getParent();
        }

        //reverses the order of the list
        pathAsLinkedList = reverseList(pathAsLinkedList); 

        return pathAsLinkedList;
    }

    public LinkedList<String[]> reverseList(LinkedList<String[]> list){
        // In: A linked list of string arrays
        // Returns: Reverse of the list

        // Reverse the list
        String temp[];
        for(int i = 0;i<list.size()/2;i++){
            temp=list.get(i);
            list.set(i,list.get(list.size()-i-1));
            list.set(list.size()-i-1,temp);
        }
        return list;
    }

    public Changes[] identifyChanges(){
        // In: Diff
        // Return: The changes that have been made, as an array of string arrays. Each string array has the following info at each index:

        // Get operations
        List<Operation> operationsList = diff.getRootOperations(); // Gets operations in the diff as a list

        // Get action
        String[] operationInfo;
        String partsOfAction;
        String[] lineInfo;
        Changes[] changes = new Changes[operationsList.size()];

        Operation op;
        for (int i=0; i<operationsList.size(); i++){
            changes[i] = new Changes();

            // Get information about the changed node
            op = operationsList.get(i);
            changes[i].name = op.getSrcNode().toString();
            if (op.getDstNode()!= null){
                changes[i].nameNew = op.getDstNode().toString();
            }

            partsOfAction = op.toString().split("\n")[0];
            operationInfo = partsOfAction.split(" ");
            changes[i].operation = operationInfo[0];
            changes[i].type = operationInfo[1];

            lineInfo = partsOfAction.split(":");

            if (lineInfo.length==1){
                changes[i].lineNr = null; 
                changes[i].lineNrTo = null; 
            }

            else if (lineInfo.length<3){
                changes[i].lineNr = lineInfo[1].split(" ")[0];
                changes[i].lineNrTo = null; 
            }
            else{
                changes[i].lineNr = lineInfo[1].split(" ")[0];
                changes[i].lineNrTo = lineInfo[2].split(" ")[0];
            }
        }

        // Returns list of changed nodes
        return changes;
    }


    public Changes[] insertDeleteCheck(){
        // Checks if the same bit of code has been both inserted and deleted on the same line. This corresponds to no change 
        HashMap<String, Integer> collision = new HashMap<String, Integer>();

        Changes[] changes = updateCheck();

        for (int i=0; i<changes.length;i++) {
            if (changes[i].operation.equals("Insert") || changes[i].operation.equals("Delete")){
                if (collision.get(changes[i].lineNr) != null){

                    Integer lineNumber = collision.get(changes[i].lineNr);

                    Changes change = changes[lineNumber];

                    // If the same node is inserted and deleted, both are removed from the list of changes
                    if (change.type.equals(changes[i].type) && change.name.equals(changes[i].name)){
                        changes[collision.get(changes[i].lineNr)] = null;
                        changes[i] = null;
                    }
                    else{
                        collision.put(changes[i].lineNr,i);
                    }
                }
                else{
                    collision.put(changes[i].lineNr,i);
                }
            }
        }
        return changes;
    }

    public Changes[] updateCheck(){
        // Checks if a node has been updated but nothing has changed
        Changes[] changes = identifyChanges();

        for (Changes change:changes){
            if (change.operation.equals("Update")){
                if(change.name.equals(change.nameNew)){
                    change = null;
                }
            }
        }

        return changes;
    }

    public String getLineNumber(){
        // In: Diff
        // Return: Line number as a string

        // Finds the line number of the first change

        Changes[] changes = insertDeleteCheck();

        int first=Integer.MAX_VALUE;
        if (changes[0] != null && changes[0].lineNr != null){ 
            first = Integer.parseInt(changes[0].lineNr);
        }
        
        int compValue=Integer.MAX_VALUE;
        for(Changes change:changes){
            if (change != null && change.lineNr != null){
                compValue = Integer.parseInt(change.lineNr);
            }
            if(compValue<first){
                first=compValue;
            }
        }

        if (first == Integer.MAX_VALUE){
            first = 1;
        }

        return Integer.toString(first);

    }







}
