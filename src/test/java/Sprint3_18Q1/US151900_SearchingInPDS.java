package Sprint3_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.WebManager;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Sprint26.US137581_GridView;
import pageobjects.GESSOAuthPage;
import pageobjects.PDSGridViewPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class US151900_SearchingInPDS extends TestBase {
	
	public static TestDataHandler testDataHandler;
	
	@Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US151900_SearchingInPDS.TestCases.PreRequisiteStep();
        US151900_SearchingInPDS.TestCases.Step1();
        US151900_SearchingInPDS.TestCases.Step2();
        US151900_SearchingInPDS.TestCases.Step3();
        US151900_SearchingInPDS.TestCases.Step4();
        US151900_SearchingInPDS.TestCases.Step5();
        US151900_SearchingInPDS.TestCases.Step6();
        US151900_SearchingInPDS.TestCases.Step7();
        US151900_SearchingInPDS.TestCases.Step8();
    }
	
	public static class TestCases{
		
		public static void PreRequisiteStep() throws InterruptedException 
		{
            ReportLog.setTestCase("PAX Protection HomePage");
            	ReportLog.setTestStep("Go to this URL: https://pax-protect-ui-shell.run.aws-usw02-pr.ice.predix.io ");
            	WebControl.clearData();
            	WebControl.open(testDataHandler.url);

            	Thread.sleep(1000);
	            if (GESSOAuthPage.authInfo.gESSOLabel.isDisplayed()) {
	                GESSOAuthPage.authInfo.sSOIDInput.typeKeys(testDataHandler.username);
	                GESSOAuthPage.authInfo.passWordInput.typeKeys(testDataHandler.password);
	                GESSOAuthPage.authInfo.submitFormShared.click();
	            }
	            GESSOAuthPage.page.verifyURL(false, 60);
		}
		
		public static void Step1() 
		{
            ReportLog.setTestCase("Verify PDS Section");
	            ReportLog.setTestStep("Verify Toggle, Filter, Search, and Solve Button");
	            
	            PaxImpactPage.filter.toggleCheckBox.verifyDisplayed(true, 5);
	            PaxImpactPage.filter.filterButton.verifyDisplayed(true, 5);
	            PaxImpactPage.filter.filterButton.verifyText("Filters");
//	            PaxImpactPage.solve.solveButton.verifyDisplayed(false, 5); //False = Solve Button should be disabled
	            
	            PaxImpactPage.filter.viewOptions("list");
	            PaxImpactPage.filter.viewOptions("grid");
	            
	            PaxImpactPage.filter.viewOptions("list").click();
	            PaxImpactPage.filter.viewOptions("grid").click();
	            
	            PaxImpactPage.filter.searchBox.verifyDisplayed(true, 5);
		}
		
		public static void Step2() throws Exception
		{
			 ReportLog.setTestCase("Search Impact Type in PDS");
			 	ReportLog.setTestStep("Step 1: Type an Impact Type in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchImpactType("Main", "RowSelection='DataSet0'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchImpactType);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
			 	String getText = PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).getText();
			 	System.out.println(getText);

			 	
		}
		
		public static void Step3() throws Exception
		{
			 ReportLog.setTestCase("Search Flight Number in PDS");
			 	ReportLog.setTestStep("Step 1: Type a Flight Number in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchFlightNumber("Main", "RowSelection='DataSet0'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchFlightNumber);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet1'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet1'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
			 	String getText = PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).getText();
			 	System.out.println(getText);
			 	
		}
		
		public static void Step4() throws Exception
		{
			 ReportLog.setTestCase("Search Tail Number in PDS");
			 	ReportLog.setTestStep("Step 1: Type a Tail Number in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchTail("Main", "RowSelection='DataSet0'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchTail);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet2'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet2'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
			 	String getText = PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).getText();
			 	System.out.println(getText);
			 	
		}
		
		public static void Step5() throws Exception
		{
			 ReportLog.setTestCase("Search Origin Port in PDS");
			 	ReportLog.setTestStep("Step 1: Type an Origin in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchOrigin("Main", "RowSelection='DataSet0'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchOrigin);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet3'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet3'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
			 	String getText = PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).getText();
			 	System.out.println(getText);
			 	
		}
		
		public static void Step6() throws Exception
		{
			 ReportLog.setTestCase("Search Destination Port in PDS");
			 	ReportLog.setTestStep("Step 1: Type a Destination in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchDestination("Main", "RowSelection='DataSet0'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchDestination);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(true, 5);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
			 	String getText = PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).getText();
			 	System.out.println(getText);
			 	
		}
		
		public static void Step7() throws Exception
		{
			 ReportLog.setTestCase("Search an non existing data in PDS");
			 	ReportLog.setTestStep("Step 1: Type an unknown data in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchOtherText("Main", "RowSelection='DataSet0'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchOtherText);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(false);
			 	
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(false);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
		}
		
		public static void Step8() throws Exception
		{
			 ReportLog.setTestCase("Search other data aside from the  in PDS");
			 	ReportLog.setTestStep("Step 1: Type an unknown data in the search box");
			 	PaxImpactPage.filter.searchBox.click();
			 	testDataHandler = TestDataHandler.searchOtherText("Main", "RowSelection='DataSet1'"); //Refer to the PDSSearch tab in the Dataset excel
			 	PaxImpactPage.filter.searchBox.typeKeys(testDataHandler.searchOtherText);
			 	
			 	ReportLog.setTestStep("Step 2: Verify the results in List View"); //Step 2 and 3 uses the Disruptions tab > FlightNumber column in the Dataset excel
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
			 	PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(false);
			 	
			 	ReportLog.setTestStep("Step 3: Verify the results in Grid View");
			 	PaxImpactPage.filter.viewOptions("grid").click();
			 	testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
			 	PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).verifyDisplayed(false);
			 	
			 	PaxImpactPage.filter.viewOptions("list").click();
			 	
		}
	}
}
