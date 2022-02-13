package com.apisero.model;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SearchResponseModel
{
	public int statusCode = 429;
    public HtmlPage sourcePage = null;
    public String sourceUrl = null;
    public SearchResponseModel(){}
}
