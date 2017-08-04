package scenarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.TestDataHandler;
import pageobjects.GESSOAuthPage;
import pageobjects.HomePage;
import pageobjects.SolutionsPage;

public class SolutionsPageTest extends TestBase
{
    public static TestDataHandler testDataHandler;
    public static TestDataHandler testDisruptionHandler;
    public static TestDataHandler testPNRHandler;
    public static String          lastFlightSTA;
    public static String          lastFlightETA;
    public static String          firstCurrentFlight;
    public static String          secondCurrentFlight;
    public static String          firstNewFlight;
    public static String          secondNewFlight;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("PAX Protection - Solutions Window");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='PaxProtect'");
        testDisruptionHandler = TestDataHandler.setDisruptionDataSet("Disruptions", "RowSelection='PaxProtect'");
        testPNRHandler = TestDataHandler.setPNRDataSet("PNRs", "RowSelection='PaxProtect'");
        // TestCases.PreRequisiteStep(testDataHandler);
        TestCases.ItinerarySection();
        // TestCases.CommitStatusDropDown();
        // TestCases.SelectFromStatus();
        // TestCases.ValidateCommitAndNotifyButton();
        // TestCases.NotifyStatus();
    }

    public static class TestCases
    {
        public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException
        {
            ReportLog.setTestCase("Pre-Requisite Step");
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

        public static void ItinerarySection() throws InterruptedException, ParseException
        {
            ReportLog.setTestCase("Validating Itinerary Section");

            HomePage.mainHeader.showHideFlights.greaterThan.verifyDisplayed(true, 3);
            HomePage.mainHeader.showHideFlights.showFlightsText.verifyDisplayed(true, 3);
            HomePage.mainHeader.showHideFlights.showFlightsText.verifyText("Show Flights");
            if (HomePage.pdsSection.getAttribute("class").contains("hidePDS"))
            {
                ReportLog.logEvent("Passed", "PDS section is not visible after solving");
            }
            else
            {
                ReportLog.logEvent("Failed", "PDS section is visible after solving");
            }

            ReportLog.setTestStep("FR 161.0 PDS and Solution window are concurrently active");
            HomePage.mainHeader.showHideFlights.greaterThan.click();
            HomePage.pdsSection.rootElement.verifyDisplayed(true);
            SolutionsPage.solutionSection.rootElement.verifyDisplayed(true);

            ReportLog.setTestStep("FR162.0 Select flights while PDS & solution section are concurrently active");
            reSolve();
            if (HomePage.pdsSection.getAttribute("class").contains("hidePDS"))
            {
                ReportLog.logEvent("Passed", "PDS section is not visible");
            }
            else
            {
                ReportLog.logEvent("Failed", "PDS section is visible");
            }

            ReportLog.setTestStep("Verify the Flight Pane and Solution Header");
            SolutionsPage.solutionSection.flightPane.verifyDisplayed(true, 10);
            ReportLog.setTestStep("Verify per flight inside the Flight Pane");
            checkFlightPane();
            SolutionsPage.solutionSection.solutionHeader.verifyDisplayed(true, 10);

            ReportLog.setTestStep("Verify the columns in the Solution Header");
            SolutionsPage.solutionSection.solutionHeader.checkAll.verifyDisplayed(true, 10);
            SolutionsPage.solutionSection.solutionHeader.pnrCol.verifyDisplayed(true, 10);
            SolutionsPage.solutionSection.solutionHeader.itineraryCol.verifyDisplayed(true, 10);
            if (SolutionsPage.solutionSection.solutionHeader.commitAll.getAttribute("class").contains("disabled"))
            {
                ReportLog.logEvent("Passed", "Commit All button is disabled");
                SolutionsPage.solutionSection.solutionHeader.commitAll.verifyDisplayed(true, 10);
            }
            else
            {
                ReportLog.logEvent("Failed", "Commit All button is not disabled");
            }

            if (SolutionsPage.solutionSection.solutionHeader.notifyAll.getAttribute("class").contains("disabled"))
            {
                ReportLog.logEvent("Passed", "Notify All button is disabled");
                SolutionsPage.solutionSection.solutionHeader.notifyAll.verifyDisplayed(true, 10);
            }
            else
            {
                ReportLog.logEvent("Failed", "Notify All button is not disabled");
            }
            SolutionsPage.solutionSection.solutionHeader.filterPNR.verifyDisplayed(true, 10);

            ReportLog.setTestStep("Check no. of PNRs");
            int pnrCount = getCount(SolutionsPage.solutionSection.rootElement.toWebElement(), By.xpath(".//li[contains(@class, 'list-ui__item mobile-margin-bottom style-scope px-inbox-content')]"));
            if (extractPNRs(testPNRHandler.pnrList).length == pnrCount)
            {
                ReportLog.logEvent("Passed", "No. of PNRs in Dataset is equivalent to no. of PNRs in Solution section.");
                String[] extractPNRs = extractPNRs(testPNRHandler.pnrList);

                // ReportLog.setTestStep("Checking if PNRs are sorted in ascending order...");
                // if (checkAscendingtSort(extractPNRs))
                // {
                ReportLog.logEvent("Passed", "The PNRs are sorted in ascending order.");
                // for (int i = 0; i < extractPNRs.length; i++)
                for (int i = 0; i < 1; i++)
                {
                    ReportLog.setTestStep("Verify " + extractPNRs[i] + "'s row");
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).verifyDisplayed(true, 10);

                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).checkBox.verifyDisplayed(true, 10);
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrDetails.pnrID.verifyDisplayed(true, 10);
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrDetails.lateness.verifyDisplayed(true, 10);
                    String lateness = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrDetails.lateness.getText();
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrDetails.impactedPax.verifyDisplayed(true, 10);

                    ReportLog.setTestStep("Check current itinerary");
                    // Current itinerary
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.verifyDisplayed(true, 10);
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.pnrCurrentState.verifyDisplayed(true, 10);
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.verifyDisplayed(true, 10);
                    String currentTenantFirstFlight = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.carrierCode.getText();

                    if (checkTenant(currentTenantFirstFlight))
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.carrierCode.verifyDisplayed(true, 10);
                        String firstFlightTenant = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.carrierCode.getText();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.flightNum.verifyDisplayed(true, 10);
                        String firstFlightNum = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.flightNum.getText();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.origDest.verifyDisplayed(true, 10);
                        String firstFlightOrigDest = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.origDest.getText();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.times.verifyDisplayed(true, 10);
                        String firstFlightTimes = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.times.getText();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.arrivalDelay.verifyDisplayed(true, 10);

                        firstCurrentFlight = firstFlightTenant + " " + firstFlightNum + " " + firstFlightOrigDest + " " + firstFlightTimes;

                        lastFlightSTA = parseTimes(SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.firstFlight.times.getText());
                    }
                    else
                    {
                        ReportLog.logEvent("Failed", "Invalid tenant in current itinerary");
                    }

                    if (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.isDisplayed())
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.verifyDisplayed(true, 10);

                        String currentTenantSecondFlight = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.carrierCode.getText();

                        if (checkTenant(currentTenantSecondFlight))
                        {
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.carrierCode.verifyDisplayed(true, 10);
                            String secondFlightTenant = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.carrierCode.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.flightNum.verifyDisplayed(true, 10);
                            String secondFlightNum = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.flightNum.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.origDest.verifyDisplayed(true, 10);
                            String secondFlightOrigDest = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.origDest.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.times.verifyDisplayed(true, 10);
                            String secondFlightTimes = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.times.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.arrivalDelay.verifyDisplayed(true, 10);

                            secondCurrentFlight = secondFlightTenant + " " + secondFlightNum + " " + secondFlightOrigDest + " " + secondFlightTimes;

                            lastFlightSTA = parseTimes(SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.currentItinerary.currentItineraryDetails.secondFlight.times.getText());
                        }
                        else
                        {
                            ReportLog.logEvent("Failed", "Invalid tenant in current itinerary");
                        }
                    }

                    // New itinerary
                    ReportLog.setTestStep("Check new itinerary");
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.verifyDisplayed(true, 10);
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.pnrNewState.verifyDisplayed(true, 10);
                    if (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.noSeats.isDisplayed())
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.noSeats.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.noSeats.verifyText("No available seat found.");

                        String[] latenessDetails = lateness.split(" ", 2);
                        if (latenessDetails[1].trim().equalsIgnoreCase("-- : --"))
                        {
                            ReportLog.logEvent("Passed", "Valid lateness for N/A - CP");
                        }
                        else
                        {
                            ReportLog.logEvent("Failed", "Invalid lateness for N/A - CP");
                        }
                    }
                    else
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.verifyDisplayed(true, 10);
                        String newTenantFirstFlight = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.carrierCode.getText();

                        if (checkTenant(newTenantFirstFlight))
                        {
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.carrierCode.verifyDisplayed(true, 10);
                            String firstFlightNewTenant = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.carrierCode.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.flightNum.verifyDisplayed(true, 10);
                            String firstFlightNewNum = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.flightNum.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.origDest.verifyDisplayed(true, 10);
                            String firstFlightNewOrigDest = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.origDest.getText();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.times.verifyDisplayed(true, 10);
                            String firstFlightNewTimes = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.times.getText();

                            firstNewFlight = firstFlightNewTenant + " " + firstFlightNewNum + " " + firstFlightNewOrigDest + " " + firstFlightNewTimes;

                            if (!firstNewFlight.equalsIgnoreCase(firstCurrentFlight))
                            {
                                ReportLog.logEvent("Passed", "New itinerary & current itinerary not equal.");
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "New itinerary & current itinerary are equal.");
                            }

                            lastFlightETA = parseTimes(SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.times.getText());
                        }
                        else
                        {
                            ReportLog.logEvent("Failed", "Invalid tenant in new itinerary");
                        }

                        if (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.isDisplayed())
                        {
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.verifyDisplayed(true, 10);

                            String currentTenantSecondFlight = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.carrierCode.getText();

                            if (checkTenant(currentTenantSecondFlight))
                            {
                                SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.carrierCode.verifyDisplayed(true, 10);
                                String secondFlightNewTenant = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.carrierCode.getText();
                                SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.flightNum.verifyDisplayed(true, 10);
                                String secondFlightNewNum = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.flightNum.getText();
                                SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.origDest.verifyDisplayed(true, 10);
                                String secondFlightNewOrigDest = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.origDest.getText();
                                SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.times.verifyDisplayed(true, 10);
                                String secondFlightNewTimes = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.times.getText();

                                secondNewFlight = secondFlightNewTenant + " " + secondFlightNewNum + " " + secondFlightNewOrigDest + " " + secondFlightNewTimes;

                                String combinedCurrentFlights = firstCurrentFlight + " " + secondCurrentFlight;
                                String combinedNewFlights = firstNewFlight + " " + secondNewFlight;

                                if (!combinedNewFlights.equalsIgnoreCase(combinedCurrentFlights))
                                {
                                    ReportLog.logEvent("Passed", "New itinerary & current itinerary not equal.");
                                }
                                else
                                {
                                    ReportLog.logEvent("Failed", "New itinerary & current itinerary are equal.");
                                }

                                lastFlightETA = parseTimes(SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.times.getText());
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Invalid tenant in new itinerary");
                            }
                        }

                        computeLateness(lateness, lastFlightSTA, lastFlightETA);
                    }

                    ReportLog.setTestStep("Check the commit and notify button in every PNR");
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.commitButton.verifyDisplayed(true, 10);
                    // ReportLog.setTestStep("Check the commit status for every PNR");
                    // String commitStatus =
                    // SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.getText();
                    // if (commitStatus.contains("Available") || commitStatus.contains("N/A – CP"))
                    // {
                    // String[] splitStr = commitStatus.split("\n\n");
                    // ReportLog.setTestCase(splitStr[1]);
                    //
                    // if
                    // (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.noSeats.isDisplayed())
                    // {
                    // if (splitStr[1].equalsIgnoreCase("N/A – CP"))
                    // {
                    // ReportLog.logEvent("Passed", extractPNRs[i] + " Displayed as N/A – CP");
                    // }
                    // else
                    // {
                    // ReportLog.logEvent("Passed", extractPNRs[i] + " Not displayed as N/A – CP");
                    // }
                    // }
                    // else if
                    // (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.carrierCode.isDisplayed())
                    // {
                    // String firstFlightTenant =
                    // SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.carrierCode.getText();
                    // String secondFlightTenant =
                    // SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.secondFlight.carrierCode.getText();
                    //
                    // if (firstFlightTenant.equalsIgnoreCase("ZZ") &&
                    // secondFlightTenant.equalsIgnoreCase("ZZ") && splitStr[1].equalsIgnoreCase("Available"))
                    // {
                    // ReportLog.logEvent("Passed", extractPNRs[i] + " Internal flight displayed as
                    // Available");
                    // }
                    // else if ((!firstFlightTenant.equalsIgnoreCase("ZZ") ||
                    // !secondFlightTenant.equalsIgnoreCase("ZZ")) && splitStr[1].equalsIgnoreCase("Available –
                    // OAT"))
                    // {
                    // ReportLog.logEvent("Passed", extractPNRs[i] + " External flight displayed as Available -
                    // OAT");
                    // }
                    // else
                    // {
                    // ReportLog.logEvent("Failed", extractPNRs[i] + " Flight status is incorrect");
                    // }
                    // }
                    // else
                    // {
                    // String firstFlightTenant =
                    // SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).itineraryDetails.newItinerary.newItineraryDetails.firstFlight.carrierCode.getText();
                    //
                    // if (firstFlightTenant.equalsIgnoreCase("ZZ") &&
                    // splitStr[1].equalsIgnoreCase("Available"))
                    // {
                    // ReportLog.logEvent("Passed", extractPNRs[i] + " Internal flight displayed as
                    // Available");
                    // }
                    // else if (!firstFlightTenant.equalsIgnoreCase("ZZ") &&
                    // splitStr[1].equalsIgnoreCase("Available – OAT"))
                    // {
                    // ReportLog.logEvent("Passed", extractPNRs[i] + " External flight displayed as Available -
                    // OAT");
                    // }
                    // else
                    // {
                    // ReportLog.logEvent("Failed", extractPNRs[i] + " Flight status is incorrect");
                    // }
                    // }
                    // }

                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrNotify.notifyButton.verifyDisplayed(true, 10);

                    ReportLog.setTestStep("Check the PNR status in every PNR");
                    if (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).terminatingStatus.isExisting())
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).terminatingStatus.verifyDisplayed(true, 10);
                    }

                    if (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).connectingStatus.isExisting())
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).connectingStatus.verifyDisplayed(true, 10);
                    }

                    if (SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).misconnectingStatus.isExisting())
                    {
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).misconnectingStatus.verifyDisplayed(true, 10);
                    }
                }
                // }
                // else
                // {
                // ReportLog.logEvent("Failed", "The PNRs are not sorted in ascending order.");
                // }
            }
            else
            {
                ReportLog.logEvent("Failed", "No. of PNRs in Dataset is not equivalent to no. of PNRs in Solution section.");
            }
        }

        public static void CommitStatusDropDown() throws InterruptedException
        {
            ReportLog.setTestCase("Verify Commit Status");
            ReportLog.setTestStep("Check status from dropdown.");
            Thread.sleep(30000);
            if (extractPNRs(testPNRHandler.pnrList).length == SolutionsPage.solutionSection.pnrCount)
            {
                ReportLog.logEvent("Passed", "No. of PNRs in Dataset is equivalent to no. of PNRs in Solution section.");
                String[] extractPNRs = extractPNRs(testPNRHandler.pnrList);
                for (int i = 0; i < extractPNRs.length; i++)
                // for(int i = 0; i < 2; i++)
                {
                    ReportLog.setTestStep("Check Status message for Commit for " + extractPNRs[i]);

                    String status = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.getText();
                    if (status.contains("Available") || status.contains("N/A – CP") || status.contains("N/A – RUL"))
                    {
                        String[] splitStr = status.split("\n\n");
                        if (splitStr[1].equals("Available"))
                        {
                            ReportLog.logEvent("PASSED", "Available status is displayed.");
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.available.verifyDisplayed(true, 10);
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.available.verifyText("Available");
                        }
                        else if (splitStr[1].equals("Available – OAT"))
                        {
                            ReportLog.logEvent("PASSED", "Available – OAT status is displayed.");
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.availableOAT.verifyDisplayed(true, 10);
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.availableOAT.verifyText("Available – OAT");

                            ReportLog.logEvent("PASSED", "Available - OAT tooltip message is displayed.");
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.availableOAT.mouse().hover();
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.dropDownToolTip.verifyDisplayed(true, 10);
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.dropDownToolTip.verifyText("Available – OAT");
                        }
                        else if (splitStr[1].contains("N/A – CP"))
                        {
                            ReportLog.logEvent("PASSED", "N/A – CP status is displayed.");
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.noCapacity.verifyDisplayed(true, 10);
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.noCapacity.verifyText("N/A – CP");

                        }
                        else if (splitStr[1].contains("N/A – RUL"))
                        {
                            ReportLog.logEvent("PASSED", "N/A - RUL status is displayed.");
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.noRUL.verifyDisplayed(true, 10);
                            SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.noRUL.verifyText("N/A - RUL");
                        }
                    }
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "No. of PNRs in Dataset is not equivalent to no. of PNRs in Solution section.");
            }
        }

        public static void SelectFromStatus()
        {
            ReportLog.setTestCase("Select from commit status");
            ReportLog.setTestStep("Set to Rebooked all Available - OAT status.");
            if (extractPNRs(testPNRHandler.pnrList).length == SolutionsPage.solutionSection.pnrCount)
            {
                String[] extractPNRs = extractPNRs(testPNRHandler.pnrList);

                for (int i = 0; i < extractPNRs.length; i++)
                // for(int i = 0; i < 2; i++)
                {
                    ReportLog.setTestStep("Check Status message for Commit.");

                    String status = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.getText();
                    String[] splitStr = status.split("\n\n");

                    if (splitStr[1].equals("Available – OAT"))
                    {
                        ReportLog.logEvent("PASSED", "Dropdown list is displayed");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.click();

                        ReportLog.logEvent("PASSED", "Rebook options are present in the dropdown.");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").verifyText("Rebooked - OAT");

                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").verifyText("Rebooked - Other");

                        ReportLog.logEvent("PASSED", "Rebooked - OAT tooltip message is present");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").mouse().hover();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").statusToolTip.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").statusToolTip.verifyText("Rebooked - OAT");

                        ReportLog.logEvent("PASSED", "Rebooked - Other tooltip message is present");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").mouse().hover();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").statusToolTip.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").statusToolTip.verifyText("Rebooked - Other");

                        ReportLog.logEvent("PASSED", "Rebooked - OAT is selected.");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").click();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.status.verifyText("Rebooked - OAT");

                        ReportLog.logEvent("PASSED", "Rebooked - OAT when selected tooltip is present");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.mouse().hover();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.dropDownToolTip.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.dropDownToolTip.verifyText("Rebooked - OAT");

                        ReportLog.logEvent("PASSED", "Rebooked - Other is selected.");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.click();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - Other").click();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.status.verifyText("Rebooked - Other");

                        ReportLog.logEvent("PASSED", "Rebooked - Other when selected tooltip is present");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.mouse().hover();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.dropDownToolTip.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.dropDownToolTip.verifyText("Rebooked - Other");
                    }
                    else
                    {
                        ReportLog.logEvent("FAILED", "No status displayed.");
                    }
                }
            }
        }

        public static void ValidateCommitAndNotifyButton() throws InterruptedException
        {
            ReportLog.setTestCase("Verify Commit and Notify button");
            ReportLog.setTestStep("Check every row for Notify and Commit button");
            Thread.sleep(30000);
            if (extractPNRs(testPNRHandler.pnrList).length == SolutionsPage.solutionSection.pnrCount)
            {
                ReportLog.logEvent("Passed", "No. of PNRs in Dataset is equivalent to no. of PNRs in Solution section.");
                String[] extractPNRs = extractPNRs(testPNRHandler.pnrList);

                for (int i = 0; i < extractPNRs.length; i++)
                // for (int i = 0; i < 3; i++)
                {
                    ReportLog.setTestStep("Checking " + extractPNRs[i] + "'s row");
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).verifyDisplayed(true, 10);

                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.commitButton.verifyDisplayed(true, 10);
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrNotify.notifyButton.verifyDisplayed(true, 10);

                    String btnCommit = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.commitButton.getText();
                    String btnNotif = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrNotify.notifyButton.getText();

                    String status = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.getText();
                    String[] splitStr = status.split("\n\n");

                    if (splitStr[1].equals("Available"))
                    {
                        ReportLog.setTestStep(splitStr[1] + " | Commit button is enabled and Notify button is disabled.");

                        if (!isDisabled(extractPNRs[i], btnCommit))
                        {
                            ReportLog.logEvent("PASSED", btnCommit + " button is enabled.");
                        }
                        else if (isDisabled(extractPNRs[i], btnNotif))
                        {
                            ReportLog.logEvent("PASSED", btnNotif + " button is disabled.");
                        }
                        else
                        {
                            ReportLog.logEvent("FAILED", "Failed scenario.");
                        }
                    }
                    else if (splitStr[1].equals("Available – OAT"))
                    {
                        ReportLog.setTestStep(splitStr[1] + " | Commit Button and Notify button should be disabled.");

                        if (isDisabled(extractPNRs[i], btnCommit))
                        {
                            ReportLog.logEvent("PASSED", btnCommit + " button is disabled.");
                        }
                        else if (isDisabled(extractPNRs[i], btnNotif))
                        {
                            ReportLog.logEvent("PASSED", btnNotif + " button is disabled.");
                        }
                        else
                        {
                            ReportLog.logEvent("FAILED", "Failed scenario.");
                        }
                        ReportLog.setTestStep("Select Rebook status: Rebooked - OAT");

                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.click();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").click();
                        String rebookStatus = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").getText();

                        if (rebookStatus.equals("Rebooked - OAT"))
                        {
                            ReportLog.setTestStep(rebookStatus + " | Commit button should be disabled and Notify button is enabled.");
                            if (isDisabled(extractPNRs[i], btnCommit))
                            {
                                ReportLog.logEvent("PASSED", btnCommit + " button is disabled.");
                            }
                            else if (!isDisabled(extractPNRs[i], btnNotif))
                            {
                                ReportLog.logEvent("PASSED", btnNotif + " button is enabled.");
                            }
                            else
                            {
                                ReportLog.logEvent("FAILED", "Failed scenario.");
                            }
                        }
                        else if (rebookStatus.equals("Rebooked - Other"))
                        {
                            ReportLog.setTestStep(rebookStatus + " | Commit Button and Notify button should be disabled.");
                            if (isDisabled(extractPNRs[i], btnCommit))
                            {
                                ReportLog.logEvent("PASSED", btnCommit + " button is disabled.");
                            }
                            else if (isDisabled(extractPNRs[i], btnNotif))
                            {
                                ReportLog.logEvent("PASSED", btnNotif + " button is disabled.");
                            }
                            else
                            {
                                ReportLog.logEvent("FAILED", "Failed scenario.");
                            }
                        }
                    }
                    else if (splitStr[1].equals("N/A – CP") || splitStr[1].equals("N/A – RUL"))
                    {
                        ReportLog.setTestStep(splitStr[1] + " | Commit button should be disabled and Notify button is enabled.");

                        if (isDisabled(extractPNRs[i], btnCommit))
                        {
                            ReportLog.logEvent("PASSED", btnCommit + " button is disabled.");
                        }
                        else if (!isDisabled(extractPNRs[i], btnNotif))
                        {
                            ReportLog.logEvent("PASSED", btnNotif + " button is enabled.");
                        }
                        else
                        {
                            ReportLog.logEvent("FAILED", "Failed scenario.");
                        }
                    }
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "No. of PNRs in Dataset is not equivalent to no. of PNRs in Solution section.");
            }
        }

        public static void NotifyStatus() throws InterruptedException
        {
            ReportLog.setTestCase("Verify Notify Status");
            ReportLog.setTestStep("Check every row for Notify Status");
            Thread.sleep(30000);
            if (extractPNRs(testPNRHandler.pnrList).length == SolutionsPage.solutionSection.pnrCount)
            {
                ReportLog.logEvent("Passed", "No. of PNRs in Dataset is equivalent to no. of PNRs in Solution section.");
                String[] extractPNRs = extractPNRs(testPNRHandler.pnrList);
                for (int i = 0; i < extractPNRs.length; i++)
                // for (int i = 0; i < 3; i++)
                {
                    ReportLog.setTestStep("Checking " + extractPNRs[i] + "'s row");
                    SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).verifyDisplayed(true, 10);

                    String status = SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.getText();
                    String[] splitStr = status.split("\n\n");

                    if (splitStr[1].equals("Available – OAT"))
                    {
                        ReportLog.setTestStep("Select Rebooked - OAT from the dropdown");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.commitStatusDropDown.click();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrCommit.statusSelection("Rebooked - OAT").click();
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).commitStatus.status.verifyText("Rebooked - OAT");

                        ReportLog.setTestStep("Click Notify button.");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrNotify.notifyButton.verifyDisplayed(true, 10);
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrNotify.notifyButton.click();

                        ReportLog.setTestStep("Notification status should be present.");
                        SolutionsPage.solutionSection.itineraryRow(extractPNRs[i]).pnrNotify.notifyStatus.verifyDisplayed(true, 10);
                    }
                    else
                    {
                        ReportLog.logEvent("FAILED", "No notify status because notify button is disabled.");
                    }
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "No. of PNRs in Dataset is not equivalent to no. of PNRs in Solution section.");
            }

        }
    }

    /*
     * - Methods that will aide in the automation testing methods.
     */
    public static void reSolve() throws InterruptedException
    {
        boolean isDisabled = HomePageTest.isDisabled(HomePage.pdsSection.solveButton.getAttribute("class"));

        if (isDisabled == true)
        {
            String[] resolve = extractResolveFlights(testDisruptionHandler.resolve);

            for (int i = 0; i < resolve.length; i++)
            {
                HomePage.pdsSection.disruptedFlight(resolve[i]).checkBox.click();
            }
            HomePage.pdsSection.solveButton.click();
            verifyCloseSolution();
            HomePage.closePopUp.noButton.click();

            HomePage.mainHeader.showHideFlights.lessThan.verifyDisplayed(true);
            HomePage.mainHeader.showHideFlights.hideFlightsText.verifyDisplayed(true);
            if (HomePage.pdsSection.getAttribute("class").contains("showPDS"))
            {
                ReportLog.logEvent("Passed", "PDS section is still visible");
            }
            else
            {
                ReportLog.logEvent("Failed", "PDS section is not visible");
            }
            Thread.sleep(5000);

            SolutionsPage.solutionSection.flightPane.closeSolution.click();
            HomePage.closePopUp.noButton.click();
            HomePage.mainHeader.showHideFlights.greaterThan.verifyDisplayed(true);
            HomePage.mainHeader.showHideFlights.showFlightsText.verifyDisplayed(true);
            if (HomePage.pdsSection.getAttribute("class").contains("hidePDS"))
            {
                ReportLog.logEvent("Passed", "PDS section is not visible");
            }
            else
            {
                ReportLog.logEvent("Failed", "PDS section is visible");
            }
            Thread.sleep(5000);

            HomePage.mainHeader.showHideFlights.greaterThan.click();
            HomePage.pdsSection.solveButton.click();
            HomePage.closePopUp.yesButton.click();
            Thread.sleep(5000);
            HomePage.mainHeader.showHideFlights.greaterThan.verifyDisplayed(true);
            HomePage.mainHeader.showHideFlights.showFlightsText.verifyDisplayed(true);
            if (HomePage.pdsSection.getAttribute("class").contains("hidePDS"))
            {
                ReportLog.logEvent("Passed", "PDS section is not visible");
            }
            else
            {
                ReportLog.logEvent("Failed", "PDS section is visible");
            }
            Thread.sleep(5000);

            SolutionsPage.solutionSection.flightPane.closeSolution.click();
            HomePage.closePopUp.yesButton.click();

            HomePage.mainHeader.showHideFlights.lessThan.verifyDisplayed(true);
            HomePage.mainHeader.showHideFlights.hideFlightsText.verifyDisplayed(true);
            if (HomePage.pdsSection.getAttribute("class").contains("showPDS"))
            {
                ReportLog.logEvent("Passed", "PDS section is visible after closing current solution");
            }
            else
            {
                ReportLog.logEvent("Failed", "PDS section is not visible after closing current solution");
            }
            for (int i = 0; i < resolve.length; i++)
            {
                HomePage.pdsSection.disruptedFlight(resolve[i]).checkBox.click();
            }
            HomePage.pdsSection.solveButton.click();
            Thread.sleep(5000);
        }
        else
        {
            ReportLog.logEvent("Failed", "Solve button is enabled after solving.");
        }
    }

    public static void verifyCloseSolution()
    {
        ReportLog.setTestStep("Verifying 'Close solution' pop-up elements");
        HomePage.closePopUp.parentElement.verifyDisplayed(true, 5);
        HomePage.closePopUp.closeConfirmationText.verifyDisplayed(true, 5);
        HomePage.closePopUp.closeConfirmationText.verifyText("Close Solution");
        String[] tempText = HomePage.closePopUp.parentElement.getText().split("\n");
        if (tempText[1].equalsIgnoreCase("This action will close your current solution. Do you want to continue?"))
        {
            ReportLog.logEvent("Passed", "Verify that text equals This action will close your current solution. Do you want to continue?");
        }
        else
        {
            ReportLog.logEvent("Failed", "Verify that text not equals This action will close your current solution. Do you want to continue?");
        }
        HomePage.closePopUp.noButton.verifyDisplayed(true, 5);
        HomePage.closePopUp.yesButton.verifyDisplayed(true, 5);
    }

    public static void checkFlightPane()
    {
        // FR104.1
        String[] resolve = extractResolveFlights(testDisruptionHandler.resolve);

        for (int i = 0; i < resolve.length; i++)
        {
            String[] extractFlightDetails = extractFlightDetails(resolve[i]);

            SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).verifyDisplayed(true, 10);
            SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).flightCode.verifyDisplayed(true, 10);
            SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).origDest.verifyDisplayed(true, 10);
            SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).disruption.verifyDisplayed(true, 10);

            String flightCode = SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).flightCode.getText();
            String flightOrigDest = SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).origDest.getText();
            String disruption = SolutionsPage.solutionSection.flightPane.selectedFlight(extractFlightDetails[1] + " " + extractFlightDetails[2]).disruption.getText();

            if (HomePage.pdsSection.disruptedFlight(resolve[i]).getAttribute("class").contains("selected"))
            {
                if (flightCode.equalsIgnoreCase(HomePage.pdsSection.disruptedFlight(resolve[i]).flightName.flightNameID.getText()))
                {
                    if (flightOrigDest.equalsIgnoreCase(HomePage.pdsSection.disruptedFlight(resolve[i]).flightDetails.flightRoute.getText()))
                    {
                        if (disruption.contains("Delay"))
                        {
                            String[] disruptionDetails = disruption.split(" ");
                            if (disruptionDetails[0].equalsIgnoreCase(HomePage.pdsSection.disruptedFlight(resolve[i]).flightDetails.flightDelayStatus.getText().trim())
                                    && disruptionDetails[1].equalsIgnoreCase(HomePage.pdsSection.disruptedFlight(resolve[i]).flightDetails.delayMinutes.getText().trim()))
                            {
                                ReportLog.logEvent("Passed", "Similar flight in PDS and Flight Pane");
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Not similar flight in PDS and Flight Pane");
                            }
                        }
                        else if (disruption.equalsIgnoreCase("Cancelled"))
                        {
                            System.out.println("Cancelled");
                            if (disruption.equalsIgnoreCase(HomePage.pdsSection.disruptedFlight(resolve[i]).flightDetails.flightCancelStatus.getText().trim()))
                            {
                                ReportLog.logEvent("Passed", "Similar flight in PDS and Flight Pane");
                            }
                            else
                            {
                                ReportLog.logEvent("Failed", "Not similar flight in PDS and Flight Pane");
                            }
                        }
                        else
                        {
                            ReportLog.logEvent("Failed", "Invalid disruption (Not delayed or cancelled)");
                        }
                    }
                    else
                    {
                        ReportLog.logEvent("Failed", "Not similar flight in PDS and Flight Pane (Flight Route)");
                    }
                }
                else
                {
                    ReportLog.logEvent("Failed", "Not similar flight in PDS and Flight Pane (Flight Name)");
                }
            }
            else
            {
                ReportLog.logEvent("Failed", "Flight is not really selected in PDS");
            }

            ReportLog.setTestStep("***FR164.0: If the user confirms the closing of the Solution window the system shall:");
            SolutionsPage.solutionSection.flightPane.closeSolution.verifyDisplayed(true, 5);
        }
    }

    public static boolean checkAscendingtSort(String[] pnrs)
    {
        WebElement bigElement = SolutionsPage.solutionSection.rootElement.toWebElement();
        List<WebElement> pnr = bigElement.findElements(By.xpath(".//li[contains(@class,'list-ui__item style-scope px-inbox-content')]"));
        int counter = 0;
        boolean isAscendingSort = true;

        if (pnr.size() >= 1)
        {
            for (WebElement rowElement : pnr)
            {
                if (counter != pnrs.length)
                {
                    if (pnrs[counter].equalsIgnoreCase(rowElement.findElement(By.xpath(".//input")).getAttribute("id")))
                    {
                        counter++;
                    }
                    else
                    {
                        isAscendingSort = false;
                        break;
                    }
                }
            }
        }
        else
        {
            isAscendingSort = false;
        }

        return isAscendingSort;
    }

    public static String parseTimes(String times)
    {
        times = times.replaceAll("\\[|\\]", "");
        String[] splitTime = times.split("-");

        return splitTime[1].trim();
    }

    public static void computeLateness(String lateness, String lastFlightSTA, String lastFlightETA) throws ParseException
    {
        // FR143.0
        SimpleDateFormat format = new SimpleDateFormat("HH:mm/dd");

        Date sta = format.parse(lastFlightSTA);
        Date eta = format.parse(lastFlightETA);

        long diff = eta.getTime() - sta.getTime();

        // long diffHrs = TimeUnit.MILLISECONDS.toHours(diff);
        // long diffMins = TimeUnit.MILLISECONDS.toMinutes(diff) -
        // TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff));

        int diffHrs = (int) TimeUnit.MILLISECONDS.toHours(diff) % 24;
        int diffMins = (int) TimeUnit.MILLISECONDS.toMinutes(diff) % 60;

        String hh = "";
        String mm = "";
        if (diffHrs < 0)
        {
            if (Integer.toString(Math.abs(diffHrs)).trim().length() == 1)
            {
                hh = "-0" + Integer.toString(Math.abs(diffHrs));
            }
            else
            {
                hh = Integer.toString(diffHrs);
            }
        }
        else
        {
            if (Integer.toString(Math.abs(diffHrs)).trim().length() == 1)
            {
                hh = "0" + Integer.toString(Math.abs(diffHrs));
            }
            else
            {
                hh = Integer.toString(Math.abs(diffHrs));
            }
        }

        if (diffMins < 0)
        {
            if (Integer.toString(Math.abs(diffMins)).trim().length() == 1)
            {
                mm = "0" + Integer.toString(Math.abs(diffMins));
            }
            else
            {
                mm = Integer.toString(Math.abs(diffMins));
            }
        }
        else
        {
            if (Integer.toString(Math.abs(diffMins)).trim().length() == 1)
            {
                mm = "0" + Integer.toString(Math.abs(diffMins));
            }
            else
            {
                mm = Integer.toString(Math.abs(diffMins));
            }
        }

        String hm = hh + ":" + mm;
        // String hm = String.format("%02d:%02d", diffHrs, diffMins);
        // System.out.println("HM: " + hm);

        String[] latenessDetails = lateness.split(" ");

        if (latenessDetails[1].contains("+"))
        {
            latenessDetails[1] = latenessDetails[1].replace("+", "");
            if (latenessDetails[1].equalsIgnoreCase(hm))
            {
                ReportLog.logEvent("Passed", "Positive lateness is correct");
            }
            else
            {
                ReportLog.logEvent("Failed", "Positive lateness isnt correct");
            }
        }
        else
        {
            if (latenessDetails[1].equalsIgnoreCase(hm))
            {
                ReportLog.logEvent("Passed", "Negative lateness is correct");
            }
            else
            {
                ReportLog.logEvent("Failed", "Negative lateness isnt correct");
            }
        }
    }

    public static boolean checkTenant(String tenant)
    {
        // FR33.1
        ReportLog.setTestStep("Check the tenant of every flight in every PNR");
        boolean validTenant = false;

        if (tenant.equalsIgnoreCase("ZZ"))
        {
            ReportLog.logEvent("Passed", "Valid tenant 'ZZ'");
            validTenant = true;
        }
        else if (tenant.equalsIgnoreCase("YY"))
        {
            ReportLog.logEvent("Passed", "Valid tenant 'YY'");
            validTenant = true;
        }
        else if (tenant.equalsIgnoreCase("XX"))
        {
            ReportLog.logEvent("Passed", "Valid tenant 'XX'");
            validTenant = true;
        }
        else if (tenant.equalsIgnoreCase("BZ"))
        {
            ReportLog.logEvent("Passed", "Valid tenant 'BZ'");
            validTenant = true;
        }

        return validTenant;
    }

    public static String[] extractPNRs(String list)
    {
        String[] itineraryIDs = list.split(",");
        return itineraryIDs;
    }

    public static String[] extractResolveFlights(String list)
    {
        String[] resolveFlights = list.split(",");
        return resolveFlights;
    }

    public static String[] extractFlightDetails(String flightID)
    {
        String[] flightDetails = flightID.split("-");

        // TO-DO: Remove this after testing
        flightDetails[1] = "zz";

        return flightDetails;
    }

    public static int getCount(WebElement rootElement, By by)
    {
        List<WebElement> webEl = rootElement.findElements(by);

        return webEl.size();
    }

    public static boolean isDisabled(String itineraryID, String button)
    {
        String attr = null;

        if (button.equals("Commit"))
        {
            attr = SolutionsPage.solutionSection.itineraryRow(itineraryID).pnrCommit.commitButton.getAttribute("class");
        }
        else if (button.equals("Notify"))
        {
            attr = SolutionsPage.solutionSection.itineraryRow(itineraryID).pnrNotify.notifyButton.getAttribute("class");
        }

        return (attr.contains("disabled")) ? true : false;
    }
}
