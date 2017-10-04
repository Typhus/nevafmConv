import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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

    public static String removeRestrictedSymbols(String title) {
        title =  title.replaceAll("[\\\\/:*?\";<>|]", "");
        title = title.replaceAll("&quot", "");
        if (title.length() > 30) {
            title = title.substring(0, 30);
        }
        return title;
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

    public static void decodeFiles(String path) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                decodeFiles(file.getAbsolutePath());
            } else {
                decodeFile(file.getAbsolutePath());
            }
        }
    }

    public static void decodeFile(String path) {
        String[] segments = path.split("/");
        String fileName = segments[segments.length - 1];
        fileName = decode(fileName);
        if (fileName == null) {
            System.out.println("decodeFile errors CharMap empty");
            return;
        }
        File realFile = new File(path);

        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        path += fileName;
        realFile.renameTo(new File(path));
        System.out.println("decodeFile from " + segments[segments.length - 1] + " to " + fileName);
    }

    private static String decode(String fileName) {
        if (sCharMap.size() == 0) {
            return null;
        }
        String result = fileName;
        Object[] keySet = sCharMap.keySet().toArray();
        for (int i = 0; i < keySet.length; i++) {
            String charr = (String) keySet[i];
            result = result.replace(charr, sCharMap.get(charr));
        }

        return result;
    }

    private static Map<String, String> sCharMap = new HashMap<>();
    static {
        sCharMap.put("Р°", "а");
        sCharMap.put("Р±", "б");
        sCharMap.put("РІ", "в");
        sCharMap.put("Рі", "г");
        sCharMap.put("Рґ", "д");
        sCharMap.put("Рµ", "е");
        sCharMap.put("С%91", "ё");
        sCharMap.put("Р¶", "ж");
        sCharMap.put("Р·", "з");
        sCharMap.put("Рё", "и");
        sCharMap.put("Р№", "й");
        sCharMap.put("Рє", "к");
        sCharMap.put("Р»", "л");
        sCharMap.put("Рј", "м");
        sCharMap.put("РЅ", "н");
        sCharMap.put("Рѕ", "о");
        sCharMap.put("Рї", "п");
        sCharMap.put("С%80", "р");
        sCharMap.put("С%81", "с");
        sCharMap.put("С%82", "т");
        sCharMap.put("С%83", "у");
        sCharMap.put("С%84", "ф");
        sCharMap.put("С%85", "х");
        sCharMap.put("С%86", "ц");
        sCharMap.put("С%87", "ч");
        sCharMap.put("С%88", "ш");
        sCharMap.put("С%89", "щ");
        sCharMap.put("С%8A", "ъ");
        sCharMap.put("С%8B", "ы");
        sCharMap.put("С%8C", "ь");
        sCharMap.put("С%8D", "э");
        sCharMap.put("С%8E", "ю");
        sCharMap.put("С%8F", "я");

        sCharMap.put("Р%90", "А");
        sCharMap.put("Р%91", "Б");
        sCharMap.put("Р%92", "В");
        sCharMap.put("Р%93", "Г");
        sCharMap.put("Р%94", "Д");
        sCharMap.put("Р%95", "Е");
        sCharMap.put("Р%96", "Ж");
        sCharMap.put("Р%97", "З");
        sCharMap.put("Р%98", "И");
        sCharMap.put("Р%99", "Й");
        sCharMap.put("Р%9A", "К");
        sCharMap.put("Р%9B", "Л");
        sCharMap.put("Р%9C", "М");
        sCharMap.put("Р%9D", "Н");
        sCharMap.put("Р%9E", "О");
        sCharMap.put("Р%9F", "П");
        sCharMap.put("Р ", "Р");
        sCharMap.put("РЎ", "С");
        sCharMap.put("Рў", "Т");
        sCharMap.put("РЈ", "У");
        sCharMap.put("Р¤", "Ф");
        sCharMap.put("РҐ", "Х");
        sCharMap.put("Р§", "Ч");
        sCharMap.put("РЁ", "Ш");
        sCharMap.put("Р©", "Щ");

        sCharMap.put("Р«", "Ы");
        sCharMap.put("Р¬", "Ь");
        sCharMap.put("Р\u00AD", "Э");
        sCharMap.put("Р®", "Ю");
        sCharMap.put("РЇ", "Я");
    }

}
