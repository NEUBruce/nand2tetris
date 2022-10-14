import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Integer> variableTable;
    private Map<String, String> compTable;
    private Map<String, String> destTable;
    private Map<String, String> jumpTable;

    public SymbolTable() {
        compTable = new HashMap<String, String>();
        destTable = new HashMap<String, String>();
        jumpTable = new HashMap<String, String>();
        variableTable = new HashMap<String, Integer>();
        init();
    }

    private void init() {
        //init variable table:
        variableTable.put("SP", 0);
        variableTable.put("LCL", 1);
        variableTable.put("ARG", 2);
        variableTable.put("THIS", 3);
        variableTable.put("THAT", 4);
        variableTable.put("SCREEN", 16384);
        variableTable.put("KBD", 24576);
        for (int i = 0; i <= 15; i++) {
            variableTable.put("R" + i, i);
        }

        //init comp table
        compTable.put("0", "0101010");
        compTable.put("1", "0111111");
        compTable.put("-1", "0111010");
        compTable.put("D", "0001100");
        compTable.put("A", "0110000");
        compTable.put("!D", "0001101");
        compTable.put("!A", "0110001");
        compTable.put("-D", "0001111");
        compTable.put("-A", "0110011");
        compTable.put("D+1", "0011111");
        compTable.put("A+1", "0110111");
        compTable.put("D-1", "0001110");
        compTable.put("A-1", "0110010");
        compTable.put("D+A", "0000010");
        compTable.put("D-A", "0010011");
        compTable.put("A-D", "0000111");
        compTable.put("D&A", "0000000");
        compTable.put("D|A", "0010101");
        compTable.put("M", "1110000");
        compTable.put("!M", "1110001");
        compTable.put("-M", "1110011");
        compTable.put("M+1", "1110111");
        compTable.put("M-1", "1110010");
        compTable.put("D+M", "1000010");
        compTable.put("D-M", "1010011");
        compTable.put("M-D", "1000111");
        compTable.put("D&M", "1000000");
        compTable.put("D|M", "1010101");

        //init jump table
        jumpTable.put("null", "000");
        jumpTable.put("JGT", "001");
        jumpTable.put("JEQ", "010");
        jumpTable.put("JGE", "011");
        jumpTable.put("JLT", "100");
        jumpTable.put("JNE", "101");
        jumpTable.put("JLE", "110");
        jumpTable.put("JMP", "111");

        //init dest table
        destTable.put("null", "000");
        destTable.put("M", "001");
        destTable.put("D", "010");
        destTable.put("MD", "011");
        destTable.put("A", "100");
        destTable.put("AM", "101");
        destTable.put("AD", "110");
        destTable.put("AMD", "111");

    }

    public Integer getVariable(String label) {
        return (Integer) variableTable.get(label);
    }

    public boolean keyCheck(String label) {
        return variableTable.containsKey(label);
    }

    private void addLabel(String label, Integer number) {
        variableTable.put(label, number);
    }

    public String getDestCode(String dest) {
        return (String) destTable.get(dest);
    }

    public String getCompCode(String comp) {
        return (String) compTable.get(comp);
    }

    public String getJumpCode(String jump) {
        return (String) jumpTable.get(jump);
    }

    public void putVariable(String key, Integer value) {
        variableTable.put(key, value);
    }

}
