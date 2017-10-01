import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n551jm on 01.10.2017.
 */
public class Program {

    public static final String PATH = "podcasts";

    private final String mRealPath;
    private final String mWantedPath;

    private File mProgramFolder;
    private final File[] mPodcastFiles;
    private List<Record> mRecords;
    private String mId;
    private String mTitle;
    private String mPodcastString;
    private File mPodcastFile;

    public Program(String realPath, String wantedPath) {
        mRealPath = realPath;
        mWantedPath = wantedPath;

        mProgramFolder = new File(mRealPath + "/" + PATH);
        mPodcastFiles = mProgramFolder.listFiles();
        mRecords = new ArrayList<>();
    }

    public void addId(String programId) {
        mId = programId;
        mPodcastFile = findPodcastById(mId);
    }

    public File create() {
        File podcastFile = findPodcastById(mId);
        if (podcastFile == null) {
            System.out.println("error podcast not found " + mId);
        }

        if (mPodcastFile == null) {
            System.out.println("error podcastString " + mId);
        }

        mTitle = getProgramTitle(getPodcastString());
        File programFolder = createProgramFolder();
        return programFolder;
    }

    private String getPodcastString() {
        if (mPodcastString != null) {
            return mPodcastString;
        }
        return Utils.fileToString(mPodcastFile);
    }

    private File createProgramFolder() {
        if (mProgramFolder != null) {
            return mProgramFolder;
        }
        mProgramFolder = new File(mWantedPath + "/" + mTitle + "/");
        try {
            mProgramFolder.createNewFile();
        } catch (IOException e) {
            System.out.println("Error createFolder " + mWantedPath + "/" + mTitle + "/");
            e.printStackTrace();
        }
        mProgramFolder.mkdirs();
        return mProgramFolder;
    }

    private String getProgramTitle(String podcastString) {
        return Utils.findSubstring(podcastString, "<title>", "</title>");
    }

    private File findPodcastById(String id) {
        for (File podcastFile : mPodcastFiles) {
            if (podcastFile.getName().equals(mId)) {
                return podcastFile;
            }
        }
        return null;
    }

    public String getTitle() {
        return mTitle;
    }

    public void addRecords() {
        String podcastItemsString = getPodcastItemsString();
        if (podcastItemsString == null) {
            System.out.println("Error addRecords <item> not found " + mWantedPath + "/" + mTitle + "/");
            return;
        }

        Utils utils = new Utils();
        String recordTitle;
        String recordLink;
        do {
            recordTitle = utils.findSubstring(podcastItemsString, "<title>", "</title>", true);
            recordLink = utils.findSubstring(podcastItemsString, "<guid isPermaLink=\"true\">http://www.neva.fm/", "</guid>", true);
            if (recordTitle != null && recordLink != null) {
                Record record = new Record(recordTitle, mRealPath + "/" + recordLink, mWantedPath + "/" + mTitle + "/");
                record.create();
                mRecords.add(record);
            }
        } while (recordTitle != null && recordLink != null);
    }

    private String getPodcastItemsString() {
        return Utils.findSubstring(getPodcastString(), "<item>", "</item>");
    }
}
