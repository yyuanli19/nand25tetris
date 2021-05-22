import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

public class Parser {
  public int counter;
  private String FileStr;
  public  String FileStrArr[];  //string array
  private Hashtable<String, String> command_dic;
  
  public Parser(String InputFile) {
    command_dic = new Hashtable<String, String>();
    command_dic.put("add", "C_ARITHMETIC");
    command_dic.put("sub", "C_ARITHMETIC");
    command_dic.put("neg", "C_ARITHMETIC");
    command_dic.put("eq", "C_ARITHMETIC");
    command_dic.put("gt", "C_ARITHMETIC");
    command_dic.put("lt", "C_ARITHMETIC");
    command_dic.put("and", "C_ARITHMETIC");
    command_dic.put("or", "C_ARITHMETIC");
    command_dic.put("not", "C_ARITHMETIC");
    command_dic.put("pop", "C_POP");
    command_dic.put("push", "C_PUSH");
    command_dic.put("label", "C_LABEL");
    command_dic.put("goto", "C_GOTO");
    command_dic.put("if-goto", "C_IF");
    command_dic.put("function", "C_FUNCTION");
    command_dic.put("call", "C_CALL");
    command_dic.put("return", "C_RETURN");

    FileInputStream fis = null;

    try {
      fis = new FileInputStream(InputFile);
      int content;
      while ((content = fis.read()) != -1) {
          // convert to char and display it
          FileStr += (char) content;
      }

      System.out.println("After reading file");
      //System.out.println("1" + FileStr);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
          if (fis != null)
              fis.close();
      } catch (IOException ex) {
          ex.printStackTrace();
      }
    }
      FileStr =  removeComments(FileStr);
    //copies string to string array
    FileStrArr = FileStr.split("\n");
    //trim
    for(int i=0; i < FileStrArr.length; i++){
        FileStrArr[i] =  FileStrArr[i].trim();
      
    counter = 0;
  }
 
      }
  public enum commandType
    {
        // C_ARITHMETIC is returned for all the arthemetic/logical commands
        C_ARITHMETIC, C_PUSH, C_POP,
        C_LABEL, C_GOTO, C_IF,
        C_FUNCTION, C_RETURN, C_CALL
    }

  public boolean hasMoreCommands() {
    if(counter != FileStrArr.length) return true;
    return false;
  }

  public void advance() {
    if (hasMoreCommands()) {
        counter ++;
}
  }
  
  public String commandType() {
    String currentLine = FileStrArr[counter];
    String[] parts = currentLine.split("\\s+");
    //System.out.println(parts[0]);
    return command_dic.get(parts[0]);
}
   
   public String arg1() {
    String currentLine = FileStrArr[counter];
    String command = commandType();
    if (command == "C_ARITHMETIC"){
       return currentLine;
    }
    else {
        String[] parts = currentLine.split("\\s+");
        return parts[1];
}
  }
  
  public int arg2() {
    String currentLine = FileStrArr[counter];
    //System.out.println("current line");
    //System.out.println(currentLine);
    String command = commandType();
    String[] parts = currentLine.split("\\s+");
    //System.out.println(parts[0]);
    if (command=="C_PUSH" ||command=="C_POP" || command=="C_FUNCTION" || command=="C_CALL") {
        return Integer.parseInt(parts[2]);
    }
    
    else {
        System.out.println(command);
        throw new IllegalArgumentException("not a valid command");
}
    }

    //removes comments from source file
    public String removeComments(String file) {
        String tmpFile =  file.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/|(?m)^[ \t]*\r?\n|null|\t", "" );
        tmpFile = tmpFile.replaceAll("(?m)^[ \t]*\r?\n", "");
        return tmpFile;
    }
    
}


