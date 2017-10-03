package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Fillo.Recordset;

public class TestDataHandler
{

    public static XSSFSheet    ExcelWSheet;
    public static XSSFWorkbook ExcelWBook;
    public static XSSFCell     Cell;
    public static XSSFRow      Row;

    // SSO Authentication
    public String              url;
    public String              username;
    public String              password;
    
    // PDS Window
    public String              disruptionList;
    public String              multipleDisruptedFlights;
    public String              disruptedFlight;
    public String              resolve;
    
    // Solution Window
    public String              pnrList;
    public String              multiplePNRs;
    public String              originList;
    public String              destinationList;
    public String              flightList;
    //public String              impactTypeList;
    //public String              bookingClassList;
    //public String              notifStatusList;
    //public String              ssrList;
    //public String              ffList;
    
    // Config page
    public String              maxOverbook;
    public String              maxLateness;

    public static int          start, last, numRow, temp = 0;
    public static int[]        selectedRows;

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
    
    // Set Flight Disruption List
    public static TestDataHandler setDisruptionDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setDisruptionDataSet(record);
    }

    public static TestDataHandler setDisruptionDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("Disruptions");
        
        testDataHandler.disruptionList = DataRepository.testDataToBeUsed().getRowData("Disruptions", recordSet).getField("DisruptionList");
        testDataHandler.multipleDisruptedFlights = DataRepository.testDataToBeUsed().getRowData("Disruptions", recordSet).getField("MultipleDisruptedFlights");
        testDataHandler.disruptedFlight = DataRepository.testDataToBeUsed().getRowData("Disruptions", recordSet).getField("DisruptedFlight");
        testDataHandler.resolve = DataRepository.testDataToBeUsed().getRowData("Disruptions", recordSet).getField("Resolve");
        return testDataHandler;
    }

    // Set PDS Filter
    public static TestDataHandler setFilterDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setFilterDataSet(record);
    }

    public static TestDataHandler setFilterDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("PDSFilter");
        
        testDataHandler.originList = DataRepository.testDataToBeUsed().getRowData("PDSFilter", recordSet).getField("Origin");
        testDataHandler.destinationList = DataRepository.testDataToBeUsed().getRowData("PDSFilter", recordSet).getField("Destination");
        testDataHandler.flightList = DataRepository.testDataToBeUsed().getRowData("PDSFilter", recordSet).getField("Flight");

        return testDataHandler;
    }

    // Set PNR list after solving a Disrupted Flight
    public static TestDataHandler setPNRDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setPNRDataSet(record);
    }

    public static TestDataHandler setPNRDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("PNR");
        
        testDataHandler.pnrList = DataRepository.testDataToBeUsed().getRowData("PNR", recordSet).getField("PNRList");
        testDataHandler.multiplePNRs = DataRepository.testDataToBeUsed().getRowData("PNR", recordSet).getField("MultiplePNRs");
        return testDataHandler;
    }
    
    // Set PNR Filters in the Solution Window
    public static TestDataHandler setPNRFilterDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setPNRFilterDataSet(record);
    }

    public static TestDataHandler setPNRFilterDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("PNRFilter");
        
        testDataHandler.originList = DataRepository.testDataToBeUsed().getRowData("PNRFilter", recordSet).getField("Origin");
        testDataHandler.destinationList = DataRepository.testDataToBeUsed().getRowData("PNRFilter", recordSet).getField("Destination");
        testDataHandler.flightList = DataRepository.testDataToBeUsed().getRowData("PNRFilter", recordSet).getField("Flight");
        testDataHandler.pnrList = DataRepository.testDataToBeUsed().getRowData("PNRFilter", recordSet).getField("PNR");
        return testDataHandler;
    }
    
    // Set Configuration Data
    public static TestDataHandler setConfigDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setConfigDataSet(record);
    }

    public static TestDataHandler setConfigDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        String recordSet = record.getField("Config");
        
        testDataHandler.maxOverbook = DataRepository.testDataToBeUsed().getRowData("PNRFilter", recordSet).getField("MaxOverbook");
        testDataHandler.maxLateness = DataRepository.testDataToBeUsed().getRowData("PNRFilter", recordSet).getField("MaxLateness");
        return testDataHandler;
    }

    // updated for review
    private static int getRowContains(int colNum, String sTestCaseName) throws Exception
    {
        int i;

        try
        {

            int rowCount = getRowUsed();
            int ctr = 0;
            selectedRows = new int[rowCount];
            for (i = 1; i <= rowCount; i++)
            {
                if (getCellData(i, colNum).contains(sTestCaseName))
                {
                    numRow++;
                    selectedRows[ctr] = i;
                    last = i;
                    ctr++;
                }
            }

            outer : for (i = 1; i < rowCount; i++)
            {
                if (getCellData(i, colNum).contains(sTestCaseName))
                {
                    start = i;
                    break outer;
                }
            }

            return i;
        }
        catch (Exception e)
        {
            throw (e);
        }
    }

    private static int getRowUsed() throws Exception
    {
        try
        {
            int RowCount = ExcelWSheet.getLastRowNum();
            return RowCount;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw (e);
        }

    }

    // This method is to read the test data from the Excel cell, in this we are passing parameters as Row num
    // and Col num
    private static String getCellData(int RowNum, int ColNum) throws Exception
    {
        String CellData = null;

        try
        {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            // Cell.setCellType(Cell.CELL_TYPE_STRING);
            CellData = Cell.getStringCellValue();

            return CellData;

        }
        catch (Exception e)
        {
            return "";
        }
    }

    // UPDATED: for review
    public static void loadIterativeTestData(String sheetName, String sTestCaseName) throws Exception
    {
        // These steps iterates the input of Worksheet information which are derived from excel
        FileInputStream ExcelFile = new FileInputStream("./src/test/resources/data/StandardTestData.xlsx");
        ExcelWBook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet = ExcelWBook.getSheet(sheetName);
        int startRow = getRowContains(0, sTestCaseName);
        System.out.println("[Test Data] Start Row with data is:  " + startRow);
        System.out.println("[Test Data] Last Row with data is:  " + last);
    }

    // UPDATED: for review
    public static String writeCellValue(int RowNum, int ColNum, String text) throws Exception
    {
        String text1 = "exception error";
        try
        {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            System.out.println("setCellValue Row Number: " + RowNum + " and colnum " + ColNum);
            Cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            String filepath = "./src/test/resources/data/StandardTestData.xlsx";
            Cell.setCellValue(text);
            FileOutputStream webdata = new FileOutputStream(filepath);

            if (RowNum == last)
                ExcelWBook.write(webdata);

            return text1;
        }
        catch (Exception e)
        {
            return text1;
        }
    }

}
