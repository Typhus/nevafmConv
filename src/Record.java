import java.io.*;

/**
 * Created by n551jm on 01.10.2017.
 */
public class Record {

    private String mRealPath;
    private String mWantedPath;
    private final String mTitle;

    public Record(String recordTitle, String realPath, String wantedPath) {
        mTitle = Utils.removeRestrictedSymbols(recordTitle);
        mRealPath = realPath;
        mWantedPath = wantedPath;
    }

    public void create() {
        new File(mWantedPath).mkdirs();

        try {
            InputStream in = new FileInputStream(mRealPath);
            OutputStream out = new FileOutputStream(mWantedPath + mTitle + ".mp3");

        // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

            in.close();
            out.close();
            System.out.println("create record " + mWantedPath + mTitle + ".mp3" + " from " + mRealPath);
        } catch (IOException e) {
            System.out.println("Error while creating record " + mWantedPath + mTitle + ".mp3" + " from " + mRealPath);
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return mTitle;
    }
}
