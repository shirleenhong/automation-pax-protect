package Sprint24;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.Element;
import auto.framework.web.WebControl;

import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import org.json.JSONObject;


public class US137580_ListView extends TestBase {
	
	public static TestDataHandler testDataHandler;
	
	@Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US137580_ListView.TestCases.PreRequisiteStep();
        US137580_ListView.TestCases.Step1();
        US137580_ListView.TestCases.Step2();
        US137580_ListView.TestCases.Step3();
    }
	
	public static class TestCases 
	{
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
            ReportLog.setTestStep("Verifying Headers");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();
            PaxImpactPage.filter.toggleCheckBox.click();
			PaxImpactPage.headers.headerCheckBox.verifyDisplayed(true, 5);
            PaxImpactPage.headers.impactHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.impactHeader.verifyText("IMPACT");
            PaxImpactPage.headers.flightNumberHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.flightNumberHeader.verifyText("FLIGHT#");
            PaxImpactPage.headers.tailHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.tailHeader.verifyText("TAIL");
            PaxImpactPage.headers.originHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.originHeader.verifyText("ORIG");
            PaxImpactPage.headers.destinationHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.destinationHeader.verifyText("DEST");
            PaxImpactPage.headers.departureHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.departureHeader.verifyText("STD");
            PaxImpactPage.headers.arrivalHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.arrivalHeader.verifyText("STA");
            PaxImpactPage.headers.paxHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.paxHeader.verifyText("PAX");
            PaxImpactPage.headers.misconnectionHeader.verifyDisplayed(true, 5);
            PaxImpactPage.headers.misconnectionHeader.verifyText("MISCONX");
            
            ReportLog.setTestStep("Verify Toggle, Filter, and Solve Button");
            PaxImpactPage.filter.toggleCheckBoxLabel.verifyDisplayed(true, 5);
            PaxImpactPage.filter.toggleCheckBoxLabel.verifyText("Toggle List");
            PaxImpactPage.filter.toggleCheckBox.verifyDisplayed(true, 5);
            PaxImpactPage.filter.filterButton.verifyDisplayed(true, 5);
            PaxImpactPage.filter.filterButton.verifyText("Filters");
            PaxImpactPage.solve.solveButton.verifyDisplayed(false, 5); //False = Solve Button should be disabled
		}
		public static void Step2() throws Exception
		{
			ReportLog.setTestCase("Verify List View Data");
			
            ReportLog.setTestStep("Default Tab");
            //set the ID first in the Dataset > Disruptions > "FlightNumber" column, then edit the number in "RowSelection='DataSet0'" here
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet1'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet2'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet3'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("Delayed Tab");
            PaxImpactPage.summaryDrawer.delayed.click();
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet1'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet2'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet3'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("<15 Mins Tab");
            PaxImpactPage.summaryDrawer.lessThan15Min.click();
            
            ReportLog.setTestStep("<60 Mins tab");
            PaxImpactPage.summaryDrawer.lessThan60Min.click();
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("<180 Tab");
            PaxImpactPage.summaryDrawer.lessThan180Min.click();
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("Cancelled Tab");
            PaxImpactPage.summaryDrawer.cancelled.click();
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet5'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("All Tab");
            PaxImpactPage.summaryDrawer.all.click();
            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            

		}
		
		public static void Step3() throws Exception
		{
			ReportLog.setTestCase("Sorting List View Headers");
            ReportLog.setTestStep("Ascending Order");
            PaxImpactPage.headers.flightNumberHeader.click(); //flights will be sorted depending on the direction of the arrow-ASCENDING, header can be changed
            //ASSERTIONS: set the ID first in the Dataset > Disruptions > "FlightSortAsc" column
            testDataHandler = TestDataHandler.setDisruptionDataSetAsc("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetAsc("Main", "RowSelection='DataSet1'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetAsc("Main", "RowSelection='DataSet2'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetAsc("Main", "RowSelection='DataSet3'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("Descending Order");
            PaxImpactPage.headers.flightNumberHeader.click(); //flights will be sorted in opposite direction of the arrow-DESCENDING, header can be changed
            //ASSERTIONS: set the ID first in the Dataset > Disruptions > "FlightSortDesc" column
            testDataHandler = TestDataHandler.setDisruptionDataSetDesc("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetDesc("Main", "RowSelection='DataSet1'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetDesc("Main", "RowSelection='DataSet2'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetDesc("Main", "RowSelection='DataSet3'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
            ReportLog.setTestStep("Other Header: Ascending Order");
            PaxImpactPage.headers.paxHeader.click(); //flights will be sorted according to the new column headers value-NEW ASCENDING, header can be changed
            //ASSERTIONS: set the ID first in the Dataset > Disruptions > "FlightSortNewAsc" column
            testDataHandler = TestDataHandler.setDisruptionDataSetNewAsc("Main", "RowSelection='DataSet0'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetNewAsc("Main", "RowSelection='DataSet1'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetNewAsc("Main", "RowSelection='DataSet2'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            testDataHandler = TestDataHandler.setDisruptionDataSetNewAsc("Main", "RowSelection='DataSet3'");
            PDSListViewPage.listView.disruptedItem(testDataHandler.flightNumber).verifyDisplayed(true, 5);
            
		}
	}
}
