package DatabaseManagement;

import Helper.Helper;

import java.util.ArrayList;
import java.util.Arrays;

public class QueryProcessor {
    Helper helper =new Helper();

    Query queryObj =new Query();
    public void queryClassifier(String query){
        String[] queryFragments=query.split(" ");
        if(queryFragments[0].equalsIgnoreCase("create")){createQueryProcessor(query);}
        else if(queryFragments[0].equalsIgnoreCase("select")){selectQueryProcessor(query);}
        else if(queryFragments[0].equalsIgnoreCase("insert")){insertQueryProcessor(query);}
        else if(queryFragments[0].equalsIgnoreCase("update")){updateQueryProcessor(query);}
        else if(queryFragments[0].equalsIgnoreCase("delete")){deleteQueryProcessor(query);}
        else{
            invalidMsgPrinter();
        }
        return;
    }
    void invalidMsgPrinter(){
        System.out.println("invalid query please check your query");
    }


    // for commands like "update tablename set columnname = xx"
    public void updateQueryProcessor(String query){
        String[] queryFragments=Arrays.stream(query.split("[, =()'\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);


        if(queryFragments[2].equalsIgnoreCase("set") && query.contains("=") && queryFragments.length==5){
            String tableName=queryFragments[1];

            String columnName=queryFragments[3];
            String value=queryFragments[4];

            queryObj.update(columnName,value,tableName);
        }
else{
    invalidMsgPrinter();
    return;

        }
    }


//    #only this command works insert into tablename (dfd,fdf,fdfd) values ();
    public void insertQueryProcessor(String query){
        String[] queryFragments=Arrays.stream(query.split("[, ()'\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        String tableName;
        if(queryFragments[1].equalsIgnoreCase("into") ){
            tableName=queryFragments[2];
        }
        else {
            System.out.println(1);
            invalidMsgPrinter();
            return;
        }

        String stringBetweenFirstBrackets=query.substring(query.indexOf('(')+1,query.indexOf(")"));
        ArrayList<String> columnsNames = new ArrayList<>(Arrays.asList(Arrays.stream(stringBetweenFirstBrackets.split("[, '\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new)));
        String values=query.substring(query.indexOf(")")+1);
        String[] valuesSplit=Arrays.stream(values.split("[, ()'\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        if(valuesSplit.length==0){
            System.out.println("please make sure that your query is in following format\n \" insert into tableName (column1,columns2,...) values (value1, value2,...); \"");
       return;
        }
        if(!valuesSplit[0].equalsIgnoreCase("values") || !values.contains("(") || !values.contains(")")){
//            System.out.println(2);
//            System.out.println(tableName);
            invalidMsgPrinter();
            return;
        }
        String[] resultArray = Arrays.stream(values.substring(values.indexOf('(')+1,values.indexOf(")")).split("[ ,'\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        ArrayList<String> columnsValues = new ArrayList<String>(Arrays.asList(resultArray));
//        System.out.println(columnsNames);
//        System.out.println(columnsValues);
//        System.out.println(tableName);
        if(columnsNames.size()==columnsValues.size()){
            queryObj.insert(columnsNames,columnsValues,tableName);
        }
        else
            System.out.println("column count doesn't match with the values count please check it");
    }

    void selectQueryProcessor(String query){
        String[] queryFragments=Arrays.stream(query.split("[, '\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
//        for(String x:queryFragments)
//        System.out.println(x);
        if(queryFragments[1].equalsIgnoreCase("*") && queryFragments[2].equalsIgnoreCase("from") && queryFragments.length==4){
            queryObj.select(queryFragments[3]);
        }
        else {
            ArrayList<String> a=new ArrayList<>();

            if(!queryFragments[queryFragments.length-2].equalsIgnoreCase("from")){
                System.out.println(1);
                invalidMsgPrinter();
                return;
            }
            String tableName=queryFragments[queryFragments.length-1];
            for(int x=1;x<queryFragments.length-2;x++){
                a.add(queryFragments[x]);
            }
            queryObj.select(a,tableName);
        }



        return;
    }

    void deleteQueryProcessor(String query){
        String[] queryFragments=Arrays.stream(query.split("[, '\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);

        if(queryFragments.length==3 && queryFragments[1].equalsIgnoreCase("from") ){

            queryObj.delete(queryFragments[2]);
        }
        else
            System.out.println("please verify the query its incorrect or not int correct format");

    }

    void createQueryProcessor(String query){
        String[] brokenQuery=Arrays.stream(query.split(" ")).filter(e -> e.trim().length() > 0).toArray(String[]::new);

        if(!brokenQuery[1].equalsIgnoreCase("table")){
            System.out.println(1);
            invalidMsgPrinter();
            return;
        }
        String tableName=brokenQuery[2];
        if(tableName.contains("(") || !query.contains("(") || !query.contains(")")){
            System.out.println(2);
            invalidMsgPrinter();
            return;
        }

        String textBetweenString=query.substring(query.indexOf("(")+1, query.indexOf(")"));
        String[] nameTypo=Arrays.stream(textBetweenString.split(",")).filter(e -> e.trim().length() > 0).toArray(String[]::new);

        String columns="";
        for(String x:nameTypo){
            String[] splitted=Arrays.stream(x.split(" ")).filter(e -> e.trim().length() > 0).toArray(String[]::new);

            if(splitted.length!=2){
                System.out.println(3);
                invalidMsgPrinter();
                return;
            }
            columns+=splitted[0]+"#";
        }

        queryObj.create(columns,tableName);



    }




}
