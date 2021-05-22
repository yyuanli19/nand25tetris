import java.util.Hashtable;

public class Coder {
  
    private Hashtable<String, String> comp_dic;
    private Hashtable<String, String> dest_dic;
    private Hashtable<String, String> jump_dic;
    
    public Coder() {
        
        comp_dic = new Hashtable<String, String>();
        comp_dic.put("0", "0101010");
        comp_dic.put("1", "0111111");
        comp_dic.put("-1", "0111010");
        comp_dic.put("D", "0001100");
        comp_dic.put("A", "0110000");
        comp_dic.put("!D", "0001101");
        comp_dic.put("!A", "0110001");
        comp_dic.put("-D", "0001111");
        comp_dic.put("-A", "0110011");
        comp_dic.put("D+1", "0011111");
        comp_dic.put("A+1", "0110111");
        comp_dic.put("D-1", "0001110");
        comp_dic.put("A-1", "0110010");
        comp_dic.put("D+A", "0000010");
        comp_dic.put("D-A", "0010011");
        comp_dic.put("A-D", "0000111");
        comp_dic.put("D&A", "0000000");
        comp_dic.put("D|A", "0010101");
        comp_dic.put("M", "1110000");
        comp_dic.put("!M", "1110001");
        comp_dic.put("-M", "1110011");
        comp_dic.put("M+1", "1110111");
        comp_dic.put("M-1", "1110010");
        comp_dic.put("D+M", "1000010");
        comp_dic.put("D-M", "1010011");
        comp_dic.put("M-D", "1000111");
        comp_dic.put("D&M", "1000000");
        comp_dic.put("D|M", "1010101");
        
        dest_dic = new Hashtable<String, String>();
        dest_dic.put("null", "000");
        dest_dic.put("M","001");
        dest_dic.put("D","010");
        dest_dic.put("MD","011");
        dest_dic.put("A","100");
        dest_dic.put("AM","101");
        dest_dic.put("AD","110");
        dest_dic.put("AMD","111");
        
        jump_dic = new Hashtable<String, String>();
        jump_dic.put("null", "000");
        jump_dic.put("JGT", "001");
        jump_dic.put("JEQ", "010");
        jump_dic.put("JGE", "011");
        jump_dic.put("JLT","100");
        jump_dic.put("JNE","101");
        jump_dic.put("JLE","110");
        jump_dic.put("JMP", "111");

    }
    
    
   public String dest(String dest_mne) {
    return dest_dic.get(dest_mne);
  }
  
    public String comp(String comp_mne) {
    return comp_dic.get(comp_mne);
  }
  
    public String jump(String jump_mne) {
    return jump_dic.get(jump_mne);
  }
}

