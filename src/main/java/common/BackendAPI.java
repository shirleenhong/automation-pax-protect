package common;

import auto.framework.ReportLog;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.submit.transports.http.SinglePartHttpResponse;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.RestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequest;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.support.types.StringToObjectMap;
import org.junit.Assert;
import org.mortbay.log.Log;

import java.util.List;

/**
 * Created by 212617361 on 8/23/2017.
 */
public class BackendAPI {
    public String getPayload(String testSuiteName, String testCaseName){
        String responseContent = null;
        int statusCode = 0;
        WsdlProject wProject = new WsdlProject("src/test/resources/data/ReflowWorkflowService-soapui-project.xml");

        // Get token for Authorization
        WsdlTestSuite gTokenTestSuite = wProject.getTestSuiteByName("Authorization");
        WsdlTestCase gTestCaseToken = gTokenTestSuite.getTestCaseByName("Authorization");
        WsdlTestCaseRunner testRunnerToken = gTestCaseToken.run(new PropertiesMap(), false);

        // Run test case and get response content
        WsdlTestSuite gTestSuite = wProject.getTestSuiteByName(testSuiteName);
        WsdlTestCase gTestCase = gTestSuite.getTestCaseByName(testCaseName);

        WsdlTestCaseRunner testRunnerCC = gTestCase.run(new StringToObjectMap(gTestCase.getProperties()), false);


        List<TestStepResult> results = testRunnerCC.getResults();
        for (TestStepResult result : results) {
            if (RestRequestStepResult.class.isInstance(result)) {
                RestRequestStepResult restResult = (RestRequestStepResult) result;
                RestTestRequestStep rtestStep = (RestTestRequestStep) restResult.getTestStep();
                RestTestRequest rTestRequest = rtestStep.getTestRequest();
                SinglePartHttpResponse httpResponse = (SinglePartHttpResponse) rTestRequest.getResponse();
                String testStepName = restResult.getTestStep().getName();
                if (httpResponse != null){
                    Log.info("****Running Test Step " + testStepName + "*****");
                    try {
                        responseContent = httpResponse.getContentAsString().toString();
                    } catch (Exception e){
                        ReportLog.addInfo("Reflow workflow service response content is null");
                    }


                    try {
                        statusCode = httpResponse.getStatusCode();
                    }catch (Exception e) {
                        ReportLog.addInfo("Response Status Code is" + Integer.toString(statusCode));
                        //Log.info("Response Status Code:" + Integer.toString(statusCode));
                       Assert.assertEquals(200, statusCode);
                    }


                }
            }
        }

        return responseContent;
    }

    public String getPayloadWithParameter(String testSuiteName, String testCaseName, String parameter){
        String responseContent = null;
        int statusCode = 0;
        WsdlProject wProject = new WsdlProject("src/test/resources/data/ReflowWorkflowService-soapui-project.xml");

        // Get token for Authorization
        WsdlTestSuite gTokenTestSuite = wProject.getTestSuiteByName("Authorization");
        WsdlTestCase gTestCaseToken = gTokenTestSuite.getTestCaseByName("Authorization");
        WsdlTestCaseRunner testRunnerToken = gTestCaseToken.run(new PropertiesMap(), false);

        // Run test case and get response content
        WsdlTestSuite gTestSuite = wProject.getTestSuiteByName(testSuiteName);
        WsdlTestCase gTestCase = gTestSuite.getTestCaseByName(testCaseName);

        if (testCaseName.equals("Solve_Transaction")){
            gTestCase.getTestStepByName("request_solve_transaction").getProperty("transactionid").setValue(parameter);
        }

        if (testCaseName.equals("Solve")){
            gTestCase.getTestStepByName("request_solve").getProperty("Request").setValue(parameter);
        }



        //WsdlTestCaseRunner testRunnerCC = gTestCase.run(new PropertiesMap(), false);
        //WsdlTestCaseRunner testRunnerCC = new WsdlTestCaseRunner(gTestCase, new StringToObjectMap(gTestCase.getProperties()));
        WsdlTestCaseRunner testRunnerCC = gTestCase.run(new StringToObjectMap(gTestCase.getProperties()), false);


        List<TestStepResult> results = testRunnerCC.getResults();
        for (TestStepResult result : results) {
            if (RestRequestStepResult.class.isInstance(result)) {
                RestRequestStepResult restResult = (RestRequestStepResult) result;
                RestTestRequestStep rtestStep = (RestTestRequestStep) restResult.getTestStep();
                RestTestRequest rTestRequest = rtestStep.getTestRequest();
                SinglePartHttpResponse httpResponse = (SinglePartHttpResponse) rTestRequest.getResponse();
                String testStepName = restResult.getTestStep().getName();
                if (httpResponse != null){
                    Log.info("****Running Test Step " + testStepName + "*****");
                    try {
                        responseContent = httpResponse.getContentAsString().toString();
                    } catch (Exception e){
                        ReportLog.addInfo("Reflow workflow service response content is null");
                    }


                    try {
                        statusCode = httpResponse.getStatusCode();
                    }catch (Exception e) {
                        ReportLog.addInfo("Response Status Code is" + Integer.toString(statusCode));
                        //Log.info("Response Status Code:" + Integer.toString(statusCode));
                        Assert.assertEquals(200, statusCode);
                    }


                }
            }
        }

        return responseContent;
    }
}
