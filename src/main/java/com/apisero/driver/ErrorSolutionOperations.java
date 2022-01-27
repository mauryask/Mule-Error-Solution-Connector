package com.apisero.driver;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apisero.processor.Solution;

public class ErrorSolutionOperations 
{
   private static final Logger LOGGER = 
		   LoggerFactory.getLogger(ErrorSolutionConfiguration.class);
   
   @MediaType("application/json")
   @Alias("Solution")
   public InputStream getSolution(@Config ErrorSolutionConfiguration config,
		   @ParameterGroup(name="Error") InputParams params) throws IOException
   {
	    LOGGER.info("Solution extraction started...");
	    
	    JSONObject solution =  null;
	    String error = params.getError().trim();
	    String source = config.getSource().trim();
	    String searchEngine = config.getSearchEngine().trim();
	    
	    if(source.equals("Mulesoft Help"))
	    	return new ByteArrayInputStream
					  ((new JSONObject()
							  .put("message", "Source: "+source+" is not yet supported"))
							  .toString()
							  .getBytes());	    
	    else
	    	solution = Solution.extract(error,/*source*/ "stackoverflow.com", searchEngine);
	   	    
	    LOGGER.info("Solution extraction completed...");
	    
	    return new ByteArrayInputStream
				  (solution.toString().getBytes());
   }
}
