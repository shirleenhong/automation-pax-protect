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
		public final Element		clearButton;
		public final Element		cancelButton;
		public final Element		applyButton;
		public final Element		delayedOption;
		public final Element		cancelledOption;
		public final Element		deselectCancelledOption;
		public final Element		selectOrigin;
		public final Element		resultOrigin;
		
		
		
		public FilteringWindow()
		{
			super("Filtering Window", By.xpath("//div[@class='modal flex flex--top flex--center full-height style-scope px-modal']//section[@class='modal__content style-scope px-modal']"));
			
			title					= new Element("Filtering Window Title", By.xpath(".//div[@class='header style-scope ppro-modal-filter-view']"));
			destination				= new Element("'Destination' Dropdown", By.xpath(".//div[contains(text(), 'Destination')]"));
			origin					= new Element("'Origin' Dropdown", By.xpath(".//div[contains(text(), 'Origin')]"));
			flightNum				= new Element("'Flight #' Dropdown", By.xpath(".//div[contains(text(), 'Flight #')]"));
			impactType				= new Element("'Impact Type' Dropdown", By.xpath(".//div[contains(text(), 'Impact Type')]"));
			clearButton			    = new Element("'Clear' Button", By.xpath(".//button[contains(text(), 'Clear')]"));
			cancelButton			= new Element("'Cancel' Button", By.xpath(".//button[contains(text(), 'Cancel')]"));
			applyButton			    = new Element("'Apply' Button", By.xpath(".//button[contains(text(), 'Apply')]"));
			delayedOption			= new Element("'delayed' Option", By.xpath(".//div[@title='delayed']"));
			cancelledOption			= new Element("'cancelled' Option", By.xpath(".//div[@title='cancelled']"));
			deselectCancelledOption	= new Element("Clear 'Impact Type'", By.xpath(".//ppro-dropdown[@display-value='Impact Type']//div[@class='dropdown-option style-scope px-dropdown iron-selected']"));
			selectOrigin			= new Element("Selected Origin", By.xpath(".//*[@id='selector']/div[1]"));
			resultOrigin			= new Element("Origin Result", By.xpath(".//div[@class='container style-scope ppro-flight-line']//div[5]"));

		}
		
		//element for Origin values
		public SelectedOrigin selectedOrigin(String originPort)
		{
			return new SelectedOrigin(originPort);
		}
		
		public static class SelectedOrigin extends Element
		{
			
			public SelectedOrigin (String originPort)
			{
				super("Selected Origin: " + originPort, By.xpath("//px-dropdown[@id='origin']//iron-selector[@id='selector']//*[contains(@title,substring-after('"+originPort+"','-'))]"));

			}
		}
		
		//element for Destination values
		public SelectedDestination selectedDestination(String destinationPort)
		{
			return new SelectedDestination(destinationPort);
		}
		
		public static class SelectedDestination extends Element
		{
			
			public SelectedDestination (String destinationPort)
			{
				super("Selected Destination: " + destinationPort, By.xpath("//px-dropdown[@id='destination']//iron-selector[@id='selector']//*[contains(@title,substring-after('"+destinationPort+"','-'))]"));

			}
		}
		
		//element for flight number values
		public SelectedFlightNumber selectedFlightNumber(String flightNumber)
		{
			return new SelectedFlightNumber(flightNumber);
		}
		
		public static class SelectedFlightNumber extends Element
		{
			
			public SelectedFlightNumber (String flightNumber)
			{
				super("Selected Flight Number: " + flightNumber, By.xpath("//iron-selector[@id='selector']//*[contains(@title,'"+flightNumber+"')]"));

			}
		}
				
	}
}
