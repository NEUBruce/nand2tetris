import java.util.List;

public class Parser {
    public Parser() {

    }

    public String parseCode(String sourceCode) {
        sourceCode = sourceCode.trim();
        StringBuffer assemblyCode = new StringBuffer();
        String[] textSplits = sourceCode.split(" ");
        for (int i = 0; i < textSplits.length; i++) {
            //if it's comment then ignore it
            if (textSplits[i].startsWith("//")) {
                break;
            }else {
                //if not, then it will be a part of command
                assemblyCode.append(textSplits[i]);
            }
        }
    if (!assemblyCode.toString().equals("")) {
        assemblyCode.append("\n");
    }
        return assemblyCode.toString();
    }
}
