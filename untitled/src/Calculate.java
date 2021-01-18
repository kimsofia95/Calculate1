import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

class Calculate extends Program {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Roman[] allRomans = Roman.values(); //all romans numbers
        //all romans numbers
        String a = "";
        String c = "";
        String tumbler = "0"; // thats save symbol +,-,/,* and give understand where id number where is symbol
        System.out.println("Input: ");
        String[] data = reader.readLine().replaceAll("\\s+", "").split(""); //get user inpit
        LinkedList<String> numMass1 = new LinkedList<>();
        LinkedList<String> numMass2 = new LinkedList<>();

        for (String dataValue : data) { //get massives user input numbers
            if (dataValue.equals("+") || dataValue.equals("-") || dataValue.equals("*") || dataValue.equals("/")) {
                tumbler = dataValue;}
            else {
                if (isNumeric(dataValue)) {
                    if (tumbler == "0") {
                        a = a + dataValue;
                    } else {
                        c = c + dataValue;
                    }
                } else {
                    for (Roman roman : allRomans) {
                        if (dataValue.contains(roman) & tumbler == "0") {
                            numMass1.add(String.valueOf(roman.value));
                        }
                        if (dataValue.contains(roman) & tumbler != "0") {
                            numMass2.add(String.valueOf(roman.value));
                        }
                    }
                }
            }
        }

        if (Program.getExceptionValue(data, numMass1, numMass2, a, c, tumbler) != "correct") { // check input errors
            throw new NullPointerException(Program.getExceptionValue(data, numMass1, numMass2, a, c, tumbler)); //if input not correct finish programm
        }
        System.out.println("Output:");

        if (isNumeric(a) & isNumeric(c)) {
            int result = Program.Result(Integer.parseInt(a), Integer.parseInt(c), tumbler); // write result for arabic input
            System.out.println(result);
        } else {
            int arabicNumA = Program.getArabicNum(numMass1); //write result for roman input
            int arabicNumB = Program.getArabicNum(numMass2);
            int arabicNumResult = Program.Result(arabicNumA, arabicNumB, tumbler);
            System.out.println(Program.convertArabicToRoman(arabicNumResult));
        }
    }
}