package Sprint26;

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

public class US137581_GridView extends TestBase {
	
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
        US137581_GridView.TestCases.PreRequisiteStep();
        US137581_GridView.TestCases.Step1();
        US137581_GridView.TestCases.Step2();
        US137581_GridView.TestCases.Step3();
        US137581_GridView.TestCases.Step4();
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
	            ReportLog.setTestStep("Verify Toggle, Filter, and Solve Button");
	            //PaxImpactPage.filter.toggleCheckBoxLabel.verifyDisplayed(true, 5);
	            //PaxImpactPage.filter.toggleCheckBoxLabel.verifyText("Toggle List");
	            PaxImpactPage.filter.toggleCheckBox.verifyDisplayed(true, 5);
	            PaxImpactPage.filter.filterButton.verifyDisplayed(true, 5);
	            PaxImpactPage.filter.filterButton.verifyText("Filters");
	            PaxImpactPage.solve.solveButton.verifyDisplayed(false, 5); //False = Solve Button should be disabled
	            
	            PaxImpactPage.filter.viewOptions("list");
	            PaxImpactPage.filter.viewOptions("grid");
	            
	            PaxImpactPage.filter.viewOptions("list").click();
	            PaxImpactPage.filter.viewOptions("grid").click();
		}
		
		public static void Step2() throws Exception
		{
			ReportLog.setTestCase("Verify Grid View Data");
			
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
                    PDSGridViewPage.gridView.disruptedItemGridView(totalAllList.get(i)).verifyDisplayed(true, 5);
            }
            
            ReportLog.setTestStep("Delayed Tab");
	            PaxImpactPage.summaryDrawer.delayed.click();
	
	                List <String> totalDelayedList = new ArrayList<String>();
	                List <String> totalGreaterThan15MinList = new ArrayList<String>();
	                List <String> totalGreaterThan60MinList = new ArrayList<String>();
	                List <String> totalGreaterThan180MinList = new ArrayList<String>();
	                List <String> totalCancelledList = new ArrayList<String>();
	
	
	                for (int i = 0 ; i < jsonArray.length() ; i++ ){
	                        JSONObject flightInfo = jsonArray.getJSONObject(i);
	                        String flightID = flightInfo.getString("flightID");
	                        JSONObject disruption = flightInfo.getJSONObject("disruption");
	
	                        if (disruption.toMap().containsKey("delay")) {
	                                totalDelayedList.add(flightID);
	                                JSONObject delay = disruption.getJSONObject("delay");
	
	                                int departureDelayMinutes = delay.getInt("departureDelayMinutes");
	
	                                if ((departureDelayMinutes >= 15) && (departureDelayMinutes < 60)) totalGreaterThan15MinList.add(flightID);
	                                else if ((departureDelayMinutes >= 60) && (departureDelayMinutes < 180)) totalGreaterThan60MinList.add(flightID);
	                                else if (departureDelayMinutes >= 180) totalGreaterThan180MinList.add(flightID);
	                        }
	                        else if (disruption.toMap().containsKey("cancel")){
	                                totalCancelledList.add(flightID);
	                        }
	                }
	
	                if (totalDelayedList.size() > 0){
	                        for (int i = 0 ; i < totalDelayedList.size() ; i++ ){
	                        	PDSGridViewPage.gridView.disruptedItemGridView(totalDelayedList.get(i)).verifyDisplayed(true, 5);
	                        }
	                }
              
                ReportLog.setTestStep("<15 Mins Tab");
	                PaxImpactPage.summaryDrawer.greaterThan15Min.click();
	
	                    if (totalGreaterThan15MinList.size() > 0){
	                            for (int i = 0 ; i < totalGreaterThan15MinList.size() ; i++ ){
	                            	PDSGridViewPage.gridView.disruptedItemGridView(totalGreaterThan15MinList.get(i)).verifyDisplayed(true, 5);
	                            }
	                    }
                ReportLog.setTestStep("<60 Mins tab");
                    PaxImpactPage.summaryDrawer.greaterThan60Min.click();

                        if (totalGreaterThan60MinList.size() > 0){
                                for (int i = 0 ; i < totalGreaterThan60MinList.size() ; i++ ){
                                	PDSGridViewPage.gridView.disruptedItemGridView(totalGreaterThan60MinList.get(i)).verifyDisplayed(true, 5);
                                }
                        }
                ReportLog.setTestStep("<180 Tab");
                        PaxImpactPage.summaryDrawer.greaterThan180Min.click();
                            if (totalGreaterThan180MinList.size() > 0){
                                    for (int i = 0 ; i < totalGreaterThan180MinList.size() ; i++ ){
                                    	PDSGridViewPage.gridView.disruptedItemGridView(totalGreaterThan180MinList.get(i)).verifyDisplayed(true, 5);
                                    }
                            }


                        
                ReportLog.setTestStep("Cancelled Tab");
                        PaxImpactPage.summaryDrawer.cancelled.click();

                            if (totalCancelledList.size() > 0){
                                    for (int i = 0 ; i < totalCancelledList.size() ; i++ ){
                                    	PDSGridViewPage.gridView.disruptedItemGridView(totalCancelledList.get(i)).verifyDisplayed(true, 5);
                                    }
                            }

                        
                ReportLog.setTestStep("All Tab");
                        PaxImpactPage.summaryDrawer.all.click();

                            if (totalAllList.size() > 0){
                                    for (int i = 0 ; i < totalAllList.size() ; i++ ){
                                    	PDSGridViewPage.gridView.disruptedItemGridView(totalAllList.get(i)).verifyDisplayed(true, 5);
                                    }
                            }
		}
		
		public static void Step3() throws Exception
		{
			List <String> flightItemsUI = new ArrayList<String>();
			
			ReportLog.setTestCase("Sorting List View Headers");
	            ReportLog.setTestStep("Ascending Order");
	            PaxImpactPage.headers.flightNumberHeader.click(); //flights will be sorted depending on the direction of the arrow-ASCENDING by FLIGHT header
	            
	            //Getting flight item list by ascending order from UI
	            SearchContext searchContext = WebManager.getDriver();
	            List<WebElement> elements = ((SearchContext)searchContext).findElements(By.tagName("ppro-flight-item"));

	            for (int i = 0 ; i < elements.size() ; i++ ){
	                flightItemsUI.add(elements.get(i).getText());
	            }

	            // Getting flight numbers  from web service response and stored in list
	            List <String> totalFligtNumberList = new ArrayList<String>();

	            for (int i = 0 ; i < jsonArray.length() ; i++ ){
	                JSONObject flightInfo = jsonArray.getJSONObject(i);
	                JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
	                String flightNumber = flightDetails.getString("flightNumber");
	                totalFligtNumberList.add(flightNumber);
	            }

	            // Sorting the flight numbers that have been got from web service response in the array
	            String[] totalFlightNumberArray = totalFligtNumberList.toArray(new String[0]);

	            Arrays.sort(totalFlightNumberArray);

	            // Checking if sorted array and sorted UI is equal
	            for (int i = 0 ; i < totalFlightNumberArray.length ; i++ ){
	                if (flightItemsUI.get(i).contains(totalFlightNumberArray[i])){
	                    ReportLog.assertTrue(true, "Ascendant Sorting for " + flightItemsUI.get(i) + " is Successful" );
	                }
	                else{
	                    ReportLog.assertTrue(false, "Ascendant Sorting for " + flightItemsUI.get(i) + " is Failed" );
	                }
	            }
	            
	            ReportLog.setTestStep("Descending Order");
	            PaxImpactPage.headers.flightNumberHeader.click(); //flights will be sorted in opposite direction of the arrow-DESCENDING by FLIGHT header

	            //Getting flight list by descending order from UI
	            flightItemsUI.clear();
	            elements = ((SearchContext)searchContext).findElements(By.tagName("ppro-flight-item"));

	            for (int i = 0 ; i < elements.size() ; i++ ){
	                flightItemsUI.add(elements.get(i).getText());
	            }


	            // Checking if reverse sorted array and sorted UI is equal
	            for (int i = 0 ; i < totalFlightNumberArray.length ; i++ ){
	                if (flightItemsUI.get(i).contains(totalFlightNumberArray[(totalFlightNumberArray.length -1)-i])){
	                    ReportLog.assertTrue(true, "Descendant Sorting for " + flightItemsUI.get(i) + " is Successful" );
	                }
	                else{
	                    ReportLog.assertTrue(false, "Descendant Sorting for " + flightItemsUI.get(i) + " is Failed" );
	                }
	            }
	            
	            ReportLog.setTestStep("Other Header: Ascending Order");
	            PaxImpactPage.headers.paxHeader.click(); //flights will be sorted according to the new column headers value-NEW ASCENDING by Flight header

	            flightItemsUI.clear();
	            elements = ((SearchContext)searchContext).findElements(By.tagName("ppro-pax-count"));

	            for (int i = 0 ; i < elements.size() ; i=i+2 ){
	                String paxAndMconXInfo = elements.get(i).getText();
	                int indexF = paxAndMconXInfo.indexOf('F');
	                int indexJ = paxAndMconXInfo.indexOf('J');
	                int indexY = paxAndMconXInfo.indexOf('Y');
	                int fCount = Integer.parseInt(paxAndMconXInfo.substring(indexF+1,indexJ-1));
	                int jCount = Integer.parseInt(paxAndMconXInfo.substring(indexJ+1,indexY-1));
	                int yCount = Integer.parseInt(paxAndMconXInfo.substring(indexY+1));
	                flightItemsUI.add((Integer.toString(fCount+jCount+yCount)));
	                //flightItemsUI.add(elements.get(i).getText().substring(index));
	            }

	            List <Integer> totalPAXList = new ArrayList<Integer>();

	            for (int i = 0 ; i < jsonArray.length() ; i++ ){
	                JSONObject flightInfo = jsonArray.getJSONObject(i);
	                JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
	                int coachCompartmentPax = flightDetails.getInt("coachCompartmentPax");
	                int businessCompartmentPax = flightDetails.getInt("businessCompartmentPax");
	                int firstCompartmentPax = flightDetails.getInt("firstCompartmentPax");
	                totalPAXList.add(coachCompartmentPax + businessCompartmentPax + firstCompartmentPax);
	            }

	            // Sorting the flight numbers that got from web service response in the array
	            Integer[] totalPAXArray = totalPAXList.toArray(new Integer[0]);

	            Arrays.sort(totalPAXArray);

	            for (int i = 0 ; i < totalPAXArray.length ; i++ ){
	                //if (flightItemsUI.get(i).contains(totalPAXArray[i].toString())){
	                if (flightItemsUI.get(i).equals(totalPAXArray[i].toString())){
	                    ReportLog.assertTrue(true, "Ascendant Sorting for " + flightItemsUI.get(i) + " is Successful" );
	                }
	                else{
	                    ReportLog.assertTrue(false, "Ascendant Sorting for " + flightItemsUI.get(i) + " is Failed" );
	                }
	            }
		}
		
		public static void Step4() throws Exception
		{
			ReportLog.setTestCase("To be able to select the flights in Grid View");
			 ReportLog.setTestStep("Select Several Delayed Flights");
//	            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet1'");
//	            PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).click();
//	            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet4'");
//	            PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).click();
//	            testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet5'");
//	            PDSGridViewPage.gridView.disruptedItemGridView(testDataHandler.flightNumber).click();
			 	
			 	PaxImpactPage.summaryDrawer.delayed.click();
			 	//Get the flightID Data
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
	                    //PDSGridViewPage.gridView.disruptedItemGridView(totalAllList.get(i)).gridViewCheckBox.click();
	                    PDSGridViewPage.gridView.disruptedItemGridView(totalAllList.get(i)).click();
	            }
	         
	         ReportLog.setTestStep("Solve Button Should Be Enabled");
	         	PaxImpactPage.solve.solveButton.verifyDisplayed(true, 5);
			 
		}
	}

}
