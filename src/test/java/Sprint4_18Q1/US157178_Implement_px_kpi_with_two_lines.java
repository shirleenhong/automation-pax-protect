package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class US157178_Implement_px_kpi_with_two_lines extends TestBase {

    public static TestDataHandler testDataHandler;

    public static BackendAPI backendAPI = new BackendAPI();

    public static String responseContent;

    public static int totalAllValue = 0;
    public static int totalDelayedValue = 0;
    public static int totalGreaterThan15MinPsngrValue = 0;
    public static int totalGreaterThan60MinPsngrValue = 0;
    public static int totalGreaterThan180MinPsngrValue = 0;
    public static int totalCanceledPsngrValue = 0;
    public static int totalMisConPsngrValue = 0;
    public static int totalDivertedPsngrValue = 0;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US157178_Implement_px_kpi_with_two_lines.TestCases.PreRequisiteStep();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step1();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step2();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step3();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step4();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step5();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step6();

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

        public static void Step1(){

            ReportLog.setTestCase("Executive Dashboard Px Kpi Section Test");
            ReportLog.setTestStep("Verify each part of the Px Kpi section ");

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").verifyDisplayed(true,5);
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();

            ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").kpiChartCountText.verifyDisplayed();

            String arrOtpUiCount = ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").kpiChartCount.getAttribute("count");
            String arrOtpUiCountText = ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").kpiChartCountText.getText();

            String d = ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").rectPathItem.getAttribute("d");

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            //Date now = new Date();
            String strDate = sdfDate.format(new Date());
            // parse date from yyyy-mm-dd pattern
            LocalDate today = LocalDate.parse(strDate);
            // add one day
            LocalDate nextDay = today.plusDays(1);

            //String q = "{\"destAirportID\":\"AP-LAX\",\"arrDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + today + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";
            String q = "{\"arrDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + today + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";

            responseContent = backendAPI.getPayloadWithProperty("Positive Test", "GET/OTP","request_GET_otp_ARR", "Request", q);

            JSONArray jsonArrayS = new JSONArray(responseContent);
            int otpArrCount = jsonArrayS.length();

            /*if ((Integer.parseInt(arrOtpUiCount) == otpArrCount) && (Integer.parseInt(arrOtpUiCountText) == otpArrCount)) {
                ReportLog.assertTrue(true, "Arrival OTP test passed");
            }else{
                ReportLog.assertFailed("Arrival OTP test failed");
            }*/

        }

        public static void Step2(){

            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").kpiChartCountText.verifyDisplayed();

        }

        public static void Step3(){
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").kpiChartCountText.verifyDisplayed();

        }

        public static void Step4(){
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").kpiChartCountText.verifyDisplayed();

            String arrOtpUiCount = ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").kpiChartCount.getAttribute("count");
            String arrOtpUiCountText = ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").kpiChartCountText.getText();

            String d = ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").rectPathItem.getAttribute("d");

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            //Date now = new Date();
            String strDate = sdfDate.format(new Date());
            // parse date from yyyy-mm-dd pattern
            LocalDate today = LocalDate.parse(strDate);
            // add one day
            LocalDate nextDay = today.plusDays(1);

            //String q = "{\"origAirportID\":\"AP-LAX\",\"dptDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + today + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";
            String q = "{\"dptDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + today + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";

            responseContent = backendAPI.getPayloadWithProperty("Positive Test", "GET/OTP","request_GET_otp_DEP", "Request", q);

            JSONArray jsonArrayS = new JSONArray(responseContent);
            int otpDepCount = jsonArrayS.length();

            /*if ((Integer.parseInt(arrOtpUiCount) == otpDepCount) && (Integer.parseInt(arrOtpUiCountText) == otpDepCount)) {
                ReportLog.assertTrue(true, "Departure OTP test passed");
            }else{
                ReportLog.assertFailed("Departure OTP test failed");
            }*/

        }

        public static void Step5(){
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").kpiChartCountText.verifyDisplayed();


        }

        public static void Step6(){

            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180MIN").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180MIN").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180MIN").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180MIN").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180MIN").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180MIN").kpiChartCountText.verifyDisplayed();

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
                        totalDivertedPsngrValue+=totalCount;
                        JSONObject misconnectingPax = jsonArray.getJSONObject(i).getJSONObject("misconnectingPax");
                        int totalMisCount = misconnectingPax.getInt("totalCount");
                        totalMisConPsngrValue+=totalMisCount;
                    }
                    else if ((departureDelayMinutes >= 60) && (departureDelayMinutes < 180)) {
                        JSONObject totalImpactedPax = jsonArray.getJSONObject(i).getJSONObject("totalImpactedPax");
                        int totalCount = totalImpactedPax.getInt("totalCount");
                        totalGreaterThan60MinPsngrValue+=totalCount;
                        totalDivertedPsngrValue+=totalCount;
                        JSONObject misconnectingPax = jsonArray.getJSONObject(i).getJSONObject("misconnectingPax");
                        int totalMisCount = misconnectingPax.getInt("totalCount");
                        totalMisConPsngrValue+=totalMisCount;
                    }
                    else if (departureDelayMinutes >= 180){
                        JSONObject totalImpactedPax = jsonArray.getJSONObject(i).getJSONObject("totalImpactedPax");
                        int totalCount = totalImpactedPax.getInt("totalCount");
                        totalGreaterThan180MinPsngrValue+=totalCount;
                        totalDivertedPsngrValue+=totalCount;
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
        }
    }
}
