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
import java.util.ArrayList;
import java.util.List;

public class US140815_ListView_SolutionScreen extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static List<String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray  jsonArray;

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

            ReportLog.setTestCase("Verify PDS Section");
            ReportLog.setTestStep("Verifying Headers");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();

            responseContent = backendAPI.getPayload("Positive Test", "GET/disruptions");

            try {
                if (responseContent.startsWith("[")) {
                    jsonArray = new JSONArray(responseContent);
                } else {
                    ReportLog.addInfo("Can not get response content from service");
                    ReportLog.assertFailed("Step1 is failed since there is no disrupted data");
                }
            } catch (NullPointerException e) {

            }

            /*for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                totalAllList.add(flightID);
            }

              for (int i = 0 ; i < totalAllList.size() ; i++ ){
                PDSListViewPage.listView.disruptedItem(totalAllList.get(i)).listViewCheckBox.click();
            }*/


            JSONObject flightInfo = jsonArray.getJSONObject(0);
            String flightID = flightInfo.getString("flightID");

            PDSListViewPage.listView.disruptedItem(flightID).listViewCheckBox.click();

            //  PaxImpactPage.solve.solveButton.highlight();
            PaxImpactPage.solve.solveButton.click();

        }

        public static void Step2() {

            SolutionScreenPage.solutionPageFrame.impactedFlightsText.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.selectedFlightsText.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.pnrDetailsText.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.selectedFlights.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.totalPNRs.verifyDisplayed(true, 5);

            responseContent = backendAPI.getPayload("Positive Test", "Solve");

            System.out.println("solve_transaction da sira");

            responseContent = backendAPI.getPayload("Positive Test", "Solve_Transaction");


            JSONObject jsonObject = new JSONObject(responseContent);
            JSONObject solutionSummary = jsonObject.getJSONObject("solutionSummary");
            JSONArray pnrs = jsonObject.getJSONArray("pnrs");

            for (int i=0; i<pnrs.length(); i++){
                JSONObject pnrObject = pnrs.getJSONObject(i);
                String confirmationNumber = pnrObject.getString("confirmationNumber");
                SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).verifyDisplayed(true, 5);
                SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).highlight();
            }


        }
    }
}
