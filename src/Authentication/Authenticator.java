package Authentication;
import Helper.Helper;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
public class Authenticator {
    Helper helper =new Helper();

    public String getCurrentUser(){

        try {
            File myObj = new File("./database//currentUser.txt");
            Scanner myReader = new Scanner(myObj);

            if(myReader.hasNextLine()){
                String data=myReader.nextLine();
                myReader.close();
                return data;
            }
            else{
                return "error";
            }

        } catch (FileNotFoundException e) {
            System.out.println("something is wrong please try again later");
            return "#";
        }


    }


    public void setCurrentUser(String s){
        try {
            File f1 = new File("./database//currentUser.txt");
            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(s);
            out.flush();
            out.close();
        }
        catch (Exception ex) {
            System.out.println("current user log out unsuccessfull");
        }
    }


    public boolean authenticate(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please Enter \"login\" to login");
        System.out.println("Please Enter \"register\" to register");
        System.out.print("/> : ");
        String userName = myObj.nextLine();
        if(userName.equalsIgnoreCase("login")){
            return login();
        }
        if(userName.equalsIgnoreCase("register")){
            return regester();
        }

        System.out.println("invalid selection");
        authenticate();
        return false;
    }

    public boolean login(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please Enter username : ");
        System.out.print("/> : ");
        String userName = myObj.nextLine();

        System.out.println("Enter password : ");
        System.out.print("/> : ");
        String password = myObj.nextLine();

        String securityQuestion =getSecurityQuestion(userName);
        if(securityQuestion.equals("#")){
            System.out.println("username doesn't exist please check your username");
            authenticate();
        }
        System.out.print(securityQuestion+" : ");

        System.out.print("/> : ");
        String securityAnswer  = myObj.nextLine();
        return authenticateUser(userName,password,securityAnswer);
    }


    public boolean regester(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please Enter username : ");
        System.out.print("/> : ");
        String userName = myObj.nextLine();

        System.out.println("Enter password : ");
        System.out.print("/> : ");
        String password = myObj.nextLine();

        System.out.println("Enter security question : ");
        System.out.print("/> : ");
        String securityQuestion = myObj.nextLine();

        System.out.println("Enter security answer : ");
        System.out.print("/> : ");
        String securityAnswer = myObj.nextLine();
        File theDir = new File("./database//"+userName+"//");
        if (theDir.exists()) {
            System.out.println("username already exists");
            return false;
        }
        theDir.mkdir();

helper.addline("./database//userCredentials.txt",userName+"#"+password+"#"+securityQuestion+"#"+securityAnswer);


        return authenticateUser(userName,password,securityAnswer);


    }




    public String getSecurityQuestion(String uName){
        try {
            File myObj = new File("./database//userCredentials.txt");
//            /home/buddy/5408/Assignment1/database/userCredentials.txt
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splited = data.split("#");
                if(uName.equals(splited[0])){
                    return splited[2];
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Username not found");
            return "#";
        }
        return "#";
    }

    public Boolean authenticateUser(String uName,String password,String securityAnswer){
        try {
            File myObj = new File("./database//userCredentials.txt");
//            /home/buddy/5408/Assignment1/database/userCredentials.txt
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splited = data.split("#");
//                System.out.println("given "+uName+password+securityAnswer);
//                System.out.println(splited[0]+splited[1]+splited[3]);
                if(uName.equals(splited[0]) && password.equals(splited[1]) && securityAnswer.equals(splited[3])){
                    setCurrentUser(uName);
                    myReader.close();
                return true;
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred plese try later");
            e.printStackTrace();
        }
        return false;

    }





}
