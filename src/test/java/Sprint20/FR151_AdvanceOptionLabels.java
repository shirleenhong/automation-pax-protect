package Sprint20;

//import org.apache.xmlbeans.impl.common.GlobalLock;
import org.testng.annotations.Test;
import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.ConfigPage;

public class FR151_AdvanceOptionLabels extends TestBase
{
	public static TestDataHandler testDataHandler;
	@Test
	public void TestScenarios() throws Exception
	{
		ReportLog.setTestName("Test");
		testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser0'");
		TestCases.PreRequisiteStep(testDataHandler);
		TestCases.Step1();
		TestCases.Step2();
	}
	
	public static class TestCases
	{
		public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException
        {
			ReportLog.setTestCase("PAX Protection HomePage");
            ReportLog.setTestStep("Go to this URL: https://aviation-in-ppro-ui-app-hub-qa.run.aws-usw02-pr.ice.predix.io/ ");
            WebControl.clearData();
            WebControl.open(testDataHandler.url);
            
            Thread.sleep(1000);
            if (GESSOAuthPage.authInfo.gESSOLabel.isDisplayed())
            {
                GESSOAuthPage.authInfo.sSOIDInput.typeKeys(testDataHandler.username);
                GESSOAuthPage.authInfo.passWordInput.typeKeys(testDataHandler.password);
                GESSOAuthPage.authInfo.submitFormShared.click();
            }
            GESSOAuthPage.page.verifyURL(false, 60);
        }
		
		public static void Step1()
		{
			ReportLog.setTestCase("[STEP 1]");
				ReportLog.setTestStep("Go to Pax Config page");
				GlobalPage.mainNavigationOptions.configPageButton.click();					
		}
		
		public static void Step2()
		{
			ReportLog.setTestCase("[STEP 2]");
				ReportLog.setTestStep("Verify Advance Options labels");
					ConfigPage.configSection.advanceBody.allowOverbookingText.verifyText("Allow Overbooking");
					ConfigPage.configSection.advanceBody.allowOverbooking.verifyDisplayed();
					ConfigPage.configSection.advanceBody.allowOverbookingText.verifyText("Allowable Booking Capacity");
					ConfigPage.configSection.advanceBody.maxOverbookingBox.verifyDisplayed();
					ConfigPage.configSection.advanceBody.maxLatenessText.verifyText("Max Lateness (hours)");
		}
	}
}