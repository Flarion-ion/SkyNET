/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dark.tank;

import dark.tank.Starter;
import dark.tank.module.core.connection.interner.TestClientConnection;
import static dark.tank.module.core.connection.interner.ClientManager.TYPE;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Витек
 */
public class Module extends Thread {

    String entry;
    public static String ModuleType = "CON";
    public static String super_ip;
    public static int super_port;
    public Module(String arg) {
        entry = arg;
    }
   
    @Override
    public void run() {
        try {
            LoadConfig();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> modulesNames = Starter.pl.getNames();
        for (String module : modulesNames) {
            if (entry.contains(module)) {
                try {

                    if (!module.equals(TYPE)) {
                        System.out.println("Resending to " + Starter.pl.get(module));
                        TestClientConnection.send(entry, Starter.pl.get(module));
                    } else {
                        System.out.println("Resending to "+super_ip+":"+super_port+" <- "+entry);
                        TestClientConnection.send(entry, super_ip,super_port);
                    }

                } catch (InterruptedException ex) {
                    System.out.println("ERROR resending to " + Starter.pl.get(module));
                }
            } else {
                //CMD for ConnectionModule
                if(entry.contains("ping")){
                    try {
                        System.out.println("Resending to "+super_ip+":"+super_port+" <- "+entry);
                        TestClientConnection.send(entry, super_ip,super_port);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        }
    }

    private static void LoadConfig() throws URISyntaxException, FileNotFoundException {
    String fileSeparator = System.getProperty("file.separator");
        String path = Module.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String[] tmp =path.split("/");
        path="";
        for(int i = 0;i<tmp.length-2;i++)
            path+=tmp[i]+fileSeparator;
        String absoluteFilePath = path + fileSeparator + "config.txt";
        File file = new File(absoluteFilePath);
       
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] cfg = line.split(" ");
                switch (cfg[0]) {
                    case "SuperServerIP:":
                        super_ip = cfg[1];
                        break;
                    case "SuperServerPort:":
                        super_port = Integer.parseInt(cfg[1]);
                }
            }   
    }

}
