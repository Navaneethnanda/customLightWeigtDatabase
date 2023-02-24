package DatabaseManagement;

import Authentication.Authenticator;

import java.util.Scanner;

public class DatabaseHandler {

    QueryProcessor queryProcessor=new QueryProcessor();

    Authenticator authenticator =new Authenticator();
    public void authenticator(){
        if(authenticator.authenticate()){
            System.out.println("use exit command to exit");
            queryCollector();
        }
        else{
            System.out.println("Invalid combination of username, password and security question please try again");
            authenticator();
        }

    }

    public void queryCollector(){

        Scanner myObj = new Scanner(System.in);
        System.out.print(" $: ");
        String query = myObj.nextLine();
        if(query.equals("exit")){
            return;
        }
        queryProcessor.queryClassifier(query);
        queryCollector();
    }



}
