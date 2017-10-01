import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n551jm on 01.10.2017.
 */
public class Category {

    public static final String PATH = "cats";

    private final String mRealPath;
    private final String mWantedPath;
    private final String mTitle;
    private List<Program> mPrograms;
    private File mCategoryFolder;

    public Category(String title, String realPath, String wantedPath) {
        mTitle = title;
        mRealPath = realPath;
        mWantedPath = wantedPath;
        mPrograms = new ArrayList<>();
    }

    public File create() {
        if (mCategoryFolder != null) {
            return mCategoryFolder;
        }
        mCategoryFolder = new File(mWantedPath + "/" + mTitle + "/");
        if (!mCategoryFolder.mkdirs()) {
            System.out.println("Error createFolder " + mWantedPath + "/" + mTitle + "/");
        }
        return mCategoryFolder;
    }

    public void addPrograms(String file) {
        List<String> programIds = getProgrammId(file);
        for (String programId : programIds) {
            Program program = new Program(mRealPath, mCategoryFolder.getAbsolutePath());
            program.addId(programId);
            System.out.println("addPrograms " + programId);
            File programFile = program.create();
            programFile.mkdirs();
            if (programFile.exists()) {
                System.out.println("createFolder " + program.getTitle());
            } else {
                System.out.println("Error createFolder " + programId);
            }
            program.addRecords();
            mPrograms.add(program);
        }
    }

    private List<String> getProgrammId(String file) {
        List<String> ids = new ArrayList<>();
        String programId;
        Utils utils = new Utils();
        do {
            programId = utils.findSubstring(file, "<a href=\"../programms/", "\">", true);
            ids.add(programId);
        } while (programId != null);
        return ids;
    }
}
