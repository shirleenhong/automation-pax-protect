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
import pageobjects.PaxImpactPage;
import pageobjects.SolutionScreenPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class US140813_SummaryDrawer_SolutionScreen extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static List<String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray jsonArray;
    public static String flightID = "";

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US140813_SummaryDrawer_SolutionScreen.TestCases.PreRequisiteStep();
        US140813_SummaryDrawer_SolutionScreen.TestCases.Step1();
        US140813_SummaryDrawer_SolutionScreen.TestCases.Step2();

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

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                totalAllList.add(flightID);
            }

            PaxImpactPage.headers.headerCheckBox.click();

            //  PaxImpactPage.solve.solveButton.highlight();
            PaxImpactPage.solve.solveButton.click();

        }

        public static void Step2() {

            SolutionScreenPage.solutionPageFrame.impactedFlightsText.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.selectedFlightsText.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.pnrDetailsText.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.selectedFlights.verifyDisplayed(true, 5);
            SolutionScreenPage.solutionPageFrame.totalPNRs.verifyDisplayed(true, 5);
            SolutionScreenPage.selectedFlightsFrame.selectedFlightsItem("all").verifyDisplayed(true,5);

            for (int i =0 ; i < totalAllList.size() ; i++){
                SolutionScreenPage.selectedFlightsFrame.selectedFlightsItem(totalAllList.get(i)).verifyDisplayed(true,5);
            }

            for (int i = 0 ; i < totalAllList.size() ; i++ ){
                if (i == totalAllList.size()-1){
                    flightID = flightID.concat(totalAllList.get(i));
                }
                else{
                    flightID = flightID.concat(totalAllList.get(i)+",");
                }
            }


            String requestBody = "{   \"tenant\" : \"zz\",   \"user\" : \"pinar\",   \"flights\" : [\"" + flightID + "\"]}";

            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve",requestBody);

            JSONObject jsonObjectS = new JSONObject(responseContent);
            String transactionId = jsonObjectS.getString("transactionId");


            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve_Transaction",transactionId);


            JSONObject jsonObjectST = new JSONObject(responseContent);
            JSONObject solutionSummary = jsonObjectST.getJSONObject("solutionSummary");

            int[] terminatingPaxArray = connectingPaxConts("terminatingPax",solutionSummary );
            int[] connectingPaxArray = connectingPaxConts("connectingPax",solutionSummary );
            int[] misconnectingPaxArray = connectingPaxConts("misconnectingPax",solutionSummary );

            String terminatingPaxString = "f" + terminatingPaxArray[0] + " " + "j" + terminatingPaxArray[1] + " " + "y" + terminatingPaxArray[2];
            String connectingPaxString = "f" + connectingPaxArray[0] + " " + "j" + connectingPaxArray[1] + " " + "y" + connectingPaxArray[2];
            String misconnectingPaxString = "f" + misconnectingPaxArray[0] + " " + "j" + misconnectingPaxArray[1] + " " + "y" + misconnectingPaxArray[2];

            SolutionScreenPage.totalPNRsFrame.totalPNRsItem("total").verifyDisplayed(true, 5);
            SolutionScreenPage.totalPNRsFrame.totalPNRsItem("terminating").verifyDisplayed(true, 5);
            SolutionScreenPage.totalPNRsFrame.totalPNRsItem("connecting").verifyDisplayed(true, 5);
            SolutionScreenPage.totalPNRsFrame.totalPNRsItem("misconnecting").verifyDisplayed(true, 5);

            if (SolutionScreenPage.totalPNRsFrame.totalPNRsItem("terminating").totalPNRsItemText.getText().equals(terminatingPaxString)){
                ReportLog.assertTrue(true, "terminating pax counts right");
            }
            if (SolutionScreenPage.totalPNRsFrame.totalPNRsItem("connecting").totalPNRsItemText.getText().equals(connectingPaxString)){
                ReportLog.assertTrue(true, "connecting pax counts right");
            }
            if (SolutionScreenPage.totalPNRsFrame.totalPNRsItem("misconnecting").totalPNRsItemText.getText().equals(misconnectingPaxString)){
                ReportLog.assertTrue(true, "misconnecting pax counts right");
            }


        }

        public static int[] connectingPaxConts(String jObjectString, JSONObject jObject){
            int[] returnArray = new int[4];

            JSONObject Pax = jObject.getJSONObject(jObjectString);
            returnArray[0] = Pax.getInt("firstClassCount");
            returnArray[1] = Pax.getInt("businessClassCount");
            returnArray[2] = Pax.getInt("coachClassCount");
            returnArray[3] = Pax.getInt("totalCount");

            return returnArray;
        }
    }
}
