package filetransfer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

/**
 * Created by George on 2017-04-09.
 */
public class FileUtils {
    // From: ghttp://stackoverflow.com/a/1816676
    public static boolean doesFileExist(String filename) {
        File file = new File(filename);
        return file.exists() && !file.isDirectory();
    }

    // From: http://stackoverflow.com/a/31186393
    public static byte[] readFileAsByteArray(String filename) {
        try {
            File file = new File(filename);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            return new byte[0];
        }
    }

    // From: http://stackoverflow.com/a/5445161
    public static String readFileAsString(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            java.util.Scanner s = new java.util.Scanner(br).useDelimiter("\\A");
            String content = s.hasNext() ? s.next() : "";

            br.close();

            return content;
        } catch (Exception e) {
            return "";
        }
    }
}
