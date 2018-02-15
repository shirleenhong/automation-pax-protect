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

        public PercentCircleItem percentCircleItem(String title){return new PercentCircleItem(title);}

        public static class PercentCircleItem extends Element{

            public final Element percentCircleItemHeader;

            PercentCircleItem(String title){
                super("Percent Circle Item", By.xpath("//ppro-stats-card[@title='"+title+"']") );
                percentCircleItemHeader = new Element("Flight Arrival Text", By.xpath("//div[text()='"+title+"']"));
            }

        }
    }
}
