
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class StartUpdate {

    static String modulesPath = "";

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        String fileSeparator = System.getProperty("file.separator");
        String path = StartUpdate.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String[] tmp = path.split("/");
        path = "";
        for (int i = 0; i < tmp.length - 2; i++) {
            path += tmp[i] + fileSeparator;
        }
        modulesPath = path + "modules\\";
        System.out.println(modulesPath);
        String updateURL = "https://darkcorparation.000webhostapp.com/update.txt";
        System.out.println(args.length);
        if (args.length == 1) {
            updateURL = args[0].substring(1);

            try {
                String line = updateURL;
                String updateName = line.split("/")[line.split("/").length - 1];

                saveUrl(modulesPath + updateName, line);

            } catch (IOException ex) {

            }

        } else {
            File file = new File(modulesPath);
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
                System.out.println(modulesPath);
            }

            String updateName = updateURL.split("/")[updateURL.split("/").length - 1];
            saveUrl(modulesPath + updateName, updateURL);
            File updateFile = new File(modulesPath + "update.txt");
            Thread.sleep(1000);
            FileInputStream fis = new FileInputStream(updateFile);
            Scanner sc = new Scanner(fis);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                updateName = line.split("/")[line.split("/").length - 1];

                saveUrl(modulesPath + updateName, line);
            }
//            file = new File(path + "module-system.jar");
//            System.out.println("Run module: " + file.getAbsolutePath());
//            run update-module
//            Process proc = Runtime.getRuntime().exec("java -jar " + file.getAbsolutePath());
        }

    }

    public static void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        System.out.println("Downloading file: " + filename);
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
        System.out.println("Download compelite");
    }
}
