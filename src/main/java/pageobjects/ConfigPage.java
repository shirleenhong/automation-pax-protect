package pageobjects;

import org.openqa.selenium.By;

import auto.framework.web.Element;
import auto.framework.web.Page;
import common.GlobalPage;

public class ConfigPage extends GlobalPage
{
    public static Page          page          = new Page("Pax Protection - Configuration", "pax-protect/#/config");
    public static ConfigSection configSection = new ConfigSection(null);

    public static class ConfigSection extends Element
    {
        public CurrentProfile currentProfile;
        public PriorityHeader priorityHeader;
        public PriorityBody   priorityBody;
        public AdvanceHeader  advanceHeader;
        public AdvanceBody    advanceBody;
        public AdvanceBodyMessage    advanceBodyMessage;

        public ConfigSection(
            Element parent)
        {
            super("Pax Configuration section", By.xpath(".//div[@id='hierarchyPage']"), parent);

            currentProfile = new CurrentProfile(this);
            priorityHeader = new PriorityHeader(this);
            priorityBody = new PriorityBody(this);
            advanceHeader = new AdvanceHeader(this);
            advanceBody = new AdvanceBody(this);
            advanceBodyMessage = new AdvanceBodyMessage(this);
        }

        public static class CurrentProfile extends Element
        {
            public final Element currentProfileText;
            public final Element applyButton;
            public final Element resetDefaultButton;

            public CurrentProfile(
                Element parent)
            {
                super("Current Profile header", By.xpath(".//div[@id='currentProfile']"), parent);

                currentProfileText = new Element("Current Profile text", By.xpath(".//div[contains(text(), 'Current Profile')]"), this);
                applyButton = new Element("Apply button", By.xpath(".//button[contains(text(), 'Apply')]"), this);
                resetDefaultButton = new Element("Reset to DEfault button", By.xpath(".//button[contains(text(), 'Reset to Default')]"), this);
            }
        }

        public static class PriorityHeader extends Element
        {
            public final Element priorityHeaderText;

            public PriorityHeader(
                Element parent)
            {
                super("Priority Header", By.xpath(".//div[@id='parametersPriorityHeader']"), parent);

                priorityHeaderText = new Element("Priority Header Text", By.xpath(".//div[contains(text(), 'Parameters Priority')]"), this);
            }
        }

        public static class PriorityBody extends Element
        {
            public AvailableParameters availableParameters;
            public PriorityMatrix      priorityMatrix;

            public PriorityBody(
                Element parent)
            {
                super("Parameters Priority Body", By.xpath(".//div[@id='parametersPriorityBody']"), parent);

                availableParameters = new AvailableParameters(this);
                priorityMatrix = new PriorityMatrix(this);
            }

            public static class AvailableParameters extends Element
            {
                public final Element availableParametersText;
                public ParamsBody    paramsBody;

                public AvailableParameters(
                    Element parent)
                {
                    super("Available Parameters section", By.xpath(".//div[@id='available-param']"), parent);

                    availableParametersText = new Element("Available Parameters Text", By.xpath(".//div[contains(text(), 'Available Parameters')]"), this);
                    paramsBody = new ParamsBody(this);
                }

                public static class ParamsBody extends Element
                {
                    public static AvailableParam availableParam;

                    public ParamsBody(
                        Element parent)
                    {
                        super("Params Body section", By.xpath(".//div[@id='sourceParamsBody']"), parent);
                    }

                    public AvailableParam availableParam(String parameter)
                    {
                        return new AvailableParam(this, parameter);
                    }

                    public static class AvailableParam extends Element
                    {
                        public final Element draggableHandle;
                        public final Element parameterText;

                        public AvailableParam(
                            Element parent,
                            String parameter)
                        {
                            super("Available Parameter '" + parameter + "'", By.xpath(".//*[contains(text(),'" + parameter + "')]/../.."), parent);

                            draggableHandle = new Element("Draggable Handle", By.xpath(".//i[@class='hierarchy-handle fa fa-arrows ui-draggable-handle']"), this);
                            parameterText = new Element("Parameter Text", By.xpath(".//span[contains(text(),'" + parameter + "')]"), this);
                        }
                    }
                }
            }

            public static class PriorityMatrix extends Element
            {
                public static HierarchyLevel hierarchyLevel;

                public PriorityMatrix(
                    Element parent)
                {
                    super("Priority Matrix body", By.xpath(".//div[@id='priorityMatrix']"), parent);
                }

                public HierarchyLevel hierarchyLevel(String level)
                {
                    return new HierarchyLevel(this, level);
                }

                public static class HierarchyLevel extends Element
                {
                    public final Element levelHeader;
                    public LevelParam    levelParam;

                    public HierarchyLevel(
                        Element parent,
                        String level)
                    {
                        super("Hierarchy level '" + level + "'", By.xpath(".//*[contains(text(),'" + level + "')]/.."), parent);

                        levelHeader = new Element("'" + level + "' header", By.xpath(".//div[contains(text(),'" + level + "')]"), this);
                        levelParam = new LevelParam(this);
                    }

                    public static class LevelParam extends Element
                    {
                        public static Parameter parameter;
                        public final Element draggableDiv;
                        
                        public LevelParam(
                            Element parent)
                        {
                            super("Level Parameter", By.xpath(".//div[@class='hierarchy-box ui-sortable']"), parent);
                            
                            draggableDiv = new Element("Draggable Div", By.xpath(".//div[@class='param panel panel-default ui-draggable']"), this);
                        }
                        
                        public Parameter parameter(String param)
                        {
                            return new Parameter(this, param);
                        }
                        
                        public static class Parameter extends Element
                        {
                            public final Element draggableIcon;
                            public final Element parameterText;
                            public final Element removeIcon;
                            
                            public Parameter(Element parent, String param)
                            {
                                super("Parameter '"+param+"'", By.xpath(".//*[contains(text(),'" + param + "')]/.."), parent);
                                
                                draggableIcon = new Element("Draggable icon", By.xpath(".//i[@class='hierarchy-handle fa fa-arrows ui-sortable-handle']"), this);
                                parameterText = new Element("Parameter text", By.xpath(".//span[contains(text(),'"+param+"')]"), this);
                                removeIcon = new Element("Remove Icon", By.xpath(".//i[@class='hierarchy-close fa fa-close']"), this);
                            }
                        }
                    }
                }
            }
        }

        public static class AdvanceHeader extends Element
        {
            public final Element advanceHeaderText;
            public final Element downArrow;
            public final Element leftArrow;
            

            public AdvanceHeader(
                Element parent)
            {
                super("Advance Options Header", By.xpath(".//div[@id='advanceOptionsHeader']"), parent);

                advanceHeaderText = new Element("Advance Options Header Text", By.xpath(".//div[contains(text(), 'Advance Options')]"), this);
                downArrow = new Element("Down Arrow", By.xpath(".//i[@class='pull-right config-icon fa fa-chevron-down']"), this);
                leftArrow = new Element("Left Arrow", By.xpath(".//i[@class='pull-right config-icon fa fa-chevron-left']"), this);
                
            }
        }

        public static class AdvanceBody extends Element
        {
            public final Element allowLateness;
            public final Element allowLatenessText;
            public final Element maxLatenessText;
            public final Element maxLatenessBox;
            
            public final Element allowOverbookingText;
            public final Element allowOverbooking;
            public final Element maxOverbookingText;
            public final Element maxOverbookingBox;
            
            public final Element toolTip;
            

            
            public AdvanceBody(
                Element parent)
            {
                super("Advance Options Body", By.xpath(".//div[@id='advanceOptionsBody']"), parent);
                
                allowLatenessText = new Element("Allow Lateness Text", By.xpath(".//label[contains(text(),'Allow Lateness')]"), this);
                allowLateness = new Element("Allow Lateness", By.xpath(".//label[@for='allowLateness']"), this);
                maxLatenessText = new Element("Max Lateness Text", By.xpath(".//label[contains(text(),'Max Lateness')]"), this);
                maxLatenessBox = new Element("Max Lateness Input box", By.xpath(".//input[@id='maxLateness']"), this);
                
                allowOverbookingText = new Element("Allow Overbooking Text", By.xpath(".//label[contains(text(),'Allow Overbooking')]"), this);
                allowOverbooking = new Element("Allow Overbooking", By.xpath(".//label[@for='allowOverbooking']"), this);
                maxOverbookingText = new Element("Allowable Booking Capacity Text", By.xpath(".//label[contains(text(),'Allowable Booking Capacity')]"), this);
                maxOverbookingBox = new Element("Allowable Booking Capacity Input box", By.xpath(".//input[@id='maxOverbooking']"), this);
                
                toolTip = new Element("Tool tip", By.xpath(".//span[@class='tooltiptext']"), this);
                
                
            }
        }
        
        public static class AdvanceBodyMessage extends Element
        {
        	public final Element advancedOptionsHeader;
        	public final Element advancedOptionsOk;
            
            public AdvanceBodyMessage(
                Element parent)
            {
                super("Advance Options Message", By.xpath("//*[@id='myModal']/section"), parent);
                
                advancedOptionsHeader = new Element("Advance Options Header", By.xpath(".//h3[@id='myModal-title']"), this);
                advancedOptionsOk = new Element("Advance Options OK Button", By.xpath(".//*[@id='btnModalPositive']"), this);
                
            }
        }
    }
}
