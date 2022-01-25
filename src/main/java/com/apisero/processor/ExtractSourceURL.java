package com.apisero.processor;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.List;

public class ExtractSourceURL 
{
  public static String getUrl(String error, String source)
  {
	  String url = null;
	  LogHandler.disable();
	  WebClient webClient = WebClientInitializer.getWebClient();	  
	  try
	  {
	    /* final HtmlPage page = 
	            webClient.getPage("https://www.google.com/search?q="+error+" "+source);	    	    
	     List<HtmlAnchor> anchor = 
	    		page.getByXPath("//div[contains(@class, 'yuRUbf')]//a");*/
		  
		  // Searching via DUckDuckGo
		  
		    final HtmlPage page = 
		            webClient.getPage("https://duckduckgo.com/?q=+mulesoft+"+error.trim().replaceAll("\\s", "+")+"+site:stackoverflow.com&ia=web");	    	    
		     List<HtmlAnchor> anchor = 
		    		page.getByXPath("//div[contains(@class, 'result__body')]//a[contains(@class, 'result__check')]");
	    
	     for(HtmlAnchor a : anchor)
	     {
	     	if(a.getAttribute("href").contains("stackoverflow.com"))
	    	{
	    		url = a.getAttribute("href");
	    		break;
	    	}
	     }        
	    
	  } catch (IOException ex) {
	    ex.printStackTrace();
	  } catch (FailingHttpStatusCodeException ex) {
	    ex.printStackTrace();
	  }
	  finally{webClient.close();}
	  
	return url;
  }
}
