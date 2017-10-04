package Sprint20;

//import org.apache.xmlbeans.impl.common.GlobalLock;
import org.testng.annotations.Test;
import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.HomePage;
import pageobjects.SolutionsPage;

public class FR64_NotRebookedPNR extends TestBase
{
	public static TestDataHandler testDataHandler;
	@Test
	public void TestScenarios() throws Exception
	{
		ReportLog.setTestName("Test");
		testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser0'");
//		testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
		TestCases.PreRequisiteStep();
		TestCases.Step1();
//		TestCases.Step2A();
		TestCases.Step2B();
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
		
		public static void Step1() throws Exception
		{
			ReportLog.setTestCase("[STEP 1]");
				ReportLog.setTestStep("Solve a flight");
				testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
				HomePage.pdsSection.disruptedFlight(testDataHandler.disruptedFlight).checkBox.click();
				HomePage.pdsSection.solveButton.click();
		}
		
		public static void Step2A() throws Exception
		{
			ReportLog.setTestCase("[STEP 2A]");
				ReportLog.setTestStep("Close the Solution Window");
				SolutionsPage.solutionSection.flightPane.closeSolution.click();
				HomePage.closePopUp.yesButton.click();
		}
		
		public static void Step2B() throws Exception
		{
			ReportLog.setTestCase("[STEP 2B]");
				ReportLog.setTestStep("Solve another flight");
				testDataHandler = TestDataHandler.setDisruptionDataSet("Main", "RowSelection='DataSet0'");
				GlobalPage.mainHeader.showHideFlights.greaterThan.click();
				HomePage.pdsSection.disruptedFlight(testDataHandler.resolve).checkBox.click();
				HomePage.pdsSection.solveButton.click();
				HomePage.closePopUp.yesButton.click();
		}
	}
}

