/*
 * [The "BSD licence"]
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
package com.github.dandelion.select2.core.generator.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dandelion.select2.core.html.HtmlSelect;

/**
 * <p>
 * Generator for the Datatables configuration.
 * 
 * @author Thibault Duchateau
 */
public class Select2Generator {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(Select2Generator.class);

	/**
	 * If no custom config is specified with table attributes, DataTables will
	 * internally use default one.
	 * 
	 * @param table
	 *            The POJO containing the HTML table.
	 * @return MainConf The main configuration file associated with the HTML
	 *         table.
	 */
	public Map<String, Object> generateConfig(HtmlSelect table) {

//		TableConfiguration tableConfiguration = table.getTableConfiguration();
//		logger.debug("Generating DataTables configuration ..");
//
		// Main configuration object
		Map<String, Object> mainConf = new HashMap<String, Object>();
//
//		generateColumnConfiguration(mainConf, table, tableConfiguration);
//		generateI18nConfiguration(mainConf, tableConfiguration);
//		generateFeatureEnablementConfiguration(mainConf, tableConfiguration);
//		generateScrollingConfiguration(mainConf, tableConfiguration);
//		generateMiscConfiguration(mainConf, tableConfiguration);
//		generateAjaxConfiguration(mainConf, tableConfiguration);
//		generateCallbackConfiguration(mainConf, tableConfiguration);
//
//		logger.debug("DataTables configuration generated");

		return mainConf;
	}

}