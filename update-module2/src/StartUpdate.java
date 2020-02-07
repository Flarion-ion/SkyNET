
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Витек
 */
public class StartUpdate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String updateURL = "https://github.com/Flarion-ion/SkyNET/blob/master/update.txt";
        System.out.println(args.length);
        if (args.length == 1) {
            updateURL = args[0].substring(1);

            try {
                String line = updateURL;
                //System.out.println("Downloading file: " + line);
                String updateName = line.split("/")[line.split("/").length - 1];
                System.out.println("Downloading file: " + updateName);
                saveUrl("D:\\Storage\\GIT\\module-system\\build\\classes\\modules\\" + updateName, line);

            } catch (IOException ex) {
                //Logger.getLogger(UpdateModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Download compelite");
        } else {
            File file = new File("D:\\Storage\\GIT\\module-system\\build\\classes\\modules\\");
            System.out.println("INFO DELETING FILES");
            try {
                for (File myFile : file.listFiles()) {
                    if (myFile.isFile()) {
                        myFile.delete();
                    }
                }
            } catch (NullPointerException ex) {

            }
            if (file.mkdir()) {
                System.out.println("INFO CREATE D:\\Storage\\GIT\\module-system\\build\\classes\\modules\\");
            }
            System.out.println("Downloading file: " + updateURL);
            String updateName = updateURL.split("/")[updateURL.split("/").length - 1];
            saveUrl("D:\\Storage\\GIT\\module-system\\build\\classes\\modules\\" + updateName, updateURL);
            File updateFile = new File("D:\\Storage\\GIT\\module-system\\build\\classes\\modules\\update.txt");
            Thread.sleep(1000);
            FileInputStream fis = new FileInputStream(updateFile);
            Scanner sc = new Scanner(fis);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                //System.out.println("Downloading file: " + line);
                updateName = line.split("/")[line.split("/").length - 1];
                System.out.println("Downloading file: " + updateName);
                saveUrl("D:\\Storage\\GIT\\module-system\\build\\classes\\modules\\" + updateName, line);
            }
        }

    }

    public static void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}
