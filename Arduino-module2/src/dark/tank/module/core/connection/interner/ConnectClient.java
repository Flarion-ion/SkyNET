/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dark.tank.module.core.connection.interner;

import dark.tank.Starter;
import dark.tank.Module;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Витек
 */
public class ConnectClient extends Thread {

    private static Socket clientDialog;
  
    public ConnectClient(Socket client) {
        ConnectClient.clientDialog = client;
    }

    @Override
    public void run() {

        try {

            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
          

                // серверная нить ждёт в канале чтения (inputstream) получения
                // данных клиента после получения данных считывает их
                String input = in.readUTF();
                String[] inBuff = input.split("~~");
                for(String entry : inBuff){
                // и выводит в консоль
                System.out.println("READ from clientDialog message - " + entry);
                    new Module(entry).start();
                }
                // инициализация проверки условия продолжения работы с клиентом
                // по этому сокету по кодовому слову - quit в любом регистре
               

            }

            in.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();

        } catch (IOException e) {

        }
    }
}
