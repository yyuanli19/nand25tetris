import java.util.Hashtable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private Hashtable<String, String> seg_dic;
    private int loop = 0;
    private BufferedWriter bw;
    private String classname;
    
    public CodeWriter(String InputFile) {
        seg_dic = new Hashtable<String, String>();
        seg_dic.put("local", "LCL");
        seg_dic.put("argument", "ARG");
        seg_dic.put("this", "THIS");
        seg_dic.put("that", "THAT");
        seg_dic.put("temp", "5");
        
        classname = InputFile.split(".", 1)[0];
        String outFileName = InputFile.replace("vm", "asm");  //out file name
        System.out.println("output file");
        System.out.println(outFileName);

        File out = new File(outFileName);  //output, .hack file
        classname = out.getName();
        int pos = classname.lastIndexOf(".");
        classname = classname.substring(0, pos);
        System.out.println(classname);

        FileWriter fw = null;
        try {
            fw = new FileWriter(out.getAbsoluteFile());
            } catch (IOException e) {
            e.printStackTrace();
        }
        bw = new BufferedWriter(fw); // Ready to write on file

    }
    
    
   public void writeArithmetic(String arith_command) {
       //first get the last value on stack
       // sp--, d = *sp
       write("@SP\nM=M-1\nA=M\nD=M");
       // arithmetic operation
       if (arith_command.equals("eq") || arith_command.equals("lt") || arith_command.equals("gt")) {
           write("A=A-1\nD=M-D\n");
           write("@FALSE"+loop);
           
           if (arith_command.equals("eq")) {
               write("D;JNE");//not ><
           }
           else if (arith_command.equals("lt")) {
               write("D;JGE");//not >=
           }
           else if (arith_command.equals("gt")) {
               write("D;JLE");//not <=
           }
           write("@SP\nM=M-1\nA=M\nM=-1\n");
           write("@CONTINUE"+loop);
           write("0;JMP\n");
           write("(FALSE"+loop+")");
           write("@SP\nM=M-1\nA=M\nM=0");
           write("(CONTINUE"+loop+")");
           write("@SP\nM=M+1");
           loop++ ;
       }
       
       else {
           if (arith_command.equals("neg")) {
               write("M=-D");
           }
           else if (arith_command.equals("not")) {
               write("M=!D");
           }
           else {
               write("@SP\nM=M-1\nA=M");
               if (arith_command.equals("add")) {
               write("M=M+D");
               }
               else if (arith_command.equals("sub")) {
                   write("M=M-D");
               }
               else if (arith_command.equals("and")) {
                   write("M=D&M");
               }
               else if (arith_command.equals("or")) {
                   write("M=M|D");
               }
           }
           // sp++
           write("@SP\nM=M+1");
       }
  }
  
    public void writePushPop(String command, String seg, int idx) {
    if (command.equals("C_PUSH")) {
        if (seg.equals("constant")){
            write("@" + Integer.toString(idx));
            write("D=A");
            write("@SP\nA=M\nM=D\n@SP\nM=M+1");
        }
        else if (seg.equals("local")||seg.equals("argument")||seg.equals("this")||seg.equals("that")){
            //addr = LCL+i, *SP = *addr, SP++
            // addr = LCL+i
            write("@" + Integer.toString(idx));
            write("D=A");
            write("@" + seg_dic.get(seg));
            write("D=D+M\n@addr\nM=D");
            // *SP = *addr
            write("@addr\nA=M\nD=M\n@SP\nA=M\nM=D");
            //SP++
            write("@SP\nM=M+1");
            
        }
        else if (seg.equals("temp")){
            write("@" + Integer.toString(idx));
            write("D=A");
            write("@" + seg_dic.get(seg));
            write("D=D+A\n@addr\nM=D");
            // *SP = *addr
            write("@addr\nA=M\nD=M\n@SP\nA=M\nM=D");
            //SP++
            write("@SP\nM=M+1");
        }
        else if (seg.equals("static")){
            write("@" + classname + Integer.toString(idx));
            write("D=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        }
        else if (seg.equals("pointer")){
            if (idx==0){
                write("@" + seg_dic.get("this"));
            }
            else{
                write("@" + seg_dic.get("that"));
            }
            write("D=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
            
        }
        
    }
    else if (command.equals("C_POP")){
        if (seg.equals("local")||seg.equals("argument")||seg.equals("this")||seg.equals("that")){
            //addr = LCL+i, *addr = *SP, SP--
            // addr = LCL+i
            write("@" + Integer.toString(idx));
            write("D=A");
            write("@" + seg_dic.get(seg));
            System.out.println("temp: " + seg_dic.get(seg));
            write("D=D+M\n@addr\nM=D");
            // SP--
            write("@SP\nM=M-1");
            //*addr = *SP
            write("@SP\nA=M\nD=M\n@addr\nA=M\nM=D");
        }
        else if (seg.equals("temp")){
            write("@" + Integer.toString(idx));
            write("D=A");
            write("@" + seg_dic.get(seg));
            System.out.println("temp: " + seg_dic.get(seg));
            write("D=D+A\n@addr\nM=D");
            // SP--
            write("@SP\nM=M-1");
            //*addr = *SP
            write("@SP\nA=M\nD=M\n@addr\nA=M\nM=D");
        }
        else if (seg.equals("static")){
            write("@" + classname + Integer.toString(idx));
            write("D=A\n@StaticAddr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@StaticAddr\nA=M\nM=D");
        }
        else if (seg.equals("pointer")){
            write("@SP\nM=M-1\n@SP\nA=M\nD=M");
            if (idx==0){
                write("@" + seg_dic.get("this"));
            }
            else{
                write("@" + seg_dic.get("that"));
            }
            write("M=D");
        }
    }
  }

    public void write(String line){
        try {
            bw.write(line + '\n');//write to hack
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    public void close() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
  }
}


