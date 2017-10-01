import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n551jm on 01.10.2017.
 */
public class Program {

    public static final String PATH = "podcasts/";

    private final String mRealPath;
    private final String mWantedPath;

    private final File[] mPodcastFiles;
    private List<Record> mRecords;
    private String mId;
    private String mTitle;
    private String mPodcastString;
    private File mPodcastFile;

    public Program(String realPath, String wantedPath) {
        mRealPath = realPath;
        mWantedPath = wantedPath;

        mPodcastFiles = new File(mRealPath + "/" + PATH).listFiles();
        mRecords = new ArrayList<>();
    }

    public void addId(String programId) {
        mId = programId;
        mPodcastFile = findPodcastById(mId);
    }

    private String getPodcastString() {
        if (mPodcastString != null) {
            return mPodcastString;
        }
        mPodcastString = Utils.fileToString(mPodcastFile);
        return mPodcastString;
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

    public void addRecords() {
        String podcastItemsString = getPodcastItemsString();
        mTitle = getProgramTitle(getPodcastString());
        mTitle = Utils.removeRestrictedSymbols(mTitle);
        if (podcastItemsString == null) {
            Utils.addToEmptyFolder(mWantedPath + "/" + mTitle + "/");
            return;
        }

        Utils utils = new Utils();
        String recordTitle;
        String recordLink;
        do {
            recordTitle = utils.findSubstring(podcastItemsString, "<title>", "</title>", true);
            recordLink = utils.findSubstring(podcastItemsString, "<guid isPermaLink=\"true\">http://www.neva.fm/", "</guid>", true);
            if (recordTitle != null && recordLink != null) {
                Record record = new Record(recordTitle, mRealPath + "/" + recordLink, mWantedPath + mTitle + "/");
                record.create();
                mRecords.add(record);
            }
        } while (recordTitle != null && recordLink != null);
    }

    private String getPodcastItemsString() {
        return Utils.findSubstring(getPodcastString(), "<item>", "</item>");
    }
}
