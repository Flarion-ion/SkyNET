package dark.tank.module.core.connection.interner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClientConnection {

    /**
     * 
     * @param args
     * @throws InterruptedException
     */

    public static void send(String str, int port) throws InterruptedException {

// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента      
        try(Socket socket = new Socket("localhost", port);  
                
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());)
        {

            System.out.println("Client connected to socket.");

// проверяем живой ли канал и работаем если живой           
               

// данные появились - работаем                      
            String clientCommand = str;

// пишем данные с консоли в канал сокета для сервера            
            oos.writeUTF(clientCommand);
            oos.flush();


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        }
       
    }
}