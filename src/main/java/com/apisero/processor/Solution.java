package com.apisero.processor;

import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Solution
{
  @MediaType(value=ANY, strict=false)
  public static String extract(String error, String source)
  {     
      JSONArray solution = new JSONArray();      
      LogHandler.disable();
      String url = ExtractSourceURL.getUrl(error,source);
      WebClient webClient = WebClientInitializer.getWebClient();
             
      try
      {      	                 
		    final HtmlPage page = 
                  webClient.getPage(url);
          
          List<DomElement> domElementList = 
        		  page.getByXPath(
                  "//div[@class='answercell post-layout--right']//div[@class='s-prose js-post-body']");
                   
          for(int i=0; i<domElementList.size(); i++)
          {
          	JSONObject object = new JSONObject();
          	object.put(i+"", domElementList.get(i).getTextContent());
          	solution.put(object);
          }          
      } 
      catch 
      (IOException ex) 
      {
          ex.printStackTrace();
      } catch (FailingHttpStatusCodeException ex){
          ex.printStackTrace();
      }
      finally {webClient.close();}
      
      return SolutionStream.getStream(solution);
  }
}
