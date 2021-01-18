import java.util.LinkedList;

public class Program {

    /*roman numbers array*/
    enum Roman implements CharSequence {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        final int value;

        private Roman(int value) {
            this.value = value;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            return 0;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return null;
        }
    }

    /*check String about can be number*/
    public static boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    /*function return all exceptions and write thats errors for user*/
    static String getExceptionValue(String[] data, LinkedList<String> numMass1, LinkedList<String> numMass2, String a, String c, String tumbler) {
        Roman[] allRomans = Roman.values();
        String exceptionValue = "";
        String errorRoman = "";
        int indicator = 0;

        if ((isNumeric(a) & !isNumeric(c)) || (isNumeric(c) & !isNumeric(a))) {
            exceptionValue = exceptionValue + " Need input only arabic or only roman.";
        }
        if (isNumeric(a) & isNumeric(c)) {
            char checkNumA = a.charAt(0);
            char checkNumC = c.charAt(0);
            if (Integer.parseInt(a) > 10 || Integer.parseInt(c) > 10) {
                exceptionValue = exceptionValue + " You cant input number more big then 10";
                return "Incorrect input:" + exceptionValue;
            }
            if (checkNumA == '0' || checkNumC == '0') {
                exceptionValue = exceptionValue + " Can input only Integer type number";
            }
        }
        if (!isNumeric(a) & !isNumeric(c)) {
            if (numMass1.isEmpty() || numMass2.isEmpty()) {
                exceptionValue = exceptionValue + " Number is not input.";
            } else {
                if (Program.getArabicNum(numMass1) > 10 || Program.getArabicNum(numMass2) > 10) {
                    exceptionValue = exceptionValue + " You cant input number more big then X";
                    return "Incorrect input:" + exceptionValue;
                }
                for (String dataValue : data) {
                    for (Roman roman : allRomans) {
                        if (dataValue.contains(roman) || dataValue.contains(tumbler)) {
                            indicator = 1;
                        }
                    }
                    if (indicator == 0) {
                        errorRoman = errorRoman + dataValue;
                    } else {
                        indicator = 0;
                    }
                }
                if (!errorRoman.isEmpty()) {
                    exceptionValue = exceptionValue + " Is not roman symbol: " + errorRoman;
                }
            }
        }
        if (exceptionValue.isEmpty()) {
            return "correct";
        } else {
            return "Incorrect input:" + exceptionValue;
        }
    }

    /*here get arabic number from roman String number*/
    static int getArabicNum(LinkedList<String> numMass) {
        int arabicNum = 0;
        if (Integer.parseInt((String) (numMass.getLast())) <= Integer.parseInt((String) numMass.getFirst())) {
            for (String numValue : numMass) {
                arabicNum = arabicNum + Integer.parseInt(numValue);
            }
        } else {
            arabicNum = Integer.parseInt(String.valueOf(numMass.getLast()));
            for (String numValue : numMass) {
                if (numValue != numMass.getLast()) {
                    arabicNum = arabicNum - Integer.parseInt(numValue);
                }
            }
        }
        return arabicNum;
    }

    static String convertArabicToRoman(int arabicNum) {
        Roman[] allRomans = Roman.values();
        LinkedList<Integer> numMass = new LinkedList<>();
        String romanNum = new String("");
        String str = "";
        str = Integer.toString(arabicNum);
        int numLenght = 0;
        int multiply = 1;
        int range = 0;
        int y = 0;

        if (arabicNum < 0) {
            arabicNum = Math.abs(arabicNum);
        }
        while (arabicNum > 0) { //we try minus arabic number while not write all roman number
            str = Integer.toString(arabicNum);
            numLenght = str.length() - 1;
            if (numLenght == 0) {
                multiply = 1; // thats need for understand number range. For example 10 or 100, thats need for exeption roman numbers
            } else {
                for (int i = 1; i <= numLenght; i++) {
                    multiply = multiply * 10;
                }
            }
            if (arabicNum / multiply >= 9) {
                multiply = multiply * 9;
                range = 9; // range need for exeption too
            }
            if (arabicNum / multiply == 4) {
                multiply = multiply * 4;
                range = 4;
            }
            if (range == 4 || range == 9) { //exeption for roman numbers, thats need for get for example IV or XC
                for (Roman roman : allRomans) {
                    int num = Integer.parseInt(String.valueOf(roman.value));
                    if (arabicNum >= multiply & num == multiply / range) {
                        if (range == 9) {
                            romanNum = romanNum + roman + String.valueOf(allRomans[y + 2]); // write to roman number exeption number
                        }
                        if (range == 4) {
                            romanNum = romanNum + roman + String.valueOf(allRomans[y + 1]);
                        }
                        arabicNum = arabicNum - multiply;
                        break;
                    }
                    y++;
                }
                y = 0;
                range = 0;
            } else {
                for (Roman roman : allRomans) {
                    int num = Integer.parseInt(String.valueOf(roman.value));
                    if (arabicNum >= num) {
                        numMass.add(roman.value);
                    }
                }

                int lastNum;
                while (!numMass.isEmpty() & arabicNum > 0 & arabicNum != 9 & arabicNum != 4) { // thats for not exception numbers
                    lastNum = numMass.getLast();
                    if (arabicNum <= 3 & numMass.size() > 1) {
                        numMass.removeLast();
                        lastNum = numMass.getLast();
                    }
                    if (arabicNum >= lastNum) {
                        arabicNum = arabicNum - lastNum;
                    }
                    if (arabicNum < lastNum) {
                        numMass.removeLast();
                    }
                    for (Roman roman : allRomans) {
                        if (lastNum == Integer.parseInt(String.valueOf(roman.value))) {
                            romanNum = romanNum + roman;
                        }
                    }
                }
            }
        }
        return romanNum;
    }

    /* return operation result*/
    static int Result(int arabicNumA, int arabicNumB, String tumbler) {
        int result;

        switch (tumbler) {
            case "+" -> result = arabicNumA + arabicNumB;
            case "-" -> result = arabicNumA - arabicNumB;
            case "*" -> result = arabicNumA * arabicNumB;
            case "/" -> result = arabicNumA / arabicNumB;
            default -> throw new IllegalStateException("Unexpected value: " + tumbler);
        }
        return result;
    }
}