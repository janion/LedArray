package array.web;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFormatter {

    private static final String TAG_REGEX = "(.*?)<(.*?)>";
    private static final Pattern TAG_REGEX_PATTERN = Pattern.compile(TAG_REGEX);
    private static final String END_TAG_FORMAT = "</%s>";
    private static final String TAB = "    ";
    private static final String SLASH = "/";
    private static final String COMMENT = "!";
    private static final String SPACE = " ";
    private static final String OPEN_BRACKET = "{";
    private static final String CLOSE_BRACKET = "}";

    public String formatHtml(String htmlString) {
        String[] lines = htmlString.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }

        indentTags(lines, htmlString);
        indentStyle(lines);

        StringBuilder formatted = new StringBuilder();
        Arrays.asList(lines).forEach(row -> formatted.append(row + "\n"));
        return formatted.toString();
    }

    private void indentTags(String[] lines, String htmlString) {
        for (int lineCount = 0; lineCount < lines.length; lineCount++) {
            String line = lines[lineCount];
            Matcher match = TAG_REGEX_PATTERN.matcher(line);
            if (match.find()) {
                String tag = match.group(2).split(SPACE)[0];
                String endTag = String.format(END_TAG_FORMAT, tag);
                if (tag.startsWith(SLASH) || tag.startsWith(COMMENT) || !htmlString.contains(endTag)) {
                    continue;
                } else if (!line.contains(endTag)) {
                    for (int nextLineCount = lineCount + 1; nextLineCount < lines.length; nextLineCount++) {
                        String nextLine = lines[nextLineCount];
                        if (!nextLine.contains(endTag)) {
                            lines[nextLineCount] = TAB + nextLine;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void indentStyle(String[] lines) {
        for (int lineCount = 0; lineCount < lines.length; lineCount++) {
            String line = lines[lineCount];
            if (line.contains(OPEN_BRACKET) && !line.contains(CLOSE_BRACKET)) {
                for (int nextLineCount = lineCount + 1; nextLineCount < lines.length; nextLineCount++) {
                    String nextLine = lines[nextLineCount];
                    if (!nextLine.contains(CLOSE_BRACKET)) {
                        lines[nextLineCount] = TAB + nextLine;
                    } else {
                        break;
                    }
                }
            }
        }
    }

}
