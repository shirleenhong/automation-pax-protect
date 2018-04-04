package Sprint3_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pageobjects.GESSOAuthPage;
import pageobjects.PDSGridViewPage;
import pageobjects.PDSListViewPage;
import pageobjects.PaxImpactPage;
import pageobjects.SolutionScreenPage;
import pageobjects.FilteringWindowPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class US152680_FilteringInPDS extends TestBase {

    public static TestDataHandler testDataHandler;
    public static BackendAPI backendAPI = new BackendAPI();

    public static List<String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray  jsonArray;
    public static String flightID;


    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser2'");
        US152680_FilteringInPDS.TestCases.PreRequisiteStep();
        US152680_FilteringInPDS.TestCases.Step1();
//        US152680_FilteringInPDS.TestCases.Step2();
//        US152680_FilteringInPDS.TestCases.Step3();
        US152680_FilteringInPDS.TestCases.Step4();

    }

    public static class TestCases {
        public static void PreRequisiteStep() throws InterruptedException, MalformedURLException {
            ReportLog.setTestCase("PAX Protection HomePage");
            ReportLog.setTestStep("Go to this URL: https://pax-protect-ui-shell.run.aws-usw02-pr.ice.predix.io ");
            WebControl.clearData();
            WebControl.open(testDataHandler.url);


            Thread.sleep(1000);
            if (GESSOAuthPage.authInfo.gESSOLabel.isDisplayed()) {
                GESSOAuthPage.authInfo.sSOIDInput.typeKeys(testDataHandler.username);
                GESSOAuthPage.authInfo.passWordInput.typeKeys(testDataHandler.password);
                GESSOAuthPage.authInfo.submitFormShared.click();
            }
            GESSOAuthPage.page.verifyURL(false, 60);
        }

        public static void Step1() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 1");
            ReportLog.setTestStep("Verify Filtering Window");
            Thread.sleep(5000);
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").click();
            PaxImpactPage.filter.filterButton.click();
            FilteringWindowPage.filteringWindow.title.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.destination.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.origin.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.flightNum.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.impactType.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.cancelButton.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.cancelButton.highlight();
            FilteringWindowPage.filteringWindow.applyButton.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.applyButton.highlight();
        }
        
        public static void Step2()
        {
            ReportLog.setTestCase("STEP 2");
            ReportLog.setTestStep("Verify Impact Type Options");
            FilteringWindowPage.filteringWindow.impactType.click();
            FilteringWindowPage.filteringWindow.delayedOption.verifyDisplayed(true, 5);
            FilteringWindowPage.filteringWindow.cancelledOption.verifyDisplayed(true, 5);
        }
        
        public static void Step3() throws InterruptedException
        {
            ReportLog.setTestCase("STEP 3");
            ReportLog.setTestStep("Filtering by Impact Type");

            FilteringWindowPage.filteringWindow.cancelledOption.click();
            FilteringWindowPage.filteringWindow.applyButton.click();
            
            responseContent = backendAPI.getPayload("Positive Test", "GET/disruptions");

            try 
            {
                if (responseContent.length() > 2) 
                {
                    jsonArray = new JSONArray(responseContent);
                } 
                else 
                {
                    ReportLog.addInfo("Can not get response content from service");
                    ReportLog.assertFailed("Step1 is failed since there is no disrupted data");
                }
            } 
            catch (NullPointerException e) 
            {

            }

            List <String> totalDelayedList = new ArrayList<String>();
            List <String> totalCancelledList = new ArrayList<String>();

            
            for (int i = 0 ; i < jsonArray.length() ; i++ )
            {
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                JSONObject disruption = flightInfo.getJSONObject("disruption");

                if (disruption.toMap().containsKey("delay")) 
                {
                        totalDelayedList.add(flightID);
                }       
                else if (disruption.toMap().containsKey("cancel"))
                {
                	totalCancelledList.add(flightID);
                }
                
            }
            
            ReportLog.setTestStep("Verify Cancelled Flights");
            
            if (totalCancelledList.size() > 0)
            {
                for (int i = 0 ; i < totalCancelledList.size() ; i++ )
                {
                	PDSListViewPage.listView.disruptedItem(totalCancelledList.get(i)).verifyDisplayed(true, 5);
                }
            }
                  
            PaxImpactPage.filter.filterButton.click();
            FilteringWindowPage.filteringWindow.cancelledOption.click();
            FilteringWindowPage.filteringWindow.deselectCancelledOption.click();
            FilteringWindowPage.filteringWindow.delayedOption.click();
            FilteringWindowPage.filteringWindow.applyButton.click();
            
            ReportLog.setTestStep("Verify Delayed Flights");
            
            if (totalDelayedList.size() > 0)
            {
                for (int i = 0 ; i < totalDelayedList.size() ; i++ )
                {
                	PDSListViewPage.listView.disruptedItem(totalDelayedList.get(i)).verifyDisplayed(true, 5);
                }
            }
            
        }
        
        public static void Step4()
        {
            ReportLog.setTestCase("STEP 4");
            ReportLog.setTestStep("Select and Apply Single Option");
                      
            FilteringWindowPage.filteringWindow.origin.click();
            
            String originOption = FilteringWindowPage.filteringWindow.selectOrigin.getText();
            String originResult = FilteringWindowPage.filteringWindow.resultOrigin.getText();
            System.out.println(originResult);
//            System.out.println(originOption);
            
            FilteringWindowPage.filteringWindow.selectedOrigin(originOption).click();
            FilteringWindowPage.filteringWindow.appplyButton.click();
        
            
            
        }

    }
}
