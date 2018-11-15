
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ----------
 * Sort log file
 *
 * log file 输入是List<String>，输出要把只有word的行放前面,按字母顺序排序，有数字的行放后面(按照原来出现顺序)。
 *
 */
public class ReorderLog {
    /**
     * Sort the logs by the requirements
     * @param logs
     * @return
     */
    public List<String> reorder(List<String> logs) {
        // Check
        if (logs == null || logs.isEmpty()) {
            return Collections.emptyList();
        }

        List<Wrapper> noDigitLogs = new ArrayList<>();
        List<Wrapper> withDigitLogs = new ArrayList<>();

        // Split log into no-digit logs and with_digit logs
        // Note that it is not clear whether there are multiple whitespaces following id and they are
        // considered in the comparison. To be conservative, split method is not used. A single whitespace is used.
        for (String log : logs) {
            // Find the fist white space
            int index = log.indexOf(' ');
            if (index < 0) {
                throw new InvalidParameterException("Invalid log format");
            }

            int id = Integer.valueOf(log.substring(0, index));
            String content = log.substring(index + 1);

            if (hasDigits(content)) {
                withDigitLogs.add(new Wrapper(id, content, log));
            } else {
                noDigitLogs.add(new Wrapper(id, content, log));

            }
        }

        Collections.sort(noDigitLogs, (p, q) -> {
            if (p.content.equals(q.content)) {
                return p.id - q.id;
            }
            return p.content.compareTo(q.content);
        });

        // Hold the sorted list
        List<String> result = new ArrayList<>();
        for (Wrapper w : noDigitLogs) {
            result.add(w.log);
        }

        for (Wrapper w : withDigitLogs) {
            result.add(w.log);
        }

        return result;
    }

    /**
     * Check if the given string consists of digits only
     *
     * @param s string to be checked
     * @return true if the string consists of only letters; false otherwise
     */
    private boolean hasDigits(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }

    private class Wrapper {
        int id;
        String content;
        String log;

        public Wrapper(int id, String content, String log) {
            this.id = id;
            this.content = content;
            this.log = log;
        }

        public String toString() {
            return "id: " + id + " content: " + content;
        }
    }

    public static void main(String[] args) {
        ReorderLog sol = new ReorderLog();

        List<String> logs = Arrays.asList(new String[]{
                "100 hello world",
                "101    hello world",
                "102 hello world2",
                "103 ehllo world2",
                "104 ehllo world!",
                "105 ehllo world2",
                "106 Hello world",

        });

        List<String> sorted = sol.reorder(logs);

        System.out.print(sorted);
    }
}