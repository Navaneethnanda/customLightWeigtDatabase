package Helper;

import java.io.FileWriter;
import java.io.IOException;

public class Helper {
    public String sArraytoString(String[] s){
        String ans="";
        for(String x:s){
            ans+=x+"#";
        }
        return ans;
    }
    public int findElementInArray(String[] s,String y){

        for(int x=0;x<s.length;x++){
            if(s[x].equalsIgnoreCase(y)){
                return x;
            }
        }
        return -1;
    }

    public boolean addline(String fileName,String addLine){
        try {
            FileWriter fw = new FileWriter(fileName, true); //the true will append the new data
            fw.write("\n" + addLine);//appends the string to the file
            fw.close();
        }
        catch(IOException e){
            System.out.println("entity not added error");
        }
        return false;
    }


}
