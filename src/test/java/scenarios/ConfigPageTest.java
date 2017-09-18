package scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.WebManager;
import auto.framework.web.Element;
import common.TestDataHandler;
import pageobjects.HomePage;
import pageobjects.ConfigPage;

public class ConfigPageTest extends TestBase
{
    public static TestDataHandler testParamDataHandler;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("PAX Configuration - Home Page");
        testParamDataHandler = TestDataHandler.setParametersDataSet("Parameters", "RowSelection='PaxProtect'");

        TestCases.TestPaxConfigPage();
    }

    public static class TestCases
    {
        public static void TestPaxConfigPage() throws Exception
        {
            ReportLog.setTestStep("Verify pax configuration section");
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Config").click();
            // ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel("Level
            // 1").levelParam.parameter("Baggage").removeIcon.click();
            // dragDrop(
            // ConfigPage.configSection.priorityBody.availableParameters.paramsBody.availableParam("Ancillary
            // Revenue"),
            // ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel("Level 1"));
            // Thread.sleep(5000);

            // verifyPaxConfigPage(); //FR151.0
            verifyAdvancedOptions(); // FR30.0 & FR31.0

            // ReportLog.setTestStep("Verify pax configuration defaults after navigating to pax protection and
            // back to pax config.");
            // HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Protection").click();
            // HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Config").click();
            // VerifyPaxConfigPage();
        }
    }

    public static void verifyPaxConfigPage() throws Exception
    {
        ReportLog.setTestStep("Verify current profile & parameters priority header and elements");
        ConfigPage.configSection.verifyDisplayed(true, 5);
        ConfigPage.configSection.currentProfile.verifyDisplayed(true, 5);
        ConfigPage.configSection.currentProfile.currentProfileText.verifyText("Current Profile");
        ConfigPage.configSection.currentProfile.applyButton.verifyDisplayed(true, 5);
        ConfigPage.configSection.currentProfile.resetDefaultButton.verifyDisplayed(true, 5);
        ConfigPage.configSection.priorityHeader.verifyDisplayed(true, 5);
        ConfigPage.configSection.priorityHeader.priorityHeaderText.verifyText("Parameters Priority");

        ReportLog.setTestStep("Verify available paremeters and hierarchy levels");
        ConfigPage.configSection.priorityBody.verifyDisplayed(true, 5);
        ConfigPage.configSection.priorityBody.availableParameters.verifyDisplayed(true, 5);
        ConfigPage.configSection.priorityBody.availableParameters.availableParametersText.verifyText("Available Parameters");
        ConfigPage.configSection.priorityBody.availableParameters.paramsBody.verifyDisplayed(true, 5);

        String[] parametersList = HomePageTest.extractByComma(testParamDataHandler.availableParameters);
        for (int i = 0; i < parametersList.length; i++)
        {
            ConfigPage.configSection.priorityBody.availableParameters.paramsBody.availableParam(parametersList[i]).verifyDisplayed(true, 5);
            ConfigPage.configSection.priorityBody.availableParameters.paramsBody.availableParam(parametersList[i]).parameterText.verifyText(parametersList[i]);
            ConfigPage.configSection.priorityBody.availableParameters.paramsBody.availableParam(parametersList[i]).draggableHandle.verifyDisplayed(true, 5);
            if (parametersList[i].equalsIgnoreCase("Baggage")
                    || parametersList[i].equalsIgnoreCase("Booking Class")
                    || parametersList[i].equalsIgnoreCase("VIP Status")
                    || parametersList[i].equalsIgnoreCase("Booking Date"))
            {
                HomePageTest.isElementDisabled(ConfigPage.configSection.priorityBody.availableParameters.paramsBody.availableParam(parametersList[i]), true);
            }
        }

        ConfigPage.configSection.priorityBody.priorityMatrix.verifyDisplayed(true, 5);
        if (HomePageTest.getListCount(ConfigPage.configSection.priorityBody.priorityMatrix, By.xpath(".//div[@class='panel panel-info']")) >= 0
                && HomePageTest.getListCount(ConfigPage.configSection.priorityBody.priorityMatrix, By.xpath(".//div[@class='panel panel-info']")) <= 4)
        {
            ReportLog.logEvent("PASSED", "There are 4 hierarchy levels."); // FR23.0
            if (checkPrioritySort())
            {
                ReportLog.logEvent("PASSED", "Hierarchy levels are sorted by priority (ascending).");
                String[] levelList = HomePageTest.extractByComma(testParamDataHandler.hierarchyLevels);
                for (int j = 0; j < levelList.length; j++)
                {
                    String[] subLevelList = HomePageTest.extractByColon(levelList[j]);
                    ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).verifyDisplayed(true, 5);
                    ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelHeader.verifyDisplayed(true, 5);
                    ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelHeader.verifyText(subLevelList[0]);

                    if (HomePageTest.getListCount(ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelParam, By.xpath(".//div[@class='panel-body']")) >= 0
                            && HomePageTest.getListCount(ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelParam, By.xpath(".//div[@class='panel-body']")) <= 1)
                    {
                        ReportLog.logEvent("PASSED", "Parameter inside this hierarchy level is not greater than 1."); // FR24.0
                        ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelParam.parameter(subLevelList[1]).verifyDisplayed(true, 5);
                        ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelParam.parameter(subLevelList[1]).draggableIcon.verifyDisplayed(true, 5);
                        ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelParam.parameter(subLevelList[1]).parameterText.verifyText(subLevelList[1]);
                        ConfigPage.configSection.priorityBody.priorityMatrix.hierarchyLevel(subLevelList[0]).levelParam.parameter(subLevelList[1]).removeIcon.verifyDisplayed(true, 5);
                    }
                    else
                    {
                        ReportLog.logEvent("FAILED", "Parameter inside this hierarchy level is greater than 1.");
                    }
                }
            }
            else
            {
                ReportLog.logEvent("FAILED", "Hierarchy levels are not sorted by priority.");
            }
        }
        else
        {
            ReportLog.logEvent("FAILED", "There are greater than 4 hierarchy levels.");
        }

        ReportLog.setTestStep("Verify advanced options and elements");
        ConfigPage.configSection.advanceHeader.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceHeader.advanceHeaderText.verifyText("Advance Options");
        ConfigPage.configSection.advanceBody.verifyDisplayed(true, 5);
        Thread.sleep(5000);
    }

    public static void verifyAdvancedOptions() throws Exception
    {
        ReportLog.setTestStep("Verify Advanced Options - Allow & Max Lateness");
        ConfigPage.configSection.advanceHeader.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceHeader.advanceHeaderText.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceHeader.advanceHeaderText.verifyText("Advance Options");
        ConfigPage.configSection.advanceHeader.downArrow.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceBody.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceBody.allowLatenessText.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceBody.allowLatenessText.verifyText("Allow Lateness");
        ConfigPage.configSection.advanceBody.allowLateness.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceBody.maxLatenessText.verifyDisplayed(true, 5);
        ConfigPage.configSection.advanceBody.maxLatenessText.verifyText("Max Lateness");
        ConfigPage.configSection.advanceBody.maxLatenessBox.verifyDisplayed(true, 5);
        if (ConfigPage.configSection.advanceBody.maxLatenessBox.getAttribute("ng-reflect-model").equalsIgnoreCase("24"))
        {
            ReportLog.logEvent(true, "Max Lateness is set to '24'");
        }
        else
        {
            ReportLog.logEvent(false, "Max Lateness is not set to '24'");
        }

        String selectAll = Keys.chord(Keys.CONTROL, "a");
        String backspace = "\u0008";
        ConfigPage.configSection.advanceBody.allowLateness.click();
        Thread.sleep(2000);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(selectAll);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(backspace);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys("1");
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(selectAll);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(backspace);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys("99999");
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(selectAll);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(backspace);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys("50000");
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(selectAll);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(backspace);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys("0");
        hoverElement(ConfigPage.configSection.advanceBody.maxLatenessBox);
        Thread.sleep(2000);
        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().equalsIgnoreCase("Minimum value should be 1 hour"))
        {
            ReportLog.logEvent(true, "\"Minimum value should be 1 hour\" error message is correct");
        }
        else
        {
            ReportLog.logEvent(false, "\"Minimum value should be 1 hour\" error message is incorrect");
        }
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(selectAll);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(backspace);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys("999999");
        hoverElement(ConfigPage.configSection.advanceBody.maxLatenessBox);
        Thread.sleep(2000);
        if (ConfigPage.configSection.advanceBody.toolTip.getText().trim().equalsIgnoreCase("Accepts whole numbers only from 1 to 99999"))
        {
            ReportLog.logEvent(true, "\"Accepts whole numbers only from 1 to 99999\" error message is correct");
        }
        else
        {
            ReportLog.logEvent(false, "\"Accepts whole numbers only from 1 to 99999\" error message is incorrect");
        }
        Thread.sleep(2000);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(selectAll);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys(backspace);
        ConfigPage.configSection.advanceBody.maxLatenessBox.typeKeys("24");
        ConfigPage.configSection.advanceBody.allowLateness.click();
        Thread.sleep(5000);
    }

    public static boolean checkPrioritySort()
    {
        ReportLog.setTestStep("Verify hierarchy level priority sort.");
        WebElement rootElement = ConfigPage.configSection.priorityBody.priorityMatrix.toWebElement();
        List<WebElement> hierarchyLevels = rootElement.findElements(By.xpath(".//div[@class='panel panel-info']"));
        int counter = 1;
        boolean isSorted = true;

        if (hierarchyLevels.size() > 1)
        {
            for (WebElement level : hierarchyLevels)
            {
                if (level.findElement(By.xpath(".//div[@class='panel-heading']")).getText().equalsIgnoreCase("Level " + counter))
                {
                    System.out.println("\t\t\t\t" + level.findElement(By.xpath(".//div[@class='panel-heading']")).getText().trim() + " is equal to Level " + counter);
                    counter++;
                }
                else
                {
                    isSorted = false;
                    break;
                }
            }
        }

        return isSorted;
    }

    public static void hoverElement(Element element)
    {
        WebDriver driver = WebManager.getDriver();
        WebElement webEl = element.toWebElement();

        Actions action = new Actions(driver);
        action.moveToElement(webEl).build().perform();
        ReportLog.logEvent(true, "Hover over element");
    }

    public static void dragAndDrop(WebElement sourceElement, WebElement destinationElement)
    {
        WebDriver driver = WebManager.getDriver();
        try
        {
            if (sourceElement.isDisplayed() && destinationElement.isDisplayed())
            {
                Actions action = new Actions(driver);
                action.dragAndDrop(sourceElement, destinationElement).build().perform();
            }
            else
            {
                System.out.println("Element was not displayed to drag");
            }
        }
        catch (StaleElementReferenceException e)
        {
            System.out.println("Element with " + sourceElement + "or" + destinationElement + "is not attached to the page document " + e.getStackTrace());
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Element " + sourceElement + "or" + destinationElement + " was not found in DOM " + e.getStackTrace());
        }
        catch (Exception e)
        {
            System.out.println("Error occurred while performing drag and drop operation " + e.getStackTrace());
        }
    }

    public static void dragDrop(Element fromElement, Element toElement) throws InterruptedException
    {
        // WebDriver driver = WebManager.getDriver();
        // driver.navigate().to("http://jqueryui.com/droppable/");
        // //Wait for the frame to be available and switch to it
        // WebDriverWait wait = new WebDriverWait(driver, 5);
        // wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".demo-frame")));
        // WebElement Sourcelocator = driver.findElement(By.cssSelector(".ui-draggable"));
        // WebElement Destinationlocator = driver.findElement(By.cssSelector(".ui-droppable"));
        // dragAndDrop(Sourcelocator,Destinationlocator);
        // String actualText=driver.findElement(By.cssSelector("#droppable>p")).getText();
        // Assert.assertEquals(actualText, "Dropped!");

        fromElement.highlight();
        toElement.highlight();

        List<WebElement> fElement = fromElement.toWebElement().findElements(By.xpath(".//i[@class='hierarchy-handle fa fa-arrows ui-draggable-handle']"));
        List<WebElement> tElement = toElement.toWebElement().findElements(By.xpath(".//div[@class='hierarchy-box ui-sortable']"));

        System.out.println(fElement.size() + " " + tElement.size());

        if (fElement.size() == 1 && tElement.size() == 1)
        {
            System.out.println("Okay!");
            dragAndDrop(fElement.get(0), tElement.get(0));
        }
        //
        // WebDriver driver = WebManager.getDriver();
        //
        // JavascriptExecutor js = (JavascriptExecutor) driver;
        //
        // String xto=Integer.toString(tElement.getLocation().x);
        // String yto=Integer.toString(tElement.getLocation().y);
        //
        // Actions builder = new Actions(driver);
        // builder = builder.clickAndHold(fElement);
        // builder = builder.moveByOffset(1, 1);
        // builder.moveToElement(tElement, tElement.getLocation().x,
        // tElement.getLocation().y).release().build().perform();

        // js.executeScript("function simulate(f,c,d,e){var b,a=null;for(b in
        // eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0}
        // var
        // eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/};
        // " +
        // "simulate(arguments[0],\"mousedown\",0,0);
        // simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]);
        // simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
        // fElement,xto,yto);

        String loadJQuery =
                "(function(jqueryUrl, callback) {"
                        + "if (typeof jqueryUrl != 'string') {"
                        + " jqueryUrl = 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js';"
                        + "}"
                        + "if(typeof jQuery=='undefined')"
                        + "{"
                        + " var script = document.createElement('script');"
                        + " var head = document.getElementsByTagName('head')[0];"
                        + " var done = false;"
                        + " script.onload = script.onreadystatechange = (function() {"
                        + "     if (!done && (!this.readyState || this.readyState == 'loaded'"
                        + "             || this.readyState == 'complete')) {"
                        + "         done = true;"
                        + "         script.onload = script.onreadystatechange = null;"
                        + "         head.removeChild(script);"
                        + "         callback();"
                        + "     }"
                        + " });"
                        + " script.src=jqueryUrl;"
                        + " head.appendChild(script);"
                        + "}"
                        + "else{"
                        + " callback();}"
                        + "})(arguments[0],arguments[arguments.length-1]);";

        // js.executeAsyncScript(loadJQuery, "http://code.jquery.com/jquery-1.11.2.min.js");

        String dragAndDropScript =
                "(function( $ ) {"
                        + "$.fn.simulateDragDrop = function(options) {"
                        + " return this.each(function() {"
                        + "     new $.simulateDragDrop(this, options);"
                        + " });"
                        + "};"
                        + "$.simulateDragDrop = function(elem, options) {"
                        + " this.options = options;"
                        + " this.simulateEvent(elem, options);"
                        + "};"
                        + "$.extend($.simulateDragDrop.prototype, {"
                        + " simulateEvent: function(elem, options) {"
                        + "     var type = 'dragstart';"
                        + "     var event = this.createEvent(type);"
                        + "     this.dispatchEvent(elem, type, event);"
                        + "     type = 'drop';"
                        + "     var dropEvent = this.createEvent(type, {});"
                        + "     dropEvent.dataTransfer = event.dataTransfer;"
                        + "     this.dispatchEvent($(options.dropTarget)[0], type, dropEvent);"
                        + "     type = 'dragend';"
                        + "     var dragEndEvent = this.createEvent(type, {});"
                        + "     dragEndEvent.dataTransfer = event.dataTransfer;"
                        + "     this.dispatchEvent(elem, type, dragEndEvent);"
                        + " },"
                        + " createEvent: function(type) {"
                        + "     var event = document.createEvent(\"CustomEvent\");"
                        + "     event.initCustomEvent(type, true, true, null);"
                        + "     event.dataTransfer = {"
                        + "         data: {"
                        + "         },"
                        + "         setData: function(type, val){"
                        + "             this.data[type] = val;"
                        + "         },"
                        + "         getData: function(type){"
                        + "             return this.data[type];"
                        + "         }"
                        + "     };"
                        + "     return event;"
                        + " },"
                        + " dispatchEvent: function(elem, type, event) {"
                        + "     if(elem.dispatchEvent) {"
                        + "         elem.dispatchEvent(event);"
                        + "     }else if( elem.fireEvent ) {"
                        + "         elem.fireEvent(\"on\"+type, event);"
                        + "     }"
                        + " }"
                        + "});"
                        + "})(jQuery);";

        // dragAndDropScript = dragAndDropScript +
        // "$(arguments[0]).simulateDragDrop({dropTarget:arguments[1]});";
        // js.executeScript(dragAndDropScript, fElement, tElement);
    }
}
