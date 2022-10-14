import java.util.HashMap;
import java.util.Map;

public class Code {
    private SymbolTable symbolTable;
    public Code() {
        symbolTable = new SymbolTable();
    }

    public String codeTranslate(String assemblyCode) {
        assemblyCode = firstPass(assemblyCode);
        assemblyCode = secondPass(assemblyCode);

        String[] allCommands = assemblyCode.split("\n");
        StringBuffer machineCode = new StringBuffer();

        for (int i = 0; i < allCommands.length; i ++) {
            if (allCommands[i].startsWith("@")) {
                machineCode.append(AInstructionTranslate(allCommands[i]));
            }else {
                machineCode.append(CInstructionTranslate(allCommands[i]));
            }
            if (i != allCommands.length - 1) {
                machineCode.append("\n");
            }
        }
        return machineCode.toString();
    }

    private String secondPass(String assemblyCode) {
        StringBuffer newCode = new StringBuffer();
        String[] allCommands = assemblyCode.split("\n");
        int k = 16;

        for (int i = 0; i < allCommands.length; i ++) {
            String command = allCommands[i];
            if (command.startsWith("@")) {
                String variable = command.split("@")[1];
                if (isNumeric(variable)) {
                    newCode.append(command + "\n");
                }else {
                    if (symbolTable.keyCheck(variable)) {
                        newCode.append("@" + symbolTable.getVariable(variable) + "\n");
                    }else {
                        symbolTable.putVariable(variable, k);
                        newCode.append("@" + k + "\n");
                        k++;
                    }
                }

            }else {
                newCode.append(command + "\n");
            }
        }


        return newCode.toString();
    }

    private String firstPass(String assemblyCode) {
        StringBuffer newCode = new StringBuffer();
        String[] allCommands = assemblyCode.split("\n");
        int cnt = 0;

        for (int i = 0; i < allCommands.length; i ++) {
            if (allCommands[i].startsWith("(")) {
                String[] tmpList = allCommands[i].split("\\(");
                tmpList = tmpList[1].split("\\)");
                String label = tmpList[0];
                symbolTable.putVariable(label, cnt);
            }else {
                newCode.append(allCommands[i] + "\n");
                cnt ++;
            }
        }
        return newCode.toString();

    }

    private String CInstructionTranslate(String assemblyCode) {
        String dest = "000";
        String comp = "";
        String jump = "000";

        // check if the command has a dest part:
        String[] tmpList = assemblyCode.split("=");
        if (tmpList.length > 1) {
            dest = symbolTable.getDestCode(tmpList[0]);
            tmpList = tmpList[1].split(";");
        }else {
            tmpList = tmpList[0].split(";");
        }

        //check if the command has a jump part :
        if (tmpList.length > 1) {
            jump = symbolTable.getJumpCode(tmpList[1]);
        }

        //translate comp part:
        comp = symbolTable.getCompCode(tmpList[0]);

       return "111" + comp + dest + jump;
    }

    public String AInstructionTranslate(String assemblyCode) {
        assemblyCode = assemblyCode.split("@")[1];
        if (isNumeric(assemblyCode)) {
            return "0" + to15BitBinary(assemblyCode);
        }

        return null;

    }

    private String to15BitBinary(String assemblyCode) {
        Integer num = Integer.parseInt(assemblyCode);
        String binaryNum = "";
        Integer bit;
        for (int i = 1; i <= 15; i ++) {
            bit = num & 1;
            num  = num >> 1;
            binaryNum = bit + binaryNum;
        }

        return binaryNum;
    }

    public static boolean isNumeric(String str){
        for (int i = str.length() - 1; i>=0; i--){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
