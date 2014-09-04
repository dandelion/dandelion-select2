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
package com.github.dandelion.select2.core.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dandelion.core.asset.generator.jquery.JQueryAssetBuffer;
import com.github.dandelion.core.utils.JsonIndentingWriter;
import com.github.dandelion.select2.core.generator.config.Select2Generator;
import com.github.dandelion.select2.core.html.HtmlSelect;

public class Select2AssetBuffer extends JQueryAssetBuffer {
	
	private Select2AssetBuffer(){
	}
	
	protected static Logger logger = LoggerFactory.getLogger(Select2AssetBuffer.class);

	private String processedId;
	private String originalId;

	private StringBuilder select2Conf;

	public String getProcessedId() {
		return processedId;
	}

	public void setProcessedId(String processedId) {
		this.processedId = processedId;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public StringBuilder getDataTablesConf() {
		return select2Conf;
	}

	public void appendToSelect2Conf(String select2Conf) {
		if (this.select2Conf == null) {
			this.select2Conf = new StringBuilder();
		}
		this.select2Conf.append(select2Conf);
	}

	public static Select2AssetBuffer create(HtmlSelect select){
		
		/**
		 * Main configuration file building
		 */
		Select2AssetBuffer dab = new Select2AssetBuffer();
		
		Select2Generator configGenerator = new Select2Generator();
		Map<String, Object> mainConf = configGenerator.generateConfig(select);

		/**
		 * Main configuration generation
		 */
		logger.debug("Transforming configuration to JSON...");
		// Allways pretty prints the JSON
		try {
			Writer writer = new JsonIndentingWriter();
			JSONValue.writeJSONString(mainConf, writer);
//			dab.appendToSelect2Conf(writer.toString());
			dab.appendToComponentConf("$('#toto').select2();");
//			dab.
		} catch (IOException e) {
			e.printStackTrace();
//			throw new WebResourceGenerationException("Unable to generate the JSON configuration", e);
		}
		
		return dab;
	}
}
