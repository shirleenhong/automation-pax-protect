package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;


import org.openqa.selenium.By;

public class PDSListViewPage extends GlobalPage
{
	public static ListView listView = new ListView(null);
	
	public static class ListView extends Element
	{		
		public ListView(Element parent)
		{
			super("List View", By.xpath("//*[contains(@class,'list-content style-scope impacted-flight-list')]"), parent);
		}
		
		public DisruptedItem disruptedItem(String id)
		{
			return new DisruptedItem(id);
		}
		
		public static class DisruptedItem extends Element
		{
			public final Element listViewCheckBox;
			public final Element listViewFlightNumber;
			
			public DisruptedItem (String id)
			{
				super("List View - Flight ID: " + id, By.xpath("//*[contains(@class,'list style-scope ppro-list')]//*[contains(@id,'"+id+"')]"));
				listViewCheckBox = new Element("List View Check Box " + id, By.xpath(".//div[@class='squarebox style-scope ppro-checkbox']"), this);
				listViewFlightNumber = new Element("Impacted Flights Text", By.xpath(".//div[@class='ppro-table-cell style-scope ppro-flight-line']"));

			}
		}
	}
}
