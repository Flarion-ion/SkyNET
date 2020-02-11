package dark.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Витек
 */
public class GuardUtils {
    
    public static final String[] p = {"cmd.exe"};
 public static void check() {
        if(GuardUtils.checkProcesses(p)) {
            try{
                Class<?> af = Class.forName("java.lang.Shutdown");
                Method m = af.getDeclaredMethod("halt0", int.class);
                m.setAccessible(true);
                m.invoke(null, 1);
            } catch (Exception e) { }
        }
    }
 
 public static boolean checkProcesses(String[] onlineData)
        {
            if (onlineData == null) return false;
            
            try
            {
                int platform = getPlatform();
                String line; Process p;
                List<String> processes = new ArrayList<>();
                
                if (platform == 2) p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe /v /fo list");
                else p = Runtime.getRuntime().exec("ps -e");
                
                try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())))
                {
                    
                    while ((line = input.readLine()) != null)
                    {
                        processes.add(line.toLowerCase());
                    }
                    
                }
                
                for(String process : processes)
                {
                    for(String Data : onlineData)
                    {
                        if(process.contains(Data.toLowerCase()))
                        {
                            return true;
                        }
                    }
                }
                
            } catch (Exception e) {}
            
            return false;
        }
 
 public static int getPlatform()
    {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win"))
            return 2;
        if (osName.contains("mac"))
            return 3;
        if (osName.contains("solaris"))
            return 1;
        if (osName.contains("sunos"))
            return 1;
        if (osName.contains("linux"))
            return 0;
        if (osName.contains("unix"))
            return 0;

        return 4;
    }
}
