package Sprint3_18Q1;

import Sprint23.US137579_SummaryDrawer;
import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.testng.annotations.Test;
import pageobjects.GESSOAuthPage;
import pageobjects.PaxImpactPage;

public class US153947_Passenger_Report_Component extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static int uiDisruptedPaxCount = 0;
    public static int uiNeedsToRebookCount = 0;
    public static int uiNoReflowCount = 0;
    public static int uiRebookedCount = 0;


    @Test
    public void TestScenarios() throws Exception {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US153947_Passenger_Report_Component.TestCases.PreRequisiteStep(testDataHandler);
        US153947_Passenger_Report_Component.TestCases.Step1();
        //US153947_Passenger_Report_Component.TestCases.Step2();

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
            ReportLog.setTestStep("Verify all headers of the summary drawer");
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();

            PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").highlight();
            if (!PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").getText().contains("---")){
                uiNeedsToRebookCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").getText().substring(PaxImpactPage.summaryDrawer.summaryDrawerItem("needsToRebook").getText().indexOf("\n")+1));
            }


            PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").highlight();
            //String h = PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").getText().substring(PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").getText().indexOf("\n")+1);
            if (!PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").getText().contains("---")){
                uiNoReflowCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").itemValue.getText().substring(PaxImpactPage.summaryDrawer.summaryDrawerItem("noReflow").getText().indexOf("\n")+1));
            }



            PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").highlight();
           // String r = PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").getText().substring(PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").getText().indexOf("\n")+1);
            if (!PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").getText().contains("---")){
                uiRebookedCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").itemValue.getText().substring(PaxImpactPage.summaryDrawer.summaryDrawerItem("rebooked").getText().indexOf("\n")+1));
            }


            PaxImpactPage.summaryDrawer.summaryDrawerItem("disruptedPax").highlight();
            //String s = PaxImpactPage.summaryDrawer.summaryDrawerItem("disruptedPax").itemValue.getText();
            uiDisruptedPaxCount = Integer.parseInt(PaxImpactPage.summaryDrawer.summaryDrawerItem("disruptedPax").itemValue.getText());

        }
    }
}
