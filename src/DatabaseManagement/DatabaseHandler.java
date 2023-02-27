package DatabaseManagement;

import Authentication.Authenticator;

import java.util.Scanner;

public class DatabaseHandler {

    QueryProcessor queryProcessor=new QueryProcessor();

    Authenticator authenticator =new Authenticator();
    public void start(){
        if(authenticator.authenticate()){
            System.out.println("logged in successfully");
            System.out.println("use exit command to exit");
            System.out.println("yoursql is ready to use");

            queryCollector();
        }
        else{
            System.out.println("Invalid combination of username, password and security question please try again");
            start();
        }

    }

    public void queryCollector(){

        Scanner myObj = new Scanner(System.in);
        System.out.print(" $: ");
        String query = myObj.nextLine();
        if(query.equals("exit")){
            authenticator.setCurrentUser("");
            System.out.println("logout successfull");
            return;
        }
        queryProcessor.queryClassifier(query);

        queryCollector();
    }



}
