package Helper;

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

}
