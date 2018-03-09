package Sprint5_18Q1;

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
import pageobjects.ExecutiveDashboardPage;

import java.net.MalformedURLException;

public class US149839_ExecSummaryDrawerHeader extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static String responseContent;

    public static int totalAllValue = 0;
    public static int totalDelayedValue = 0;
    public static int totalGreaterThan15MinPsngrValue = 0;
    public static int totalGreaterThan60MinPsngrValue = 0;
    public static int totalGreaterThan180MinPsngrValue = 0;
    public static int totalCanceledPsngrValue = 0;
    public static int totalDivertedPsngrValue = 0;
    public static int totalMisConPsngrValue = 0;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser2'");
        US149839_ExecSummaryDrawerHeader.TestCases.PreRequisiteStep();
        US149839_ExecSummaryDrawerHeader.TestCases.Step1();
//        US149839_ExecSummaryDrawerHeader.TestCases.Step2();
//        US149839_ExecSummaryDrawerHeader.TestCases.Step3();
//        US149839_ExecSummaryDrawerHeader.TestCases.Step4();
//        US149839_ExecSummaryDrawerHeader.TestCases.Step5();
        US149839_ExecSummaryDrawerHeader.TestCases.Step6();
//        US149839_ExecSummaryDrawerHeader.TestCases.Step7();
    }

    public static class TestCases {
        public static void PreRequisiteStep() throws InterruptedException, MalformedURLException {
            ReportLog.setTestCase("PAX Protection HomePage");
            ReportLog.setTestStep("Go to this URL: https://pax-protect-ui-shell.run.aws-usw02-pr.ice.predix.io ");
            WebControl.clearData();
            WebControl.open(testDataHandler.url);

            calculateStatisticCounts();

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
            ReportLog.setTestCase("STEP 1");
            ReportLog.setTestStep("Verify Summary Drawer Header");
            Thread.sleep(10000);
            
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();
            ExecutiveDashboardPage.summaryDrawer.greaterThan15Min.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.summaryDrawer.greaterThan60Min.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.summaryDrawer.greaterThan180Min.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.summaryDrawer.cancelled.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.summaryDrawer.diverted.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.summaryDrawer.misconx.verifyDisplayed(true, 5);
        }
        
        public static void Step2() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 2");
            ReportLog.setTestStep("Verify >=15 MIN count in Summary Drawer Header");

            ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("gt15Min").itemValue.verifyDisplayed();
            
            String greaterThan15MinUiCountText = ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("gt15Min").itemValue.getText();
            
            if ((Integer.parseInt(greaterThan15MinUiCountText) == totalGreaterThan15MinPsngrValue)) 
            {
                ReportLog.assertTrue(true, ">=15 MIN count is " + totalGreaterThan15MinPsngrValue);
            }
            else
            {
                ReportLog.assertFailed(">=15 MIN has incorrect value");
            }
        }
        
        public static void Step3() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 3");
            ReportLog.setTestStep("Verify >=60 MIN count in Summary Drawer Header");

            ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("gt60Min").itemValue.verifyDisplayed();
            
            String greaterThan60MinUiCountText = ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("gt60Min").itemValue.getText();
            
            if ((Integer.parseInt(greaterThan60MinUiCountText) == totalGreaterThan60MinPsngrValue)) 
            {
                ReportLog.assertTrue(true, ">=60 MIN count is " + totalGreaterThan60MinPsngrValue);
            }
            else
            {
                ReportLog.assertFailed(">=60 MIN has incorrect value");
            }
        }
        
        public static void Step4() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 4");
            ReportLog.setTestStep("Verify >=180 MIN count in Summary Drawer Header");

            ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("gt180Min").itemValue.verifyDisplayed();
            
            String greaterThan180MinUiCountText = ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("gt180Min").itemValue.getText();
            
            if ((Integer.parseInt(greaterThan180MinUiCountText) == totalGreaterThan180MinPsngrValue)) 
            {
                ReportLog.assertTrue(true, ">=180 MIN count is " + totalGreaterThan180MinPsngrValue);
            }
            else
            {
                ReportLog.assertFailed(">=180 MIN has incorrect value");
            }
        }
        
        public static void Step5() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 5");
            ReportLog.setTestStep("Verify CANCELLED count in Summary Drawer Header");

            ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("cancelled").itemValue.verifyDisplayed();
            
            String cancelledUiCountText = ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("cancelled").itemValue.getText();
            
            if ((Integer.parseInt(cancelledUiCountText) == totalCanceledPsngrValue)) 
            {
                ReportLog.assertTrue(true, "CANCELLED count is " + totalCanceledPsngrValue);
            }
            else
            {
                ReportLog.assertFailed("CANCELLED has incorrect value");
            }
        }
        
        public static void Step6() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 6");
            ReportLog.setTestStep("Verify DIVERTED count in Summary Drawer Header");

            ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("diverted").itemValue.verifyDisplayed();
            
            String divertedUiCountText = ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("diverted").itemValue.getText();
            
            System.out.println(totalDivertedPsngrValue);
            
            if ((Integer.parseInt(divertedUiCountText) == totalDivertedPsngrValue)) 
            {
                ReportLog.assertTrue(true, "DIVERTED count is " + totalDivertedPsngrValue);
            }
            else
            {
                ReportLog.assertFailed("DIVERTED has incorrect value");
            }
        }
        
        public static void Step7() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 7");
            ReportLog.setTestStep("Verify MISCONNECTED count in Summary Drawer Header");

            ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("misconnected").itemValue.verifyDisplayed();
            
            String misconxUiCountText = ExecutiveDashboardPage.summaryDrawer.summaryDrawerItem("misconnected").itemValue.getText();
            
            if ((Integer.parseInt(misconxUiCountText) == totalMisConPsngrValue)) 
            {
                ReportLog.assertTrue(true, "MISCONX count is " + totalMisConPsngrValue);
            }
            else
            {
                ReportLog.assertFailed("MISCONX has incorrect value");
            }
        }
        
        public static void  calculateStatisticCounts(){
            responseContent = backendAPI.getPayload("Positive Test","GET/disruptions");

            JSONArray jsonArray = new JSONArray(responseContent);

            totalAllValue = jsonArray.length();

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                JSONObject disruption = flightInfo.getJSONObject("disruption");

                if (disruption.toMap().containsKey("delay")) {
                    totalDelayedValue++ ;
                    JSONObject delay = disruption.getJSONObject("delay");

                    int departureDelayMinutes = delay.getInt("departureDelayMinutes");

                    if ((departureDelayMinutes >= 15) && (departureDelayMinutes < 60)){
                        JSONObject totalImpactedPax = jsonArray.getJSONObject(i).getJSONObject("totalImpactedPax");
                        int totalCount = totalImpactedPax.getInt("totalCount");
                        totalGreaterThan15MinPsngrValue+=totalCount;
                        JSONObject misconnectingPax = jsonArray.getJSONObject(i).getJSONObject("misconnectingPax");
                        int totalMisCount = misconnectingPax.getInt("totalCount");
                        totalMisConPsngrValue+=totalMisCount;
                    }
                    else if ((departureDelayMinutes >= 60) && (departureDelayMinutes < 180)) {
                        JSONObject totalImpactedPax = jsonArray.getJSONObject(i).getJSONObject("totalImpactedPax");
                        int totalCount = totalImpactedPax.getInt("totalCount");
                        totalGreaterThan60MinPsngrValue+=totalCount;
                        JSONObject misconnectingPax = jsonArray.getJSONObject(i).getJSONObject("misconnectingPax");
                        int totalMisCount = misconnectingPax.getInt("totalCount");
                        totalMisConPsngrValue+=totalMisCount;
                    }
                    else if (departureDelayMinutes >= 180){
                        JSONObject totalImpactedPax = jsonArray.getJSONObject(i).getJSONObject("totalImpactedPax");
                        int totalCount = totalImpactedPax.getInt("totalCount");
                        totalGreaterThan180MinPsngrValue+=totalCount;
                        JSONObject misconnectingPax = jsonArray.getJSONObject(i).getJSONObject("misconnectingPax");
                        int totalMisCount = misconnectingPax.getInt("totalCount");
                        totalMisConPsngrValue+=totalMisCount;
                    }
                }
                else if (disruption.toMap().containsKey("cancel")){
                    JSONObject totalImpactedPax = jsonArray.getJSONObject(i).getJSONObject("totalImpactedPax");
                    int totalCount = totalImpactedPax.getInt("totalCount");
                    totalCanceledPsngrValue+=totalCount;
                    JSONObject misconnectingPax = jsonArray.getJSONObject(i).getJSONObject("misconnectingPax");
                    int totalMisCount = misconnectingPax.getInt("totalCount");
                    totalMisConPsngrValue+=totalMisCount;
                }
            }

            for (int i = 0 ; i < jsonArray.length() ; i++ ) {
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                JSONArray pnrs = flightInfo.getJSONArray("pnrs");
                if (pnrs.length() > 0) {
                    for (int j = 0; j < pnrs.length(); j++) {
                        if (pnrs.getJSONObject(j).toMap().containsKey("reflowStatus")) {
                            String reflowStatus = pnrs.getJSONObject(j).getString("reflowStatus");
                            if (reflowStatus.equals("N/A – CP") || reflowStatus.equals("N/A - RUL")) {
                                int totalCount = pnrs.getJSONObject(j).getJSONObject("compartmentCounts").getInt("totalCount");
                                totalDivertedPsngrValue+=totalCount;
                            }
                        }
                    }
                }
            }
        }

    }
}
