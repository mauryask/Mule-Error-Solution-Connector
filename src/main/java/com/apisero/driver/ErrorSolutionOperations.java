package com.apisero.driver;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import java.io.IOException;
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
   
   @MediaType(value = ANY, strict= false)
   @Alias("Solution")
   public String getSolution(@Config ErrorSolutionConfiguration config,
		   @ParameterGroup(name="Error") InputParams params) throws IOException
   {
	    LOGGER.info("Solution extraction started...");
	    
	    String solution = "";
	    String error = params.getError().trim();
	    String source = config.getSource().trim();
	    
	    switch(source)
	    {
	       case "Stack Overflow":
	    	   solution = Solution.extract(error, "stackoverflow.com");
	    	   break;
	       case "Mulesoft Help":
	    	   solution = "Source: "+source+" is not supported";
	    	   break;
	       default:
	    	   solution = "Source: "+source+" is not supported";
	    	   break;	    	   
	    }
	    
	    LOGGER.info("Solution extraction completed...");
	    
	    return solution;
   }
}
