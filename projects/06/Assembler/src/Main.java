import java.io.*;

public class Main {
    public static void main(String[] args) {
        BufferedWriter bw = null;
        BufferedReader br = null;
        Code code = new Code();
        Parser parser = new Parser();
        try {
            String sourceFileName = "args[0]";
            String targetFileName = sourceFileName.split("\\.")[0] + ".hack";

            File sourceFile = new File(sourceFileName);
            File targetFile = new File(targetFileName);

            bw = new BufferedWriter(new FileWriter(targetFile));
            br = new BufferedReader(new FileReader(sourceFile));

            StringBuffer assemblyCode = new StringBuffer();
            String sourceCode;
            while ((sourceCode = br.readLine()) != null) {
                assemblyCode.append(parser.parseCode(sourceCode));
            }

            String machineCode = code.codeTranslate(assemblyCode.toString());

            bw.write(machineCode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}