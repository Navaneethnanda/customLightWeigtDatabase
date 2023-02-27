import Authentication.Authenticator;
import DatabaseManagement.DatabaseHandler;
import DatabaseManagement.Query;
import DatabaseManagement.QueryProcessor;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//DatabaseHandler obj=new DatabaseHandler();
//obj.start();

        QueryProcessor obj1=new QueryProcessor();
        obj1.queryClassifier("delete from cusomr");



    }
}