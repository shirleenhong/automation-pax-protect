package Sprint25;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BackendAPI;
import common.GlobalPage;
import common.TestDataHandler;
import org.testng.annotations.Test;
import pageobjects.GESSOAuthPage;
import pageobjects.PaxImpactPage;


public class US137579_SummaryDrawer_Font extends TestBase {

    public static TestDataHandler testDataHandler;

    @Test
    public void TestScenarios() throws Exception
    {
        ReportLog.setTestName("Test");
        testDataHandler = TestDataHandler.loadTestData("URL", "RowSelection='LoginUser1'");
        US137579_SummaryDrawer_Font.TestCases.PreRequisiteStep(testDataHandler);
        US137579_SummaryDrawer_Font.TestCases.Step1();

    }

    public static class TestCases {
        public static void PreRequisiteStep(TestDataHandler testDataHandler) throws InterruptedException {
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

        public static void Step1() throws Exception {
            ReportLog.setTestCase("[STEP 1]");
            ReportLog.setTestStep("Verify if summary drawer style is right");

            if (ConvertToHex(GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").getCssValue("color")).equals("#b6c3cc") && GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").getCssValue("font-size").equals("15px") && GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").getCssValue("font-weight").equals("400") && GlobalPage.mainPXNavigationOptions.navigateToNavbarLink("Pax Impact").getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Pax Impact Tab style passed");
            }
            else{
                ReportLog.assertTrue(false, "Pax Impact Tab style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.impactedFlightsHeader.getCssValue("color")).equals("#ffffff") && PaxImpactPage.headersStyle.impactedFlightsHeader.getCssValue("font-size").equals("30px") && PaxImpactPage.headersStyle.impactedFlightsHeader.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.impactedFlightsHeader.getCssValue("font-family").contains("GE Inspira")){
                ReportLog.assertTrue(true, "Impacted Flights header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Impacted Flights header style failed");
            }

            if (ConvertToHex(PaxImpactPage.headersStyle.allHeader.getCssValue("color")).equals("#4289a8") && PaxImpactPage.headersStyle.allHeader.getCssValue("font-size").equals("15px") ){
                ReportLog.assertTrue(true, "Total ALL Header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total ALL Header style failed");
            }

            if (ConvertToHex(PaxImpactPage.headersStyle.delayedHeader.getCssValue("color")).equals("#4289a8") && PaxImpactPage.headersStyle.delayedHeader.getCssValue("font-size").equals("15px") ){
                ReportLog.assertTrue(true, "Total DELAYED Header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total DELAYED Header style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.gt15MinHeader.getCssValue("color")).equals("#4289a8") && PaxImpactPage.headersStyle.gt15MinHeader.getCssValue("font-size").equals("15px") ){
                ReportLog.assertTrue(true, "Total ≥ 15MIN Header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total ≥ 15MIN Header style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.gt60MinHeader.getCssValue("color")).equals("#4289a8") && PaxImpactPage.headersStyle.gt60MinHeader.getCssValue("font-size").equals("15px") ){
                ReportLog.assertTrue(true, "Total ≥ 60MIN Header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total ≥ 60MIN Header style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.gt180MinHeader.getCssValue("color")).equals("#4289a8") && PaxImpactPage.headersStyle.gt180MinHeader.getCssValue("font-size").equals("15px") ){
                ReportLog.assertTrue(true, "Total ≥ 180MIN Header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total ≥ 180MIN Header style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.cancelledHeader.getCssValue("color")).equals("#4289a8") && PaxImpactPage.headersStyle.cancelledHeader.getCssValue("font-size").equals("15px") ){
                ReportLog.assertTrue(true, "Total CANCELLED Header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total CANCELLED Header style failed");
            }

            if (ConvertToHex(PaxImpactPage.headersStyle.allHeaderValue.getCssValue("color")).equals("#ffffff") && PaxImpactPage.headersStyle.allHeaderValue.getCssValue("font-size").equals("20px") && PaxImpactPage.headersStyle.allHeaderValue.getCssValue("font-weight").equals("700") && PaxImpactPage.headersStyle.allHeaderValue.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Total All header value style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total All header value style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.delayedHeaderValue.getCssValue("color")).equals("#b6c3cc") && PaxImpactPage.headersStyle.delayedHeaderValue.getCssValue("font-size").equals("20px") && PaxImpactPage.headersStyle.delayedHeaderValue.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.delayedHeaderValue.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Total Delayed header value style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total Delayed header value style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.gt15MinHeaderValue.getCssValue("color")).equals("#dfac00") && PaxImpactPage.headersStyle.gt15MinHeaderValue.getCssValue("font-size").equals("20px") && PaxImpactPage.headersStyle.gt15MinHeaderValue.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.gt15MinHeaderValue.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Total gt15Min header value style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total gt15Min header value style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.gt60MinHeaderValue.getCssValue("color")).equals("#ff8b3b") && PaxImpactPage.headersStyle.gt60MinHeaderValue.getCssValue("font-size").equals("20px") && PaxImpactPage.headersStyle.gt60MinHeaderValue.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.gt60MinHeaderValue.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Total gt60Min header value style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total gt60Min header value style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.gt180MinHeaderValue.getCssValue("color")).equals("#f34437") && PaxImpactPage.headersStyle.gt180MinHeaderValue.getCssValue("font-size").equals("20px") && PaxImpactPage.headersStyle.gt180MinHeaderValue.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.gt180MinHeaderValue.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Total gt180Min header value style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total gt180Min header value style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.cancelledHeaderValue.getCssValue("color")).equals("#b6c3cc") && PaxImpactPage.headersStyle.cancelledHeaderValue.getCssValue("font-size").equals("20px") && PaxImpactPage.headersStyle.cancelledHeaderValue.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.cancelledHeaderValue.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Total Cancelled header value style passed");
            }
            else{
                ReportLog.assertTrue(false, "Total Cancelled header value style failed");
            }
            if (ConvertToHex(PaxImpactPage.headersStyle.impactedFlightsMidHeader.getCssValue("color")).equals("#c5d1d8") && PaxImpactPage.headersStyle.impactedFlightsMidHeader.getCssValue("font-size").equals("15px") && PaxImpactPage.headersStyle.impactedFlightsMidHeader.getCssValue("font-weight").equals("400") && PaxImpactPage.headersStyle.impactedFlightsMidHeader.getCssValue("font-family").contains("GE Inspira Sans")){
                ReportLog.assertTrue(true, "Impacted Flights Middle header style passed");
            }
            else{
                ReportLog.assertTrue(false, "Impacted Flights Middle header style failed");
            }


        }


    }

    public static String ConvertToHex(String color) throws Exception{

        String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");

        int hexValue1=Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2=Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3=Integer.parseInt(hexValue[2]);

        String colorHexValue = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

        return colorHexValue;

    }
}
