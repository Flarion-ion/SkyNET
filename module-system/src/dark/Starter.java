package dark;

import static dark.Config.AppPath;
import static dark.Config.LOAD_MODE;
import static dark.Config.ModulesPath;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Starter {

    

    public static void main(String[] args) throws IOException, FileNotFoundException,
            URISyntaxException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ClassNotFoundException, InterruptedException {
        Config cfg = new Config();
        
        System.out.println("INFO LoadMode: " + LOAD_MODE);
        if (LOAD_MODE.equals("default")) {
            //DEFAULT LOADING ALGORITM
            
            File modulesDir = new File(ModulesPath);
            try {
                int i=0;
                for (File module : modulesDir.listFiles()) {
                    if (module.isFile()) {

                        if (!module.getName().contains("update")) {
                            System.out.println("Run module: " + module.getAbsolutePath() + "\\" + module.getName());
                            //run update-module
                            Process proc = Runtime.getRuntime().exec("java -jar " + ModulesPath + module.getName());
                            i++;
                        } else {
                            if (module.getName().contains("jar")) {
                                System.out.println("Continued load module: " + module.getName());
                            }
                        }
                    }
                }
                System.out.println("Loaded "+i+" modules");
            } catch (NullPointerException ex) {
                if (modulesDir.mkdir()) {
                    main(null);
                }
            }
        }
        if (LOAD_MODE.equals("update")) {
            //UPDATE LOADING ALGORITM
            //Update update-module
            String line = "https://darkcorparation.000webhostapp.com/update-module2.jar";
            String updateName = line.split("/")[line.split("/").length - 1];
            System.out.println("Updating update-module: " + line);

            File file = new File(AppPath + "temp\\");
            if (!file.exists()) {
                file.mkdir();
            }

            saveUrl(AppPath + "temp\\" + updateName, "https://darkcorparation.000webhostapp.com/update-module2.jar");
            System.out.println("Run module: " + file.getAbsolutePath() + "\\" + updateName);
            //run update-module
            Process proc = Runtime.getRuntime().exec("java -jar " + file.getAbsolutePath() + "\\" + updateName);
            proc.waitFor();
            InputStream is = proc.getInputStream();
            byte b[] = new byte[is.available()];
            is.read(b, 0, b.length);
            System.out.println(new String(b));

            //deleting temp files
            System.out.println("INFO DELETING TEMP FILES");
            try {
                for (File myFile : file.listFiles()) {
                    if (myFile.isFile()) {
                        myFile.delete();
                    }
                }
            } catch (NullPointerException ex) {

            }
            if (file.delete()) {

            }
            cfg.setDefaultMode();
            main(null);
        }
    }

    public static void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        System.out.println("Downloading file: " + urlString);
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
