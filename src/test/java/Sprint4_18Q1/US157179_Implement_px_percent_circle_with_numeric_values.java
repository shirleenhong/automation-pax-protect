package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.WebManager;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;


public class US157179_Implement_px_percent_circle_with_numeric_values extends TestBase {

    public static TestDataHandler testDataHandler;
    //public static BackendAPI backendAPI = new BackendAPI();

   // public static String responseContent;

    public static WebDriver driver = WebManager.getDriver();

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.PreRequisiteStep();
        US157179_Implement_px_percent_circle_with_numeric_values.TestCases.Step1();
        //US157179_Implement_px_percent_circle_with_numeric_values.TestCases.Step2();


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

            ReportLog.setTestCase("Executive Dashboard PxPercentCircle Section Test");
            ReportLog.setTestStep("Verify each part of the PxPercentCircle section ");

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleItem.highlight();

            String stroke_dashoffset = ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT ARRIVAL").circleItem.getAttribute("stroke-dashoffset");

            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST ARRIVAL").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("FLIGHT DEPARTURE").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CUST DEPARTURE").statisticItemHeader.verifyDisplayed(true,5);

        }
    }
}
