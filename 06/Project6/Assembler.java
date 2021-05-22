import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
  
    private static int lineCounter=0;
    private static int nextRam=16;
    
    public static void main(String[] args) {
        
        String name = args[0];    //copies name of existing file without the file type

        String outFileName = name.replace("asm", "hack");  //out file name
        System.out.println("output file");
        System.out.println(outFileName);

        SymbolTable table = new SymbolTable(); //init's symbol table

        Coder coder = new Coder();  //init's code tables

        Parser parser = new Parser(args[0]);  //new parser object

        File out = new File(outFileName);  //output, .hack file

        FileWriter fw = null;
        try {
            fw = new FileWriter(out.getAbsoluteFile());
            } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw); // Ready to write on file

        //first pass
        while(parser.hasMoreCommands()) {
        if(parser.commandType()== "l") {

            table.addEntry(parser.symbol(), Integer.toString(lineCounter)) ; //adds new symbol to symbol table
        }
        else lineCounter++; //next line

            parser.advance();  // next command

        }
        parser.counter =0;   // resets counter for starts from first line
  
        //second pass
        while(parser.hasMoreCommands()){
            if(parser.commandType()== "a") {
                String tmp  = parser.symbol(); //returns xxx
                int xxx;
                    if(isNumeric(tmp))  //checks if xxx is number
                    {
                        xxx = Integer.parseInt(tmp);
                    }
                    else {//there is a symble, so it's a l command
                        if (!table.contains(tmp)) {
                            table.addEntry(tmp, Integer.toString(nextRam));
                            nextRam ++;
                        }
                        String addr = table.GetAddress(tmp);
                        xxx = Integer.parseInt(addr);
                        
                    }
                tmp = pad(xxx);
                try {
                    bw.write(tmp + '\n');//write to hack
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(parser.commandType()== "c") {
                String dest_str = coder.dest(parser.dest());
                String comp_str = coder.comp(parser.comp());
                String jump_str = coder.jump(parser.jump());
                try {
                    bw.write("111" + comp_str+ dest_str +jump_str + '\n');//write to hack
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
            parser.advance();
            }
        
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

