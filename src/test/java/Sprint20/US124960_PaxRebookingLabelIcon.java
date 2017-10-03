package Sprint20;

//import org.apache.xmlbeans.impl.common.GlobalLock;
import org.testng.annotations.Test;
import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;

public class US124960_PaxRebookingLabelIcon extends TestBase
{
	public static TestDataHandler testDataHandler;
	@Test
	public void TestScenarios() throws Exception
	{
		ReportLog.setTestName("Test");
		testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser0'");
		TestCases.PreRequisiteStep();
		TestCases.Step1();
	}
	
	public static class TestCases
	{
		public static void PreRequisiteStep() throws InterruptedException
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
				ReportLog.setTestStep("Verify Pax Rebooking Text and Icon");
					GlobalPage.mainNavigationOptions.navigateToLink("Pax Rebooking").highlight();
					GlobalPage.mainNavigationOptions.navigateToLink("Pax Rebooking").verifyText("Pax Rebooking");
					GlobalPage.mainNavigationOptions.navigateToLink("Pax Rebooking").icon.highlight();
					GlobalPage.mainNavigationOptions.navigateToLink("Pax Rebooking").verifyDisplayed(true, 5);
		}
	}
}

