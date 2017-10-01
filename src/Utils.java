import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        if (endIndex == -1) {
            return null;
        }

        return source.substring(startIndex, endIndex);
    }

    int currentIndex = 0;
    public String findSubstring(String source, String start, String end, boolean findNext) {
        int startIndex = source.indexOf(start, currentIndex);
        if (startIndex == -1) {
            return null;
        }

        startIndex += start.length();

        int endIndex = source.indexOf(end, startIndex);
        if (endIndex == -1) {
            return null;
        }

        if (findNext) {
            currentIndex = endIndex;
        }

        return source.substring(startIndex, endIndex);
    }

    public static String fileToString(File file) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(file.getPath())), "UTF-8");
        } catch (IOException e) {
            System.out.println("Error on create create");
            e.printStackTrace();
            return null;
        }
        return content;
    }
}
