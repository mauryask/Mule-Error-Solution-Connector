package com.apisero.processor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class WebClientInitializer
{
   public static WebClient getWebClient()
   {
	    final WebClient webClient = new WebClient((BrowserVersion.CHROME));	
	    webClient.setAjaxController(
             new NicelyResynchronizingAjaxController());
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.getOptions().setUseInsecureSSL(false);
	    webClient.getOptions().setCssEnabled(true);
	    webClient.getOptions().setJavaScriptEnabled(true);   
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);		
		return webClient;
   }
}
