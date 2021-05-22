import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
  public int counter;
  private String FileStr;
  public  String FileStrArr[];  //string array

  
  public Parser(String InputFile) {

    FileInputStream fis = null;

    try {
      fis = new FileInputStream(InputFile);
      int content;
      while ((content = fis.read()) != -1) {
          // convert to char and display it
          FileStr += (char) content;
      }

      System.out.println("After reading file");
      //System.out.println(FileStr);

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
    if (currentLine.startsWith("(") ){
        return "l";
    }
    else
        if (currentLine.startsWith("@")) {
        return "a";
    }
    else {
    return "c";
    }
}
  
  public String symbol() {
      String currentLine = FileStrArr[counter];
      String retLable = "";
          if(currentLine.startsWith("@")) {
              retLable = currentLine;
              retLable = retLable.replaceAll("@", "");
          }
          else
              if(currentLine.startsWith("(")) {
                  retLable = currentLine;
                  retLable = retLable.replaceAll("\\((.*?)\\)", "$1");
              }
          return retLable;
  }
   
   public String dest() {
    String currentLine = FileStrArr[counter];
    String[] parts = currentLine.split("=");
    if (parts.length == 1){
       return "null";
    }
    else {
        return parts[0];
    }
  }
  
  public String comp() {
    String currentLine = FileStrArr[counter];
    //System.out.println("current line");
    //System.out.println(currentLine);
    String[] parts = currentLine.split(";");
    parts = parts[0].split("=");
    //System.out.println(parts[0]);
    if (parts.length == 2) {
        return parts[1];
    }
    return parts[0];
    }

  
  public String jump() {
    String currentLine = FileStrArr[counter];
    String[] parts = currentLine.split(";");
    if (parts.length == 1){
     return "null";
    }
    else {
      return parts[1];
      }
  }

    //removes comments from source file
    public String removeComments(String file) {
        String tmpFile =  file.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/|(?m)^[ \t]*\r?\n|null|\t", "" );
        tmpFile = tmpFile.replaceAll("(?m)^[ \t]*\r?\n", "");
        return tmpFile;
    }
    
}

