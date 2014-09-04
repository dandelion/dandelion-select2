/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2014 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion.select2.jsp.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dandelion.core.asset.generator.JavascriptGenerator;
import com.github.dandelion.core.asset.generator.jquery.StandardJQueryJavascriptGenerator;
import com.github.dandelion.core.asset.locator.impl.DelegateLocator;
import com.github.dandelion.core.web.AssetRequestContext;
import com.github.dandelion.select2.core.generator.Select2AssetBuffer;
import com.github.dandelion.select2.core.generator.jquery.Select2JQueryJavascriptGenerator;
import com.github.dandelion.select2.core.html.HtmlOption;
import com.github.dandelion.select2.core.html.HtmlSelect;

public class SelectTag extends TagSupport implements DynamicAttributes {

	private static final long serialVersionUID = 1528832829817373708L;

	// Logger
	protected static Logger logger = LoggerFactory.getLogger(SelectTag.class);

	/**
	 * Tag attributes
	 */
	protected String id;
	protected String itemValue;
	protected String itemLabel;

	// First way to populate the select: using a Collection
	protected Object data;

	// Second way to populate the select: using the URL that returns data
	// protected String url;

	/**
	 * Internal attributes
	 */
	protected HtmlSelect select;
	protected String dataSourceType;
	protected Iterator<Object> iterator;
	protected Object currentObject;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		// TODO Auto-generated method stub

	}

	@Override
	public int doStartTag() throws JspException {
		
		request = (HttpServletRequest) pageContext.getRequest();
		response = (HttpServletResponse) pageContext.getResponse();
		
		select = new HtmlSelect(id, request, response, null, null);
		
		try {
			while (iterator.hasNext()) {
				Object o = iterator.next();
				Object propertyValue = PropertyUtils.getNestedProperty(o, itemValue);
				HtmlOption option = new HtmlOption();
				option.setText(String.valueOf(propertyValue));
				option.setValue(String.valueOf(propertyValue));
				select.addOption(option);
			}
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		return setupHtmlGeneration();
	}

	protected int setupHtmlGeneration() throws JspException {

		// Get the right Javascript generator or create it if it doesn't exist
		JavascriptGenerator javascriptGenerator = AssetRequestContext.get(this.request).getParameterValue(
				"dandelion-select2", DelegateLocator.DELEGATED_CONTENT_PARAM);
		
		if (javascriptGenerator == null) {
			javascriptGenerator = new StandardJQueryJavascriptGenerator();
		}
		
		// Generate the web resources (JS, CSS) and wrap them into a
		// WebResources POJO
		Select2AssetBuffer dab = Select2AssetBuffer.create(this.select);
		logger.debug("Web content generated successfully");

		// Buffering generated Javascript
		javascriptGenerator.fillBuffer(dab);

		// Update the asset request context with the enabled bundles and
				// Javascript generator
		AssetRequestContext
			.get(this.request)
			.addBundles("ddl-s2")
			.addBundles("select2")
			.addParameter("dandelion-select2", DelegateLocator.DELEGATED_CONTENT_PARAM, javascriptGenerator,
					false);

		try {
			this.pageContext.getOut().println(this.select.toHtml());

		}
		catch (IOException e) {
			throw new JspException("Unable to generate the HTML markup for the table " + id, e);
		}

		return EVAL_PAGE;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setData(Collection<Object> data) {
		this.dataSourceType = "DOM";
		this.data = data;

		Collection<Object> dataTmp = (Collection<Object>) data;
		if (dataTmp != null && dataTmp.size() > 0) {
			iterator = dataTmp.iterator();
		}
		else {
			iterator = null;
			currentObject = null;
		}
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	// public void setUrl(String url) {
	// String processedUrl = UrlUtils.getProcessedUrl(url, (HttpServletRequest)
	// pageContext.getRequest(),
	// (HttpServletResponse) pageContext.getResponse());
	// stagingConf.put(TableConfig.AJAX_SOURCE, processedUrl);
	// this.dataSourceType = "AJAX";
	// this.url = url;
	// }
}
