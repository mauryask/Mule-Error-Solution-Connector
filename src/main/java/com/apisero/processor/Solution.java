package com.apisero.processor;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.param.MediaType;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Solution
{
  @MediaType(value=ANY, strict=false)
  public static JSONObject extract(String error, String source, String searchEngine)
  {	 	
      // Disabling unnecessary logs
      Logger.getLogger("com.gargoylesoftware.htmlunit")
      .setLevel(java.util.logging.Level.OFF);
      Logger.getLogger("org.apache.http").setLevel(
      java.util.logging.Level.OFF);
      
      JSONObject solution = new JSONObject(); 
      
      String url = ExtractSourceURL.getUrl(error,source, searchEngine);
      WebClient webClient = WebClientInitializer.getWebClient();
             
      try
      {      	                 
		    final HtmlPage page = 
                  webClient.getPage(url);
          
          List<DomElement> domElementList = 
        		  page.getByXPath(
                  "//div[@class='answercell post-layout--right']//div[@class='s-prose js-post-body']");
          
          
          solution.put("error", error);
          solution.put("source", url);
                  
          JSONArray temp = new JSONArray();  
          
          for(int i=0; i<domElementList.size(); i++)
          {
          	JSONObject object = new JSONObject();
          	object.put(i+"", domElementList.get(i)
          			.getTextContent()
          			.replaceAll("(\r\n|\n)", " ")
          			.replaceAll("\"", ""));
          	temp.put(object);
          }
          
          solution.put("data", temp);
      } 
      catch 
      (IOException ex) 
      {
          ex.printStackTrace();
      } catch (FailingHttpStatusCodeException ex){
          ex.printStackTrace();
      }
      finally {webClient.close();}
      
      return solution;
  }
}
