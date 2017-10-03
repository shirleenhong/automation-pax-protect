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

public class DisruptionFilterPage extends GlobalPage
{
    public static DisruptionFilter       disruptionFilter       = new DisruptionFilter(null);

    public static class DisruptionFilter extends Element
    {   
        public final Element originDropdown;
        public final Element originTextbox;
        public final Element destinationDropdown;
        public final Element destinationTextbox;
        public final Element flightNumDropdown;
        public final Element flightNumTextBox;
        public final Element impactTypeDropdown;
        public final Element clearAllButton;
        public final Element applybutton;
    	
        public DisruptionFilter (Element parent)
            {
        		super("Disruption Filter section.", By.xpath("//div[@id='disruption-filter']"), parent);
        		
        		originDropdown = new Element("'Origin' Dropdown", By.xpath(".//px-dropdown[@id='dpOrig']/div[@id='dropcell']"));
        		originTextbox = new Element("'Origin' Textbox", By.xpath(".//input[@placeholder='Enter Origin']"));
        		destinationDropdown = new Element("'Destination' Dropdown", By.xpath(".//px-dropdown[@id='dpDest']/div[@id='dropcell']"));
        		destinationTextbox = new Element("'Destination' Textbox", By.xpath(".//input[@placeholder='Enter Destination']"));
        		flightNumDropdown = new Element("'Flight #' Dropdown", By.xpath(".//px-dropdown[@id='dpFlight']/div[@id='dropcell']"));
        		flightNumTextBox = new Element("'Flight #' Textbox", By.xpath(".//input[@placeholder='Enter Flight #']"));
        		impactTypeDropdown = new Element("'Impact Type' Dropdown", By.xpath(".//px-dropdown[@id='dpImpact']/div[@id='dropcell']"));
        		clearAllButton = new Element("Clear All", By.xpath(".//button[contains(text(),'Clear All')]"));
        		applybutton = new Element("Apply", By.xpath(".//button[contains(text(),'Apply')]"));
            }
        
    }
}