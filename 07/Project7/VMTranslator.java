import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VMTranslator {
    
    public static void main(String[] args) {

        String name = args[0];    //copies name of existing file without the file type
        CodeWriter coder = new CodeWriter(args[0]);
        Parser parser = new Parser(args[0]);  //new parser object

        while(parser.hasMoreCommands()){
            String command = parser.commandType();
            System.out.println("command type: " + command);
            if(command.equals("C_ARITHMETIC")) {
                coder.writeArithmetic(parser.arg1());
            }
            else {
                System.out.println("seg: " + parser.arg1() + parser.arg2());
                coder.writePushPop(command, parser.arg1(), parser.arg2());
            }
            parser.advance();
            }
        coder.close();
        }

    public static boolean isNumeric(String num) {
        try {
            Double.parseDouble(num);
            return true;
          } catch(NumberFormatException e){
            return false;
          }
    }
    
    public static String pad(int num){
       return String.format("%16s", Integer.toBinaryString(num)).replace(' ', '0');
    }
}

