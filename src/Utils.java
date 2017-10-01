import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean createDirectory(String path) {
        File folder = new File(path);
        if (!folder.mkdirs()) {
            System.out.println("Error createFolder " + path);
            return false;
        }
        return true;
    }

    private static List<String> sEmptyList = new ArrayList<>();

    public static void addToEmptyFolder(String emptyFolder) {
        sEmptyList.add(emptyFolder);
    }

    public static void printEmptyFolders() {
        System.out.println("--EmptyFolders----------------------------- ");
        for (String emptyFolder : sEmptyList) {
            System.out.println(emptyFolder);
        }
    }

    public static String removeRestrictedSymbols(String path) {
        return path.replaceAll("[\\\\/:*?\"<>|]", "");
    }

    public static void renameFile(String path) {
        try {
            String[] segments = path.split("/");
            String fileName = segments[segments.length - 1];
            fileName = new String(fileName.getBytes(), "UTF-8");
            File realFile = new File(path);

            int lastIndex = path.lastIndexOf("/") + 1;
            path = path.substring(0, lastIndex);
            path += fileName;
            realFile.renameTo(new File(path));
            System.out.println("Rename file from " + segments[segments.length - 1] + " to " + fileName);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException " + path);
            e.printStackTrace();
        }
    }

    public static void renameFiles(String path) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                renameFiles(file.getAbsolutePath());
            } else {
                renameFile(file.getAbsolutePath());
            }
        }
    }
}
