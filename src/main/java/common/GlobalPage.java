package common;

import org.openqa.selenium.By;

import auto.framework.PageBase;
import auto.framework.web.Element;


public class GlobalPage extends PageBase
{
    public static MainPXNavigationOptions mainPXNavigationOptions = new MainPXNavigationOptions();
    public static TimeUTC timeUTC = new TimeUTC();

    public static class MainPXNavigationOptions extends Element {

       // public final Element configPageButton;

        public MainPXNavigationOptions()
        {
            super("Main Navigation Options", By.xpath("//*[@id='items']"));
        //    configPageButton = new Element("Pax Config Button", By.xpath(".//p[contains(text(),'Pax Protection')]"), this);
        }

        public NavigateTo navigateToNavbarLink(String name)
        {
            return new MainPXNavigationOptions.NavigateTo("Navigate To: " + name, By.xpath(".//p[contains(text(),'" + name + "')]"), this);
        }

        public static class NavigateTo extends Element
        {
            public final Element icon;
            public final Element navText;
           // public final Element impactPage;
           // public final Element protectionPage;


            public NavigateTo(
                    String name,
                    By by,
                    Element parent)
            {
                super(name, by, parent);
                icon = new Element(this.name + " / Icon", By.xpath("//*[@id='items']//px-icon[contains(@class,'px-app-nav-item') and @icon='fa:cog']"), this);
                navText = new Element(this.name + " Text", By.xpath(".//p[contains(text(),'" + name + "']"), this);
                //impactPage = new Element(this.name + " Text", By.xpath("/html/body/app-hub-shell/poly-app-app/iron-pages/poly-disruptions/div"), this);
                //protectionPage = new Element(this.name + " Text", By.xpath("/html/body/app-hub-shell/poly-app-app/iron-pages/poly-config/div"), this);
                // impactPage = new Element(this.name + " Text", By.xpath(".//poly-disruptions[@name='disruptions']//div[contains(@class,'poly-disruptions')]"), this);
               // protectionPage = new Element(this.name + " Text", By.xpath(".//poly-config[@name='config']/div"), this);
            }
        }

    }

    public static class TimeUTC extends Element
    {
       public final Element timeUTCText;

       public TimeUTC(){
           super("Time shown in UTC Label",By.xpath("/html/body/app-hub-shell/div/nav-bar/div/div"));
           timeUTCText = new Element("Time shown in UTC Text",By.xpath(".//span[contains(text(),'Time shown in UTC')]"),this);
       }
    }

}
