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

public class PaxProtectionPage extends GlobalPage
{
    public static Page        page        = new Page("Pax Protection", "pax-protect/protection");
    public static PDSSection  pdsSection  = new PDSSection(null);
    public static PaperToast  paperToast  = new PaperToast(null);
    public static CancelPopUp cancelPopUp = new CancelPopUp(null);
    public static ClosePopUp  closePopUp  = new ClosePopUp(null);

    public static class PDSSection extends Element
    {
    	public final Element          rootElement;
        public final Element          impactedFlightsText;
        public final Element          impactedFlightsCount;
        public final Element          sortBy;
        public final Element          caretUp;
        public final Element          caretDown;
        public final Element          departureTime;
        public final Element          disruptionFilterIcon;
        public final Element          selectedDisruptionFilterIcon;
        public final Element          filterIcon;
        public final Element          selectedFilterIcon;
        public final Element          checkAll;
        public final Element          solveButton;
        public final Element          disruptedItem;
     
        public PDSSection (Element parent)
            {
                super("PDS section that contains the disrupted flights.", By.xpath(".//div[@id='list']"), parent);

                rootElement = new Element("Parent shadow root element", By.xpath("//px-inbox"));
                impactedFlightsText = new Element("Impacted Flights text", By.xpath(".//span[@id='impactedFlights']"));
                impactedFlightsCount = new Element("Impacted Flights count", By.xpath(".//span[@id='impactedFlightsValue']"));
                sortBy = new Element("'Sort by: text'", By.xpath(".//span[contains(text(),'Sort by:')]"));
                caretUp = new Element("Caret up icon", By.xpath(".//i[contains(@class,'fa fa-arrow-circle-up style-scope px-inbox')]"));
                caretDown = new Element("Caret down icon", By.xpath(".//i[contains(@class,'fa fa-arrow-circle-down style-scope px-inbox')]"));
                departureTime = new Element("'Departure Time' text", By.xpath(".//span[contains(text(),'Departure Time')]"));
                disruptionFilterIcon = new Element("Disruption Window Filter Icon", By.xpath(".//i[contains(@class,'fa fa-clock-o fa-stack-2x style-scope px-inbox')]"));
                selectedDisruptionFilterIcon = new Element("Selected Disruption Window Filter Icon", By.xpath(".//i[contains(@class,'fa fa-clock-o fa-stack-2x filter-icon-white style-scope px-inbox')]"));
                filterIcon = new Element("Filter icon", By.xpath(".//i[contains(@class,'fa fa-filter fa-stack-1x style-scope px-inbox')]"));
                selectedFilterIcon = new Element("Selected Filter Icon", By.xpath(".//i[contains(@class,'fa fa-filter fa-stack-1x filter-icon-white style-scope px-inbox')]"));
                checkAll = new Element("Check All checkbox", By.xpath(".//input[contains(@class,'px-inbox-checkbox style-scope px-inbox')]"));
                solveButton = new Element("Solve button", By.xpath(".//button[contains(text(),'Solve')]"));
//                disruptedItem = new Element("Disrupted Flight class", By.xpath(".//li[contains(@class, 'disruption-items')]"));
                disruptedItem = new Element("Disrupted Flight class", By.xpath(".//div[@id='disruption-scrollable']/ul/li[1]/div/div[1]"));
            }
    }
    
    public static class PaperToast extends Element
	    {
	        public final Element paperStatus;
	
	        public PaperToast (Element parent)
	        {
	            super("Paper toast", By.xpath(".//paper-toast[@class='x-scope paper-toast-0 paper-toast-open']"), parent);
	
	            paperStatus = new Element("Paper toast element", By.xpath(".//span[@id='label']"), this);
	        }
	    }
    
    public static class CancelPopUp extends Element
    {
        public final Element parentElement;
        public final Element cancelConfirmationText;
        public final Element noButton;
        public final Element yesButton;

        public CancelPopUp (Element parent)
        {
            super("Cancel Pop-up", By.xpath(".//px-modal[@id='cancel-modal']"), parent);

            parentElement = new Element("Parent element", By.xpath(".//section[@class='modal__content u-p+ style-scope px-modal']"));
            cancelConfirmationText = new Element("Cancel Confirmation Text", By.xpath(".//h3[@class='modal__title u-mt0 epsilon weight--normal style-scope px-modal']"), this);
            noButton = new Element("No Button", By.xpath(".//button[@id='btnModalNegative']"), this);
            yesButton = new Element("Yes Button", By.xpath(".//button[@id='btnModalPositive']"), this);
        }
    }

    public static class ClosePopUp extends Element
    {
        public final Element parentElement;
        public final Element closeConfirmationText;
        public final Element noButton;
        public final Element yesButton;

        public ClosePopUp (Element parent)
        {
            super("Close Pop-up", By.xpath(".//px-modal[@id='close-modal']"), parent);

            parentElement = new Element("Parent element", By.xpath(".//section[@class='modal__content u-p+ style-scope px-modal']"));
            closeConfirmationText = new Element("Close Confirmation Text", By.xpath(".//h3[@class='modal__title u-mt0 epsilon weight--normal style-scope px-modal']"), this);
            noButton = new Element("No Button", By.xpath(".//button[@id='btnModalNegative']"), this);
            yesButton = new Element("Yes Button", By.xpath(".//button[@id='btnModalPositive']"), this);
        }
    }
}