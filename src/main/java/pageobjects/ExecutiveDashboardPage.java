package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import pageobjects.PaxImpactPage.SummaryDrawer.SummaryDrawerItem;

import org.openqa.selenium.By;


public class ExecutiveDashboardPage extends GlobalPage {

    public static StatisticFrame statisticFrame = new StatisticFrame();
    public static SummaryDrawer summaryDrawer = new SummaryDrawer();

    public static class StatisticFrame extends Element {

        public final Element flightListIcon;

        public StatisticFrame() {
            super("Executive Dashboard Statistic Frame", By.xpath("//div[@class='statistics style-scope ppro-x-summary']"));
            flightListIcon = new Element(By.xpath("//px-icon[contains(@class,'style-scope ppro-summary-drawer-view x-scope px-icon')]"));
        }

        public StatisticItem statisticItem(String title){return new StatisticItem(title);}

        public static class StatisticItem extends Element{

            public final Element statisticItemHeader;
            public final Element circleItem;
            //public final Element circleProgress;
            public final Element circleInMiddleText;
            public final Element rectItem;
            public final Element rectPathItem;
            public final Element kpiChartCount;
            public final Element kpiChartCountText;

            StatisticItem(String title){
                super("Statistic Frame Item", By.xpath("//ppro-stats-card[@title='"+title+"']") );
                statisticItemHeader = new Element("Statistic Frame Headers Text", By.xpath("//ppro-stats-card[@title='"+title+"']//div[text()='"+title+"']"));
                circleItem = new Element("px circle item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='circle' and @transform='rotate(-90, 62.5, 69.5)']"));
                //circleProgress = new Element("px circle progress", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-kpi-progress-circle[contains(@class,'style-scope ppro-x-summary')]"));
                circleInMiddleText = new Element("px circle middle text", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-kpi-progress-circle[contains(@class,'style-scope')]//b[contains(@class,'ppro-kpi-progress-circle')]"));

                rectItem = new Element("px kpi rect item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='rect' and @fill='#14242e']"));
                rectPathItem = new Element("px kpi rect path item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='path' and @stroke='#1ac6ff']"));
                kpiChartCount = new Element("ppro kpi chart count", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-chart-kpi[contains(@class,'style-scope')]"));
                kpiChartCountText = new Element("ppro kpi chart count text", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-chart-kpi[contains(@class,'style-scope')]//span[contains(@class,'ppro-chart-kpi')]"));
            }

        }
    }

    public static class SummaryDrawer extends Element
    {
    	public final Element greaterThan15Min;
    	public final Element greaterThan60Min;
    	public final Element greaterThan180Min;
    	public final Element cancelled;
    	public final Element diverted;
    	public final Element misconx;
    	
    	public SummaryDrawer()
    	{
    		super("Summary Drawer", By.xpath("//div[@class='content style-scope ppro-summary-drawer-view']"));
    		
    		greaterThan15Min = new Element("Greater than 15 min delayed pax", By.xpath("//stat-val[@id='gt15Min']"));
    		greaterThan60Min = new Element("Greater than 60 min delayed pax", By.xpath("//stat-val[@id='gt60Min']"));
    		greaterThan180Min = new Element("Greater than 180 min delayed pax", By.xpath("//stat-val[@id='gt180Min']"));
    		cancelled = new Element("Cancelled Pax", By.xpath("//stat-val[@id='cancelled']"));
    		diverted = new Element("Diverted Pax", By.xpath("//stat-val[@id='diverted']"));
    		misconx = new Element("Misconnected Pax", By.xpath("//stat-val[@id='misconnected']"));
    	}
    	
        public SummaryDrawerItem summaryDrawerItem(String id)
        {
            return new SummaryDrawerItem(id);
        }
        
        public class SummaryDrawerItem extends Element
        {
            public  Element itemValue ;

            public SummaryDrawerItem( String id)
            {
                super("Summary Drawer Item: " + id, By.xpath(".//stat-val[@id='"+id+"']"));
                itemValue = new Element("Summary Drawer Item Value ", By.xpath(".//stat-val[@id='"+id+"']//div[@class='value value-none-importance  style-scope stat-val']"));
            }

        }
    }

}
