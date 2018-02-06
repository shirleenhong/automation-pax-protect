package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import org.openqa.selenium.By;

public class SolutionScreenPage extends GlobalPage {

    public static SolutionPageFrame solutionPageFrame = new SolutionPageFrame();
    public static PNRListView pnrListView = new PNRListView();
    public static SelectedFlightsFrame selectedFlightsFrame = new SelectedFlightsFrame();
    public static TotalPNRsFrame totalPNRsFrame = new TotalPNRsFrame();

    public static class SolutionPageFrame extends Element{

        public final Element          impactedFlightsText;
        public final Element          selectedFlightsText;
        public final Element          pnrDetailsText;
        public final Element          selectedFlights;
        public final Element          totalPNRs;

        public SolutionPageFrame(){

            super("Solution Screen Drawer", By.xpath(".//ppro-summary-drawer-view[contains(@class,'pnr-solutions')]"));

            impactedFlightsText           = new Element("Impacted Flights Text", By.xpath(".//span[text()='Impacted Flights']"));
            selectedFlightsText           = new Element("Selected Flights Text", By.xpath(".//span[contains(text(),'Selected Flights')]"));
            pnrDetailsText                = new Element("PNR Details Text", By.xpath(".//ppro-page-header[text()='PNR Details']"));

            selectedFlights           = new Element("Impacted Flights", By.xpath(".//ppro-horizontal-selector[@header='SELECTED FLIGHTS']"));
            totalPNRs           = new Element("Total PNRs", By.xpath(".//ppro-horizontal-selector[@selected-id='total']"));

        }

    }

    public static class SelectedFlightsFrame extends Element
    {

        public SelectedFlightsFrame() {

            super("Selected Flights Frame", By.xpath(".//ppro-horizontal-selector[@header='SELECTED FLIGHTS']"));

        }
        public SelectedFlightsItem selectedFlightsItem(String id) {return new SelectedFlightsItem(id);}

        public static class SelectedFlightsItem extends Element{

            public SelectedFlightsItem(String id){
                super("Selected Flights Item: " + id, By.xpath(".//stat-val[@id='"+id+"']"));
            }
        }

        public SelectedFlightsButton selectedFlightsButton = new SelectedFlightsButton();

        public static class SelectedFlightsButton extends Element{

            public SelectedFlightsButton (){
                super("Selected Flights Button: ", By.xpath(".//div[@class='button style-scope ppro-horizontal-selector']"));
            }

        }


    }

    public static class TotalPNRsFrame extends Element {
        public TotalPNRsFrame() {

            super("Total PNRs Frame", By.xpath(".//ppro-horizontal-selector[@selected-id='total']"));

        }

        public TotalPNRsItem totalPNRsItem(String id) {return new TotalPNRsItem(id);}

        public static class TotalPNRsItem extends Element{

            public final Element totalPNRsItemText;

            public TotalPNRsItem(String id){
                super("Total PNRs Item: " + id, By.xpath(".//stat-val[@id='"+id+"']"));
                totalPNRsItemText = new Element("Total PNRs Item " + id, By.xpath(".//div[contains(@class,'stat-val')]"), this);
            }
        }
    }

    public static class PNRListView extends Element
    {
        public PNRListView()
        {
            super("List View", By.xpath("//*[contains(@class,'style-scope pnr-solution-list')]"));
        }

        public PNRItem pnrItem(String id)
        {
            return new PNRItem(id);
        }

        public static class PNRItem extends Element
        {
            public final Element pnrItemCheckBox;

            public PNRItem (String id)
            {
                super("PNR Item: " + id, By.xpath(".//ppro-list-item[@id='"+id+"']"));
                pnrItemCheckBox = new Element("PNR Item Check Box " + id, By.xpath(".//div[@class='squarebox style-scope ppro-checkbox']"), this);

            }
        }

    }


}
