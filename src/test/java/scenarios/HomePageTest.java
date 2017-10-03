package scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.Element;
import auto.framework.web.WebControl;

import common.GlobalPage;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.HomePage;

public class HomePageTest extends TestBase
{
    public static TestDataHandler testDataHandler;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("PAX Protection - Home Page");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='PaxProtect'");

        TestCases.PreRequisiteStep();
        TestCases.OpenPAXProtectSite();
        //TestCases.CheckPDSSection();
        //TestCases.DisruptionWindowFilter();
        //TestCases.Filter();
        TestCases.SolveDisruptions();
        Thread.sleep(5000);
    }

    public static class TestCases
    {
        public static void PreRequisiteStep() throws InterruptedException 
        {
            ReportLog.setTestCase("PAX Protection HomePage");
            ReportLog.setTestStep("Go to this URL: https://aviation-in-pax-protect-ui-app-hub-qa.run.aws-usw02-pr.ice.predix.io/ ");
            WebControl.clearData();
            WebControl.open(testDataHandler.url);
            Thread.sleep(1000);
            if (GESSOAuthPage.authInfo.gESSOLabel.isDisplayed())
            {
                GESSOAuthPage.authInfo.sSOIDInput.typeKeys(testDataHandler.username);
                GESSOAuthPage.authInfo.passWordInput.typeKeys(testDataHandler.password);
                GESSOAuthPage.authInfo.submitFormShared.click();
            }
            GESSOAuthPage.page.verifyURL(false, 60);
        }

        public static void OpenPAXProtectSite()
        {
            ReportLog.setTestCase("Validate Header Section");

            HomePage.paperToast.verifyDisplayed(true, 1);
            extractPaperToastText(HomePage.paperToast.paperStatus.getText(), 
                    getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]")), 
                    Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()));

            ReportLog.setTestStep("Verify navigation pane");
            HomePage.navigationSection.toggleButton.verifyDisplayed(true, 5);
            
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Protection").highlight();
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Protection").verifyText("Pax Protection");
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Protection").icon.verifyDisplayed(true, 5);
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Config").highlight();
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Config").verifyText("Pax Config");
            HomePage.navigationSection.mainNavigationOptions().navigateToLink("Pax Config").icon.verifyDisplayed(true, 5);
            WebControl.takeScreenshot();

            ReportLog.setTestStep("Verify 'PAX Protection' header");
            HomePage.mainHeader.headerTitle.verifyDisplayed(true, 5);
            HomePage.mainHeader.headerTitle.verifyText("PAX Protection", true);

            ReportLog.setTestStep("'> Hide Flights' is displayed");
            HomePage.mainHeader.showHideFlights.verifyDisplayed(true, 3);
            HomePage.mainHeader.showHideFlights.lessThan.verifyDisplayed(true, 3);
            HomePage.mainHeader.showHideFlights.hideFlightsText.verifyDisplayed(true, 3);
            HomePage.mainHeader.showHideFlights.hideFlightsText.verifyText("Hide Flights");
        }

        public static void CheckPDSSection() throws InterruptedException
        {
            ReportLog.setTestCase("Validate PDS Section");
            ReportLog.setTestStep("Validate Impacted Flights Text & Count");
            HomePage.pdsSection.impactedFlightsText.verifyDisplayed(true, 5);
            HomePage.pdsSection.impactedFlightsText.verifyText("Impacted Flights");
            HomePage.pdsSection.impactedFlightsCount.verifyDisplayed(true, 5);
            ReportLog.setTestStep("'Sort By:' text is present");
            HomePage.pdsSection.sortBy.verifyText("Sort by:", true);
            ReportLog.setTestStep("Caret Up icon is present");
            HomePage.pdsSection.caretUp.verifyDisplayed(true, 10);
            HomePage.pdsSection.caretUp.click();
            ReportLog.setTestStep("Caret Down icon is present");
            HomePage.pdsSection.caretDown.waitForDisplay(true, 10);
            HomePage.pdsSection.caretDown.verifyDisplayed(true, 10);
            ReportLog.setTestStep("'Departure Time' text is present");
            HomePage.pdsSection.departureTime.verifyText("Departure Time", true);
            ReportLog.setTestStep("Disruption window filter icon is present");
            HomePage.pdsSection.disruptionFilterIcon.verifyDisplayed(true, 10);
            ReportLog.setTestStep("Filter icon is present");
            HomePage.pdsSection.filterIcon.verifyDisplayed(true, 10);
            ReportLog.setTestStep("CheckAll checkbox is present");
            HomePage.pdsSection.checkAll.verifyDisplayed(true, 10);
            ReportLog.setTestStep("Solve button is present");
            HomePage.pdsSection.solveButton.verifyDisplayed(true, 10);
            HomePage.pdsSection.caretDown.click();

            ReportLog.setTestStep("Check no. of disrupted flights");
            Thread.sleep(10000);
            if (extractByComma(testDisruptionHandler.disruptionList).length == HomePage.pdsSection.disruptionCount)
            {
                ReportLog.logEvent("Passed", "No. of disrupted flights in Dataset is equivalent to no. of disrupted flights in PDS.");
                String[] extractDisruptions = extractByComma(testDisruptionHandler.disruptionList);

                if (checkDefaultSort(extractDisruptions))
                {
                    ReportLog.logEvent("Passed", "Default sort was followed.");
                    for (int i = 0; i < HomePage.pdsSection.disruptionCount; i++)
                    // for (int i = 0; i < 3; i++)
                    {
                        ReportLog.setTestStep("Verify " + extractDisruptions[i] + "'s row");
                        HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).verifyDisplayed(true, 10);

                        HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).checkBox.verifyDisplayed(true, 10);

                        String flightID = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).checkBox.getAttribute("id");
                        if (extractDisruptions[i].equalsIgnoreCase(flightID))
                        {
                            String[] extractFlightDetails = extractByDash(extractDisruptions[i]);

                            HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightName.flightNameID.verifyDisplayed(true, 10);
                            String flightName = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightName.flightNameID.getText();
                            String classAttribute = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightName.flightNameID.getAttribute("class");

                            if (flightName.equalsIgnoreCase(extractFlightDetails[1] + " " + extractFlightDetails[2]))
                            {
                                ReportLog.logEvent("Passed", "Flight name is correct");
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Flight name is not correct");
                            }

                            HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightName.flightNameTime.verifyDisplayed(true, 10);

                            HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightName.bookedPaxCount.verifyDisplayed(true, 10);

                            HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightRoute.verifyDisplayed(true, 10);
                            String origDest = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightRoute.getText();

                            if (origDest.equalsIgnoreCase(extractFlightDetails[4] + " - " + extractFlightDetails[5]))
                            {
                                ReportLog.logEvent("Passed", "Origin and destination is correct");
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Origin and destination is not correct");
                            }

                            if (classAttribute.contains("delayed-15") || classAttribute.contains("delayed-3"))
                            {
                                HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightDelayStatus.verifyDisplayed(true, 10);
                                String status = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightDelayStatus.getText();
                                if (status.equalsIgnoreCase("Delay"))
                                {
                                    HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.delayMinutes.verifyDisplayed(true, 10);
                                    if (compareTimes(classAttribute, HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.delayMinutes.getText()))
                                    {
                                        ReportLog.logEvent("Passed", "Valid flight disruption status and color");
                                    }
                                    else
                                    {
                                        ReportLog.logEvent("Failed", "Invalid flight disruption status and color");
                                    }
                                }
                                else
                                {
                                    ReportLog.logEvent("Failed", "Invalid flight disruption 'delay' status");
                                }
                            }
                            else if (classAttribute.contains("cancelled"))
                            {
                                HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightCancelStatus.verifyDisplayed(true, 10);
                                String status = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightCancelStatus.getText();
                                if (status.equalsIgnoreCase("Cancelled"))
                                {
                                    ReportLog.logEvent("Passed", "Valid flight disruption status and color");
                                }
                                else
                                {
                                    ReportLog.logEvent("Failed", "Invalid flight disruption 'cancelled' status");
                                }
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Flight disruption status does not exist");
                            }

                            HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightMisconxLabel.verifyDisplayed(true, 10);
                            String misconLabel = HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightMisconxLabel.getText();

                            if (misconLabel.equalsIgnoreCase("Misconx"))
                            {
                                ReportLog.logEvent("Passed", "Valid misconnecting label");
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Invalid misconnecting label");
                            }

                            HomePage.pdsSection.disruptedFlight(extractDisruptions[i]).flightDetails.flightMisconxCount.verifyDisplayed(true, 10);
                        }
                        else
                        {
                            ReportLog.logEvent("Failed", "Default sort was not followed.");
                            break;
                        }
                    }
                }
                else
                {
                    ReportLog.logEvent("Failed", "Default sort was not followed.");
                    Assert.fail();
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "No. of disrupted flights in Dataset is not equivalent to no. of disrupted flights in PDS.");
                Assert.fail();
            }
        }

        public static void DisruptionWindowFilter() throws InterruptedException
        {
            ReportLog.setTestCase("Disruption window validation. FR3.0: The user shall have the ability to modify the PDS window configuration value through the UI");
            HomePage.pdsSection.disruptionFilterIcon.click();
            HomePage.pdsSection.disruptionFilter.verifyDisplayed(true, 5);
            HomePage.pdsSection.selectedDisruptionFilterIcon.verifyDisplayed(true, 5);
            HomePage.pdsSection.disruptionFilter.disruptionFilterText.verifyDisplayed(true, 5);
            HomePage.pdsSection.disruptionFilter.disruptionFilterText.verifyText("Schedule to depart within");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.verifyDisplayed(true, 5);
            HomePage.pdsSection.disruptionFilter.hoursText.verifyDisplayed(true, 5);
            HomePage.pdsSection.disruptionFilter.hoursText.verifyText("Hours");
            HomePage.pdsSection.disruptionFilter.applyButton.verifyDisplayed(true, 5);
            isElementDisabled(HomePage.pdsSection.disruptionFilter.applyButton, false);

            ReportLog.setTestStep("Input '1' (minimum value) and apply it.");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("1");
            HomePage.pdsSection.disruptionFilter.applyButton.click();
            Thread.sleep(3000);
            HomePage.paperToast.verifyDisplayed(true, 5);
            extractPaperToastText(HomePage.paperToast.paperStatus.getText(), 
                    getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]")), 
                    Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()));
            Thread.sleep(10000);

            ReportLog.setTestStep("Input '99999' (maximum value) and apply it.");
            HomePage.pdsSection.disruptionFilterIcon.click();
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("99999");
            HomePage.pdsSection.disruptionFilter.applyButton.click();
            Thread.sleep(3000);
            HomePage.paperToast.verifyDisplayed(true, 5);
            extractPaperToastText(HomePage.paperToast.paperStatus.getText(), 
                    getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]")), 
                    Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()));
            Thread.sleep(10000);

            ReportLog.setTestStep("Input '50000' (middle value) and apply it.");
            HomePage.pdsSection.disruptionFilterIcon.click();
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("50000");
            HomePage.pdsSection.disruptionFilter.applyButton.click();
            Thread.sleep(3000);
            HomePage.paperToast.verifyDisplayed(true, 5);
            extractPaperToastText(HomePage.paperToast.paperStatus.getText(), 
                    getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]")), 
                    Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()));
            Thread.sleep(10000);

            ReportLog.setTestStep("Input '24' (default) and apply it.");
            HomePage.pdsSection.disruptionFilterIcon.click();
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("24");
            HomePage.pdsSection.disruptionFilter.applyButton.click();
            Thread.sleep(3000);
            HomePage.paperToast.verifyDisplayed(true, 5);
            extractPaperToastText(HomePage.paperToast.paperStatus.getText(), 
                    getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]")), 
                    Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()));
            Thread.sleep(10000);

            ReportLog.setTestStep("Remove the value in the text box");
            HomePage.pdsSection.disruptionFilterIcon.click();
            String selectAll = Keys.chord(Keys.CONTROL, "a");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.sendKeys(selectAll);
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.sendKeys("\u0008");
            Thread.sleep(2000);
            HomePage.pdsSection.disruptionFilter.errorTextMessage.verifyText("Invalid input");
            HomePage.pdsSection.disruptionFilter.errorTextMessage.getAttribute("style").equalsIgnoreCase("color:red");
            isElementDisabled(HomePage.pdsSection.disruptionFilter.applyButton, true);
            Thread.sleep(3000);

            ReportLog.setTestStep("Input negative value");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("-1");
            Thread.sleep(2000);
            HomePage.pdsSection.disruptionFilter.errorTextMessage.verifyText("Accepts only numbers from 1 to 99999");
            HomePage.pdsSection.disruptionFilter.errorTextMessage.getAttribute("style").equalsIgnoreCase("color:red");
            isElementDisabled(HomePage.pdsSection.disruptionFilter.applyButton, true);
            Thread.sleep(3000);

            ReportLog.setTestStep("Input non-integer value");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("A@");
            Thread.sleep(2000);
            HomePage.pdsSection.disruptionFilter.errorTextMessage.verifyText("Accepts only numbers from 1 to 99999");
            HomePage.pdsSection.disruptionFilter.errorTextMessage.getAttribute("style").equalsIgnoreCase("color:red");
            isElementDisabled(HomePage.pdsSection.disruptionFilter.applyButton, true);
            Thread.sleep(3000);

            ReportLog.setTestStep("Input 0 value");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("0");
            Thread.sleep(2000);
            HomePage.pdsSection.disruptionFilter.errorTextMessage.verifyText("Minimum value should be 1 hour");
            HomePage.pdsSection.disruptionFilter.errorTextMessage.getAttribute("style").equalsIgnoreCase("color:red");
            isElementDisabled(HomePage.pdsSection.disruptionFilter.applyButton, true);
            Thread.sleep(3000);

            ReportLog.setTestStep("Input float value");
            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("9.99");
            Thread.sleep(2000);
            HomePage.pdsSection.disruptionFilter.errorTextMessage.verifyText("Accepts only numbers from 1 to 99999");
            HomePage.pdsSection.disruptionFilter.errorTextMessage.getAttribute("style").equalsIgnoreCase("color:red");
            isElementDisabled(HomePage.pdsSection.disruptionFilter.applyButton, true);
            Thread.sleep(3000);

            HomePage.pdsSection.disruptionFilter.disruptionTextBox.typeKeys("24");
            HomePage.pdsSection.disruptionFilter.applyButton.click();
            Thread.sleep(5000);
        }

        public static void Filter() throws InterruptedException
        {
            By by = By.xpath(".//li[contains(@class,'px-dropdown--listitem style-scope px-dropdown-content')]");
            String[] originOptions = extractByComma(testFilterHandler.originList);
            String[] flightOptions = extractByComma(testFilterHandler.flightList);
            String[] searchOriginOptions = extractByComma(testFilterInputHandler.originList);
            String[] searchFlightOptions = extractByComma(testFilterInputHandler.flightList);

            ReportLog.setTestCase("Filter validation [FR12.0, FR107.0, FR145.0, FR146.0, FR147.0, FR152.0, FR154.0, FR155.0, FR157.0]");
            ReportLog.setTestStep("Verifying FR12.0, FR107.0 & FR152.0 scenarios");
            HomePage.pdsSection.filterIcon.verifyDisplayed(true, 10);
            HomePage.pdsSection.filterIcon.click();
            HomePage.pdsSection.filter.verifyDisplayed(true, 5);
            HomePage.pdsSection.selectedFilterIcon.verifyDisplayed(true, 5);
            verifyDropdown("dpOrig");
            verifyDropdown("dpDest");
            verifyDropdown("dpFlight");
            verifyDropdown("dpImpact");
            HomePage.pdsSection.filter.clearAllButton.verifyDisplayed(true, 5);
            isElementDisabled(HomePage.pdsSection.filter.clearAllButton, false);
            HomePage.pdsSection.filter.applyButton.verifyDisplayed(true, 5);
            isElementDisabled(HomePage.pdsSection.filter.applyButton, true);

            Thread.sleep(3000);
            ReportLog.setTestStep("Verifying FR154.0 scenarios - deselecting all options");
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            Thread.sleep(2000);
            getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, "All").click();
            Thread.sleep(2000);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            isElementDisabled(HomePage.pdsSection.filter.applyButton, false);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            boolean isOptionSelected = false;
            for (int i = 0; i < originOptions.length; i++)
            {
                if (getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, "All").isSelected())
                {
                    isOptionSelected = true;
                    break;
                }
            }
            if (!isOptionSelected)
            {
                ReportLog.logEvent("Passed", "All options are not selected");
            }
            else
            {
                ReportLog.logEvent("Failed", "There is an option that is still selected");
            }

            Thread.sleep(3000);
            ReportLog.setTestStep("Verifying FR154.0 scenarios - selecting a single option");
            getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[0]).click();
            Thread.sleep(2000);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            isElementDisabled(HomePage.pdsSection.filter.applyButton, false);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            if (getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[0]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
            {
                ReportLog.logEvent("Passed", searchOriginOptions[0] + " option is selected");
            }
            else
            {
                ReportLog.logEvent("Failed", searchOriginOptions[0] + " option is not selected");
            }

            Thread.sleep(3000);
            ReportLog.setTestStep("Verifying FR154.0 scenarios - selecting multiple options");
            getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[1]).click();
            Thread.sleep(2000);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            isElementDisabled(HomePage.pdsSection.filter.applyButton, false);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            for (int j = 0; j < searchOriginOptions.length; j++)
            {
                if (getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[j]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    ReportLog.logEvent("Passed", searchOriginOptions[j] + " option is selected");
                }
                else
                {
                    ReportLog.logEvent("Passed", searchOriginOptions[j] + " option is not selected");
                }
            }

            Thread.sleep(3000);
            ReportLog.setTestStep("Verifying FR154.0 scenarios - reverting to defaults in closing filter section");
            HomePage.pdsSection.disruptionFilterIcon.click();
            Thread.sleep(2000);
            HomePage.pdsSection.filterIcon.click();
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            boolean isSelectedAll = true;
            for (int i = 0; i < originOptions.length; i++)
            {
                if (!getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, originOptions[i]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    isSelectedAll = false;
                    break;
                }
            }
            if (isSelectedAll)
            {
                ReportLog.logEvent("Passed", "All options are selected");
            }
            else
            {
                ReportLog.logEvent("Failed", "There is an option that is still not selected");
            }
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            isElementDisabled(HomePage.pdsSection.filter.clearAllButton, false);
            isElementDisabled(HomePage.pdsSection.filter.applyButton, true);
            Thread.sleep(3000);

            ReportLog.setTestStep("Verifying FR145.0 & FR147.0 scenarios - clearing before applying");
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, "All").click();
            Thread.sleep(2000);
            for (int i = 0; i < searchOriginOptions.length; i++)
            {
                getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[i]).click();
            }
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, "All").click();
            Thread.sleep(2000);
            for (int i = 0; i < searchFlightOptions.length; i++)
            {
                getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, searchFlightOptions[i]).click();
            }
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            HomePage.pdsSection.filter.clearAllButton.click();
            isElementDisabled(HomePage.pdsSection.filter.applyButton, true);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            isSelectedAll = true;
            for (int i = 0; i < originOptions.length; i++)
            {
                if (!getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, originOptions[i]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    isSelectedAll = false;
                    break;
                }
            }
            if (isSelectedAll)
            {
                ReportLog.logEvent("Passed", "All options are selected");
            }
            else
            {
                ReportLog.logEvent("Failed", "There is an option that is still not selected");
            }
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            isSelectedAll = true;
            for (int i = 0; i < flightOptions.length; i++)
            {
                if (!getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, flightOptions[i]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    isSelectedAll = false;
                    break;
                }
            }
            if (isSelectedAll)
            {
                ReportLog.logEvent("Passed", "All options are selected");
            }
            else
            {
                ReportLog.logEvent("Failed", "There is an option that is still not selected");
            }
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            Thread.sleep(3000);

            ReportLog.setTestStep("Verifying FR145.0 & FR147.0 scenarios - applying filters");
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, "All").click();
            Thread.sleep(2000);
            for (int i = 0; i < searchOriginOptions.length; i++)
            {
                getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[i]).click();
            }
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, "All").click();
            Thread.sleep(2000);
            for (int i = 0; i < searchFlightOptions.length; i++)
            {
                getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, searchFlightOptions[i]).click();
            }
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            HomePage.pdsSection.filter.applyButton.click();
            Thread.sleep(3000);
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            for (int j = 0; j < searchOriginOptions.length; j++)
            {
                if (getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, searchOriginOptions[j]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    ReportLog.logEvent("Passed", searchOriginOptions[j] + " option is selected");
                }
                else
                {
                    ReportLog.logEvent("Passed", searchOriginOptions[j] + " option is not selected");
                }
            }
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            for (int j = 0; j < searchFlightOptions.length; j++)
            {
                if (getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, searchFlightOptions[j]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    ReportLog.logEvent("Passed", searchFlightOptions[j] + " option is selected");
                }
                else
                {
                    ReportLog.logEvent("Passed", searchFlightOptions[j] + " option is not selected");
                }
            }
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            isElementDisabled(HomePage.pdsSection.filter.clearAllButton, false);
            isElementDisabled(HomePage.pdsSection.filter.applyButton, true);
            Thread.sleep(2000);
            if (getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]"))
                    == Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()))
            {
                ReportLog.logEvent(true, "# of flights in the PDS & # of impacted pax are equal");
            }
            else
            {
                ReportLog.logEvent(false, "# of flights in the PDS & # of impacted pax are not equal");
            }
            Thread.sleep(3000);

            ReportLog.setTestStep("Verifying FR145.0 & FR147.0 scenarios - clearing applied filters");
            HomePage.pdsSection.filter.clearAllButton.click();
            HomePage.pdsSection.filter.applyButton.click();
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            isSelectedAll = true;
            for (int i = 0; i < originOptions.length; i++)
            {
                if (!getElement(HomePage.pdsSection.filter.dropDown("dpOrig").dropdownDetails, by, originOptions[i]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    isSelectedAll = false;
                    break;
                }
            }
            if (isSelectedAll)
            {
                ReportLog.logEvent("Passed", "All options are selected");
            }
            else
            {
                ReportLog.logEvent("Failed", "There is an option that is still not selected");
            }
            HomePage.pdsSection.filter.dropDown("dpOrig").click();
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            isSelectedAll = true;
            for (int i = 0; i < flightOptions.length; i++)
            {
                if (!getElement(HomePage.pdsSection.filter.dropDown("dpFlight").dropdownDetails, by, flightOptions[i]).findElement(By.xpath(".//input[@type='checkbox']")).isSelected())
                {
                    isSelectedAll = false;
                    break;
                }
            }
            if (isSelectedAll)
            {
                ReportLog.logEvent("Passed", "All options are selected");
            }
            else
            {
                ReportLog.logEvent("Failed", "There is an option that is still not selected");
            }
            HomePage.pdsSection.filter.dropDown("dpFlight").click();
            isElementDisabled(HomePage.pdsSection.filter.clearAllButton, false);
            isElementDisabled(HomePage.pdsSection.filter.applyButton, true);
            Thread.sleep(2000);
            if (getListCount(HomePage.pdsSection.rootElement, By.xpath(".//li[contains(@class, 'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]"))
                    == Integer.parseInt(HomePage.pdsSection.impactedFlightsCount.getText().trim()))
            {
                ReportLog.logEvent(true, "# of flights in the PDS & # of impacted pax are equal");
            }
            else
            {
                ReportLog.logEvent(false, "# of flights in the PDS & # of impacted pax are not equal");
            }
            Thread.sleep(3000);
        }

        public static void SolveDisruptions() throws InterruptedException
        {
            Thread.sleep(10000);
//            ReportLog.setTestCase("Solve Flight Disruptions multiple disrupted flights are selected");
//
//            if (isDisabled(HomePage.pdsSection.solveButton))
//            {
//                ReportLog.logEvent("Passed", "The Solve button is disabled before selecting any flights");
//                HomePage.pdsSection.checkAll.click();
//                if (!isDisabled(HomePage.pdsSection.solveButton))
//                {
//                    ReportLog.logEvent("Passed", "The Solve button is enabled after selecting all flights");
//                    Thread.sleep(2000);
//                    HomePage.pdsSection.checkAll.click();
//                    if (isDisabled(HomePage.pdsSection.solveButton))
//                    {
//                        ReportLog.logEvent("Passed", "The Solve button is disabled after unselecting all flights");
//                        Thread.sleep(2000);
//                        String[] multipleDisruptedFlights = extractByComma(testDisruptionHandler.multipleDisruptedFlights);
//                        for (int i = 0; i < multipleDisruptedFlights.length; i++)
//                        {
//                            HomePage.pdsSection.disruptedFlight(multipleDisruptedFlights[i]).checkBox.click();
//                        }
//
//                        Thread.sleep(2000);
//                        HomePage.pdsSection.solveButton.click();
//                        verifyProgressBar();
//                        HomePage.pdsSection.progressBarSection().cancelButton.click();
//                        verifyCancelConfirmation();
//                        HomePage.cancelPopUp.yesButton.click();
//                        if (HomePage.pdsSection.getAttribute("class").contains("showPDS"))
//                        {
//                            ReportLog.logEvent("Passed", "PDS section is visible");
//                        }
//                        else
//                        {
//                            ReportLog.logEvent("Failed", "PDS section is not visible");
//                        }
//
//                        boolean isRehighlighted = true;
//                        for (int i = 0; i < multipleDisruptedFlights.length; i++)
//                        {
//                            if (!HomePage.pdsSection.disruptedFlight(multipleDisruptedFlights[i]).getAttribute("class").contains("selected"))
//                            {
//                                ReportLog.logEvent("Failed", "Flight was not re-highlighted after cancelling a solving task");
//                                isRehighlighted = false;
//                                break;
//                            }
//                        }
//
//                        if (isRehighlighted)
//                        {
//                            ReportLog.logEvent("Passed", "All flights were re-highlighted after cancelling a solving task");
//                        }
//                        Thread.sleep(3000);
//                    }
//                    else
//                    {
//                        ReportLog.logEvent("Failed", "The Solve button is enabled after unselecting all flights");
//                    }
//                }
//                else
//                {
//                    ReportLog.logEvent("Failed", "The Solve button is disabled after selecting all flights");
//                }
//            }
//            else
//            {
//                ReportLog.logEvent("Failed", "The Solve button is enabled before selecting any flights");
//            }

            ReportLog.setTestCase("Solve Flight Disruptions one disrupted flight is selected");

            String disruptedFlight = testDisruptionHandler.disruptedFlight;

            if (isDisabled(HomePage.pdsSection.solveButton))
            {
                ReportLog.logEvent("Passed", "The Solve button is disabled before selecting any flights");
                HomePage.pdsSection.disruptedFlight(disruptedFlight).checkBox.click();
                if (!isDisabled(HomePage.pdsSection.solveButton))
                {
                    ReportLog.logEvent("Passed", "The Solve button is enabled after selecting one flight");
                    Thread.sleep(2000);
                    HomePage.pdsSection.disruptedFlight(disruptedFlight).checkBox.click();
                    if (isDisabled(HomePage.pdsSection.solveButton))
                    {
                        ReportLog.logEvent("Passed", "The Solve button is disabled after unselecting one flight");
                        Thread.sleep(2000);
                        HomePage.pdsSection.disruptedFlight(disruptedFlight).checkBox.click();
                        Thread.sleep(2000);
                        HomePage.pdsSection.solveButton.click();
//                        HomePage.pdsSection.progressBarSection().cancelButton.click();
//                        Thread.sleep(2000);
//                        HomePage.cancelPopUp.noButton.click();
                    }
                    else
                    {
                        ReportLog.logEvent("Failed", "The Solve button is enabled after unselecting one flight");
                    }
                }
                else
                {
                    ReportLog.logEvent("Failed", "The Solve button is disabled after selecting one flight");
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "The Solve button is enabled before selecting any flights");
            }
        }
    }

    /*
     * - Methods that will aide in the automation testing methods.
     */
    public static void verifyDropdown(String dropdownID) throws InterruptedException
    {
        String testFilterHandlerList = "";
        String testFilterInputHandlerList = "";
        String selectAll = Keys.chord(Keys.CONTROL, "a");
        String backspace = "\u0008";
        By by = By.xpath(".//li[contains(@class,'px-dropdown--listitem style-scope px-dropdown-content')]");

        HomePage.pdsSection.filter.dropDown(dropdownID).verifyDisplayed(true, 5);
        HomePage.pdsSection.filter.dropDown(dropdownID).dropdownName.verifyDisplayed(true, 5);
        if (dropdownID.equalsIgnoreCase("dpOrig"))
        {
            testFilterHandlerList = testFilterHandler.originList;
            testFilterInputHandlerList = testFilterInputHandler.originList;
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownName.verifyText("Origin");
        }
        if (dropdownID.equalsIgnoreCase("dpDest"))
        {
            testFilterHandlerList = testFilterHandler.destinationList;
            testFilterInputHandlerList = testFilterInputHandler.destinationList;
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownName.verifyText("Destination");
        }
        if (dropdownID.equalsIgnoreCase("dpFlight"))
        {
            testFilterHandlerList = testFilterHandler.flightList;
            testFilterInputHandlerList = testFilterInputHandler.flightList;
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownName.verifyText("Flight #");
        }
        if (dropdownID.equalsIgnoreCase("dpImpact"))
        {
            testFilterHandlerList = testFilterHandler.impactTypeList;
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownName.verifyText("Impact Type");
        }

        HomePage.pdsSection.filter.dropDown(dropdownID).dropDownIcon.verifyDisplayed(true, 5);
        HomePage.pdsSection.filter.dropDown(dropdownID).click();
        HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.verifyDisplayed(true, 5);

        if (!dropdownID.equalsIgnoreCase("dpImpact"))
        {
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.verifyDisplayed(true, 5);
            if (checkTextInsideSearchBox(dropdownID, HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.getAttribute("placeholder")))
            {
                ReportLog.logEvent("Passed", "Text inside the search box is correct");
            }
            else
            {
                ReportLog.logEvent("Failed", "Text inside the search box is incorrect");
            }

            // Using the search box
            String[] filterInput = extractByComma(testFilterInputHandlerList);

            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.sendKeys(filterInput[0]);
            if (getListCount(HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails, by) == 1)
            {
                if (filterInput[0].equalsIgnoreCase(getElement(HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails, by, filterInput[0]).getText().trim()))
                {
                    ReportLog.logEvent("Passed", "Same filter dropdown option as we are searching");
                }
                else
                {
                    ReportLog.logEvent("Failed", "Different filter dropdown option as we are searching");
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "Text we are searching is invalid");
            }

            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.sendKeys(selectAll);
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.sendKeys(backspace);
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.sendKeys("@BC");

            if (getListCount(HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails, by) == 0)
            {
                ReportLog.logEvent("Passed", "No options after searching an invalid option");
            }
            else
            {
                ReportLog.logEvent("Failed", "Has option(s) after searching an invalid option");
            }

            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.sendKeys(selectAll);
            HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.searchBox.sendKeys(backspace);
            Thread.sleep(2000);
        }

        if (getListCount(HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails, by) == extractByComma(testFilterHandlerList).length)
        {
            ReportLog.logEvent("Passed", "Equal no. of dropdown options");
            String[] filterOptions = extractByComma(testFilterHandlerList);
            if (checkFilterOptions(dropdownID, filterOptions))
            {
                ReportLog.logEvent("Passed", "Same filter dropdown options");
            }
            else
            {
                ReportLog.logEvent("Failed", "Different filter dropdown options");
            }
        }
        else
        {
            ReportLog.logEvent("Failed", "Not equal no. of dropdown options");
        }

        HomePage.pdsSection.filter.dropDown(dropdownID).click();
       
    }

    public static WebElement getElement(Element rootElement, By by, String textToBeLocated)
    {
        WebElement locatedElement = null;

        WebElement webEl = rootElement.toWebElement();
        List<WebElement> webElementList = webEl.findElements(by);

        for (WebElement option : webElementList)
        {
            if (textToBeLocated.equalsIgnoreCase(option.getText().trim()))
            {
                locatedElement = option;
                break;
            }
        }

        return locatedElement;
    }

    public static boolean checkFilterOptions(String dropdownID, String[] filterOptions)
    {
        WebElement rootElement = HomePage.pdsSection.filter.dropDown(dropdownID).dropdownDetails.toWebElement();
        List<WebElement> webEl = rootElement.findElements(By.xpath(".//li[contains(@class,'px-dropdown--listitem style-scope px-dropdown-content')]"));
        int counter = 0;
        boolean isComplete = true;

        if (webEl.size() >= 1)
        {
            for (WebElement optionElement : webEl)
            {
                if (counter != filterOptions.length)
                {
                    if (filterOptions[counter].equalsIgnoreCase(optionElement.getText().trim()))
                    {
                        counter++;
                    }
                    else
                    {
                        isComplete = false;
                        break;
                    }
                }
            }
        }
        else
        {
            isComplete = false;
        }

        return isComplete;
    }

    public static boolean checkDefaultSort(String[] disruptions)
    {
        // FR10.0
        WebElement bigElement = HomePage.pdsSection.rootElement.toWebElement();
        List<WebElement> disruption = bigElement.findElements(By.xpath(".//li[contains(@class,'list-ui__item disruption-items old mobile-margin-bottom style-scope px-inbox')]"));
        int counter = 0;
        boolean isDefaultSort = true;

        if (disruption.size() >= 1)
        {
            for (WebElement rowElement : disruption)
            {
                if (counter != disruptions.length)
                {
                    if (disruptions[counter].equalsIgnoreCase(rowElement.findElement(By.xpath(".//input")).getAttribute("id")))
                    {
                        System.out.println("\t\t\t\t" + rowElement.findElement(By.xpath(".//input")).getAttribute("id").trim() + " is equal to ID " + disruptions[counter]);
                        counter++;
                    }
                    else
                    {
                        isDefaultSort = false;
                        break;
                    }
                }
            }
        }
        else
        {
            isDefaultSort = false;
        }

        return isDefaultSort;
    }

    public static void verifyProgressBar()
    {
        ReportLog.setTestStep("Progress status, progress bar and cancel button are present while the system generates new itineraries");
        HomePage.pdsSection.progressBarSection().progressStatus.verifyDisplayed(true);
        HomePage.pdsSection.progressBarSection().progressBar.verifyDisplayed(true);
        HomePage.pdsSection.progressBarSection().cancelButton.verifyDisplayed(true);
    }

    public static void verifyCancelConfirmation()
    {
        ReportLog.setTestStep("Verifying 'Cancel confirmation' pop-up elements");
        HomePage.cancelPopUp.parentElement.verifyDisplayed(true, 5);
        HomePage.cancelPopUp.cancelConfirmationText.verifyDisplayed(true, 5);
        HomePage.cancelPopUp.cancelConfirmationText.verifyText("Cancel Confirmation");
        String[] tempText = HomePage.cancelPopUp.parentElement.getText().split("\n");
        if (tempText[1].equalsIgnoreCase("Are you sure you want to cancel the solving process?"))
        {
            ReportLog.logEvent("Passed", "Verify that text equals Are you sure you want to cancel the solving process?");
        }
        else
        {
            ReportLog.logEvent("Failed", "Verify that text not equals Are you sure you want to cancel the solving process?");
        }
        HomePage.cancelPopUp.noButton.verifyDisplayed(true, 5);
        HomePage.cancelPopUp.yesButton.verifyDisplayed(true, 5);
    }

    public static String[] extractByComma(String list)
    {
        String[] extractedList = list.split(",");

        return extractedList;
    }

    public static String[] extractByDash(String list)
    {
        String[] extractedList = list.split("-");

        return extractedList;
    }
    
    public static String[] extractByColon(String list)
    {
        String[] extractedList = list.split(":");

        return extractedList;
    }

    public static void extractPaperToastText(String paperStat, int numDisruptedFlight, int impactedNum)
    {
        String[] paperStatus = paperStat.split(":");

        if (numDisruptedFlight == Integer.parseInt(paperStatus[1].trim()) && numDisruptedFlight == impactedNum)
        {
            ReportLog.logEvent(true, "# of flights in the paper toast is equal to the # of flights in the PDS & # of impacted pax");
        }
        else
        {
            ReportLog.logEvent(false, "# of flights in the paper toast is not equal to the # of flights in the PDS and/or # of impacted pax");
        }
    }

    public static boolean checkTextInsideSearchBox(String dropdownID, String placeholder)
    {
        boolean isEqual = false;

        if (dropdownID.equalsIgnoreCase("dpOrig") && placeholder.equalsIgnoreCase("Enter Origin"))
        {
            isEqual = true;
        }

        if (dropdownID.equalsIgnoreCase("dpDest") && placeholder.equalsIgnoreCase("Enter Destination"))
        {
            isEqual = true;
        }

        if (dropdownID.equalsIgnoreCase("dpFlight") && placeholder.equalsIgnoreCase("Enter Flight #"))
        {
            isEqual = true;
        }
        
        if (dropdownID.equalsIgnoreCase("dpPNR") && placeholder.equalsIgnoreCase("Enter PNR"))
        {
            isEqual = true;
        }
        
        if (dropdownID.equalsIgnoreCase("dpFreqFly") && placeholder.equalsIgnoreCase("Enter Frequent Flyer"))
        {
            isEqual = true;
        }

        return isEqual;
    }

    public static boolean compareTimes(String attribute, String delayMinutes)
    {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Boolean isTrue = false;

        try
        {
            Date delayMins = parser.parse(delayMinutes);

            if (attribute.contains("delayed-15"))
            {
                Date range1 = parser.parse("00:15");
                Date range2 = parser.parse("03:00");

                if (delayMins.after(range1) && delayMins.before(range2))
                {
                    isTrue = true;
                }
            }
            else if (attribute.contains("delayed-3"))
            {
                Date range1 = parser.parse("03:00");

                if (delayMins.equals(range1) || delayMins.after(range1))
                {
                    isTrue = true;
                }
            }
            else
            {
                return isTrue;
            }
        }
        catch (ParseException e)
        {
            return isTrue;
        }

        return isTrue;
    }

    public static boolean isDisabled(Element element)
    {
        String classAttribute = element.getAttribute("class");
        
        if (classAttribute.contains("disabled"))
        {
            ReportLog.logEvent(true, "Element is disabled");
            return true;
        }
        else
        {
            ReportLog.logEvent(true, "Element is not disabled");
            return false;
        }
    }

    public static void isElementDisabled(Element element, boolean isDisabledAtDefault)
    {
        String classAttribute = element.getAttribute("class");
        
        if (classAttribute.contains("disabled"))
        {
            if (isDisabledAtDefault)
            {
                ReportLog.logEvent(true, "Element is disabled");
            }
            else
            {
                ReportLog.logEvent(false, "Element is enabled");
            }
        }
        else
        {
            if (!isDisabledAtDefault)
            {
                ReportLog.logEvent(true, "Element is enabled");
            }
            else
            {
                ReportLog.logEvent(false, "Element is disabled");
            }
        }
    }

    public static int getListCount(Element rootElement, By by)
    {
        WebElement webEl = rootElement.toWebElement();
        List<WebElement> webElementList = webEl.findElements(by);

        return webElementList.size();
    }
}
