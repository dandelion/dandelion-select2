/*
 * Copyright (c) 2012 Dandelion
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
package com.github.dandelion.select2.core.html;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.dandelion.core.html.HtmlTag;

/**
 * Plain old HTML <code>select</code> tag.
 * 
 * @author Thibault Duchateau
 * @since 0.11.0
 */
public class HtmlSelect extends HtmlTag {

	// Internal attributes
	private String originalId;
	private List<HtmlOption> options = new LinkedList<HtmlOption>();
	
//	private HtmlCaption caption;
//	private List<HtmlRow> head = new LinkedList<HtmlRow>();
//	private List<HtmlRow> body = new LinkedList<HtmlRow>();
//	private List<HtmlRow> foot = new LinkedList<HtmlRow>();
//	private TableConfiguration tableConfiguration;

	public HtmlSelect(String id, HttpServletRequest request, HttpServletResponse response) {
		this(id, request, response, null, null);
	}

	public HtmlSelect(String id, HttpServletRequest request, HttpServletResponse response, String groupName) {
		this(id, request, response, groupName, null);
	}

	public HtmlSelect(String id, HttpServletRequest request, HttpServletResponse response, String groupName, Map<String, String> dynamicAttributes) {
		this.tag = "select";
		this.originalId = id;
		this.id = processId(id);
		this.dynamicAttributes = dynamicAttributes;
	}

	protected StringBuilder getHtmlAttributes() {
		StringBuilder html = new StringBuilder();
		html.append(writeAttribute("id", this.id));
//		html.append(writeAttribute("class", TableConfig.CSS_CLASS.valueFrom(this.tableConfiguration)));
//		html.append(writeAttribute("style", TableConfig.CSS_STYLE.valueFrom(this.tableConfiguration)));
		return html;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	private String processId(String id){
		return id.replaceAll("[^A-Za-z0-9 ]", "");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder toHtml() {
		StringBuilder html = new StringBuilder();
		html.append(getHtmlOpeningTag());
		html.append(getHtmlBody());
		html.append(getHtmlClosingTag());
		return html;
	}
	
	private StringBuilder getHtmlBody() {
		StringBuilder html = new StringBuilder();
		for (HtmlOption option : this.options) {
			html.append(option.toHtml());
		}
		return html;
	}
	
	public void addOption(HtmlOption htmlOption){
		options.add(htmlOption);
	}
}