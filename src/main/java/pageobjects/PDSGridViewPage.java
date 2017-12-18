package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import pageobjects.PDSListViewPage.ListView;
import pageobjects.PDSListViewPage.ListView.DisruptedItem;

import org.openqa.selenium.By;

public class PDSGridViewPage extends GlobalPage
{
	public static GridView gridView = new GridView(null);
	
	public static class GridView extends Element
	{		
		public GridView(Element parent)
		{
			super("Grid View", By.xpath("//*[contains(@class,'list-content style-scope impacted-flight-list')]"), parent);
		}
		
		public DisruptedItemGridView disruptedItemGridView(String id)
		{
			return new DisruptedItemGridView(id);
		}
		
		public static class DisruptedItemGridView extends Element
		{
			public final Element gridViewCheckBox;
			
			public DisruptedItemGridView (String id)
			{
				super("Grid View - Flight ID: " + id, By.xpath("//*[contains(@class,'card style-scope ppro-list')]//*[contains(@id,'"+id+"')]"));
				gridViewCheckBox = new Element("Grid View Check Box " + id, By.xpath(".//div[@class='squarebox style-scope ppro-checkbox']"), this);

			}
		}
	}
}
