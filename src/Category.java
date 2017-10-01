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

    public Category(String title, String realPath, String wantedPath) {
        mTitle = Utils.removeRestrictedSymbols(title);;
        mRealPath = realPath;
        mWantedPath = wantedPath;
        mPrograms = new ArrayList<>();
    }

    public void addPrograms(String file) {
        List<String> programIds = getProgrammId(file);
        for (String programId : programIds) {
            Program program = new Program(mRealPath, mWantedPath + "/" + mTitle + "/");
            program.addId(programId);
            System.out.println("addPrograms " + programId);
            program.addRecords();
            mPrograms.add(program);
        }
    }

    private List<String> getProgrammId(String file) {
        List<String> ids = new ArrayList<>();
        String programId;
        Utils utils = new Utils();
        do {
            programId = utils.findSubstring(file, "<h4><a href=\"../programms/", "\">", true);
            if (programId != null) {
                ids.add(programId);
            }
        } while (programId != null);
        return ids;
    }
}
