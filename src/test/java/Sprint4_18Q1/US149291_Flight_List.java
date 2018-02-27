package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.WebManager;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardListPage;
import pageobjects.ExecutiveDashboardPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class US149291_Flight_List extends TestBase{

    public static TestDataHandler testDataHandler;

    public static BackendAPI backendAPI = new BackendAPI();

    public static String responseContent;
    public static JSONArray  jsonArray;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US149291_Flight_List.TestCases.PreRequisiteStep();
        US149291_Flight_List.TestCases.Step1();
        US149291_Flight_List.TestCases.Step2();


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

            WebElement myDynamicElement = (new WebDriverWait(WebManager.getDriver(), 50))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//p[contains(text(),'Executive Dashboard')]")));
        }


        public static void Step1() {

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").verifyDisplayed(true,5);
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();

            WebElement myDynamicElement = (new WebDriverWait(WebManager.getDriver(), 50))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ppro-stats-card[@title='ARRIVALS OTP']//div[text()='ARRIVALS OTP']")));

            ExecutiveDashboardPage.statisticFrame.flightListIcon.click();

            if (!ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").isDisplayed()){
                ReportLog.assertTrue(true, "Flight List opened successfully");
            }else{
                ReportLog.assertFailed("Flight List can not be opened");
            }


           // WebControl.activeWindow().waitForPageToLoad(5);

            responseContent = backendAPI.getPayload("Positive Test","GET/disruptions");

            try {
                if (responseContent.startsWith("[")) {
                    jsonArray = new JSONArray(responseContent);
                } else {
                    ReportLog.addInfo("Can not get response content from service");
                    ReportLog.assertFailed("Step1 is failed");
                }
            }catch(NullPointerException e){

            }

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                String tenant = flightInfo.getString("tenant");
                String flightNumber = flightInfo.getJSONObject("flightDetails").getString("flightNumber");
                String origin = flightInfo.getJSONObject("flightDetails").getString("origin");
                String destination = flightInfo.getJSONObject("flightDetails").getString("destination");

                int cost = flightInfo.getJSONArray("costs").getJSONObject(0).getJSONArray("data").getJSONObject(0).getJSONObject("total-cost").getInt("value");

                ExecutiveDashboardListPage.disruptedFlightList.disruptedFlightListItem(flightID).verifyDisplayed(true,5);
                String[] parsedItem = ExecutiveDashboardListPage.disruptedFlightList.disruptedFlightListItem(flightID).getText().split("\n");

                if (tenant.concat(flightNumber).equals(parsedItem[1])){
                    ReportLog.assertTrue(true,"flight field value is passed");
                }else{
                    ReportLog.assertFailed("flight field value is failed");
                }
                if (origin.split("-")[1].equals(parsedItem[2])){
                    ReportLog.assertTrue(true,"origin field value is passed");
                }else{
                    ReportLog.assertFailed("origin field value is failed");
                }
                if (destination.split("-")[1].equals(parsedItem[3])){
                    ReportLog.assertTrue(true,"dest field value is passed");
                }else{
                    ReportLog.assertFailed("dest field value is failed");
                }
                if (Integer.parseInt(parsedItem[6]) == cost){
                    ReportLog.assertTrue(true,"cost field value is passed");
                }else{
                    ReportLog.assertFailed("cost field value is failed");
                }

            }

        }

        public static void Step2() {
            ExecutiveDashboardListPage.disruptedFlightListHeader.impactHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.flightHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.origHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.destHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.paxHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.misconxHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.welfareHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.impactCodeHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.psngrValueHeader.highlight();

        }
    }
}
