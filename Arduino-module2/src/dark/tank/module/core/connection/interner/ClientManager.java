/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dark.tank.module.core.connection.interner;

import dark.tank.Starter;
import static dark.tank.Starter.pl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Витек
 */
public class ClientManager extends Thread {

    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);
    public static String TYPE;

    /**
     * @param args
     */
    public ClientManager(String type) {
        TYPE = type;
    }

    @Override
    public void run() {
        System.out.println("Run server on " + pl.get(TYPE) + " port");
        // стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try {
            ServerSocket server = new ServerSocket(pl.get(TYPE));
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Server wait a new connection");

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                Socket client = server.accept();

                executeIt.execute(new ConnectClient(client));
                System.out.print("Connection accepted.");
            }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.print("Server nor run. Port " + pl.get(TYPE) + " use other process");
        }
    }
}
