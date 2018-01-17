package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;

//import com.codoid.products.fillo.Recordset;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.fillo.Recordset;


public class TestDataHandler
{
	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
    public static XSSFCell Cell;
    public static XSSFRow Row;

    // SSO Authentication
    public String              url;
    public String              username;
    public String              password;

    public String              rwsResponse;
    
    // Flight# or ID in PDS section
    public String              flightNumber;
    public String              flightNumberAsc;
    public String              flightNumberDesc;
    public String              flightNumberNewAsc;
    
    public static int start,last, numRow, temp=0;
	public static int[] selectedRows;
	
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
    
    //Set Disruption Data in PDS
    public static TestDataHandler setDisruptionDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setDisruptionDataSet(record);
    }
    
    public static TestDataHandler setDisruptionDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("Disruptions");
        
        testDataHandler.flightNumber = DataRepository.testDataToBeUsed().getRowData("Disruptions", "RowSelection='"+recordSet+"'").getField("FlightNumber");
        
        return testDataHandler;
    }
    
    //Set Disruption Data in PDS Sorted by Ascending Order
    public static TestDataHandler setDisruptionDataSetAsc(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setDisruptionDataSet(record);
    }
    
    public static TestDataHandler setDisruptionDataSetAsc(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("Disruptions");
        
        testDataHandler.flightNumberAsc = DataRepository.testDataToBeUsed().getRowData("Disruptions", "RowSelection='"+recordSet+"'").getField("FlightSortAsc");
        
        return testDataHandler;
    }
    
    //Set Disruption Data in PDS Sorted by Descending Order
    public static TestDataHandler setDisruptionDataSetDesc(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setDisruptionDataSet(record);
    }
    
    public static TestDataHandler setDisruptionDataSetDesc(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("Disruptions");
        
        testDataHandler.flightNumberDesc = DataRepository.testDataToBeUsed().getRowData("Disruptions", "RowSelection='"+recordSet+"'").getField("FlightSortDesc");
        
        return testDataHandler;
    }
    
    //Set Disruption Data in PDS Sorted a New Header by Ascending Order
    public static TestDataHandler setDisruptionDataSetNewAsc(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setDisruptionDataSet(record);
    }
    
    public static TestDataHandler setDisruptionDataSetNewAsc(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("Disruptions");
        
        testDataHandler.flightNumberNewAsc = DataRepository.testDataToBeUsed().getRowData("Disruptions", "RowSelection='"+recordSet+"'").getField("FlightSortAsc");
        
        return testDataHandler;
    }

}
