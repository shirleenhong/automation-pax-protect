package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import auto.framework.web.Element;
import auto.framework.web.Page;
import common.GlobalPage;

public class SolutionsPage extends GlobalPage
{
    public static Page            page            = new Page("Pax Protection - Solutions Window", "pax-protect/protection");
    public static SolutionSection solutionSection = new SolutionSection(null);

    public static class SolutionSection extends Element
    {
        public final Element    rootElement;
        public FlightPane       flightPane;
        public SolutionHeader   solutionHeader;
        public static Itinerary itineraryRow;
        public final int        pnrCount;

        public SolutionSection(
            Element parent)
        {
            super("Itinerary section that contains the newly generated itineraries.", By.xpath(".//div[@id='detail']"), parent);

            rootElement = new Element("Solutions Section", By.xpath(".//div[@id='detail']"));
            pnrCount = getCount(rootElement.toWebElement(), By.xpath(".//li[contains(@class, 'list-ui__item mobile-margin-bottom style-scope px-inbox-content')]"));
            flightPane = new FlightPane(this);
            solutionHeader = new SolutionHeader(this);
        }

        public static class FlightPane extends Element
        {
            public static SelectedFlight selectedFlight;
            public final Element closeSolution;

            public FlightPane(
                Element parent)
            {
                super("Flight pane", By.xpath(".//div[@id='flights-pane']"), parent);
                
                closeSolution = new Element("Close current solution", By.xpath(".//i[@id='closeSolution']"), this);
            }

            public SelectedFlight selectedFlight(String flightName)
            {
                return new SelectedFlight(this, flightName);
            }

            public static class SelectedFlight extends Element
            {
                public final Element flightCode;
                public final Element origDest;
                public final Element disruption;

                public SelectedFlight(
                    Element parent,
                    String flightName)
                {
                    super("Solution's Section Flight Pane", By.xpath(".//*[contains(text(),'" + flightName + "')]/../.."), parent);

                    flightCode = new Element("Flight code of flight", By.xpath(".//span[1]"), this);
                    origDest = new Element("Origin and destination of flight", By.xpath(".//span[2]"), this);
                    disruption = new Element("Disruption of flight", By.xpath(".//span[3]"), this);
                }
            }
        }

        public static class SolutionHeader extends Element
        {
            public final Element checkAll;
            public final Element pnrCol;
            public final Element itineraryCol;
            public final Element commitAll;
            public final Element notifyAll;
            public final Element filterPNR;

            public SolutionHeader(
                Element parent)
            {
                super("Solution's Section Header", By.xpath(".//div[@id='solution-window-header']"), parent);

                checkAll = new Element("Check all iterenary", By.xpath(".//input[contains(@class,'px-inbox-checkbox style-scope px-inbox-content')]"), this);
                pnrCol = new Element("PNR column header", By.xpath(".//span[contains(text(),'PNR')]"), this);
                itineraryCol = new Element("Itinerary column header", By.xpath(".//span[contains(text(),'Itinerary')]"), this);
                commitAll = new Element("Commit All button", By.xpath(".//button[contains(text(),'Commit')]"), this);
                notifyAll = new Element("Notify All button", By.xpath(".//button[contains(text(),'Notify')]"), this);
                filterPNR = new Element("Filter PNR icon", By.xpath(".//i[contains(@class,'fa fa-filter fa-stack-1x style-scope px-inbox-content')]"));
            }
        }

        public Itinerary itineraryRow(String itineraryID)
        {
            return new Itinerary(this, itineraryID);
        }

        public static class Itinerary extends Element
        {
            public final Element    checkBox;
            public PNRDetails       pnrDetails;
            public ItineraryDetails itineraryDetails;
            public PNRCommit        pnrCommit;
            public PNRNotify        pnrNotify;
            public final Element    terminatingStatus;
            public final Element    connectingStatus;
            public final Element    misconnectingStatus;

            public CommitStatus     commitStatus;

            public Itinerary(
                Element parent,
                String itineraryID)
            {
                super("PNR row", By.xpath(".//*[@id='" + itineraryID + "']/../../../.."), parent);

                checkBox = new Element("PNR's checkbox", By.xpath(".//input[@id='" + itineraryID + "']"), this);
                pnrDetails = new PNRDetails(this);
                itineraryDetails = new ItineraryDetails(this);
                pnrCommit = new PNRCommit(this, itineraryID);
                pnrNotify = new PNRNotify(this, itineraryID);
                commitStatus = new CommitStatus(this, itineraryID);
                terminatingStatus = new Element("PNR's terminating status", By.xpath(".//i[contains(@class,'fa fa-circle terminating style-scope px-inbox-content')]"), this);
                connectingStatus = new Element("PNR's connecting status", By.xpath(".//i[contains(@class,'fa fa-circle connecting style-scope px-inbox-content')]"), this);
                misconnectingStatus = new Element("PNR's misconnecting status", By.xpath(".//i[contains(@class,'fa fa-circle misconnecting style-scope px-inbox-content')]"), this);
            }

            public static class PNRDetails extends Element
            {
                public final Element pnrID;
                public final Element lateness;
                public final Element impactedPax;

                public PNRDetails(
                    Element parent)
                {
                    super("PNR's details", By.xpath(".//div[contains(@class,'flex__item flex__item--top green style-scope px-inbox-content')]"), parent);

                    pnrID = new Element("PNR's ID", By.xpath(".//span[contains(@class,'title style-scope px-inbox-content')]"), this);
                    lateness = new Element("PNR's lateness", By.xpath(".//span[contains(text(),'Lateness')]"), this);
                    impactedPax = new Element("PNR's impacted pax, SSR & FF", By.xpath(".//span[contains(text(),'PAX')]"), this);
                }
            }

            public static class ItineraryDetails extends Element
            {
                public CurrentItinerary currentItinerary;
                public NewItinerary     newItinerary;

                public ItineraryDetails(
                    Element parent)
                {
                    super("PNR's Itinerary Details", By.xpath(".//div[contains(@class,'flex__item--top u-1/2-desk u-1/2-lap u-1/1-palm div-pad red order-2 style-scope px-inbox-content')]"), parent);

                    currentItinerary = new CurrentItinerary(this);
                    newItinerary = new NewItinerary(this);
                }

                public static class CurrentItinerary extends Element
                {
                    public final Element           pnrCurrentState;
                    public CurrentItineraryDetails currentItineraryDetails;

                    public CurrentItinerary(
                        Element parent)
                    {
                        super("Current itinerary", By.xpath(".//div[contains(@class,'iti-current flex flex-row div-pad-bottom style-scope px-inbox-content')]"), parent);

                        pnrCurrentState = new Element("PNR's state", By.xpath(".//span[contains(text(),'Current')]"), this);
                        currentItineraryDetails = new CurrentItineraryDetails(this);
                    }

                    public static class CurrentItineraryDetails extends Element
                    {
                        public FirstFlight  firstFlight;
                        public SecondFlight secondFlight;

                        public CurrentItineraryDetails(
                            Element parent)
                        {
                            super("Current PNR's details", By.xpath(".//div[contains(@class,'flex__item flex__item--top green style-scope px-inbox-content')]"), parent);

                            firstFlight = new FirstFlight(this);
                            secondFlight = new SecondFlight(this);
                        }

                        public static class FirstFlight extends Element
                        {
                            public final Element carrierCode;
                            public final Element flightNum;
                            public final Element origDest;
                            public final Element times;
                            public final Element arrivalDelay;

                            public FirstFlight(
                                Element parent)
                            {
                                super("First flight in the Current PNR detail", By.xpath(".//div[1][contains(@class,'flex flex-row style-scope px-inbox-content')]"), parent);

                                carrierCode = new Element("Flight's carrier code", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-carrier green style-scope px-inbox-content')]"), this);
                                flightNum = new Element("Flight's number", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-flight-no green style-scope px-inbox-content')]"), this);
                                origDest =
                                        new Element("Flight's origin and destination", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-origin-destination green style-scope px-inbox-content')]"), this);
                                times = new Element("Flight's times", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-departure-arrival green style-scope px-inbox-content')]"), this);
                                arrivalDelay = new Element("Flight's arrival delay", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-arrival-delay green style-scope px-inbox-content')]"), this);
                            }
                        }

                        public static class SecondFlight extends Element
                        {
                            public final Element carrierCode;
                            public final Element flightNum;
                            public final Element origDest;
                            public final Element times;
                            public final Element arrivalDelay;

                            public SecondFlight(
                                Element parent)
                            {
                                super("Second flight in the Current PNR detail", By.xpath(".//div[2][contains(@class,'flex flex-row style-scope px-inbox-content')]"), parent);

                                carrierCode = new Element("Flight's carrier code", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-carrier green style-scope px-inbox-content')]"), this);
                                flightNum = new Element("Flight's number", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-flight-no green style-scope px-inbox-content')]"), this);
                                origDest =
                                        new Element("Flight's origin and destination", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-origin-destination green style-scope px-inbox-content')]"), this);
                                times = new Element("Flight's times", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-departure-arrival green style-scope px-inbox-content')]"), this);
                                arrivalDelay = new Element("Flight's arrival delay", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-arrival-delay green style-scope px-inbox-content')]"), this);
                            }
                        }
                    }
                }

                public static class NewItinerary extends Element
                {
                    public final Element       pnrNewState;
                    public NewItineraryDetails newItineraryDetails;

                    public NewItinerary(
                        Element parent)
                    {
                        super("New itinerary", By.xpath(".//div[contains(@class,'iti-new flex flex-row style-scope px-inbox-content')]"), parent);

                        pnrNewState = new Element("PNR's state", By.xpath(".//span[contains(text(),'New')]"), this);
                        newItineraryDetails = new NewItineraryDetails(this);
                    }

                    public static class NewItineraryDetails extends Element
                    {
                        public Element      noSeats;
                        public FirstFlight  firstFlight;
                        public SecondFlight secondFlight;

                        public NewItineraryDetails(
                            Element parent)
                        {
                            super("Current PNR's details", By.xpath(".//div[contains(@class,'flex__item flex__item--top green style-scope px-inbox-content')]"), parent);

                            noSeats = new Element("No available seat found error", By.xpath(".//span[contains(text(),'No available seat found.')]"), this);
                            firstFlight = new FirstFlight(this);
                            secondFlight = new SecondFlight(this);
                        }

                        public static class FirstFlight extends Element
                        {
                            public final Element carrierCode;
                            public final Element flightNum;
                            public final Element origDest;
                            public final Element times;

                            public FirstFlight(
                                Element parent)
                            {
                                super("First flight in the Current PNR detail", By.xpath(".//div[1][contains(@class,'flex flex-row style-scope px-inbox-content')]"), parent);

                                carrierCode = new Element("Flight's carrier code", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-carrier green style-scope px-inbox-content')]"), this);
                                flightNum = new Element("Flight's number", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-flight-no green style-scope px-inbox-content')]"), this);
                                origDest =
                                        new Element("Flight's origin and destination", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-origin-destination green style-scope px-inbox-content')]"), this);
                                times = new Element("Flight's times", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-departure-arrival green style-scope px-inbox-content')]"), this);
                            }
                        }

                        public static class SecondFlight extends Element
                        {
                            public final Element carrierCode;
                            public final Element flightNum;
                            public final Element origDest;
                            public final Element times;

                            public SecondFlight(
                                Element parent)
                            {
                                super("Second flight in the Current PNR detail", By.xpath(".//div[2][contains(@class,'flex flex-row style-scope px-inbox-content')]"), parent);

                                carrierCode = new Element("Flight's carrier code", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-carrier green style-scope px-inbox-content')]"), this);
                                flightNum = new Element("Flight's number", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-flight-no green style-scope px-inbox-content')]"), this);
                                origDest =
                                        new Element("Flight's origin and destination", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-origin-destination green style-scope px-inbox-content')]"), this);
                                times = new Element("Flight's times", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-iti-departure-arrival green style-scope px-inbox-content')]"), this);
                            }
                        }
                    }
                }
            }

            public static class PNRCommit extends Element
            {
                public final Element commitButton;
                public final Element noCapacity;
                public final Element available;
                public final Element availableOAT;
                public final Element noRUL;
                public final Element commitButtonDisabled;

                public PNRCommit(
                    Element parent,
                    String itineraryID)

                {
                    super("PNR's Commit", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-commit green style-scope px-inbox-content')]"), parent);

                    commitButton = new Element("Specific PNR commit button", By.xpath(".//button[contains(text(),'Commit') and contains(@id,'" + itineraryID + "')]"), this);
                    commitButtonDisabled =
                            new Element(
                                    "Specific PNR commit button",
                                    By.xpath(".//button[contains(text(),'Commit') and contains(@id,'" + itineraryID + "') and contains(@class,'btn btn--disabled style-scope px-inbox-content')]"),
                                    this);
                    available = new Element("Available text", By.xpath(".//span[contains(text(),'Available')]"), this);
                    availableOAT = new Element("Available - OAT", By.xpath(".//span[contains(text(),'Available – OAT')]"), this);
                    noCapacity = new Element("N/A-CP text", By.xpath(".//span[contains(text(),'N/A – CP')]"), this);
                    noRUL = new Element("N/A - RUL text", By.xpath(".//span[contains(text(),'N/A – RUL')]"), this);

                }

                public StatusSelection statusSelection(String toolTip)
                {
                    return new StatusSelection(this, toolTip);
                }

                public static class StatusSelection extends Element
                {

                    public final Element statusToolTip;

                    public StatusSelection(
                        Element parent,
                        String toolTip)
                    {
                        super("Tool tip message", By.xpath("//*[@id='list']/li[contains(text(),'" + toolTip + "')]"));
                        statusToolTip = new Element("Status tool tip message", By.xpath("//div[@id='tooltip']//div[@id='message']/span[contains(text(),'" + toolTip + "')]"));
                    }
                }
            }

            public static class PNRNotify extends Element
            {
                public final Element notifyButton;
                public final Element notifyStatus;
                public final Element notifyButtonDisabled;

                public PNRNotify(
                    Element parent,
                    String itineraryID)
                {
                    super("PNR's Notify", By.xpath(".//div[contains(@class,'flex__item flex__item--top div-notify green style-scope px-inbox-content')]"), parent);

                    notifyButton = new Element("Specific PNR notify button", By.xpath(".//button[contains(text(),'Notify') and contains(@id,'" + itineraryID + "')]"), this);
                    notifyStatus = new Element("Notify Status", By.xpath(".//span[contains(@class,'subtitle style-scope px-inbox-content')]"), this);
                    notifyButtonDisabled =
                            new Element(
                                    "Specific PNR notify button",
                                    By.xpath(".//button[contains(text(),'Notify') and contains(@id,'" + itineraryID + "') and contains(@class,'btn btn--disabled style-scope px-inbox-content')]"),
                                    this);

                }
            }

            public static class CommitStatus extends Element
            {
                public final Element status;
                public final Element commitStatusDropDown;
                // public final Element statusSelection;
                public final Element dropDownToolTip;

                public CommitStatus(
                    Element parent,
                    String itineraryID)
                {
                    super("Commit Status", By.xpath(".//px-dropdown[contains(@id,'" + itineraryID + "')]"), parent);

                    commitStatusDropDown = new Element("Commit Status Dropdown", By.xpath(".//div[@id='dropcell']"), this);
                    status = new Element("Commit Status", By.xpath(".//span[@id='textWrap']"), this);
                    dropDownToolTip = new Element("Status tool tip message", By.xpath("//div[@id='tooltip']//div[@id='message']//span[contains(@class,'style-scope px-tooltip')]"), this);
                }
            }

        }

        public static int getCount(WebElement rootElement, By by)
        {
            List<WebElement> webEl = rootElement.findElements(by);

            return webEl.size();
        }

    }
}
