package test.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestAsServer {

/**
 * 
 * @param args
 * @throws InterruptedException
 */
    public static void main(String[] args) throws InterruptedException {
//  стартуем сервер на порту 3345
while(true){
        try (ServerSocket server= new ServerSocket(7000)){
                               
                Socket client = server.accept();        
                System.out.println("Connection accepted.");
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
                String entry;
                
//                while(true){
//                entry = in.readUTF(); 
//                if(entry.equals("?CON")){
//                    System.out.println("Send OK");      
//                out.writeUTF("OK");                    
//                out.flush();
//                break;
//                } else {
//                    client.close();
//                    break;
//                }
//                }
                
                while(!client.isClosed()){
                    
         

                
 
                 
                out.writeUTF("toFo");                        
                out.flush();    

                }




                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    }
}
