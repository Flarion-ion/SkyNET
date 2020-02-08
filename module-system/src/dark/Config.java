package dark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {

    public static String LOAD_MODE = "default";
    public static String AppPath="";
    public static String ModulesPath;

    public Config() throws FileNotFoundException, IOException, URISyntaxException {
        String fileSeparator = System.getProperty("file.separator");
        String path = Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String[] tmp =path.split("/");
        path="";
        for(int i = 0;i<tmp.length-1;i++)
            path+=tmp[i]+fileSeparator;
        AppPath=path;
        String absoluteFilePath = path + fileSeparator + "config.txt";
        File file = new File(absoluteFilePath);
        if (file.createNewFile()) {
            createConfigFile();
        } 
            System.out.println("INFO Load config file " + file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] cfg = line.split(" ");
                switch (cfg[0]) {
                    case "LoadMode:":
                        LOAD_MODE = cfg[1];
                        break;
                    case "ModulesPath:":
                        ModulesPath = cfg[1];
                }
            }
        
    }

    public void createConfigFile() throws IOException, URISyntaxException {
        String fileSeparator = System.getProperty("file.separator");
        String absoluteFilePath = AppPath + fileSeparator + "config.txt";
        File file = new File(absoluteFilePath);
        if (file.delete()) {

        }
        System.out.println("INFO Creating config file " + file.getAbsolutePath());
        FileWriter writer = new FileWriter(file, false);
        writer.write("/*Config file SYS module*/\n");
        writer.write("LoadMode: update\n");
        writer.write("ModulesPath: "+AppPath+"modules");
        writer.flush();
        writer.close();
    }
    public void setDefaultMode() throws URISyntaxException, IOException{
    String fileSeparator = System.getProperty("file.separator");
        
        String absoluteFilePath = AppPath + fileSeparator + "config.txt";
        File file = new File(absoluteFilePath);
        
        FileReader fr = new FileReader(file);
        Scanner sc = new Scanner(fr);
        ArrayList<String> list = new ArrayList<String>();
        while(sc.hasNextLine())
            list.add(sc.nextLine());
        sc.close();
        fr.close();
        if (file.delete()) {

        }
        FileWriter writer = new FileWriter(file, false);
        
        for(String str : list){
            
            if(str.contains("LoadMode:"))
                
                 writer.write("LoadMode: default\n");
            else
                writer.write(str+"\n");
        }
        writer.flush();    
    }
}
