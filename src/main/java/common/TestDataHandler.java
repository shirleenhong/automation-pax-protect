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
    public String              disruptionList;
    public String              multipleDisruptedFlights;
    public String              disruptedFlight;
    public String              resolve;
    public String              pnrList;
    public String              multiplePNRs;
    public String              originList;
    public String              destinationList;
    public String              flightList;
    public String              impactTypeList;
    public String              bookingClassList;
    public String              notifStatusList;
    public String              ssrList;
    public String              ffList;
    public String              availableParameters;
    public String              hierarchyLevels;

    public static int          start, last, numRow, temp = 0;
    public static int[]        selectedRows;

    // Load Test Data
    public static TestDataHandler loadTestData(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return loadTestData(record);
    }

    public static TestDataHandler loadTestData(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        testDataHandler.url = record.getField("URL");
        testDataHandler.username = record.getField("Username");
        testDataHandler.password = record.getField("Password");
        return testDataHandler;
    }

    public static TestDataHandler setDisruptionDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setDisruptionDataSet(record);
    }

    public static TestDataHandler setDisruptionDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        testDataHandler.disruptionList = record.getField("DisruptionList");
        testDataHandler.multipleDisruptedFlights = record.getField("MultipleDisruptedFlights");
        testDataHandler.disruptedFlight = record.getField("DisruptedFlight");
        testDataHandler.resolve = record.getField("Resolve");
        return testDataHandler;
    }

    public static TestDataHandler setPNRDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setPNRDataSet(record);
    }

    public static TestDataHandler setPNRDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        testDataHandler.pnrList = record.getField("PNRList");
        testDataHandler.multiplePNRs = record.getField("MultiplePNRs");
        return testDataHandler;
    }

    public static TestDataHandler setFilterDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setFilterDataSet(record);
    }

    public static TestDataHandler setFilterDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        testDataHandler.originList = record.getField("Origin");
        testDataHandler.destinationList = record.getField("Destination");
        testDataHandler.flightList = record.getField("Flight");
        testDataHandler.impactTypeList = record.getField("ImpactType");
        return testDataHandler;
    }

    public static TestDataHandler setPNRFilterDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setPNRFilterDataSet(record);
    }

    public static TestDataHandler setPNRFilterDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        testDataHandler.originList = record.getField("Origin");
        testDataHandler.destinationList = record.getField("Destination");
        testDataHandler.flightList = record.getField("Flight");
        testDataHandler.bookingClassList = record.getField("BookingClass");
        testDataHandler.notifStatusList = record.getField("NotifStatus");
        testDataHandler.ssrList = record.getField("SSR");
        testDataHandler.pnrList = record.getField("PNR");
        testDataHandler.ffList = record.getField("FrequentFlyer");
        return testDataHandler;
    }

    public static TestDataHandler setParametersDataSet(String sheet, String... where) throws Exception
    {
        Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
        return setParametersDataSet(record);
    }

    public static TestDataHandler setParametersDataSet(Recordset record) throws Exception
    {
        TestDataHandler testDataHandler = new TestDataHandler();
        testDataHandler.availableParameters = record.getField("Parameters");
        testDataHandler.hierarchyLevels = record.getField("HierarchyLevel");
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
