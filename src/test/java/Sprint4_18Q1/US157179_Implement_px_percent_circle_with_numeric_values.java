package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;

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
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("FLIGHT ARRIVAL").highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("FLIGHT ARRIVAL").percentCircleItemHeader.highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("CUST ARRIVAL").highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("CUST ARRIVAL").percentCircleItemHeader.highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("FLIGHT DEPARTURE").highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("FLIGHT DEPARTURE").percentCircleItemHeader.highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("CUST DEPARTURE").highlight();
            ExecutiveDashboardPage.statisticFrame.percentCircleItem("CUST DEPARTURE").percentCircleItemHeader.highlight();

        }
    }
}
