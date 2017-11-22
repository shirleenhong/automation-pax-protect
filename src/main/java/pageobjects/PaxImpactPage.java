package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import org.openqa.selenium.By;

public class PaxImpactPage extends GlobalPage{

    public static SummaryDrawer summaryDrawer = new SummaryDrawer();

    public static class SummaryDrawer extends Element{
        public final Element          rootElement;
        public final Element          all;
        public final Element          delayed;
        public final Element          lessThan15Min;
        public final Element          lessThan60Min;
        public final Element          lessThan180Min;
        public final Element          canceled;
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
            canceled              = new Element("Canceled disrupted flight section ", By.xpath("//*[@id='cancelled']"));
            disruptedPax          = new Element("Disrupted Pax disrupted flight section ", By.xpath(""));
            needsToRebook         = new Element("Needs To Rebook disrupted flight section ", By.xpath(""));
            noReflow              = new Element("No Reflow disrupted flight section ", By.xpath(""));
            rebooked              = new Element("Rebooked disrupted flight section ", By.xpath(""));

        }
    }

}
