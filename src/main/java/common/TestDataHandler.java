package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.codoid.products.fillo.Recordset;



public class TestDataHandler
{

    // SSO Authentication
    public String              url;
    public String              username;
    public String              password;


    public String               rwsResponse;

    // Load Test Data, login user
    public static TestDataHandler loadTestData(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return loadTestData(record);
    }

    public static TestDataHandler loadTestData(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
//        String recordSet = record.getField("URL");
        
        testDataHandler.url = record.getField("URL");
        testDataHandler.username = record.getField("Username");
        testDataHandler.password = record.getField("Password");
        return testDataHandler;
    }

    //Get Reflow Workflow Service Response Json

    public static TestDataHandler getRWSResponse(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return getRWSResponse(record);
    }

    public static TestDataHandler getRWSResponse(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();

        testDataHandler.rwsResponse = record.getField("ReflowWorkflowResponseJson");

        return testDataHandler;
    }


}
