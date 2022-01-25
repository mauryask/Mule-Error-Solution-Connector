package com.apisero.processor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONArray;

public class SolutionStream
{
   public static String getStream(JSONArray solution)
   {
		  StringBuilder response = null;
		  try(BufferedReader br = new BufferedReader
				  (new InputStreamReader
						  (new ByteArrayInputStream
								  (solution.toString().getBytes("utf-8")))))
		  {
				response = new StringBuilder();
				String tempResponse = null;
		 		while((tempResponse = br.readLine())!=null)
		 		{
					response.append(tempResponse.trim());
				}
		   }
		   catch(IOException ex)
		   {
			  ex.printStackTrace();
		   }
		  
		 return response.toString();
   }
}
