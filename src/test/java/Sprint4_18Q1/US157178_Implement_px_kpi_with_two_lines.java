package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;

public class US157178_Implement_px_kpi_with_two_lines extends TestBase {

    public static TestDataHandler testDataHandler;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US157178_Implement_px_kpi_with_two_lines.TestCases.PreRequisiteStep();
        US157178_Implement_px_kpi_with_two_lines.TestCases.Step1();

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

            String count = ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").kpiChartCount.getAttribute("count");
            String countText = ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").kpiChartCountText.getText();

            String d = ExecutiveDashboardPage.statisticFrame.statisticItem("ARRIVALS OTP").rectPathItem.getAttribute("d");

            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("CANCELLED").kpiChartCountText.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("MISCONX").kpiChartCountText.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("DEPARTURES OTP").kpiChartCountText.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("DIVERTED").kpiChartCountText.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").statisticItemHeader.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").rectItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").rectPathItem.verifyDisplayed(true,5);
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").kpiChartCount.verifyDisplayed();
            ExecutiveDashboardPage.statisticFrame.statisticItem("> 180min").kpiChartCountText.verifyDisplayed();


        }
    }
}
