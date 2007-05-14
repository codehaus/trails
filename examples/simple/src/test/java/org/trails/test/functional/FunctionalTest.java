/*
 * Created on Dec 12, 2004
 *
 * Copyright 2004 Chris Nelson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.trails.test.functional;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import junit.framework.TestCase;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author fus8882
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FunctionalTest extends TestCase
{

    WebClient webClient;
    protected HtmlPage startPage;

    public void setUp() throws Exception
    {
        Properties testProperties = new Properties();
        testProperties.load(this.getClass().getResourceAsStream("/functionaltest.properties"));
        webClient = new WebClient();
        startPage = (HtmlPage)webClient.getPage(new URL(testProperties.getProperty("test.url")));
    }

    protected HtmlForm getFirstForm(HtmlPage page)
    {
        return (HtmlForm)page.getForms().get(0);
    }

    protected HtmlPage clickButton(HtmlForm form, String buttonValue) throws IOException
    {
        return (HtmlPage)((HtmlSubmitInput)form.getInputByValue(buttonValue)).click();
    }

    protected HtmlPage clickButton(HtmlPage page, String buttonValue) throws IOException
    {
        return clickButton((HtmlForm)page.getForms().get(0), buttonValue);
    }
    
    protected HtmlPage clickLinkOnPage(HtmlPage page, String linkText) throws IOException
    {
        return (HtmlPage)page.getFirstAnchorByText(linkText).click();
    }

    protected HtmlDivision getErrorDiv(HtmlPage page) throws JaxenException
    {
        return (HtmlDivision) new HtmlUnitXPath("//div[@class='error']").selectSingleNode(page);
    }

    protected String getId(String idField, HtmlPage savedCategoryPage) throws JaxenException
    {
        HtmlSpan span = (HtmlSpan)new HtmlUnitXPath("//span/preceding-sibling::label[contains(text(), '" + idField + "')]/following-sibling::span").selectSingleNode(savedCategoryPage);
        return span.asText();
    }

    protected HtmlTextInput getTextInputForField(HtmlPage newProductPage, String field) throws JaxenException
    {
        HtmlTextInput input = (HtmlTextInput)
            new HtmlUnitXPath("//span/preceding-sibling::label[contains(text(), '" + field + "')]/following-sibling::span/input").selectSingleNode(newProductPage);
        return input;
    }

    protected void assertXPathPresent(HtmlPage page, String xpath) throws Exception
    {
        assertNotNull(new HtmlUnitXPath(xpath).selectSingleNode(page));
        
    }

    protected void assertXPathNotPresent(HtmlPage page, String xpath) throws Exception
    {
        assertNull(new HtmlUnitXPath(xpath).selectSingleNode(page));
    }
    
    protected HtmlTextArea getTextAreaByName(HtmlPage page, String name) throws JaxenException
    {
        HtmlTextArea textArea = (HtmlTextArea) 
            new HtmlUnitXPath("//span/preceding-sibling::label[contains(text(), '" + name + "')]/following-sibling::span/textarea").selectSingleNode(page);
        return textArea;
    }

    protected HtmlInput getInputByName(HtmlPage page, String name) throws JaxenException
    {
        return (HtmlInput) 
            new HtmlUnitXPath("//span/preceding-sibling::label[contains(text(), '" + name + "')]/following-sibling::span/input").selectSingleNode(page);
    }

}
