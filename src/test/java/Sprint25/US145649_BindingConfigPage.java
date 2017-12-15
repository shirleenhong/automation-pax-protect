package Sprint25;

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
import pageobjects.PaxConfigPage;
import pageobjects.GESSOAuthPage;

public class US145649_BindingConfigPage extends TestBase 
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
            ReportLog.setTestStep("Go to this URL: https://pax-protect-ui-shell.run.aws-usw02-pr.ice.predix.io/ ");
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
			GlobalPage.mainNavigationOptions.PaxConfigPageButton.click();
			PaxConfigPage.configSection.verifyDisplayed(true, 5);
		}
		
		public static void Step2() throws InterruptedException
		{
			ReportLog.setTestStep("Verify Advanced Options - Allow & Max Lateness");
	        PaxConfigPage.configSection.advanceHeader.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceHeader.advanceHeaderText.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceHeader.advanceHeaderText.verifyText("Advance Options");
	        PaxConfigPage.configSection.advanceHeader.downArrow.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.allowLatenessText.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.allowLatenessText.verifyText("Allow Lateness");
	        PaxConfigPage.configSection.advanceBody.allowLateness.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.maxLatenessText.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.maxLatenessText.verifyText("Max Lateness (hours)");
	        PaxConfigPage.configSection.advanceBody.maxLatenessBox.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.allowOverbookingText.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.allowOverbookingText.verifyText("Allow Overbooking");
	        PaxConfigPage.configSection.advanceBody.allowOverbooking.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.maxOverbookingText.verifyDisplayed(true, 5);
	        PaxConfigPage.configSection.advanceBody.maxOverbookingText.verifyText("Allowable Booking Capacity");
	        PaxConfigPage.configSection.advanceBody.maxOverbookingBox.verifyDisplayed(true, 5);
	        
		}
		
		public static void Step3() throws InterruptedException
		{
			ReportLog.setTestCase("Ensure that the system should only accept whole numbers from 1 to 100");
			
			ReportLog.setTestStep("Input Decimal Values");
			PaxConfigPage.configSection.advanceBody.allowOverbooking.click(); //Enable this scenario if initial toggle is DISABLED
	        PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("1.0");
	        hoverElement(PaxConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (PaxConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Non-Numeric Values");
			
	        PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("ABC");
	        hoverElement(PaxConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (PaxConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Negative Values");
			
	        PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("-1.0");
	        hoverElement(PaxConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (PaxConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Less Than 1");
			
	        PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("0");
	        hoverElement(PaxConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (PaxConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
	        {
	            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 100\" error message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 100\" error message is not present");
	        }
	        
	        ReportLog.setTestStep("Input Greater Than 100");
			
	        PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("101");
	        hoverElement(PaxConfigPage.configSection.advanceBody.maxOverbookingBox);
	        if (PaxConfigPage.configSection.advanceBody.toolTip.getText().trim().contains("Accepts whole numbers only from 1 to 100"))
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
			PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("50");
			PaxConfigPage.configSection.currentProfile.applyButton.click();
			
			ReportLog.setTestStep("Advance Options Message");
			PaxConfigPage.configSection.advanceBodyMessage.verifyDisplayed(true, 5);
			PaxConfigPage.configSection.advanceBodyMessage.advancedOptionsHeader.verifyDisplayed(true, 5);
			if (PaxConfigPage.configSection.advanceBodyMessage.getText().trim().contains("Your config settings are now applied."))
	        {
	            ReportLog.logEvent(true, "\"Your config settings are now applied.\" message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "\"Your config settings are now applied.\" message is not present");
	        }
			PaxConfigPage.configSection.advanceBodyMessage.advancedOptionsOk.click();
			
			ReportLog.setTestStep("Return to Default Values");
			PaxConfigPage.configSection.advanceBody.maxOverbookingBox.typeKeys("100");
			PaxConfigPage.configSection.currentProfile.applyButton.click();
			PaxConfigPage.configSection.advanceBodyMessage.advancedOptionsOk.click();
			PaxConfigPage.configSection.advanceBody.allowOverbooking.click();
			PaxConfigPage.configSection.currentProfile.applyButton.click();
			PaxConfigPage.configSection.advanceBodyMessage.verifyDisplayed(true, 5);
			if (PaxConfigPage.configSection.advanceBodyMessage.getText().trim().contains("Your config settings are now applied. The Max Lateness or Booking Capacity are not saved when their toggles are turned off."))
	        {
	            ReportLog.logEvent(true, "Confirmation for the toggle message is present");
	        }
	        else
	        {
	            ReportLog.logEvent(false, "Confirmation for the toggle message is not present");
	        }
			
			PaxConfigPage.configSection.advanceBodyMessage.advancedOptionsOk.click();
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
