package scenarios.Sprint19;

import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.WebManager;
import auto.framework.web.Element;
import auto.framework.web.WebControl;
import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.ConfigPage;
import pageobjects.GESSOAuthPage;
import pageobjects.HomePage;

public class BookingCapacityTest extends TestBase 
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
		TestCases.Step3();
		TestCases.Step4();
	}
	
	public static class TestCases
	{
		public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException
        {
			ReportLog.setTestCase("PAX Protection HomePage");
            ReportLog.setTestStep("Go to this URL: https://aviation-in-pax-protect-ui-app-hub-qa.run.aws-usw02-pr.ice.predix.io/ ");
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
		
		public static void Step1() throws InterruptedException
		{
			ReportLog.setTestCase("Go to Pax Config Page");
			ReportLog.setTestStep("Click Pax Config");
			GlobalPage.mainNavigationOptions.configPageButton.click();
			ConfigPage.configSection.verifyDisplayed(true, 5);
			//test123
		}
		
		public static void Step2() throws InterruptedException
		{
			ReportLog.setTestStep("Verify Advanced Options - Allow & Max Lateness");
	        ConfigPage.configSection.advanceHeader.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceHeader.advanceHeaderText.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceHeader.advanceHeaderText.verifyText("Advance Options");
	        ConfigPage.configSection.advanceHeader.downArrow.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.allowLatenessText.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.allowLatenessText.verifyText("Allow Lateness");
	        ConfigPage.configSection.advanceBody.allowLateness.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.maxLatenessText.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.maxLatenessText.verifyText("Max Lateness (hours)");
	        ConfigPage.configSection.advanceBody.maxLatenessBox.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.allowOverbookingText.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.allowOverbookingText.verifyText("Allow Overbooking");
	        ConfigPage.configSection.advanceBody.allowOverbooking.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.maxOverbookingText.verifyDisplayed(true, 5);
	        ConfigPage.configSection.advanceBody.maxOverbookingText.verifyText("Allowable Booking Capacity");
	        ConfigPage.configSection.advanceBody.maxOverbookingBox.verifyDisplayed(true, 5);
	        
		}
		
		public static void Step3() throws InterruptedException
		{
			ReportLog.setTestCase("Ensure that the system should only accept whole numbers from 1 to 100");
			
			ReportLog.setTestStep("Input Decimal Values");
			ConfigPage.configSection.advanceBody.allowOverbooking.click();
	        ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("1.0");
	        hoverElement(ConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Non-Numeric Values");
			//ConfigPage.configSection.advanceBody.allowOverbooking.click();
	        ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("ABC");
	        hoverElement(ConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Negative Values");
			//ConfigPage.configSection.advanceBody.allowOverbooking.click();
	        ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("-1.0");
	        hoverElement(ConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Less Than 1");
			//ConfigPage.configSection.advanceBody.allowOverbooking.click();
	        ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("0");
	        hoverElement(ConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Minimum value should be 1%"))
	        {
	            ReportLog.logEvent(true, "\"Minimum value should be 1%\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Minimum value should be 1%\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Greater Than 100");
			//ConfigPage.configSection.advanceBody.allowOverbooking.click();
	        ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("101");
	        hoverElement(ConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
		}
		
		public static void Step4() throws InterruptedException
		{
			ReportLog.setTestCase("Input and Apply valid values");
			ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("50");
			ConfigPage.configSection.advanceHeader.advanceApplyButton.click();
			
			ReportLog.setTestStep("Advance Options Message");
			ConfigPage.configSection.advanceBodyMessage.verifyDisplayed(true, 5);
			ConfigPage.configSection.advanceBodyMessage.advancedOptionsHeader.verifyDisplayed(true, 5);
			if (ConfigPage.configSection.advanceBodyMessage.getText().trim().contains("Your modified advanced options are saved."))
	        {
	            ReportLog.logEvent(true, "\"Your modified advanced options are saved.\" message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Your modified advanced options are saved.\" message is not present");
	        }
			ConfigPage.configSection.advanceBodyMessage.advancedOptionsOk.click();
			
			ReportLog.setTestStep("Return to Default Values");
			ConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("100");
			ConfigPage.configSection.advanceHeader.advanceApplyButton.click();
			ConfigPage.configSection.advanceBodyMessage.advancedOptionsOk.click();
			ConfigPage.configSection.advanceBody.allowOverbooking.click();
			ConfigPage.configSection.advanceHeader.advanceApplyButton.click();
			ConfigPage.configSection.advanceBodyMessage.advancedOptionsOk.click();
			ReportLog.setTestStep("FR28.0 and 29.0 - Test Passed");
		}
		
	    public static void hoverElement(Element element)
	    {
	        WebDriver driver = WebManager.getDriver();
	        WebElement webEl = element.toWebElement();

	        Actions action = new Actions(driver);
	        action.moveToElement(webEl).build().perform();
	        ReportLog.logEvent(true, "Hover over element");
	    }
	}

}
