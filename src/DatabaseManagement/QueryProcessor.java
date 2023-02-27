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

    public void updateQueryProcessor(String query){
        if(query.split("[ ']")[2].equalsIgnoreCase("set")){
            String tableName=query.split("[ ']")[1];
            String[] resultArray =
                    Arrays.stream(query.split("[ ,'=\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
            String columnName=resultArray[3];
            String value=resultArray[4];

            queryObj.update(columnName,value,tableName);
        }
else{
            System.out.println("invalid query");
        }
    }

    public void insertQueryProcessor(String query){
        String tableName=query.split("[ \"']")[2];

        ArrayList<String> columnsNames = new ArrayList<>(Arrays.asList(query.substring(query.indexOf('(')+1,query.indexOf(")")).split("[ ,'\"]")));
        String values=query.substring(query.indexOf(")")+1);
        String[] resultArray = Arrays.stream(values.substring(values.indexOf('(')+1,values.indexOf(")")).split("[,'\";]")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        ArrayList<String> columnsValues = new ArrayList<String>(Arrays.asList(resultArray));
        System.out.println(columnsNames);
        System.out.println(columnsValues);
        if(columnsNames.size()==columnsValues.size()){
            queryObj.insert(columnsNames,columnsValues,tableName);
        }
        else
            System.out.println("column count doesn't match with the values count please check it");
    }

    void selectQueryProcessor(String query){

        String[] queryFragments=query.split("[, '\";]");
        ArrayList<String> a=new ArrayList<>();
        int fromPosition=0;
        for(int x=0;x<queryFragments.length;x++){
            if(queryFragments[x].equalsIgnoreCase("from")){
                fromPosition=x;
            }
        }
        String tableName=queryFragments[queryFragments.length-1];
        if(fromPosition==2 && queryFragments[1].equals("*")){
            queryObj.select(tableName);
        }
        else{
            for(int x=1;x<fromPosition;x++){
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
        String[] nameTypo=textBetweenString.split(",");
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
