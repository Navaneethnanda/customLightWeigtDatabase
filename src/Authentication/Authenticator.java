package Authentication;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class Authenticator {

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


    public boolean authenticate(){

        Scanner myObj = new Scanner(System.in);
        System.out.print("Please Enter username : ");
        String userName = myObj.nextLine();

        System.out.print("Enter password : ");
        String password = myObj.nextLine();

        String securityQuestion =getSecurityQuestion(userName);
        if(securityQuestion.equals("#")){
            System.out.println("username doesn't exist please check your username");
            authenticate();
        }
        System.out.print(securityQuestion+" : ");
        String securityAnswer  = myObj.nextLine();
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
                if(uName.equals(splited[0]) && password.equals(splited[1]) && securityAnswer.equals(splited[3])){
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
