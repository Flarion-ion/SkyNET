
import java.net.URISyntaxException;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

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
    public static void main(String[] args) throws URISyntaxException, SerialPortException, InterruptedException {
        String fileSeparator = System.getProperty("file.separator");
        String path = ArduinoModule.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String[] tmp = path.split("/");
        path = "";
        for (int i = 0; i < tmp.length - 2; i++) {
            path += tmp[i] + fileSeparator;
        }
        modulesPath = path;
        //OPEN COM PORT
                //Передаём в конструктор имя порта
        serialPort = new SerialPort("COM1");
        try {
            //Открываем порт
            serialPort.openPort();
            //Выставляем параметры
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            //Включаем аппаратное управление потоком
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                                          SerialPort.FLOWCONTROL_RTSCTS_OUT);
            //Устанавливаем ивент лисенер и маску
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            //Отправляем запрос устройству
            
            while(serialPort.isOpened()){
                //JOB WITH BUFFER FILE
                Thread.sleep(1000);
                 serialPort.writeString("IMETATION COMMAND\n");
                //WRITE TO COM PORT
            }
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        
    }
    private static class PortReader implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    //Получаем ответ от устройства, обрабатываем данные и т.д.
                    String data = serialPort.readString(event.getEventValue());
                    System.out.println(data);
                    //И снова отправляем запрос
                    serialPort.writeString("OK");
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}

        
       
    

