package common;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.submit.transports.http.SinglePartHttpResponse;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.RestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequest;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestStepResult;
import org.junit.Assert;
import org.mortbay.log.Log;

import java.util.List;

/**
 * Created by 212617361 on 8/23/2017.
 */
public class BackendAPI {
    public String getPayload(String testSuiteName, String testCaseName){
        String responseContent = null;
        WsdlProject wProject = new WsdlProject("src/test/resources/data/ReflowWorkflowService-soapui-project.xml");

        // Get token for Authorization
        WsdlTestSuite gTokenTestSuite = wProject.getTestSuiteByName("Authorization");
        WsdlTestCase gTestCaseToken = gTokenTestSuite.getTestCaseByName("Authorization");
        WsdlTestCaseRunner testRunnerToken = gTestCaseToken.run(new PropertiesMap(), false);

        // Run test case and get response content
        WsdlTestSuite gTestSuite = wProject.getTestSuiteByName(testSuiteName);
        WsdlTestCase gTestCase = gTestSuite.getTestCaseByName(testCaseName);
        WsdlTestCaseRunner testRunnerCC = gTestCase.run(new PropertiesMap(), false);

        List<TestStepResult> results = testRunnerCC.getResults();
        for (TestStepResult result : results) {
            if (RestRequestStepResult.class.isInstance(result)) {
                RestRequestStepResult restResult = (RestRequestStepResult) result;
                RestTestRequestStep rtestStep = (RestTestRequestStep) restResult.getTestStep();
                RestTestRequest rTestRequest = rtestStep.getTestRequest();
                SinglePartHttpResponse httpResponse = (SinglePartHttpResponse) rTestRequest.getResponse();
                String testStepName = restResult.getTestStep().getName();
                if (httpResponse != null){
                    //System.out.println("****Running Test Step " + testStepName + "*****");
                    Log.info("****Running Test Step " + testStepName + "*****");
                    responseContent = httpResponse.getContentAsString().toString();
                    int statusCode = httpResponse.getStatusCode();
                    //System.out.println("Response Status Code:" + statusCode);
                    Log.info("Response Status Code:" + Integer.toString(statusCode));
                    Assert.assertEquals(200, statusCode);
                   // return responseContent;
                }
            }
        }

        return responseContent;
    }
}
