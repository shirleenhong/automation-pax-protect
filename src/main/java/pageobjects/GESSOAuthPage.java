package pageobjects;

import org.openqa.selenium.By;

import auto.framework.web.Element;
import auto.framework.web.Page;
import common.GlobalPage;

public class GESSOAuthPage extends GlobalPage {

	public static Page page = new Page("GE - SSO Authentication Page", ".*https://ssologin.ssogen2.corporate.ge.com/SSOLogin/rsologin.do?site=corporate&TYPE=33554433&REALMOID=06-00003337-ea33-1593-a5fd-2277036c900d&GUID=&SMAUTHREASON=0&METHOD=GET&SMAGENTNAME=-SM-vwCHtKdjTBwgZPPp17mNrMlVnbOgC2n3r%2brD%2bNn6%2bHYLFIS4X1pxQoPU9LgQv1Vw&TARGET=-SM-HTTPS%3a%2f%2fssocentralck%2eregistrar%2ege%2ecom%2fPfIdpSmAuth%2fPFRedirect%2ejsp%3ffedHost%3dhttps%3a%2f%2ffss%2egecompany%2ecom%2ffss%26resumePath%3d-%2Fidp-%2FRtRx6-%2FresumeSAML20-%2Fidp-%2FSSO%2eping.*");
	
	public static AuthInfo authInfo = new AuthInfo();
	
	public static class AuthInfo extends Element{
		
		public final Element gESSOLabel;
		public final Element sSOIDInput;
		public final Element passWordInput;
		public final Element submitFormRememberMe;
		public final Element submitFormShared;
		
		public AuthInfo()
		{
			super("Status - Main Header", By.xpath("//div[@class='loginFrm']"));
			
			gESSOLabel 				= new Element("GE Single Sign On Label", By.xpath(".//h1[contains(text(),'GE Single Sign On')]"), this);
			sSOIDInput 				= new Element("Username/SSO ID Input", By.xpath(".//*[@id='username']"), this);
			passWordInput 			= new Element("Password Input", By.xpath(".//*[@id='password']"), this);
			submitFormRememberMe 	= new Element("Login - Remember me", By.xpath(".//*[@id='submitFrm']"), this);
			submitFormShared 		= new Element("Login - Shared Computer", By.xpath(".//*[@id='submitFrmShared']"));
		}
	}
}
