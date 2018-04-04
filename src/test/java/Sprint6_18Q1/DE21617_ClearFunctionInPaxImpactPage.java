package Sprint6_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import pageobjects.GESSOAuthPage;
import pageobjects.PDSGridViewPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import pageobjects.FilteringWindowPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class DE21617_ClearFunctionInPaxImpactPage extends TestBase
{
	public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static String flightID;
    public static List <String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray  jsonArray;

    
	@Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");        
        DE21617_ClearFunctionInPaxImpactPage.TestCases.PreRequisiteStep();
        DE21617_ClearFunctionInPaxImpactPage.TestCases.Step1();
        DE21617_ClearFunctionInPaxImpactPage.TestCases.Step2();
        DE21617_ClearFunctionInPaxImpactPage.TestCases.Step3();
        DE21617_ClearFunctionInPaxImpactPage.TestCases.Step4();
    }
	
	public static class TestCases 
	{
		public static void PreRequisiteStep() throws InterruptedException, MalformedURLException 
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
		
	    public static void Step1() throws InterruptedException
	    {
	        ReportLog.setTestCase("Verifying Filter Window");
	        ReportLog.setTestStep("Verify Filter Window's Dropdown and Buttons");
	        Thread.sleep(5000);
	        GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();
	        PaxImpactPage.filter.filterButton.click();
	        FilteringWindowPage.filteringWindow.title.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.destination.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.origin.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.flightNum.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.impactType.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.cancelButton.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.cancelButton.highlight();
	        FilteringWindowPage.filteringWindow.applyButton.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.applyButton.highlight();
	        FilteringWindowPage.filteringWindow.clearButton.verifyDisplayed(true, 5);
	        FilteringWindowPage.filteringWindow.clearButton.highlight();
	    }
	    
        public static void Step2()
        {
            ReportLog.setTestCase("Select a Value in a Single Parameter then Clear");
            ReportLog.setTestStep("Select and Clear Impact Type Options");
            FilteringWindowPage.filteringWindow.impactType.click();
            FilteringWindowPage.filteringWindow.cancelledOption.click();
            FilteringWindowPage.filteringWindow.clearButton.click();
            
            FilteringWindowPage.filteringWindow.impactType.verifyText("Impact Type");

        }
        
        public static void Step3() throws InterruptedException
        {
            ReportLog.setTestCase("Select a Value in a Single Parameter then Clear");
            ReportLog.setTestStep("Select and Clear Flight Number Options");
            
            
            responseContent = backendAPI.getPayload("Positive Test","GET/disruptions");

            try {
                    if (responseContent.startsWith("[")) {
                            jsonArray = new JSONArray(responseContent);
                    } else {
                            ReportLog.addInfo("Can not get response content from service");
                            ReportLog.assertFailed("Step2 is failed");
                    }
            }
            
            catch(NullPointerException e)
            {

            }
            
            FilteringWindowPage.filteringWindow.flightNum.click();
            
            JSONObject flightInfo = jsonArray.getJSONObject(0);
            JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
            String flightNumber = flightDetails.getString("flightNumber");            
            FilteringWindowPage.filteringWindow.selectedFlightNumber(flightNumber).verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.selectedFlightNumber(flightNumber).click();
            
            Thread.sleep(5000);

            FilteringWindowPage.filteringWindow.clearButton.click();   
            FilteringWindowPage.filteringWindow.flightNum.verifyText("Flight #");
            
            Thread.sleep(5000);
        }
        
        public static void Step4() throws InterruptedException
        {
            ReportLog.setTestCase("Select Multiple Values in a Parameter then Clear");
            ReportLog.setTestStep("Select and Clear All Parameters' Options");
            
            
            responseContent = backendAPI.getPayload("Positive Test","GET/disruptions");

            try {
                    if (responseContent.startsWith("[")) {
                            jsonArray = new JSONArray(responseContent);
                    } else {
                            ReportLog.addInfo("Can not get response content from service");
                            ReportLog.assertFailed("Step2 is failed");
                    }
            }
            
            catch(NullPointerException e)
            {

            }
            
            //Select Destination
            FilteringWindowPage.filteringWindow.destination.click();
            JSONObject flightInfo = jsonArray.getJSONObject(0);
            JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
            String destination = flightDetails.getString("destination");            
            FilteringWindowPage.filteringWindow.selectedDestination(destination).verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.selectedDestination(destination).click();
            Thread.sleep(5000);
            
            //Select Origin
            FilteringWindowPage.filteringWindow.origin.click();
            JSONObject flightInfoOrigin = jsonArray.getJSONObject(0);
            JSONObject flightDetailsOrigin = flightInfoOrigin.getJSONObject("flightDetails");
            String origin = flightDetailsOrigin.getString("origin");            
            FilteringWindowPage.filteringWindow.selectedOrigin(origin).verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.selectedOrigin(origin).click();
            Thread.sleep(5000);
            
            //Select Flight Number
            FilteringWindowPage.filteringWindow.flightNum.click();
            JSONObject flightInfoFlight = jsonArray.getJSONObject(0);
            JSONObject flightDetailsFlight = flightInfoFlight.getJSONObject("flightDetails");
            String flightNumber = flightDetailsFlight.getString("flightNumber");            
            FilteringWindowPage.filteringWindow.selectedFlightNumber(flightNumber).verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.selectedFlightNumber(flightNumber).click();
            Thread.sleep(5000);
            
            FilteringWindowPage.filteringWindow.impactType.click();
            FilteringWindowPage.filteringWindow.delayedOption.click();
            
            FilteringWindowPage.filteringWindow.clearButton.click();   
            FilteringWindowPage.filteringWindow.destination.verifyText("Destination");
            FilteringWindowPage.filteringWindow.origin.verifyText("Origin");
            FilteringWindowPage.filteringWindow.flightNum.verifyText("Flight #");
            FilteringWindowPage.filteringWindow.impactType.verifyText("Impact Type");
            
            Thread.sleep(5000);
        }
        
	}

}
