package Sprint26;


import common.GlobalDefPage;
import common.TestDataHandler;

import org.junit.Assert;
import org.testng.annotations.Test;

import pageobjects.PaxImpactPage;
import pageobjects.SSOAuthPage;
import seleniumwireframe.WebManager;


public class US137574_NewApphubNavbar{
    public static TestDataHandler testDataHandler;
    public static SSOAuthPage ssoAuthPage;

    @Test
    public void TestScenarios() throws Exception
    {
        //ReportLog.setTestName("Test");

        //System.setProperty("webdriver.chrome.driver", "C:/workspace/if-qa-ui-tests/chromedriver_win32_2.31/chromedriver.exe");
        //WebDriver driver = new ChromeDriver();


        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        //ssoAuthPage = new SSOAuthPage(testDataHandler.url);

        TestCases.PreRequisiteStep(testDataHandler);
        TestCases.Step1();
        TestCases.Step2();
        TestCases.Step3();
        TestCases.Step4();
    }

    public static class TestCases {
        public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException {
           /* ReportLog.setTestCase("PAX Protection HomePage");
            ReportLog.setTestStep("Go to this URL: https://pax-protect-ui-shell.run.aws-usw02-pr.ice.predix.io ");
            WebControl.clearData();
            WebControl.open(testDataHandler.url);*/

            WebManager.setDriver();
            WebManager.driver.get(testDataHandler.url);



            Thread.sleep(1000);
            if (SSOAuthPage.authInfo.gESSOLabel.isDisplayed()) {
                SSOAuthPage.authInfo.sSOIDInput.sendKeys(testDataHandler.username);
                SSOAuthPage.authInfo.passWordInput.sendKeys(testDataHandler.password);
                SSOAuthPage.authInfo.submitFormShared.click();

            }
           // SSOAuthPage.page.verifyURL(false, 60);
        }

        public static void Step1() {
           // ReportLog.setTestCase("[STEP 1]");
          //  ReportLog.setTestStep("Verify Pax Protection Text and Icon");

            WebManager.highlight(GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").icon);
            WebManager.highlight(GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").icon);
            WebManager.highlight(GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").navText);
            WebManager.highlight(GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").navText);
            assert (GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").navText.isDisplayed());



            /*GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").verifyDisplayed(true, 5);
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").icon.highlight();
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").verifyText("Pax Protection");
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").highlight();
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").verifyDisplayed(true, 5);
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").verifyText("Pax Impact");*/


        }

        public static void Step2() {

           // ReportLog.setTestCase("[STEP 2]");
           // ReportLog.setTestStep("Verify Pax Impact Page");
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").navText.click();
           // PaxImpactPage.summaryDrawer.rootElement.verifyDisplayed(true,5);


        }

        public static void Step3() {

           // ReportLog.setTestCase("[STEP 3]");
            //ReportLog.setTestStep("Verify Pax Protection Page");
            GlobalDefPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Protection").navText.click();
        }

        public static void Step4() {

            //ReportLog.setTestCase("[STEP 4]");
           // ReportLog.setTestStep("Verify Time shown in UTC Text");
            WebManager.highlight(GlobalDefPage.timeUTC.timeUTCText);
            //GlobalDefPage.timeUTC.timeUTCText.verifyText("Time shown in UTC");

        }

    }

}
