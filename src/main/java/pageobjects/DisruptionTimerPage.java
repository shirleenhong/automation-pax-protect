package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import auto.framework.ReportLog;
import auto.framework.WebManager;
import auto.framework.web.Element;
import auto.framework.web.Page;
import common.GlobalPage;

public class DisruptionTimerPage extends GlobalPage
{
    public static DisruptionTimer       disruptionTimer        = new DisruptionTimer(null);

    public static class DisruptionTimer extends Element
    {   
        public final Element disruptionTimerText;
        public final Element hoursTextBox;
        public final Element hoursLabel;
        public final Element applyButton;
        public final Element errorTextMessage;	
    	
        public DisruptionTimer (Element parent)
            {
        		super("Disruption Timer section.", By.xpath("//div[@id='disruption-timer']"), parent);
        		
        		disruptionTimerText = new Element("'Schedule to depart within' text", By.xpath(".//span[contains(text(),'Schedule to depart within')]"));
        		hoursTextBox = new Element("'Hours' textbox", By.xpath(".//input[@id='disruptionInput']"));
        		hoursLabel = new Element("'Hours' label", By.xpath(".//span[contains(text(),'Hours')]"));
        		applyButton = new Element("Apply", By.xpath(".//button[@id='btnPDSApply']"));
        		errorTextMessage = new Element("'Invalid Input' text", By.xpath(".//span[@id='message']"));
            }
        
    }
}