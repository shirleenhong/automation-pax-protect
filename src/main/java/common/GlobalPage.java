package common;

import org.openqa.selenium.By;

import auto.framework.PageBase;
import auto.framework.web.Element;
import pageobjects.ConfigPage;

public class GlobalPage extends PageBase
{
    public static NavigationSection navigationSection = new NavigationSection();
    public static MainNavigationOptions mainNavigationOptions = new MainNavigationOptions();
    public static MainPXNavigationOptions mainPXNavigationOptions = new MainPXNavigationOptions();
    public static TimeUTC timeUTC = new TimeUTC();

    public static MainHeader        mainHeader        = new MainHeader();

    public static class NavigationSection extends Element
    {
        public final Element toggleButton;


        public NavigationSection()
        {
            super("Left Side Navigation Section", By.xpath("//body"));
            toggleButton = new Element("Toggle icon button", By.xpath(".//*[@class='pxh-drawer-toggle']/a"), this);
        }

    }
    
    public static class MainNavigationOptions extends Element
    {
		public final Element configPageButton;
		
        public MainNavigationOptions()
        {
            super("Main Navigation Options", By.xpath("//*[contains(@class,'pxh-navigation')]"));
            configPageButton = new Element("Pax Config Button", By.xpath(".//a[contains(@title,'Pax Config')]"), this);
        }

        public NavigateTo navigateToLink(String name)
        {
            return new NavigateTo("Navigate To: " + name, By.xpath(".//a[contains(@title,'" + name + "')]"), this);
        }

        public static class NavigateTo extends Element
        {
            public final Element icon;
            public final Element navText;
            public final Element tooltipMessage;

            public NavigateTo(
                String name,
                By by,
                Element parent)
            {
                super(name, by, parent);
                tooltipMessage = new Element(this.name + " / Tooltip Message", By.xpath(".//a[contains(@title,'" + name + "')]"), this);
                icon = new Element(this.name + " / Icon", By.xpath(".//i[contains(@class,'item-icon')]"), this);
                navText = new Element(this.name + " Text", By.xpath(".//div[contains(@class, 'item-text')]"), this);
            }

        }
    }

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
            public final Element impactPage;
            public final Element protectionPage;


            public NavigateTo(
                    String name,
                    By by,
                    Element parent)
            {
                super(name, by, parent);
                icon = new Element(this.name + " / Icon", By.xpath("//*[@id='items']//px-icon[contains(@class,'px-app-nav-item') and @icon='fa:cog']"), this);
                navText = new Element(this.name + " Text", By.xpath(".//p[contains(text(),'" + name + "']"), this);
                impactPage = new Element(this.name + " Text", By.xpath("/html/body/app-hub-shell/poly-app-app/iron-pages/poly-disruptions/div"), this);
                protectionPage = new Element(this.name + " Text", By.xpath("/html/body/app-hub-shell/poly-app-app/iron-pages/poly-config/div"), this);
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

    public static class MainHeader extends Element
    {
        public final Element   headerTitle;
        public ShowHideFlights showHideFlights;

        public MainHeader()
        {
            super("PAX Protect -- Main Header", By.xpath("//div[@id='microapp-header']"));
            headerTitle = new Element("PAX Protect -- Main Header", By.xpath("//div[@id='microapp-header']"));
            showHideFlights = new ShowHideFlights(this);
        }

        public static class ShowHideFlights extends Element
        {
            public final Element greaterThan;
            public final Element lessThan;
            public final Element showFlightsText;
            public final Element hideFlightsText;

            public ShowHideFlights(
                Element parent)
            {
                super("Show Hide Flights", By.xpath("//div[@class='div-showHidePds style-scope px-inbox']"), parent);

                greaterThan = new Element("'>'", By.xpath(".//i[@class='fa fa-chevron-right style-scope px-inbox']"), this);
                showFlightsText = new Element("Show Flights", By.xpath(".//span[contains(text(),'Show Flights')]"), this);
                lessThan = new Element("'<'", By.xpath(".//i[@class='fa fa-chevron-left style-scope px-inbox']"), this);
                hideFlightsText = new Element("Hide Flights", By.xpath(".//span[contains(text(),'Hide Flights')]"), this);
            }
        }
    }

    public static class UserMenu extends Element
    {
        public final Element avatarIcon;
        public final Element caretIcon;
        public final Element loginName;
        public final Element loginMenuLink;
        public final Element loginAgainLink;

        public UserMenu()
        {
            super("User Menu", By.xpath("//div[@id='pxh-user-menu']"));
            avatarIcon = new Element("", By.xpath(".//i[contains(@class,'avatar-icon')]"), this);
            caretIcon = new Element("", By.xpath(".//i[contains(@class,'caret-icon')]"), this);
            loginName = new Element("", By.xpath(".//div[contains(@class,'login__name')]"), this);
            loginMenuLink = new Element("", By.xpath(".//li[contains(@class,'menu__item')]//a[@title='Sign Out']"), this);
            loginAgainLink = new Element("", By.xpath("//*[text()='Click here to login again']"));
        }
    }
}
