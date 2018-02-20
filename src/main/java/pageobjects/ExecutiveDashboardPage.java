package pageobjects;

import auto.framework.web.Element;
import common.GlobalPage;
import org.openqa.selenium.By;


public class ExecutiveDashboardPage extends GlobalPage {

    public static StatisticFrame statisticFrame = new StatisticFrame();

    public static class StatisticFrame extends Element {

        public StatisticFrame() {
            super("Executive Dashboard Statistic Frame", By.xpath("//div[@class='statistics style-scope ppro-x-summary']"));
        }

        public StatisticItem statisticItem(String title){return new StatisticItem(title);}

        public static class StatisticItem extends Element{

            public final Element statisticItemHeader;
            public final Element circleItem;
            public final Element circleProgress;
            public final Element circleInMiddleText;
            public final Element rectItem;
            public final Element rectPathItem;
            public final Element kpiChartCount;
            public final Element kpiChartCountText;

            StatisticItem(String title){
                super("Statistic Frame Item", By.xpath("//ppro-stats-card[@title='"+title+"']") );
                statisticItemHeader = new Element("Statistic Frame Headers Text", By.xpath("//ppro-stats-card[@title='"+title+"']//div[text()='"+title+"']"));
                circleItem = new Element("px circle item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='circle' and @transform='rotate(-90, 62.5, 69.5)']"));
                circleProgress = new Element("px circle progress", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-kpi-progress-circle[contains(@class,'style-scope ppro-x-summary')]"));
                circleInMiddleText = new Element("px circle middle text", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-kpi-progress-circle[contains(@class,'style-scope ppro-x-summary')]//b[contains(@class,'ppro-kpi-progress-circle')]"));

                rectItem = new Element("px kpi rect item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='rect' and @fill='#14242e']"));
                rectPathItem = new Element("px kpi rect path item", By.xpath("//ppro-stats-card[@title='"+title+"']//*[name()='svg']//*[name()='path' and @stroke='#1ac6ff']"));
                kpiChartCount = new Element("ppro kpi chart count", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-chart-kpi[@class='style-scope ppro-x-summary']"));
                kpiChartCountText = new Element("ppro kpi chart count text", By.xpath("//ppro-stats-card[@title='"+title+"']//ppro-chart-kpi[@class='style-scope ppro-x-summary']//span[contains(@class,'ppro-chart-kpi')]"));
            }

        }
    }
}
