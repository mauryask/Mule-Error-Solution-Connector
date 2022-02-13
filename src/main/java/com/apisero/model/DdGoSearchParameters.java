package com.apisero.model;

public class DdGoSearchParameters
{
  final public String searchPageUrl = "https://duckduckgo.com/";
  final public String formXpath = "/html/body/div/div[2]/div/div[1]/div[2]/form";
  final public String anchorXpath = "//div[contains(@class, 'result__body')]//a[contains(@class, 'result__check')]";  
  public DdGoSearchParameters(){}
}
