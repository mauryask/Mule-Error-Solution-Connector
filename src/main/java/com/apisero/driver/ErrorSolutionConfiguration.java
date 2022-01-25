package com.apisero.driver;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.values.OfValues;

@Operations(ErrorSolutionOperations.class)
public class ErrorSolutionConfiguration 
{  
  @Parameter
  @OfValues(SourceProvider.class)
  private String source;
  
  public String getSource()
  {
	  return source;
  }
  
  public void setSource(String source)
  {
	  this.source = source;
  }
}
