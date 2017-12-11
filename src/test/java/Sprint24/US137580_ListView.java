package Sprint24;

import auto.framework.WebManager;
import auto.framework.web.Element;
import common.BackendAPI;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;

import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import org.json.JSONObject;


public class US137580_ListView extends TestBase {
	
	public static TestDataHandler testDataHandler;
	public static BackendAPI backendAPI = new BackendAPI();

    public static List <String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray  jsonArray;
	
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


                responseContent = backendAPI.getPayload("Positive Test","GET/disruptions");

                try {
                        if (responseContent.startsWith("[")) {
                                jsonArray = new JSONArray(responseContent);
                        } else {
                                ReportLog.addInfo("Can not get response content from service");
                                ReportLog.assertFailed("Step2 is failed");
                        }
                }catch(NullPointerException e){

                }

                for (int i = 0 ; i < jsonArray.length() ; i++ ){
                        JSONObject flightInfo = jsonArray.getJSONObject(i);
                        String flightID = flightInfo.getString("flightID");
                        totalAllList.add(flightID);
                }

                for (int i = 0 ; i < totalAllList.size() ; i++ ){
                        PDSListViewPage.listView.disruptedItem(totalAllList.get(i)).verifyDisplayed(true, 5);
                }




            ReportLog.setTestStep("Delayed Tab");
            PaxImpactPage.summaryDrawer.delayed.click();

                List <String> totalDelayedList = new ArrayList<String>();
                List <String> totalLessThan15MinList = new ArrayList<String>();
                List <String> totalLessThan60MinList = new ArrayList<String>();
                List <String> totalLessThan180MinList = new ArrayList<String>();
                List <String> totalCancelledList = new ArrayList<String>();


                for (int i = 0 ; i < jsonArray.length() ; i++ ){
                        JSONObject flightInfo = jsonArray.getJSONObject(i);
                        String flightID = flightInfo.getString("flightID");
                        JSONObject disruption = flightInfo.getJSONObject("disruption");

                        if (disruption.toMap().containsKey("delay")) {
                                totalDelayedList.add(flightID);
                                JSONObject delay = disruption.getJSONObject("delay");

                                int departureDelayMinutes = delay.getInt("departureDelayMinutes");

                                if (departureDelayMinutes <= 15) totalLessThan15MinList.add(flightID);
                                else if (departureDelayMinutes <= 60) totalLessThan60MinList.add(flightID);
                                else if (departureDelayMinutes <= 180) totalLessThan180MinList.add(flightID);
                        }
                        else if (disruption.toMap().containsKey("cancel")){
                                totalCancelledList.add(flightID);
                        }
                }

                if (totalDelayedList.size() > 0){
                        for (int i = 0 ; i < totalDelayedList.size() ; i++ ){
                                PDSListViewPage.listView.disruptedItem(totalDelayedList.get(i)).verifyDisplayed(true, 5);
                        }
                }


            
            ReportLog.setTestStep("<15 Mins Tab");
            PaxImpactPage.summaryDrawer.lessThan15Min.click();

                if (totalLessThan15MinList.size() > 0){
                        for (int i = 0 ; i < totalLessThan15MinList.size() ; i++ ){
                                PDSListViewPage.listView.disruptedItem(totalLessThan15MinList.get(i)).verifyDisplayed(true, 5);
                        }
                }
            
            ReportLog.setTestStep("<60 Mins tab");
            PaxImpactPage.summaryDrawer.lessThan60Min.click();

                if (totalLessThan60MinList.size() > 0){
                        for (int i = 0 ; i < totalLessThan60MinList.size() ; i++ ){
                                PDSListViewPage.listView.disruptedItem(totalLessThan60MinList.get(i)).verifyDisplayed(true, 5);
                        }
                }


            
            ReportLog.setTestStep("<180 Tab");
            PaxImpactPage.summaryDrawer.lessThan180Min.click();

                if (totalLessThan180MinList.size() > 0){
                        for (int i = 0 ; i < totalLessThan180MinList.size() ; i++ ){
                                PDSListViewPage.listView.disruptedItem(totalLessThan180MinList.get(i)).verifyDisplayed(true, 5);
                        }
                }


            
            ReportLog.setTestStep("Cancelled Tab");
            PaxImpactPage.summaryDrawer.cancelled.click();

                if (totalCancelledList.size() > 0){
                        for (int i = 0 ; i < totalCancelledList.size() ; i++ ){
                                PDSListViewPage.listView.disruptedItem(totalCancelledList.get(i)).verifyDisplayed(true, 5);
                        }
                }

            
            ReportLog.setTestStep("All Tab");
            PaxImpactPage.summaryDrawer.all.click();

                if (totalAllList.size() > 0){
                        for (int i = 0 ; i < totalAllList.size() ; i++ ){
                                PDSListViewPage.listView.disruptedItem(totalAllList.get(i)).verifyDisplayed(true, 5);
                        }
                }

            

		}
		
		public static void Step3() throws Exception
		{

            List <String> flightNumbersUI = new ArrayList<String>();

			ReportLog.setTestCase("Sorting List View Headers");
            ReportLog.setTestStep("Ascending Order");
            PaxImpactPage.headers.flightNumberHeader.click(); //flights will be sorted depending on the direction of the arrow-ASCENDING by Flight header


            //Getting flight list by ascending order from UI
            SearchContext searchContext = WebManager.getDriver();
            List<WebElement> elements = ((SearchContext)searchContext).findElements(By.tagName("ppro-flight-item"));

            for (int i = 0 ; i < elements.size() ; i++ ){
                flightNumbersUI.add(elements.get(i).getText());
            }

            // Getting flight numbers  from web service response and stored in list
            List <String> totalFligtNumberList = new ArrayList<String>();

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
                String flightNumber = flightDetails.getString("flightNumber");
                totalFligtNumberList.add(flightNumber);
            }

            // Sorting the flight numbers that got from web service response in the array
            String[] totalFlightNumberArray = totalFligtNumberList.toArray(new String[0]);

            Arrays.sort(totalFlightNumberArray);

            // Checking if sorted array and sorted UI is equal
            for (int i = 0 ; i < totalFlightNumberArray.length ; i++ ){
                if (flightNumbersUI.get(i).contains(totalFlightNumberArray[i])){
                    ReportLog.assertTrue(true, "Ascendant Sorting for " + flightNumbersUI.get(i) + " Success" );
                }
                else{
                    ReportLog.assertTrue(false, "Ascendant Sorting for " + flightNumbersUI.get(i) + " Failed" );
                }
            }


            ReportLog.setTestStep("Descending Order");
            PaxImpactPage.headers.flightNumberHeader.click(); //flights will be sorted in opposite direction of the arrow-DESCENDING by Flight header

            //Getting flight list by descending order from UI
            flightNumbersUI.clear();
            elements = ((SearchContext)searchContext).findElements(By.tagName("ppro-flight-item"));

            for (int i = 0 ; i < elements.size() ; i++ ){
                flightNumbersUI.add(elements.get(i).getText());
            }


            // Checking if reverse sorted array and sorted UI is equal
            for (int i = 0 ; i < totalFlightNumberArray.length ; i++ ){
                if (flightNumbersUI.get(i).contains(totalFlightNumberArray[(totalFlightNumberArray.length -1)-i])){
                    ReportLog.assertTrue(true, "Descendant Sorting for " + flightNumbersUI.get(i) + " Success" );
                }
                else{
                    ReportLog.assertTrue(false, "Descendant Sorting for " + flightNumbersUI.get(i) + " Failed" );
                }
            }


            
            ReportLog.setTestStep("Other Header: Ascending Order");
            PaxImpactPage.headers.paxHeader.click(); //flights will be sorted according to the new column headers value-NEW ASCENDING by Flight header

            flightNumbersUI.clear();
            elements = ((SearchContext)searchContext).findElements(By.tagName("ppro-flight-item"));

            for (int i = 0 ; i < elements.size() ; i++ ){
                String tempString = elements.get(i).getText();
                int index = tempString.indexOf('Y');
                flightNumbersUI.add(elements.get(i).getText().substring(index));
            }

            List <Integer> totalPAXList = new ArrayList<Integer>();

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
                int coachCompartmentPax = flightDetails.getInt("coachCompartmentPax");
                totalPAXList.add(coachCompartmentPax);
            }

            // Sorting the flight numbers that got from web service response in the array
            Integer[] totalPAXArray = totalPAXList.toArray(new Integer[0]);

            Arrays.sort(totalPAXArray);

            int f = 0;
            for (int i = 0 ; i < totalPAXArray.length ; i++ ){
                if (flightNumbersUI.get(i).contains(totalPAXArray[i].toString())){
                    ReportLog.assertTrue(true, "Ascendant Sorting for " + flightNumbersUI.get(i) + " Success" );
                }
                else{
                    ReportLog.assertTrue(false, "Ascendant Sorting for " + flightNumbersUI.get(i) + " Failed" );
                }
            }
            
		}
	}
}
