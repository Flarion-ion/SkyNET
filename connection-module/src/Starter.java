
import core.IPMaster;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Витек
 */
public class Starter {

    public static String reg = "";
    public static String modulesPath;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        reg += "IP: " + new IPMaster().getMyIP() + "~~";
        reg += "MAC: " + new IPMaster().getMyMAC() + "~~";

        String fileSeparator = System.getProperty("file.separator");
        String path = Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String[] tmp = path.split("/");
        path = "";
        for (int i = 0; i < tmp.length - 2; i++) {
            path += tmp[i] + fileSeparator;
        }
        modulesPath = path;

        File modulesDir = new File(modulesPath);
        try {
            int i = 0;
            for (File module : modulesDir.listFiles()) {
                if (module.isFile()) {

                    if (module.getName().contains("module.inf")) {
                    System.out.println("READ INF "+module.getAbsolutePath());
                    FileReader fr = new FileReader(module);
                    Scanner sc = new Scanner(fr);
                    while(sc.hasNextLine()){
                        reg+=sc.nextLine()+" ";
                    }
                    reg+="~~";
                    } 
                }
            }
        } catch (NullPointerException ex) {
            
        }
        reg="UPDATE-INF~~"+reg;

    }
}
