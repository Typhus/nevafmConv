/**
 * Created by n551jm on 01.10.2017.
 */
public class Main {

    private final static String REAL_PATH = "D:/www.neva.fm";
    private final static String WANTED_PATH = "D:/experimentsNeva";

    public static final void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Neva(REAL_PATH, WANTED_PATH).create();
            }
        }).start();
    }
}
