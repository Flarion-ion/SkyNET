/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dark.tank;

import dark.tank.Starter;
import dark.tank.module.core.connection.interner.TestClientConnection;
import static dark.tank.module.core.connection.interner.ClientManager.TYPE;
import java.util.ArrayList;

/**
 *
 * @author Витек
 */
public class Module extends Thread {

    String entry;
    public static String ModuleType = "CON";

    public Module(String arg) {
        entry = arg;
    }

    @Override
    public void run() {

        ArrayList<String> modulesNames = Starter.pl.getNames();
        for (String module : modulesNames) {
            if (entry.contains(module)) {
                try {

                    if (!module.equals(TYPE)) {
                        System.out.println("Resending to " + Starter.pl.get(module));
                        TestClientConnection.send(entry, Starter.pl.get(module));
                    } else {
                        System.out.println(entry + " deleted from stream");
                    }

                } catch (InterruptedException ex) {
                    System.out.println("ERROR resending to " + Starter.pl.get(module));
                }
            }
        }
    }

}
