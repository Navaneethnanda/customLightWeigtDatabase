package  DatabaseManagement;

import Authentication.Authenticator;
import Helper.Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Query{
    Authenticator authenticator=new Authenticator();
    Helper helper = new Helper();
    public void select(String tableName){
    String currentUser= authenticator.getCurrentUser();
    try {

        File myObj = new File("./database//"+currentUser+"//"+tableName+".table");
        Scanner myReader = new Scanner(myObj);
        while(myReader.hasNextLine()){
            String[] line=myReader.nextLine().split("#");
            for(String x : line){
                System.out.print(x.substring(0,Math.min(x.length(),20))+" ".repeat(Math.max(0,20-x.length())));
            }
            System.out.println("");
        }

        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("please check the query for table name and columns corrections");
    }

}
    public void select(ArrayList<String> a,String tableName){
        String currentUser= authenticator.getCurrentUser();
        try {

            File myObj = new File("./database//"+currentUser+"//"+tableName+".table");
            Scanner myReader = new Scanner(myObj);
            String[] columns=myReader.nextLine().split("#");
            ArrayList<Integer> columnNumbers=new ArrayList<Integer>();
            for(int x=0;x<columns.length;x++){
                if(a.contains(columns[x])){
                    System.out.print(columns[x].substring(0,Math.min(columns[x].length(),20))+" ".repeat(Math.max(0,20-columns[x].length())));
                    columnNumbers.add(x);
                }
            }
            System.out.println("");
            while(myReader.hasNextLine()){


                String[] line=myReader.nextLine().split("#");
                for(int x=0;x<line.length;x++){
                    if(columnNumbers.contains(x)){
                        System.out.print(line[x].substring(0,Math.min(line[x].length(),20))+" ".repeat(Math.max(0,20-line[x].length())));

                    }
                }
                System.out.println("");
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("please check the query for table name and columns corrections");
        }
        }

    public void delete(String tableName){
        String currentUser= authenticator.getCurrentUser();
        try {

            File myObj = new File("./database//"+currentUser+"//"+tableName+".table");
            if(!myObj.exists()){
                System.out.println("table with name "+tableName+" doesn't exist");
            }
            Scanner myReader = new Scanner(myObj);
            String columnsRow=myReader.nextLine();
            FileWriter writer = new FileWriter("./database//"+currentUser+"//"+tableName+".table");
            writer.write(columnsRow);
            writer.close();
            myReader.close();
            System.out.println("successfully deleted");
        } catch ( IOException e) {
            System.out.println("please check the query for table name and columns corrections");
        }



    }

    public void create(String a,String tableName){
        String currentUser= authenticator.getCurrentUser();
        try {
            File myObj = new File("./database//"+currentUser+"//"+tableName+".table");
            if (myObj.createNewFile()) {
                FileWriter writer = new FileWriter("./database//"+currentUser+"//"+tableName+".table");
                writer.write(a);
                writer.close();
                System.out.println("File created: " + myObj.getName());

            } else {
                System.out.println("table already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    public void insert(ArrayList<String> columnsNames,ArrayList<String> columnsValues,String tableName){
        String currentUser= authenticator.getCurrentUser();

        try {
            File myObj = new File("./database//"+currentUser+"//"+tableName+".table");
            Scanner myReader = new Scanner(myObj);

            String[] columns=myReader.nextLine().split("#");
            char[] existingColumns="0".repeat(columns.length).toCharArray();
            for(int x=0;x<columns.length;x++){
                if(columnsNames.contains(columns[x])){
                    existingColumns[x]='1';
                }
            }
            String ans="";
            int x=0;
            for(int y=0;y<columns.length;y++){
                if(existingColumns[y]=='1'){
                    ans+=columnsValues.get(x)+"#";
                    x+=1;
                }
                else{
                    ans+="null#";
                }
            }


            FileWriter fw = new FileWriter("./database//"+currentUser+"//"+tableName+".table",true); //the true will append the new data
            fw.write("\n"+ans);//appends the string to the file
            fw.close();





        } catch (IOException e) {
            System.out.println("please check the query for table name and columns corrections");
        }


    }



    public void update(String columnsName,String value,String tableName){
        String currentUser= authenticator.getCurrentUser();
        List<String> lines = new ArrayList<String>();
        try {
            String line=null;
            File f1 = new File("./database//"+currentUser+"//"+tableName+".table");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            line =br.readLine();
            lines.add(line);

            int columnNumber=helper.findElementInArray(line.split("#"),columnsName);
            if(columnNumber==-1){
                System.out.println("please check the column name");
            }

            while ((line = br.readLine()) != null) {
                String[] ls=line.split("#");
                ls[columnNumber]=value;
                lines.add(helper.sArraytoString(ls));
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                out.write(s+"\n");
            out.flush();
            out.close();
        } catch (Exception ex) {
            System.out.println("something went wrong please check your query");
        }


    }







}

