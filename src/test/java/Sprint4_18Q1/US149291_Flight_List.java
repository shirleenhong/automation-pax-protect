package Sprint4_18Q1;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pageobjects.ExecutiveDashboardListPage;
import pageobjects.GESSOAuthPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class US149291_Flight_List extends TestBase{

    public static TestDataHandler testDataHandler;

    public static BackendAPI backendAPI = new BackendAPI();

    public static List<String> totalAllList = new ArrayList<String>();
    public static String responseContent;
    public static JSONArray  jsonArray;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US149291_Flight_List.TestCases.PreRequisiteStep();
        US149291_Flight_List.TestCases.Step1();
        US149291_Flight_List.TestCases.Step2();


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


        public static void Step1() {

            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").verifyDisplayed(true,5);
            GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Executive Dashboard").click();

            WebControl.activeWindow().waitForPageToLoad(5);

            responseContent = backendAPI.getPayload("Positive Test","GET/disruptions");

            try {
                if (responseContent.startsWith("[")) {
                    jsonArray = new JSONArray(responseContent);
                } else {
                    ReportLog.addInfo("Can not get response content from service");
                    ReportLog.assertFailed("Step2 is failed");
                }
            }catch(NullPointerException e){

            }

            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject flightInfo = jsonArray.getJSONObject(i);
                String flightID = flightInfo.getString("flightID");
                totalAllList.add(flightID);
            }

            for(int i = 0; i<totalAllList.size(); i++){
                ExecutiveDashboardListPage.disruptedFlightList.disruptedFlightListItem(totalAllList.get(i)).verifyDisplayed(true,5);
                ExecutiveDashboardListPage.disruptedFlightList.disruptedFlightListItem(totalAllList.get(i)).highlight();
                String[] parsedItem = ExecutiveDashboardListPage.disruptedFlightList.disruptedFlightListItem(totalAllList.get(i)).getText().split("\n");
                int f =0;
            }

        }

        public static void Step2() {
            ExecutiveDashboardListPage.disruptedFlightListHeader.impactHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.flightHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.origHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.destHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.paxHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.misconxHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.welfareHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.impactCodeHeader.highlight();
            ExecutiveDashboardListPage.disruptedFlightListHeader.psngrValueHeader.highlight();

        }
    }
}
