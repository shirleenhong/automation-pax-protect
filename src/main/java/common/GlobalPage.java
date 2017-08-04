package common;

import org.openqa.selenium.By;

import auto.framework.PageBase;
import auto.framework.web.Element;

public class GlobalPage extends PageBase
{
    public static NavigationSection navigationSection = new NavigationSection();
    public static MainHeader        mainHeader        = new MainHeader();

    public static class NavigationSection extends Element
    {
        public final Element toggleButton;

        public NavigationSection()
        {
            super("Left Side Navigation Section", By.xpath("//body"));
            toggleButton = new Element("Toggle icon button", By.xpath(".//*[@class='pxh-drawer-toggle']/a"), this);
        }

        public MainNavigationOptions mainNavigationOptions()
        {
            return new MainNavigationOptions();
        }

        public static class MainNavigationOptions extends Element
        {
            public MainNavigationOptions()
            {
                super("Main Navigation Options", By.xpath("//*[contains(@class,'pxh-navigation')]"));
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
