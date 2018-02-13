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

import java.util.ArrayList;
import java.util.List;

public class US153947_Passenger_Report_Component extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();
    public static List<String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray jsonArray;
    public static String flightID = "";

    public static int uiDisruptedPaxCount = 0;
    public static int uiNeedsToRebookCount = 0;
    public static int uiNoReflowCount = 0;
    public static int uiRebookedCount = 0;
    public static int disruptedPaxCount = 0;
    public static int needsToRebookCount = 0;
    public static int noReflowCount = 0 ;
    public static int rebookedCount = 0 ;


    @Test
    public void TestScenarios() throws Exception {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US153947_Passenger_Report_Component.TestCases.PreRequisiteStep(testDataHandler);
        US153947_Passenger_Report_Component.TestCases.Step1();
        //US153947_Passenger_Report_Component.TestCases.Step2();

    }

    public static class TestCases {
        public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException {

            ReportLog.setTestCase("Get All Disrupted Flights and Solve, Go To PAX Protection HomePage");
            ReportLog.setTestStep("Get all disrupted flights and calculate total disrupted Pax Count");

            responseContent = backendAPI.getPayload("Positive Test", "GET/disruptions");

            try {
                if (responseContent.length() > 2) {
                    jsonArray = new JSONArray(responseContent);
                    ReportLog.assertTrue(true,"Disrupted flights get successfully");
                } else {
                    ReportLog.assertFailed("PreRequisiteStep is failed since there is no disrupted data");
                }
            } catch (NullPointerException e) {

            }

            // Getting all flightids and calculate total disrupted pax count

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                totalAllList.add(flightID);
                JSONObject flightDetails = flightInfo.getJSONObject("flightDetails");
                int coachCompartmentPax = flightDetails.getInt("coachCompartmentPax");
                int businessCompartmentPax = flightDetails.getInt("businessCompartmentPax");
                int firstCompartmentPax = flightDetails.getInt("firstCompartmentPax");
                disruptedPaxCount += coachCompartmentPax + businessCompartmentPax + firstCompartmentPax;
            }

            // prepare flightID string for Solve request body

            flightID = "";

            for (int i = 0; i < totalAllList.size(); i++) {

                if (i == totalAllList.size() - 1) {
                    flightID = flightID.concat("\"" + totalAllList.get(i) + "\"");
                } else {
                    flightID = flightID.concat("\"" + totalAllList.get(i) + "\"" + ",");
                }
            }

            ReportLog.setTestStep("Solve All Disrupted Flights ");

            String requestBody = "{   \"tenant\" : \"zz\",   \"user\" : \"pinar\",   \"flights\" : [ " + flightID + " ]}";

            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve", requestBody);

            JSONObject jsonObjectS = new JSONObject(responseContent);
            String transactionId = jsonObjectS.getString("transactionId");

            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "Solve_Transaction", transactionId);

            if (responseContent.contains("solutionSummary")){
                ReportLog.assertTrue(true, "Solution response got successfully");
            }else{
                ReportLog.assertFailed("Solution response failed");
            }

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
            ReportLog.setTestCase("[STEP 1]");
            ReportLog.setTestStep("Verify all headers value of the passenger report summary drawer");

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();

            // prepare flightID string for Get/pnr-report request body

            flightID = "";

            for (int i = 0; i < totalAllList.size(); i++) {

                if (i == totalAllList.size() - 1) {
                    flightID = flightID.concat(totalAllList.get(i));
                } else {
                    flightID = flightID.concat(totalAllList.get(i) + ",");
                }
            }

            responseContent = backendAPI.getPayloadWithParameter("Positive Test", "GET/pnr-report", flightID);

            // Getting needsToRebook, noReflow, rebooked counts from pnr-report service response

            JSONObject jsonObjectS = new JSONObject(responseContent);
            JSONObject pnrReportSummary = jsonObjectS.getJSONObject("pnrReportSummary");

            if (jsonObjectS.getJSONObject("pnrReportSummary").toString().contains("needsToRebookCount")){
                needsToRebookCount = pnrReportSummary.getInt("needsToRebookCount");
            }
            if (jsonObjectS.getJSONObject("pnrReportSummary").toString().contains("noReflowCount")){
                noReflowCount = pnrReportSummary.getInt("noReflowCount");
            }
            if (jsonObjectS.getJSONObject("pnrReportSummary").toString().contains("rebookedCount")){
                rebookedCount = pnrReportSummary.getInt("rebookedCount");
            }

            // Getting needsToRebook, noReflow, rebooked counts from UI

            PaxImpactPage.summaryDrawer.summaryDrawerItem("disruptedPax").highlight();
            uiDisruptedPaxCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("disruptedPax").itemValue.getText());


            PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").highlight();
            if (needsToRebookCount > 0){
                if (!PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").itemValue.getText().contains("---")){
                    uiNeedsToRebookCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").itemValue.getText());
                }
            }

            PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").highlight();
            if (noReflowCount > 0){
                if (!PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").itemValue.getText().contains("---")){
                    uiNoReflowCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").itemValue.getText());
                }
            }

            PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").highlight();
            if (rebookedCount > 0){
                if (!PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").itemValue.getText().contains("---")){
                    uiRebookedCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").itemValue.getText());
                }
            }

            // Compare counts got from pnr-report service response and UI

            if ((needsToRebookCount == uiNeedsToRebookCount)){
                ReportLog.assertTrue(true,"Passenger Report Counts for needsToRebookCount verification is passed");
            }else{
                ReportLog.assertFailed("Passenger Report Counts for needsToRebookCount verification is failed");
            }
            if ((noReflowCount == uiNoReflowCount)){
                ReportLog.assertTrue(true,"Passenger Report Counts for noReflowCount verification is passed");
            }else{
                ReportLog.assertFailed("Passenger Report Counts for noReflowCount verification is failed");
            }
            if ((rebookedCount == uiRebookedCount)){
                ReportLog.assertTrue(true,"Passenger Report Counts for rebookedCount verification is passed");
            }else{
                ReportLog.assertFailed("Passenger Report Counts for rebookedCount verification is failed");
            }
            if ((disruptedPaxCount == uiDisruptedPaxCount)){
                ReportLog.assertTrue(true,"Passenger Report Counts for disruptedPaxCount verification is passed");
            }else{
                ReportLog.assertFailed("Passenger Report Counts for disruptedPaxCount verification is failed");
            }

        }
    }
}
