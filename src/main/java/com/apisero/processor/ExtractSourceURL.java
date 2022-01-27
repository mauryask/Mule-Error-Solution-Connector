package com.apisero.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ExtractSourceURL 
{
	static int statusCode = 200;
	private static final org.slf4j.Logger LOGGER = 
				   LoggerFactory.getLogger(ExtractSourceURL.class);
	
  public static String getUrl(String error, String source, String searchEngine)
  {	  
	  // Disabling unnecessary logs
      Logger.getLogger("com.gargoylesoftware.htmlunit")
      .setLevel(java.util.logging.Level.OFF);
      Logger.getLogger("org.apache.http").setLevel(
      java.util.logging.Level.OFF);
	  
	  WebClient webClient = WebClientInitializer.getWebClient();
	  
	     List<HtmlAnchor> anchor;
	     		 
	     switch(searchEngine)
		 {
		    case "Google":
		    	anchor = google(webClient, error, source);
		    	if(statusCode == 429)
		    	{
                        LOGGER.info("Unable to fetch solutions from Google.."
                        		+ "\nTrying with DuckDuckGo..");
    		    		anchor = duckDuckGo(webClient, error, source);	
		    	}		    	
			  break;
		    case "DuckDuckGo":
		    	anchor = duckDuckGo(webClient, error, source);
		    	break;
		    default:
		    anchor = google(webClient, error, source);	    
	    	if(statusCode == 429)
	    		anchor = duckDuckGo(webClient, error, source);		    
		 }
	 
	     String url = null;
	     
	    for(HtmlAnchor a : anchor)
	    {
	    	    url = a.getAttribute("href");
	    		if(url.contains("stackoverflow.com"))
	    		  break;
	    }
	    
	    webClient.close();
	    
	return url;
  }
  
   private static List<HtmlAnchor> google(WebClient webClient, 
		   String error, String source)
   {
	    HtmlPage page;
	    List<HtmlAnchor> anchor = new ArrayList<>();
	try {
		page = webClient.getPage("https://www.google.com/search?q="+error+" mulesoft site:"+source);
		statusCode = page.getWebResponse().getStatusCode();
	    anchor = page.getByXPath("//div[contains(@class, 'yuRUbf')]//a");
	} catch (FailingHttpStatusCodeException | IOException e) {
		e.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	     return anchor;
   }
   
   private static List<HtmlAnchor> duckDuckGo(WebClient webClient, 
		   String error, String source) 
   {	   
	    HtmlPage page;
	    List<HtmlAnchor> anchor = new ArrayList<>();
		try {
			 page = webClient.getPage("https://duckduckgo.com/?q="+error.trim().replaceAll("\\s", "+")+"+mulesoft+site:"+source+"&ia=web");
			 statusCode = page.getWebResponse().getStatusCode();
			 anchor = page.getByXPath("//div[contains(@class, 'result__body')]//a[contains(@class, 'result__check')]");
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}	
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	     return anchor;
   }
}
