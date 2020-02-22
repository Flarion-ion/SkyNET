/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dark.tank;


import static dark.tank.Module.ModuleType;
import dark.tank.module.core.connection.interner.ClientManager;
import dark.tank.module.core.connection.interner.TestClientConnection;
import dark.tank.module.core.ui.TestConnectionUI;
import dark.tank.module.core.util.PortList;
import java.util.ArrayList;

/**
 *
 * @author Витек
 */
public class Starter {
public static PortList pl = new PortList();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    ClientManager cm = new ClientManager(ModuleType);
    cm.start();
    TestConnectionUI.main(null);
    }
    
}
