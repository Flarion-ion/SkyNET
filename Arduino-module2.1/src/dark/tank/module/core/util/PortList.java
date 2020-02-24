/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dark.tank.module.core.util;

import java.util.ArrayList;

/**
 *
 * @author Витек
 */
public class PortList extends ArrayList {

    private ArrayList<String> list;

    public PortList() {
        list = new ArrayList<String>();
        add("CON 7000");
        add("SYS 7001");
        add("HARD 7002");
        add("UP 7003");
        add("CAM 7004");
    }
    public void add(String str){
    list.add(str);
    }
    public int get(String name){
        int retval = 0;
        for(int i = 0;i<list.size();i++){
            if(list.get(i).contains(name)){
                String temp = list.get(i).split(" ")[1];
                retval = Integer.parseInt(temp);
            }
        }
    return retval;
}
    public ArrayList getNames(){
        ArrayList<String> temp = new ArrayList<String>();
        for(int i =0;i<list.size();i++){
            temp.add(list.get(i).split(" ")[0]);
        }
        return temp;
       
    }
}
