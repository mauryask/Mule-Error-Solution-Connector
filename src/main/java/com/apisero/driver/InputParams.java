package com.apisero.driver;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class InputParams
{
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "")
  private String error;
  
  public void setError(String error)
  {
	  this.error = error;
  }
  
  public String getError()
  {
	  return error;
  }
}
