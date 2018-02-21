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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

public class US157178_Implement_px_kpi_with_two_lines extends TestBase {

    public static TestDataHandler testDataHandler;

    public static BackendAPI backendAPI = new BackendAPI();

    public static String responseContent;

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

            String q = "{\"destAirportID\":\"AP-LAX\",\"arrDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + today + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";

            responseContent = backendAPI.getPayloadWithProperty("Positive Test", "GET/OTP","request_GET_otp_ARR", "Request", q);

            JSONArray jsonArrayS = new JSONArray(responseContent);
            int otpArrCount = jsonArrayS.length();

            if ((Integer.parseInt(arrOtpUiCount) == otpArrCount) && (Integer.parseInt(arrOtpUiCountText) == otpArrCount)) {
                ReportLog.assertTrue(true, "Arrival OTP test passed");
            }else{
                ReportLog.assertFailed("Arrival OTP test failed");
            }



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

            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").kpiChartCountText.verifyDisplayed();


        }
    }
}
