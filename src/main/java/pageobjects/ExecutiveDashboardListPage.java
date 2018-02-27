package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import org.openqa.selenium.By;

public class ExecutiveDashboardListPage extends GlobalPage {

    public static DisruptedFlightList disruptedFlightList = new DisruptedFlightList();
    public static DisruptedFlightListHeader disruptedFlightListHeader = new DisruptedFlightListHeader();

    public static class DisruptedFlightList extends Element {

        public DisruptedFlightList(){
            super("Disrupted Flights List", By.tagName("ppro-disrupted-flights"));
        }

        public DisruptedFlightListItem disruptedFlightListItem(String id){return new DisruptedFlightListItem(id);}

        public static class DisruptedFlightListItem extends Element{
            DisruptedFlightListItem(String id){
                super(By.xpath(".//ppro-list-item[@id='"+ id +"']"));
            }
        }

    }

    public static class DisruptedFlightListHeader{
        public final Element impactHeader;
        public final Element flightHeader;
        public final Element origHeader;
        public final Element destHeader;
        public final Element paxHeader;
        public final Element misconxHeader;
        public final Element welfareHeader;
        public final Element impactCodeHeader;
        public final Element psngrValueHeader;

        public DisruptedFlightListHeader(){
            impactHeader = new Element(By.xpath("//div[@id='delayHeader']"));
            flightHeader = new Element(By.xpath("//div[@id='flightNumberHeader']"));
            origHeader = new Element(By.xpath("//div[@id='originHeader']"));
            destHeader = new Element(By.xpath("//div[@id='destinationHeader']"));
            paxHeader = new Element(By.xpath("//div[@id='cabinTypePassengerCountHeader']"));
            misconxHeader = new Element(By.xpath("//div[@id='cabinTypeMisconnectingPassengerCountHeader']"));
            welfareHeader = new Element(By.xpath("//div[@id='welfareHeader']"));
            impactCodeHeader = new Element(By.xpath("//div[@id='impactCodeHeader']"));
            psngrValueHeader = new Element(By.xpath("//div[@id='passengerValueHeader']"));

        }



    }

}
