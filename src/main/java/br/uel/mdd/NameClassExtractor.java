package br.uel.mdd;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameClassExtractor implements ClassExtractor {

    @Override
    public String extractClass(File file) {

        String fileName = file.getName();

//            Get the first occurrence of a digit in the file name
        String patternStr = "[0-9]";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(fileName);

        String className = null;

        if (matcher.find()) {
            int firstOccurrence = matcher.start();
            className = fileName.substring(0, firstOccurrence);
        } else {
            throw new IllegalArgumentException("File of name " + fileName + " does not match the format expected");
        }

        return className;
    }
}
