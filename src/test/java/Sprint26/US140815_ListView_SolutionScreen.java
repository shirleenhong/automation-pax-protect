package Sprint26;

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
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import pageobjects.SolutionScreenPage;

import java.net.MalformedURLException;

public class US140815_ListView_SolutionScreen extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static String responseContent;
    public static JSONArray  jsonArray;
    public static String flightID;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US140815_ListView_SolutionScreen.TestCases.PreRequisiteStep();
        US140815_ListView_SolutionScreen.TestCases.Step1();
        US140815_ListView_SolutionScreen.TestCases.Step2();

    }

    public static class TestCases {
        public static void PreRequisiteStep() throws InterruptedException, MalformedURLException {
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

        public static void Step1() {

            ReportLog.setTestCase("Navigate to Solution Screen Page");
            ReportLog.setTestStep("Click to Solve button");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();

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

            PDSListViewPage.listView.disruptedItem(flightID).listViewCheckBox.click();

            //  PaxImpactPage.solve.solveButton.highlight();
            PaxImpactPage.solve.solveButton.click();

        }

        public static void Step2() {
            ReportLog.setTestCase("Verify Solution Screen List View and Commit Functionality");
            ReportLog.setTestStep("Verifying All PNRs and Commit an Available PNR");

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
                if (!SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).pnrCommitButton.getCssValue("cursor").equals("not-allowed") && !oneCommitDone){
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).pnrCommitButton.click();
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).commitResponse.verifyDisplayed(true,5);
                    SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).commitResponse.highlight();
                    if (SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).commitResponse.getText().equals("REBOOKED")){
                        ReportLog.assertTrue(true, "Pnr commit successfully");
                        oneCommitDone = true;
                    }else{
                        ReportLog.assertFailed("Pnr commit failed");
                    }
                }else{
                    ReportLog.addInfo("Button disabled");
                }

            }

        }
    }
}
