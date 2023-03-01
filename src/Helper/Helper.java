package Helper;

import java.io.*;

public class Helper {


    public  void copyFolder(File source, File destination)
    {
        if (source.isDirectory())
        {
            if (!destination.exists())
            {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files)
            {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            InputStream in = null;
            OutputStream out = null;

            try
            {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0)
                {
                    out.write(buffer, 0, length);
                }
            }
            catch (Exception e)
            {
                try
                {
                    in.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }

                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    public  void emptyFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
    }
    public  void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }



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
