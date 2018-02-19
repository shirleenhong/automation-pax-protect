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

            StatisticItem(String title){
                super("Percent Circle Item", By.xpath("//ppro-stats-card[@title='"+title+"']") );
                statisticItemHeader = new Element("Flight Arrival Text", By.xpath("//ppro-stats-card[@title='"+title+"']//div[text()='"+title+"']"));
            }

        }
    }
}
