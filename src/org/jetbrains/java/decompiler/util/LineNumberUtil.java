package org.jetbrains.java.decompiler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public final class LineNumberUtil {

    private LineNumberUtil() {
    }

    public static Map<Integer, Integer> getSingleLineMapping(Map<Integer, Set<Integer>> lineMapping) {
        Map<Integer, Integer> singleLineMapping = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> entry : lineMapping.entrySet()) {
            Set<Integer> lineNumbers = entry.getValue();
            if (lineNumbers != null) {
                Optional<Integer> firstLineNumber = lineNumbers.stream().findFirst();
                if (firstLineNumber.isPresent()) {
                    singleLineMapping.put(entry.getKey(), firstLineNumber.get());
                }
            }
        }
        return singleLineMapping; 
    }

    public static String addLineNumber(String src, Map<Integer, Integer> lineMapping) {
        int maxLineNumber = 0;
        for (Integer value : lineMapping.values()) {
            if (value != null && value > maxLineNumber) {
                maxLineNumber = value;
            }
        }

        String formatStr = "/* %2d */ ";
        String emptyStr = "         ";

        StringBuilder sb = new StringBuilder();

        if (maxLineNumber >= 1000) {
            formatStr = "/* %4d */ ";
            emptyStr = "           ";
        } else if (maxLineNumber >= 100) {
            formatStr = "/* %3d */ ";
            emptyStr = "          ";
        }

        int index = 0;
        try (Scanner sc = new Scanner(src)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Integer srcLineNumber = lineMapping.get(index + 1);
                if (srcLineNumber != null && !"}".equals(line.trim())) {
                    sb.append(String.format(formatStr, srcLineNumber));
                } else {
                    sb.append(emptyStr);
                }
                sb.append(line).append("\n");
                index++;
            }
        }
        return sb.toString();
    }

}
