package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.WebManager;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class US157179_Implement_px_percent_circle_with_numeric_values extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static String responseContent;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.PreRequisiteStep();
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.Step1();
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.Step2();
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.Step3();
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.Step4();


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

            WebElement myDynamicElement1 = (new WebDriverWait(WebManager.getDriver(), 50))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//p[contains(text(),'Executive Dashboard')]")));
        }

        public static void Step1() {

            ReportLog.setTestCase("Executive Dashboard PxPercentCircle Section Test");
            ReportLog.setTestStep("Verify Flight Arrival part of the PxPercentCircle section ");

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").statisticItemHeader.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleItem.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleItem.highlight();
            //ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleProgress.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleInMiddleText.verifyDisplayed();

            String textInTheCricle = ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleInMiddleText.getText();

            String[] arrFlightOtpValuesUI = textInTheCricle.split("/");

            /*String value = ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleProgress.getAttribute("value");
            String range = ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleProgress.getAttribute("range");*/

            String stroke_dashoffset = ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleItem.getAttribute("stroke-dashoffset");

            int[] arrFlightOtpValues = calculateOTPValues("ARR");

            if((Integer.parseInt(arrFlightOtpValuesUI[0]) == arrFlightOtpValues[0]) && (Integer.parseInt(arrFlightOtpValuesUI[1]) == arrFlightOtpValues[1])){
                ReportLog.assertTrue(true,"Verifying FLIGHT ARRIVAL circle item passed");
            }else{
                ReportLog.assertFailed("Verifying FLIGHT ARRIVAL circle item failed");
            }

        }

        public static void Step2() {

            ReportLog.setTestCase("Executive Dashboard PxPercentCircle Section Test");
            ReportLog.setTestStep("Verify Cust Arrival part of the PxPercentCircle section ");

            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").statisticItemHeader.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").circleItem.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").circleItem.highlight();
            //ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").circleProgress.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").circleInMiddleText.verifyDisplayed();

            String textInTheCricle = ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").circleInMiddleText.getText();
            String[] arrCustValuesUI = textInTheCricle.split("/");

        }

        public static void Step3() {

            ReportLog.setTestCase("Executive Dashboard PxPercentCircle Section Test");
            ReportLog.setTestStep("Verify Flight Departure part of the PxPercentCircle section ");

            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").statisticItemHeader.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").circleItem.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").circleItem.highlight();
            //ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").circleProgress.verifyDisplayed();

            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").circleInMiddleText.verifyDisplayed();

            String textInTheCricle = ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").circleInMiddleText.getText();
            String[] dptFlightOtpValuesUI = textInTheCricle.split("/");

            int[] dptFlightOtpValues = calculateOTPValues("DPT");

            if((Integer.parseInt(dptFlightOtpValuesUI[0]) == dptFlightOtpValues[0]) && (Integer.parseInt(dptFlightOtpValuesUI[1]) == dptFlightOtpValues[1])){
                ReportLog.assertTrue(true,"Verifying FLIGHT ARRIVAL circle item passed");
            }else{
                ReportLog.assertFailed("Verifying FLIGHT ARRIVAL circle item failed");
            }

        }

        public static void Step4() {

            ReportLog.setTestCase("Executive Dashboard PxPercentCircle Section Test");
            ReportLog.setTestStep("Verify Flight Arrival part of the PxPercentCircle section ");

            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").statisticItemHeader.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").circleItem.verifyDisplayed(true, 5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").circleItem.highlight();
            //ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").circleProgress.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").circleInMiddleText.verifyDisplayed();

            String textInTheCricle = ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").circleInMiddleText.getText();
            String[] dptCustValuesUI = textInTheCricle.split("/");

        }

        public static int[] calculateOTPValues(String otp) {

            int arrOtpDlyCount = 0;
            int dptOtpDlyCount = 0;
            int otpArray[] = new int[2];

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            //Date now = new Date();
            String strDate = sdfDate.format(new Date());
            // parse date from yyyy-mm-dd pattern
            LocalDate today = LocalDate.parse(strDate);
            // minus one day
            LocalDate previousDay = today.minusDays(1);
            // add one day
            LocalDate nextDay = today.plusDays(3);


            if (otp.equals("ARR")) {

                //String q = "{\"destAirportID\":\"AP-LAX\",\"arrDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + today + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";
                String q = "{\"arrDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + previousDay + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T00:01:00Z\"}}]}}";

                responseContent = backendAPI.getPayloadWithProperty("Positive Test", "GET/OTP", "request_GET_otp_ARR", "q", q);

                JSONArray arrOtpArray = new JSONArray(responseContent);

                for (int i = 0; i < arrOtpArray.length(); i++) {
                    if (arrOtpArray.getJSONObject(i).getBoolean("isDly")) {
                        arrOtpDlyCount++;
                    }
                }
                otpArray[0] = arrOtpDlyCount;
                otpArray[1] = arrOtpArray.length();
            }else if(otp.equals("DPT")){
                String q = "{\"dptDateTimeSch\":{\"$and\":[{\"$ge\":{\"$date\":\"" + previousDay + "T00:01:00Z\"}},{\"$lt\":{\"$date\":\"" + nextDay + "T23:59:59Z\"}}]}}";

                responseContent = backendAPI.getPayloadWithProperty("Positive Test", "GET/OTP","request_GET_otp_DEP", "q", q);

                JSONArray depOtpArray = new JSONArray(responseContent);
                for (int i = 0; i < depOtpArray.length(); i++) {
                    if (depOtpArray.getJSONObject(i).getBoolean("isDly")) {
                        dptOtpDlyCount++;
                    }
                }
                otpArray[0] = dptOtpDlyCount;
                otpArray[1] = depOtpArray.length();
            }
            return otpArray;

        }
    }
}
