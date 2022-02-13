package com.apisero.service;

import java.io.IOException;
import java.util.logging.Logger;
import com.apisero.model.DdGoSearchParameters;
import com.apisero.model.GoogleSearchParameters;
import com.apisero.model.SearchResponseModel;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ExtractSourceURL 
{	
  public static SearchResponseModel getResponseModel(String error,
     String source, String searchEngine)
  {	  
      Logger.getLogger("com.gargoylesoftware.htmlunit")
      .setLevel(java.util.logging.Level.OFF);
      Logger.getLogger("org.apache.http").setLevel(
      java.util.logging.Level.OFF);
	  
	  WebClient webClient = WebClientInitializer.getWebClient();
	  
	  SearchResponseModel  model = new SearchResponseModel();
	     		 
	     switch(searchEngine.trim())
		 {
		     case "Google":
		       {
		    	   GoogleSearchParameters gParams  = new GoogleSearchParameters();
		       search(webClient, error, source, model, 
		    		   gParams.searchPageUrl,
		    		   gParams.formXpath, gParams.anchorXpath);		    	
			    break;
		       }
		       
		     case "DuckDuckGo":
		     {
			       DdGoSearchParameters dParams  = new DdGoSearchParameters();
			       search(webClient, error, source, model, 
			    		   dParams.searchPageUrl,
			    		   dParams.formXpath, dParams.anchorXpath);	
		       break;
		     }
		     
		  default:
		  {
		       GoogleSearchParameters gParams  = new GoogleSearchParameters();
		       search(webClient, error, source, model, 
		    		   gParams.searchPageUrl,
		    		   gParams.formXpath, gParams.anchorXpath);
		       if(model.statusCode != 200)
		       {
			       DdGoSearchParameters dParams  = new DdGoSearchParameters();
			       search(webClient, error, source, model, 
			    		   dParams.searchPageUrl,
			    		   dParams.formXpath, dParams.anchorXpath);	
		       }
		  }
	}
	    	    
	    webClient.close();
	    
	return model;
  }
    
  private static void search(WebClient webClient, String error,
		  String source, SearchResponseModel model,
		  String searchPageUrl, String formXpath,
		  String anchorXpath)
  {

      try 
      {
    	   // Go to the search page
    	    HtmlPage googleSearchPage = webClient.getPage(searchPageUrl);
    	    int statusCode = googleSearchPage.getWebResponse().getStatusCode();
    	    if(statusCode != 200)
    	      return;
		    // Get the search form
		    HtmlForm form =  googleSearchPage.getFirstByXPath(formXpath);
 	        // Pass the search query parameter
	        form.getInputByName("q").setValueAttribute("MuleSoft "+error+" site:"+source);
	        // Created button to submit the form
	        HtmlButton submitButton = (HtmlButton) googleSearchPage.createElement("button");
	        submitButton.setAttribute("type", "submit");
	        form.appendChild(submitButton);
	        // Search result page
	        HtmlPage googleSearchResultPage = submitButton.click();
	        statusCode = googleSearchResultPage.getWebResponse().getStatusCode();
	        if(statusCode != 200)
	        	return;
	       	// Get the first link from search results       
	        HtmlAnchor anchor = googleSearchResultPage.getFirstByXPath(anchorXpath);	        
	        // Get the status code
	        model.statusCode = statusCode;
	        // Get the source model
	        model.sourceUrl = anchor.getAttribute("href");
	        // Get source page from above link
	        model.sourcePage = webClient.getPage(model.sourceUrl);	          
		
	} catch (FailingHttpStatusCodeException | IOException e) {
		e.printStackTrace();
	}	    	         
  } 
}
