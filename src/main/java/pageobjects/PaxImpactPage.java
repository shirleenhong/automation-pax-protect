package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import org.openqa.selenium.By;

public class PaxImpactPage extends GlobalPage
{

    public static SummaryDrawer summaryDrawer = new SummaryDrawer();
    public static Filter filter = new Filter();
    public static Headers headers = new Headers();
    public static Solve solve = new Solve();

    public static class SummaryDrawer extends Element{
        public final Element          rootElement;
        public final Element          all;
        public final Element          delayed;
        public final Element          lessThan15Min;
        public final Element          lessThan60Min;
        public final Element          lessThan180Min;
        public final Element          cancelled;
        public final Element          disruptedPax;
        public final Element          needsToRebook;
        public final Element          noReflow;
        public final Element          rebooked;

        public SummaryDrawer(){

            super("Summary Drawer", By.xpath(".//div[contains(@class,'ppro-summary-drawer-view')]"));

            //rootElement           = new Element("Parent shadow root element", By.xpath(".//div[contains(@class,'ppro-summary-drawer-view')]"));
            rootElement           = new Element("Parent shadow root element", By.xpath(".//ppro-summary-drawer-view[contains(@header,'Impacted Flights')]"));
            all                   = new Element("All disrupted flight section ", By.xpath("//*[@id='all']"));
            delayed               = new Element("Delayed disrupted flight section ", By.xpath("//*[@id='delayed']"));
            lessThan15Min         = new Element("Less than 15 min disrupted flight section ", By.xpath("//*[@id='lt15Min']"));
            lessThan60Min         = new Element("Less than 60 min disrupted flight section ", By.xpath("//*[@id='lt60Min']"));
            lessThan180Min        = new Element("Less than 180 min disrupted flight section ", By.xpath("//*[@id='lt180Min']"));
            cancelled             = new Element("Canceled disrupted flight section ", By.xpath("//*[@id='cancelled']"));
            disruptedPax          = new Element("Disrupted Pax disrupted flight section ", By.xpath(""));
            needsToRebook         = new Element("Needs To Rebook disrupted flight section ", By.xpath(""));
            noReflow              = new Element("No Reflow disrupted flight section ", By.xpath(""));
            rebooked              = new Element("Rebooked disrupted flight section ", By.xpath(""));

        }
        
    }
    
    public static class Filter extends Element
    {
    	public final Element toggleCheckBoxLabel;
    	public final Element toggleCheckBox;
    	public final Element filterButton;
    	
    	public Filter()
    	{
    		super("Filter and Options", By.xpath(".//div[@class='actions-row style-scope pax-impact']"));
    		
    		toggleCheckBoxLabel = new Element("Toggle Check Box Label", By.xpath(".//div[@class='container style-scope ppro-checkbox']"));
    		toggleCheckBox = new Element("Toggle Check Box", By.xpath("//div[@class='squarebox style-scope ppro-checkbox']"));
    		filterButton = new Element("Filter Button", By.xpath(".//div[@class='style-scope px-modal']"));
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
			
    		solveButton = new Element("Solve Button", By.xpath(".//div[@class='btn btn--large btn--call-to-action  style-scope solve-button']"));
			
    	}
    }

}
