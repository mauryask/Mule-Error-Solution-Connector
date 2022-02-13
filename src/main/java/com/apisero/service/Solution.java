package com.apisero.service;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.param.MediaType;

import com.apisero.model.SearchResponseModel;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;

public class Solution
{
  @MediaType(value=ANY, strict=false)
  public static JSONObject extract(String error, String source, String searchEngine)
  {	 	
      // Disable unnecessary logs
      Logger.getLogger("com.gargoylesoftware.htmlunit")
      .setLevel(java.util.logging.Level.OFF);
      Logger.getLogger("org.apache.http").setLevel(
      java.util.logging.Level.OFF);
      
      JSONObject solution = new JSONObject(); 
      
      SearchResponseModel model = ExtractSourceURL.getResponseModel(error,source, searchEngine);
      
      if(model.statusCode != 200)
      {
    	  return solution.put("statusCode", model.statusCode)
    			  .put("status", "failure")
    			  .put("message", searchEngine+" is blocking the request")
    			  .put("suggestion", "try with default or other search engines available or try agian after sometime");
      }
      
      WebClient webClient = WebClientInitializer.getWebClient();
             
      try
      {      	                          
          List<DomElement> domElementList = 
        		  model.sourcePage.getByXPath(
                  "//div[@class='answercell post-layout--right']//div[@class='s-prose js-post-body']");
                    
          solution.put("error", error);
          solution.put("source", model.sourceUrl);
          solution.put("searchEngine", searchEngine);
                  
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
      catch (FailingHttpStatusCodeException ex){
          ex.printStackTrace();
      }
      finally {webClient.close();}
      
      return solution;
  }
}
