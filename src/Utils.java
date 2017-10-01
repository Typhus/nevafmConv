/**
 * Created by n551jm on 01.10.2017.
 */
public class Utils {

    public static String findSubstring(String source, String start, String end) {
        int startIndex = source.indexOf(start);
        if (startIndex == -1) {
            return null;
        }

        startIndex += start.length();

        int endIndex = source.indexOf(end, startIndex);
        if (startIndex == -1) {
            return null;
        }

        return source.substring(startIndex, endIndex);
    }
}
