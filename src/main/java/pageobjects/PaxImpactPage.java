package pageobjects;

import auto.framework.ReportLog;
import auto.framework.WebManager;
import auto.framework.web.Element;
//import seleniumwireframe.WebManager;
import common.GlobalPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaxImpactPage extends GlobalPage
{

    public static SummaryDrawer summaryDrawer = new SummaryDrawer();
    public static Filter filter = new Filter();
    public static Headers headers = new Headers();
    public static Solve solve = new Solve();

    public static HeadersStyle headersStyle = new HeadersStyle();

    public static WebDriver driver = WebManager.getDriver();

    public static class SummaryDrawer extends Element{
        public final Element          rootElement;
        public final Element          all;
        public final Element          delayed;
        public final Element          greaterThan15Min;
        public final Element          greaterThan60Min;
        public final Element          greaterThan180Min;
        public final Element          cancelled;
        public final Element          disruptedPax;
        public final Element          needsToRebook;
        public final Element          noReflow;
        public final Element          rebooked;


        public SummaryDrawer(){

            super("Summary Drawer", By.xpath(".//div[contains(@class,'ppro-summary-drawer-view')]"));

            //rootElement           = new Element("Parent shadow root element", By.xpath(".//div[contains(@class,'ppro-summary-drawer-view')]"));
            rootElement           = new Element("Parent shadow root element", By.xpath(".//ppro-summary-drawer-view[@class='style-scope impacted-flights']"));
            all                   = new Element("All disrupted flight section ", By.xpath("//*[@id='all']"));
            delayed               = new Element("Delayed disrupted flight section ", By.xpath("//*[@id='delayed']"));
            greaterThan15Min         = new Element("Greater than 15 min disrupted flight section ", By.xpath("//*[@id='lt60Min']"));
            greaterThan60Min         = new Element("Greater than 60 min disrupted flight section ", By.xpath("//*[@id='lt180Min']"));
            greaterThan180Min        = new Element("Greater than 180 min disrupted flight section ", By.xpath("//*[@id='ge180Min']"));
            cancelled             = new Element("Canceled disrupted flight section ", By.xpath("//*[@id='cancelled']"));
            disruptedPax          = new Element("Disrupted Pax disrupted flight section ", By.xpath("//*[@id='disruptedPax']"));
            needsToRebook         = new Element("Needs To Rebook disrupted flight section ", By.xpath("//*[@id='needsToRebook']"));
            noReflow              = new Element("No Reflow disrupted flight section ", By.xpath("//*[@id='noReflow']"));
            rebooked              = new Element("Rebooked disrupted flight section ", By.xpath("//*[@id='rebooked']"));

        }

        public SummaryDrawerItem summaryDrawerItem(String id){
            return new SummaryDrawerItem(id);
        }

        public class SummaryDrawerItem extends Element{
            public  Element itemValue ;

            public SummaryDrawerItem( String id){
                super("Summary Drawer Item: " + id, By.xpath(".//stat-val[@id='"+id+"']"));
                itemValue = new Element("Summary Drawer Item Value ", By.xpath(".//ppro-horizontal-selector[@id='passenger-stats']//stat-val[@id='"+id+"']//div[@class='value value-none-importance  style-scope stat-val']"));
            }

        }
        
    }
    
    public static class Filter extends Element
    {
    	public final Element toggleCheckBoxLabel;
    	public final Element toggleCheckBox;
    	public final Element filterButton;
    	public final Element searchBox;
    	
    	public Filter()
    	{
    		super("Filter and Options", By.xpath(".//div[@class='actions-row style-scope impacted-flights']"));
    		
    		toggleCheckBoxLabel = new Element("Toggle Check Box Label", By.xpath(".//div[@class='container style-scope ppro-checkbox']"));
    		toggleCheckBox = new Element("Toggle Check Box", By.xpath("//div[@class='squarebox style-scope ppro-checkbox']"));
    		filterButton = new Element("Filter Button", By.xpath(".//button[@class='filter-btn style-scope ppro-modal-filter-view']"));
    		searchBox = new Element("Search Textbox", By.xpath(".//input[@class='style-scope paper-input']"));
  
    	}
    	
    	//Elements for the Viewing Options icons, when both icons have the same elements
    	public WebElement viewOptions(String viewType)
    	{
    		WebDriver driver = WebManager.getDriver();
    		
    		WebElement viewOption = null;
    		
			WebElement viewOptionContainer = driver.findElement(By.xpath(".//*[@class='style-scope impacted-flights']/ppro-toggle-list-view"));
			
			//Listing all the similar elements
			List <WebElement> viewOptionsList = viewOptionContainer.findElements(By.tagName("iron-icon"));
			
			//Creating the distinction of the two elements, 1 = List View, 2 = Grid View. Type "list" or "grid" on the Test Page.
			if(viewType.equalsIgnoreCase("list")) {
				viewOption = viewOptionsList.get(0);
			} else if (viewType.equalsIgnoreCase("grid")) {
				viewOption = viewOptionsList.get(1);
			} else {
				ReportLog.logEvent(false, "The view type is invalid.");
			}
			
			if(viewOption.isDisplayed()==true){
				ReportLog.logEvent(true, "The "+viewType+" view option is displayed.");
			} else {
				ReportLog.logEvent(false, "The "+viewType+" view option is not displayed.");
			}
			
			return viewOption;
    	}
    }
    
    public static class Headers extends Element
    {
    	public final Element headerCheckBox;
    	public final Element impactHeader;
    	public final Element flightNumberHeader;
    	public final Element tailHeader;
    	public final Element originHeader;
    	public final Element destinationHeader;
    	public final Element departureHeader;
    	public final Element arrivalHeader;
    	public final Element paxHeader;
    	public final Element misconnectionHeader;
    	
    	public Headers()
    	{
    		super("List View", By.xpath(".//div[@class='list-container style-scope pax-impact']"));
			
			headerCheckBox = new Element("List View Check Box", By.xpath(".//div[@class='squarebox style-scope ppro-checkbox']"));
			impactHeader = new Element("Impact Type Header", By.xpath(".//div[@id='delayHeader']"));
			flightNumberHeader = new Element("Flight # Header", By.xpath(".//div[@id='flightNumberHeader']"));
			tailHeader = new Element("Tail Header", By.xpath(".//div[@id='tailHeader']"));
			originHeader = new Element("Origin Header", By.xpath(".//div[@id='originHeader']"));
			destinationHeader = new Element("Destination Header", By.xpath(".//div[@id='destinationHeader']"));
			departureHeader = new Element("Departure Time Header", By.xpath(".//div[@id='departureTimeHeader']"));
			arrivalHeader = new Element("Arrivel Time Header", By.xpath(".//div[@id='arrivalTimeHeader']"));
			paxHeader = new Element("Pax Header", By.xpath(".//div[@id='passengerCountHeader']"));
			misconnectionHeader = new Element("Misconnection Header", By.xpath(".//div[@id='misconnectionCountHeader']"));
			
    	}
    	
    	
    }
    
    public static class Solve extends Element
    {
    	public final Element solveButton;

    	
    	public Solve()
    	{
    		super("List View", By.xpath(".//div[@class='bottom-row style-scope impacted-flights']"));
    		//solveButton = new Element("Solve Button", By.xpath(".//solve-button[@class='style-scope impacted-flights']"));
            solveButton = new Element("Solve Button", By.xpath(".//solve-button[contains(@class,'style-scope impacted-flights')]"));			
    	}
    }

    public static class HeadersStyle extends Element
    {

        public final Element          impactedFlightsMidHeader;
        public final Element          impactedFlightsHeader;
        public final Element          allHeader;
        public final Element          delayedHeader;
        public final Element          gt15MinHeader;
        public final Element          gt60MinHeader;
        public final Element          gt180MinHeader;
        public final Element          cancelledHeader;
        public final Element          allHeaderValue;
        public final Element          delayedHeaderValue;
        public final Element          gt15MinHeaderValue;
        public final Element          gt60MinHeaderValue;
        public final Element          gt180MinHeaderValue;
        public final Element          cancelledHeaderValue;

        public HeadersStyle(){

            super("Summary Drawer", By.xpath(".//div[contains(@class,'ppro-summary-drawer-view')]"));

            impactedFlightsMidHeader = new Element("Impacted Flights Middle Header", By.xpath(".//span[text()='Impacted Flights']"));
            impactedFlightsHeader = new Element("Impacted Flights Header", By.xpath(".//ppro-page-header[text()='Impacted Flights']"));
            allHeader = new Element("ALL Header", By.xpath(".//div[@id='header' and contains(text(),'ALL')]"));
            delayedHeader = new Element("DELAYED Header", By.xpath(".//div[@id='header' and contains(text(),'DELAYED')]"));
            gt15MinHeader = new Element(">= 15 Header", By.xpath(".//div[@id='header' and contains(text(),'≥ 15min')]"));
            gt60MinHeader = new Element(">= 60 Header", By.xpath(".//div[@id='header' and contains(text(),'≥ 60min')]"));
            gt180MinHeader = new Element(">= 180 Header", By.xpath(".//div[@id='header' and contains(text(),'≥ 180min')]"));
            cancelledHeader = new Element("CANCELLED Header", By.xpath(".//div[@id='header' and contains(text(),'CANCELLED')]"));
            allHeaderValue = new Element("ALL Header Value", By.xpath(".//*[@id='all']//div[@class='value value-none-importance value-none-importance-highlighted style-scope stat-val']"));
            delayedHeaderValue = new Element("DELAYED Header Value", By.xpath(".//*[@id='delayed']//div[@class='value value-none-importance  style-scope stat-val']"));
            gt15MinHeaderValue = new Element("GT15MIN Header Value", By.xpath(".//*[@id='lt60Min']//div[@class='value value-low-importance  style-scope stat-val']"));
            gt60MinHeaderValue = new Element("GT60MIN Header Value", By.xpath(".//*[@id='lt180Min']//div[@class='value value-middle-importance  style-scope stat-val']"));
            gt180MinHeaderValue = new Element("GT180MIN Header Value", By.xpath(".//*[@id='ge180Min']//div[@class='value value-high-importance  style-scope stat-val']"));
            cancelledHeaderValue = new Element("CANCELLED Header Value", By.xpath(".//*[@id='cancelled']//div[@class='value value-none-importance  style-scope stat-val']"));

        }

    }



}
