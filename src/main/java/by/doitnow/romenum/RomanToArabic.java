package by.doitnow.romenum;

import java.util.*;

public class RomanToArabic {
    public static int convert(String s) {
        if(!isValid(s)){new Exception();}

        int output = 0;

        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);

        if (s.length() == 0) return 0;
        if (s.length() == 1) return map.get(String.valueOf(s.charAt(0)));

        for (int i = 0; i < (s.length() - 1); i++) {
            if (map.get(String.valueOf(s.charAt(i))) >=
                    map.get(String.valueOf(s.charAt(i + 1)))) {
                output += map.get(String.valueOf(s.charAt(i)));
            } else {
                output -= map.get(String.valueOf(s.charAt(i)));
            }

        }
        output += map.get(String.valueOf(s.charAt(s.length() - 1)));
        return output;
    }

    private static boolean isValid(String s) {
        System.out.println("isValid working");
        String matchStr = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return s.matches(matchStr);
    }
}