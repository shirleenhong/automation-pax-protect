package Sprint3_18Q1;

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

public class US153948_Selected_Flights_Component extends TestBase {

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
        US153948_Selected_Flights_Component.TestCases.PreRequisiteStep();
        US153948_Selected_Flights_Component.TestCases.Step1();
        //US153948_Selected_Flights_Component.TestCases.Step2();
        US153948_Selected_Flights_Component.TestCases.Step3();

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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                totalAllList.add(flightID);
            }

            PaxImpactPage.headers.headerCheckBox.click();

            //  PaxImpactPage.solve.solveButton.highlight();
            PaxImpactPage.solve.solveButton.click();

        }

        public static void Step3() {

            ReportLog.setTestCase("Verify Solution Screen Summary Drawer");
            ReportLog.setTestStep("Verifying All Pnrs");

            SolutionScreenPage.selectedFlightsFrame.selectedFlightsItem("all").click();

            for (int i = 0; i < totalAllList.size(); i++) {

                if (i == totalAllList.size() - 1) {
                    flightID = flightID.concat("\"" + totalAllList.get(i) + "\"");
                } else {
                    flightID = flightID.concat("\"" + totalAllList.get(i) + "\"" + ",");
                }
            }

            String requestBody = "{   \"tenant\" : \"zz\",   \"user\" : \"pinar\",   \"flights\" : [ " + flightID + " ]}";

            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve", requestBody);

            JSONObject jsonObjectS = new JSONObject(responseContent);
            String transactionId = jsonObjectS.getString("transactionId");


            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve_Transaction", transactionId);


            JSONObject jsonObjectST = new JSONObject(responseContent);

            JSONArray pnrs = jsonObjectST.getJSONArray("pnrs");

            for (int i=0; i<pnrs.length(); i++){
                JSONObject pnrObject = pnrs.getJSONObject(i);
                String confirmationNumber = pnrObject.getString("confirmationNumber");
                SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).verifyDisplayed(true);
                SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).highlight();
            }


        }

        public static void Step2() {

            ReportLog.setTestCase("Verify Solution Screen Summary Drawer");
            ReportLog.setTestStep("Verifying First Selected Flight Pnrs");


            for (int i = 0; i < totalAllList.size(); i++) {
                SolutionScreenPage.selectedFlightsFrame.selectedFlightsItem(totalAllList.get(i)).verifyDisplayed(true, 5);
            }

            SolutionScreenPage.selectedFlightsFrame.selectedFlightsItem(totalAllList.get(0)).click();

            flightID = flightID.concat("\"" + totalAllList.get(0) + "\"");

            String requestBody = "{   \"tenant\" : \"zz\",   \"user\" : \"pinar\",   \"flights\" : [ " + flightID + " ]}";

            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve", requestBody);

            JSONObject jsonObjectS = new JSONObject(responseContent);
            String transactionId = jsonObjectS.getString("transactionId");


            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve_Transaction", transactionId);


            JSONObject jsonObjectST = new JSONObject(responseContent);

            JSONArray pnrs = jsonObjectST.getJSONArray("pnrs");

            for (int i=0; i<pnrs.length(); i++){
                JSONObject pnrObject = pnrs.getJSONObject(i);
                String confirmationNumber = pnrObject.getString("confirmationNumber");
                SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).verifyDisplayed(true);
                SolutionScreenPage.pnrListView.pnrItem(confirmationNumber).highlight();
            }


        }
    }
}
