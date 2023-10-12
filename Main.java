package my.calculator;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;


public class Main {
    private final static String[] OPERATIONS = {"+", "-", "*", "/"};
	private final static String[] ARABIC = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private final static String[] ROMAN = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    

    public static String calc(String input) {
        if (input == null) throw new IllegalArgumentException();

        final String[] words = input.trim().split(" ");

        if (words.length != 3) throw new IllegalArgumentException();

        switch (handlerSystems(words)) {
            case ARABIC_NUM: {
                return String.valueOf(getResult(words));
            }

            case ROMAN_NUM: {
                int result = getResult(words);
                if (result > 0) {
                    return getRomanNumber(result);
                }
            }

            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    private static String getRomanNumber(int result) {
        if (String.valueOf(result).length() == 1) return ROMAN[result - 1];
        else if (String.valueOf(result).length() == 2) {
            StringBuilder sb = new StringBuilder();
            List<Dozens> dozens = Arrays.asList(Dozens.words());
            int i = 0;
            while (result > 9) {
                Dozens cur = dozens.get(i);
                if (cur.value <= result) {
                    sb.append(cur.name());
                    result -= cur.value;
                } else i++;
            }
            return result % 10 == 0 ? sb.toString() : sb.append(ROMAN[result - 1]).toString();
        }
        else return "C";
    }

    private static Const handlerSystems(String[] words) {
        if (!contains(OPERATIONS, words[1])) return Const.EXCEPTION;

        if (contains(ARABIC, words[0])) {
            if (contains(ARABIC, words[2])) return Const.ARABIC_NUM;
        } else if (contains(ROMAN, words[0])) {
            if (contains(ROMAN, words[2])) return Const.ROMAN_NUM;
        }

        return Const.EXCEPTION;
    }

    private static int getResult(String[] words) {
        switch (words[1]) {
            case "+":
                return getNumber(words[0]) + getNumber(words[2]);
            case "-":
                return getNumber(words[0]) - getNumber(words[2]);
            case "*":
                return getNumber(words[0]) * getNumber(words[2]);
            default:
                return getNumber(words[0]) / getNumber(words[2]);
        }
    }

    private static int getNumber(String value) {
        for (int i = 0; i < 10; i++) {
            if (ARABIC[i].equals(value)) return i + 1;
            else if (ROMAN[i].equals(value)) return i + 1;
        }
        throw new IllegalArgumentException();
    }

    private static boolean contains(String[] words, String value) {
        for (String s : words) {
            if (s.equals(value)) return true;
        }
        return false;
    }

    enum Const {
        ROMAN_NUM, ARABIC_NUM, EXCEPTION
    }

    enum Dozens {
        XC(90), L(50), XL(40), X(10);

        private final int value;

        Dozens(int value) {
            this.value = value;
        }
    }
	
	public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input:");
        System.out.println("Output:\n" + calc(reader.readLine()));
        reader.close();
    }
}
