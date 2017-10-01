import java.io.File;
import java.util.List;

/**
 * Created by n551jm on 01.10.2017.
 */
public class Program {

    private final String mRealPath;
    private final String mWantedPath;
    private List<Record> mRecords;
    private String mId;
    private String mTitle;

    public Program(String realPath, String wantedPath) {
        mRealPath = realPath;
        mWantedPath = wantedPath;
    }

    public void addId(String programId) {
        mId = programId;
    }

    public File create() {

    }

    public String getTitle() {
        return mTitle; используй
    }
}
