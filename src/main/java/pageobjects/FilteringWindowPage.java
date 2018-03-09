package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import pageobjects.PDSListViewPage.ListView.DisruptedItem;

import org.openqa.selenium.By;

public class FilteringWindowPage extends GlobalPage
{
	public static FilteringWindow filteringWindow = new FilteringWindow();
	
	public static class FilteringWindow extends Element
	{
		public final Element		title;
		public final Element		destination;
		public final Element		origin;
		public final Element		flightNum;
		public final Element		impactType;
		public final Element		cancelButton;
		public final Element		appplyButton;
		public final Element		delayedOption;
		public final Element		cancelledOption;
		public final Element		deselectCancelledOption;
		public final Element		selectOrigin;
		public final Element		resultOrigin;
		
		public FilteringWindow()
		{
			super("Filtering Window", By.xpath("//div[@class='modal flex flex--top flex--center full-height style-scope px-modal']//section[@class='modal__content style-scope px-modal']"));
			
			title					= new Element("Filtering Window Title", By.xpath(".//*[@id='myModal-title']"));
			destination				= new Element("'Destination' Dropdown", By.xpath(".//div[contains(text(), 'Destination')]"));
			origin					= new Element("'Origin' Dropdown", By.xpath(".//div[contains(text(), 'Origin')]"));
			flightNum				= new Element("'Flight #' Dropdown", By.xpath(".//div[contains(text(), 'Flight #')]"));
			impactType				= new Element("'Impact Type' Dropdown", By.xpath(".//div[contains(text(), 'Impact Type')]"));
			cancelButton			= new Element("'Cancel' Button", By.xpath(".//button[contains(text(), 'Cancel')]"));
			appplyButton			= new Element("'Apply' Button", By.xpath(".//button[contains(text(), 'Apply')]"));
			delayedOption			= new Element("'delayed' Option", By.xpath(".//div[@title='delayed']"));
			cancelledOption			= new Element("'cancelled' Option", By.xpath(".//div[@title='cancelled']"));
			deselectCancelledOption	= new Element("Clear 'Impact Type'", By.xpath(".//ppro-dropdown[@display-value='Impact Type']//div[@class='dropdown-option style-scope px-dropdown iron-selected']"));
			selectOrigin			= new Element("Selected Origin", By.xpath(".//*[@id='selector']/div[1]"));
			resultOrigin			= new Element("Origin Result", By.xpath(".//div[@class='container style-scope ppro-flight-line']//div[5]"));

		}
		
		public SelectedOrigin selectedOrigin(String title)
		{
			return new SelectedOrigin(title);
		}
		
		public static class SelectedOrigin extends Element
		{
			
			public SelectedOrigin (String title)
			{
				super("Selected Origin: " + title, By.xpath("//ppro-dropdown[@display-value='Origin']//div[@title='"+title+"']"));

			}
		}
				
	}
}
