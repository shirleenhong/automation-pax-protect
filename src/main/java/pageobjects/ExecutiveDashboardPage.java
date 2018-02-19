package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import org.openqa.selenium.By;


public class ExecutiveDashboardPage extends GlobalPage {

    public static StatisticFrame statisticFrame = new StatisticFrame();

    public static class StatisticFrame extends Element {

        public StatisticFrame() {
            super("Predix Percent Circle Frame", By.xpath("//div[@class='statistics style-scope ppro-x-summary']"));
        }

        public StatisticItem statisticItem(String title){return new StatisticItem(title);}

        public static class StatisticItem extends Element{

            public final Element statisticItemHeader;
            public final Element circleItem;
            public final Element rectItem;
            public final Element rectPathItem;

            StatisticItem(String title){
                super("Percent Circle Item", By.xpath("//ppro-stats-card[@title='"+title+"']") );
                statisticItemHeader = new Element("Flight Arrival Text", By.xpath("//ppro-stats-card[@title='"+title+"']//div[text()='"+title+"']"));
                circleItem = new Element("px circle item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='circle' and @transform='rotate(-90, 62.5, 69.5)']"));
                //rect item
                rectItem = new Element("px kpi rect item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='rect' and @fill='#14242e']"));
                //rectpath item
                rectPathItem = new Element("px kpi rect path item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='path' and @stroke='#1ac6ff']"));
            }

        }
    }
}
