
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Витек
 */
public class ArduinoModule {

    public static String modulesPath;
    private static SerialPort serialPort;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException, SerialPortException, InterruptedException, IOException {

        String fileSeparator = System.getProperty("file.separator");
        String path = ArduinoModule.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        String[] tmp = path.split("/");
        path = "";

        for (int i = 0; i < tmp.length - 2; i++) {
            path += tmp[i] + fileSeparator;
        }

        modulesPath = path;
        //Conection to conection-module
        Socket socket = null;
        DataInputStream ois = null;
        try {
            socket = new Socket("localhost", 7000);
            ois = new DataInputStream(socket.getInputStream());

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //OPEN COM PORT
        String[] portNames = SerialPortList.getPortNames();
        for (int i = 0; i < portNames.length - 1; i++) {
            try {
                System.out.println("Connect to port: " + portNames[i]);
                serialPort = new SerialPort(portNames[i]);
                serialPort.openPort();
                serialPort.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
                        | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);

            } catch (SerialPortException ex) {

            }
            if (serialPort.isOpened()) {
                break;
            }
        }

        while (serialPort.isOpened() && (!socket.isOutputShutdown())) {
            //JOB WITH BUFFER FILE

            String in = ois.readUTF();
            System.out.println("SERVER SEND: " + in);

            switch (in) {
                case "toFo":
                    serialPort.writeString("0x1001\n");
                    break;
                case "toBa":
                    serialPort.writeString("0x1002\n");
                    break;
                case "toLe":
                    serialPort.writeString("0x1003\n");
                    break;
                case "toRi":
                    serialPort.writeString("0x1004\n");
                    break;
                case "toFoLe":
                    serialPort.writeString("0x1005\n");
                    break;
                case "toFoRi":
                    serialPort.writeString("0x1006\n");
                    break;
                case "toBaLe":
                    serialPort.writeString("0x1007\n");
                    break;
                case "toBaRi":
                    serialPort.writeString("0x1008\n");
                    break;
            }
        }

    }

    private static class PortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String data = serialPort.readString(event.getEventValue());
                    System.out.println(data);
                    serialPort.writeString("OK");
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}
