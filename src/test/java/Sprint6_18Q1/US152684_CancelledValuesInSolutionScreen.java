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

import Sprint26.US140815_ListView_SolutionScreen;
import pageobjects.GESSOAuthPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import pageobjects.SolutionScreenPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class US152684_CancelledValuesInSolutionScreen extends TestBase
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
        US152684_CancelledValuesInSolutionScreen.TestCases.PreRequisiteStep();
        US152684_CancelledValuesInSolutionScreen.TestCases.Step1();
        US152684_CancelledValuesInSolutionScreen.TestCases.Step2();

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
    		ReportLog.setTestCase("Select then solve Cancelled flights in PDS");
    			ReportLog.setTestStep("Filter Cancelled flights by clicking the Cancelled tab");
    			PaxImpactPage.summaryDrawer.cancelled.click();
    			
    			ReportLog.setTestStep("Select Cancelled flightsb then click Solve");

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
                        //flightID = flightInfo.getString("flightID");
                }

                for (int i = 0 ; i < totalAllList.size() ; i++ ){
                        PDSListViewPage.listView.disruptedItem(totalAllList.get(i)).click();
                }                
                responseContent = backendAPI.getPayload("Positive Test", "GET/disruptions");

                try {
                    if (responseContent.length() > 2) {
                        jsonArray = new JSONArray(responseContent);
                    } else {
                        ReportLog.addInfo("Can not get response content from service");
                        ReportLog.assertFailed("Step1 is failed since there is no disrupted data");
                    }
                } catch (NullPointerException e) {

                }


                JSONObject flightInfo = jsonArray.getJSONObject(0);
                flightID = flightInfo.getString("flightID");
                
                PaxImpactPage.solve.solveButton.click();
    	}
    	
    	 public static void Step2() throws InterruptedException
    	 {
    		 ReportLog.setTestCase("Verify Solution Screen List View and Commit Functionality");
             	ReportLog.setTestStep("Verifying All PNRs and Impact Type");
                SolutionScreenPage.solutionPageFrame.impactedFlightsText.verifyDisplayed(true, 5);
                SolutionScreenPage.solutionPageFrame.selectedFlightsText.verifyDisplayed(true, 5);
                SolutionScreenPage.solutionPageFrame.pnrDetailsText.verifyDisplayed(true, 5);
                SolutionScreenPage.solutionPageFrame.selectedFlights.verifyDisplayed(true, 5);
                SolutionScreenPage.solutionPageFrame.totalPNRs.verifyDisplayed(true, 5);

                String requestBody = "{   \"tenant\" : \"zz\",   \"user\" : \"pinar\",   \"flights\" : [\"" + flightID + "\"]}";

                responseContent = backendAPI.getPayloadWithProperty("Positive Test", "Solve","request_solve", "Request", requestBody);

                JSONObject jsonObjectS = new JSONObject(responseContent);
                String transactionId = jsonObjectS.getString("transactionId");


                responseContent = backendAPI.getPayloadWithProperty("Positive Test", "Solve_Transaction","request_solve_transaction", "transactionid", transactionId);


                JSONObject jsonObjectST = new JSONObject(responseContent);
                JSONArray pnrs = jsonObjectST.getJSONArray("pnrs");

                boolean oneCommitDone = false;

                for (int i=0; i<pnrs.length(); i++){
                    JSONObject pnrObject = pnrs.getJSONObject(i);
                    String confirmationNumber = pnrObject.getString("confirmationNumber");
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).verifyDisplayed(true, 5);
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).highlight();
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).pnrCommitButton.highlight();
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).impactType.verifyText("Cancelled");
//                    if (!SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).pnrCommitButton.getCssValue("cursor").equals("not-allowed") && !oneCommitDone){
//                        SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).pnrCommitButton.click();
//                        SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).commitResponse.verifyDisplayed(true,5);
//                        SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).commitResponse.highlight();
//                        if (SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).commitResponse.getText().equals("REBOOKED")){
//                            ReportLog.assertTrue(true, "Pnr commit successfully");
//                            oneCommitDone = true;
//                        }else{
//                            ReportLog.assertFailed("Pnr commit failed");
//                        }
//                    }else{
//                        ReportLog.addInfo("Button disabled");
//                    }

                }
    	 }
    }

}
