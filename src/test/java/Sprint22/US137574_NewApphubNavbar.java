package Sprint22;

import org.testng.annotations.Test;
import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.PaxImpactPage;

public class US137574_NewApphubNavbar extends TestBase{
    public static TestDataHandler testDataHandler;
    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        TestCases.PreRequisiteStep(testDataHandler);
        TestCases.Step1();
        TestCases.Step2();
        TestCases.Step3();
        TestCases.Step4();
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
            ReportLog.setTestStep("Verify Pax Protection Text and Icon");

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").highlight();
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").verifyDisplayed(true, 5);
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").icon.highlight();
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").verifyText("Pax Protection");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").highlight();
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").verifyDisplayed(true, 5);
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").verifyText("Pax Impact");


        }

        public static void Step2() {

            ReportLog.setTestCase("[STEP 2]");
            ReportLog.setTestStep("Verify Pax Impact Page");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();
            PaxImpactPage.summaryDrawer.rootElement.verifyDisplayed(true,5);


        }

        public static void Step3() {

            ReportLog.setTestCase("[STEP 3]");
            ReportLog.setTestStep("Verify Pax Protection Page");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").click();
        }

        public static void Step4() {

            ReportLog.setTestCase("[STEP 4]");
            ReportLog.setTestStep("Verify Time shown in UTC Text");
            GlobalPage.timeUTC.timeUTCText.highlight();
            GlobalPage.timeUTC.timeUTCText.verifyText("Time shown in UTC");

        }

    }

}
