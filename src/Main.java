import Authentication.Authenticator;
import DatabaseManagement.DatabaseHandler;
import DatabaseManagement.Query;
import DatabaseManagement.QueryProcessor;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
//        DatabaseHandler obj1=new DatabaseHandler();
//        obj1.authenticator();


QueryProcessor obj1=new QueryProcessor();
obj1.updateQueryProcessor("update customer set customerid=puk");




    }
}