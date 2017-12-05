package Sprint23;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import common.BackendAPI;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;


import pageobjects.GESSOAuthPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import org.json.JSONObject;


public class US137579_SummaryDrawer extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static int uiTotalAllValue = 0;
    public static int uiTotalDelayedValue = 0;
    public static int uiTotalLessThan15MinValue = 0;
    public static int uiTotalLessThan60MinValue = 0;
    public static int uiTotalLessThan180MinValue = 0;
    public static int uiTotalCanceledValue = 0;
    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US137579_SummaryDrawer.TestCases.PreRequisiteStep(testDataHandler);
        US137579_SummaryDrawer.TestCases.Step1();
        US137579_SummaryDrawer.TestCases.Step2();

    }

    public static class TestCases {
        public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException {
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
            ReportLog.setTestCase("[STEP 1]");
            ReportLog.setTestStep("Verify if the header of the screen have a summary drawer");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();
            PaxImpactPage.summaryDrawer.rootElement.highlight();
            PaxImpactPage.summaryDrawer.rootElement.verifyDisplayed(true,5);
            PaxImpactPage.summaryDrawer.all.highlight();
            PaxImpactPage.summaryDrawer.all.verifyDisplayed(true,5);
            uiTotalAllValue = Integer.parseInt(PaxImpactPage.summaryDrawer.all.getAttribute("value"));
            PaxImpactPage.summaryDrawer.delayed.verifyDisplayed(true,5);
            PaxImpactPage.summaryDrawer.delayed.highlight();
            uiTotalDelayedValue = Integer.parseInt(PaxImpactPage.summaryDrawer.delayed.getAttribute("value"));
            PaxImpactPage.summaryDrawer.lessThan15Min.verifyDisplayed(true,5);
            PaxImpactPage.summaryDrawer.lessThan15Min.highlight();
            uiTotalLessThan15MinValue = Integer.parseInt(PaxImpactPage.summaryDrawer.lessThan15Min.getAttribute("value"));
            PaxImpactPage.summaryDrawer.lessThan60Min.verifyDisplayed(true,5);
            PaxImpactPage.summaryDrawer.lessThan60Min.highlight();
            uiTotalLessThan60MinValue = Integer.parseInt(PaxImpactPage.summaryDrawer.lessThan60Min.getAttribute("value"));
            PaxImpactPage.summaryDrawer.lessThan180Min.verifyDisplayed(true,5);
            PaxImpactPage.summaryDrawer.lessThan180Min.highlight();
            uiTotalLessThan180MinValue = Integer.parseInt(PaxImpactPage.summaryDrawer.lessThan180Min.getAttribute("value"));
            PaxImpactPage.summaryDrawer.cancelled.highlight();
            PaxImpactPage.summaryDrawer.cancelled.verifyDisplayed(true,5);
            uiTotalCanceledValue = Integer.parseInt(PaxImpactPage.summaryDrawer.cancelled.getAttribute("value"));
           
        }

        public static void Step2() throws Exception {

            int totalAllValue = 0;
            int totalDelayedValue = 0;
            int totalLessThan15MinValue = 0;
            int totalLessThan60MinValue = 0;
            int totalLessThan180MinValue = 0;
            int totalCanceledValue = 0;

            String responseContent = null;
            JSONArray  jsonArray = null;

            ReportLog.setTestCase("[STEP 2]");
            ReportLog.setTestStep("Verify if each segment value is equal with service response in summary drawer");


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

            totalAllValue = jsonArray.length();

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                JSONObject disruption = flightInfo.getJSONObject("disruption");

                if (disruption.toMap().containsKey("delay")) {
                    totalDelayedValue++ ;
                    JSONObject delay = disruption.getJSONObject("delay");

                    int departureDelayMinutes = delay.getInt("departureDelayMinutes");

                    if (departureDelayMinutes <= 15) totalLessThan15MinValue++ ;
                    else if (departureDelayMinutes <= 60) totalLessThan60MinValue++ ;
                    else totalLessThan180MinValue++ ;
                }
                else if (disruption.toMap().containsKey("cancel")){
                    totalCanceledValue++;
                }
            }

            if (uiTotalAllValue == totalAllValue){
                ReportLog.assertTrue(true, "Total All Value passed");
            }
            else {
                ReportLog.assertTrue(false, "Total All Value failed");
            }
            if (uiTotalDelayedValue == totalDelayedValue){
                ReportLog.assertTrue(true, "Total Delayed Value passed");
            }
            else {
                ReportLog.assertTrue(false, "Total Delayed Value failed");
            }
            if (uiTotalLessThan15MinValue == totalLessThan15MinValue){
                ReportLog.assertTrue(true, "Total LessThan15Min Value");
            }
            else {
                ReportLog.assertTrue(false, "Total LessThan15Min Value failed");
            }
            if (uiTotalLessThan60MinValue == totalLessThan60MinValue){
                ReportLog.assertTrue(true, "Total LessThan60Min Value passed");
            }
            else {
                ReportLog.assertTrue(false, "Total LessThan60Min Value failed");
            }
            if (uiTotalLessThan180MinValue == totalLessThan180MinValue){
                ReportLog.assertTrue(true, "Total LessThan180Min Value passed");
            }
            else {
                ReportLog.assertTrue(false, "Total LessThan180Min Value failed");
            }
            if (uiTotalCanceledValue == totalCanceledValue){
                ReportLog.assertTrue(true, "Total Canceled Value passed");
            }
            else {
                ReportLog.assertTrue(false, "Total Canceled Value failed");
            }

        }

        /*public static void Step3(){
            ReportLog.setTestCase("[STEP 3]");
            ReportLog.setTestStep("Summary Drawer segment value is handled in any error in reflow workflow service");

            if ((uiTotalAllValue == 0) & (uiTotalDelayedValue == 0) & (uiTotalLessThan15MinValue == 0) & (uiTotalLessThan60MinValue == 0) & (uiTotalLessThan180MinValue == 0) & (uiTotalCanceledValue == 0) ){
                ReportLog.assertTrue(true, "Summary Drawer segment value is handled in any error in reflow workflow service ");
            }
            else {
                ReportLog.assertFailed("Step 3 failed");
            }

        }*/
    }
}
