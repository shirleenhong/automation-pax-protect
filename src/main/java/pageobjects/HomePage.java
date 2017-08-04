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

public class HomePage extends GlobalPage
{
    public static Page        page        = new Page("Pax Protection", "pax-protect/protection");
    public static PDSSection  pdsSection  = new PDSSection(null);
    public static PaperToast  paperToast  = new PaperToast(null);
    public static CancelPopUp cancelPopUp = new CancelPopUp(null);
    public static ClosePopUp  closePopUp  = new ClosePopUp(null);

    public static class PDSSection extends Element
    {
        public static DisruptedFlight disruptedFlight;
        public final Element          rootElement;
        public final Element          impactedFlightsText;
        public final Element          impactedFlightsCount;
        public final Element          sortBy;
        public final Element          caretUp;
        public final Element          caretDown;
        public final Element          departureTime;
        public final Element          disruptionFilterIcon;
        public final Element          selectedDisruptionFilterIcon;
        public DisruptionFilter       disruptionFilter;
        public final Element          filterIcon;
        public final Element          selectedFilterIcon;
        public Filter                 filter;
        public final Element          checkAll;
        public final Element          solveButton;
        public final Element          disruptedItem;
        public final int              disruptionCount;

        public PDSSection(
            Element parent)
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
            disruptionFilter = new DisruptionFilter(this);
            filterIcon = new Element("Filter icon", By.xpath(".//i[contains(@class,'fa fa-filter fa-stack-1x style-scope px-inbox')]"));
            selectedFilterIcon = new Element("Selected Filter Icon", By.xpath(".//i[contains(@class,'fa fa-filter fa-stack-1x filter-icon-white style-scope px-inbox')]"));
            filter = new Filter(this);
            checkAll = new Element("Check All checkbox", By.xpath(".//input[contains(@class,'px-inbox-checkbox style-scope px-inbox')]"));
            solveButton = new Element("Solve button", By.xpath(".//button[contains(text(),'Solve')]"));
            disruptedItem = new Element("Disrupted Flight class", By.xpath(".//li[contains(@class, 'disruption-items')]"));
            disruptionCount = disruptionsCount(rootElement.toWebElement(), By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]"));
        }

        public static class DisruptionFilter extends Element
        {
            public final Element disruptionFilterText;
            public final Element disruptionTextBox;
            public final Element hoursText;
            public final Element applyButton;
            public final Element errorTextMessage;

            public DisruptionFilter(
                Element parent)
            {
                super("Disruption Filter dropdown", By.xpath(".//div[@id='disruption-timer']"), parent);

                disruptionFilterText = new Element("Disruption Filter Text", By.xpath(".//span[contains(text(),'Schedule to depart within')]"), this);
                disruptionTextBox = new Element("Disruption Text Box", By.xpath(".//input[@id='disruptionInput']"), this);
                hoursText = new Element("Hours", By.xpath(".//span[contains(text(),'Hours')]"), this);
                applyButton = new Element("Apply button", By.xpath(".//button[@id='btnPDSApply']"), this);
                errorTextMessage = new Element("Error Text Message", By.xpath(".//span[@id='message']"), this);
            }
        }

        public static class Filter extends Element
        {

            public final Element   clearAllButton;
            public final Element   applyButton;
            public static DropDown dropDown;

            public Filter(
                Element parent)
            {
                super("Filter dropdown", By.xpath(".//div[@id='disruption-filter']"), parent);

                clearAllButton = new Element("Clear All", By.xpath(".//button[contains(text(),'Clear All')]"), this);
                applyButton = new Element("Apply", By.xpath(".//button[contains(text(),'Apply')]"), this);
            }

            public DropDown dropDown(String id)
            {
                return new DropDown(this, id);
            }

            public static class DropDown extends Element
            {
                public final Element   dropdownName;
                public final Element   dropDownIcon;
                public DropDownDetails dropdownDetails;
                // public static DropDownOption dropdownOption;

                public DropDown(
                    Element parent,
                    String id)
                {
                    super(id + " dropdown", By.xpath(".//px-dropdown[@id='" + id + "']/div[@id='dropcell']"), parent);

                    dropdownName = new Element(id + "filter dropdown", By.xpath(".//span[@id='textWrap']"), this);
                    dropDownIcon = new Element(id + "filter dropdown icon", By.xpath(".//iron-icon[@icon='fa:fa-angle-down']"), this);
                    dropdownDetails = new DropDownDetails(this, id);
                }

                public static class DropDownDetails extends Element
                {
                    public final Element searchBox;

                    public DropDownDetails(
                        Element parent,
                        String id)
                    {
                        super("Dropdown details for " + id, By.xpath("..//px-dropdown-content[@id='" + id + "']/div[@class='px-dropdown--content style-scope px-dropdown-content']"), parent);

                        searchBox = new Element(id + "Search box", By.xpath(".//input[@class='text-input input--search u-ml-- u-mb-- u-p-- style-scope px-dropdown-content']"), this);
                    }
                }
            }
        }

        public DisruptedFlight disruptedFlight(String flightID)
        {
            return new DisruptedFlight(this, flightID);
        }

        public static class DisruptedFlight extends Element
        {
            public final Element checkBox;
            public FlightName    flightName;
            public FlightDetails flightDetails;

            public DisruptedFlight(
                Element parent,
                String flightID)
            {
                super("Disrupted flight row", By.xpath(".//*[@id='" + flightID + "']/../../.."), parent);

                checkBox = new Element("Check box per disrupted flight", By.xpath(".//input[@id='" + flightID + "']"), this);
                flightName = new FlightName(this);
                flightDetails = new FlightDetails(this);
            }

            public static class FlightName extends Element
            {
                public final Element flightNameID;
                public final Element flightNameTime;
                public final Element bookedPaxCount; // J = business | Y = coach

                public FlightName(
                    Element parent)
                {
                    super("Disrupted flight's name", By.xpath(".//div[@class='text u-2/6 style-scope px-inbox']"), parent);
                    flightNameID = new Element("Flight name's ID", By.xpath(".//span[contains(@class,'title')]"), this);
                    flightNameTime = new Element("Flight name's time", By.xpath(".//span[contains(text(),'/')]"), this);
                    bookedPaxCount = new Element("Booked Pax count", By.xpath(".//span[contains(text(),'F') and contains(text(),'J') and contains(text(),'Y')]"), this);
                }
            }

            public static class FlightDetails extends Element
            {
                public final Element flightRoute;
                public final Element flightCancelStatus;
                public final Element flightDelayStatus;
                public final Element delayMinutes;
                public final Element flightMisconxLabel;
                public final Element flightMisconxCount;

                public FlightDetails(
                    Element parent)
                {
                    super("Disrupted flight's details", By.xpath(".//div[@class='text u-3/6 style-scope px-inbox']"), parent);
                    flightRoute = new Element("Flight detail's origin and destination", By.xpath(".//span[1]"), this);
                    flightCancelStatus = new Element("Flight detail's status", By.xpath(".//span[contains(text(),'Cancelled')]"), this);
                    flightDelayStatus = new Element("Flight detail's status", By.xpath(".//span[contains(text(),'Delay')]"), this);
                    delayMinutes = new Element("Delay minutes", By.xpath(".//span[contains(text(),':')]"), this);
                    flightMisconxLabel = new Element("Misconx label", By.xpath(".//span[contains(text(),'Misconx')]"), this);
                    flightMisconxCount = new Element("Misconx count", By.xpath(".//span[contains(text(),'J') and contains(text(),'Y')]"), this);
                }
            }
        }

        public ProgressBarSection progressBarSection()
        {
            return new ProgressBarSection();
        }

        public static class ProgressBarSection extends Element
        {
            public final Element progressStatus;
            public final Element progressBar;
            public final Element cancelButton;

            public ProgressBarSection()
            {
                super("Progress Bar Section", By.xpath(".//*[@id='modal']"));

                progressStatus = new Element("Progress Bar Status", By.xpath(".//div[contains(@class, 'loading')]"), this);
                progressBar = new Element("Progress Bar", By.xpath(".//div[contains(@class,'box-progress')]"), this);
                cancelButton = new Element("Cancel button in progress bar section", By.xpath(".//button[contains(text(),'Cancel')]"), this);
            }
        }

        public int disruptionsCount(WebElement bigElement, By by)
        {
            List<WebElement> disruption = bigElement.findElements(by);

            return disruption.size();
        }
    }

    public static class PaperToast extends Element
    {
        public final Element paperStatus;

        public PaperToast(
            Element parent)
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

        public CancelPopUp(
            Element parent)
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

        public ClosePopUp(
            Element parent)
        {
            super("Close Pop-up", By.xpath(".//px-modal[@id='close-modal']"), parent);

            parentElement = new Element("Parent element", By.xpath(".//section[@class='modal__content u-p+ style-scope px-modal']"));
            closeConfirmationText = new Element("Close Confirmation Text", By.xpath(".//h3[@class='modal__title u-mt0 epsilon weight--normal style-scope px-modal']"), this);
            noButton = new Element("No Button", By.xpath(".//button[@id='btnModalNegative']"), this);
            yesButton = new Element("Yes Button", By.xpath(".//button[@id='btnModalPositive']"), this);
        }
    }
}