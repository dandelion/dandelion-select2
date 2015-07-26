/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2015 Dandelion
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
package com.github.dandelion.select2.core.config;

import java.util.HashMap;
import java.util.Map;

import com.github.dandelion.core.util.StringUtils;
import com.github.dandelion.select2.core.html.HtmlSelect;
import com.github.dandelion.select2.core.option.Select2Options;

/**
 * <p>
 * Configuration generator of the ddl-s2 component.
 * </p>
 * 
 * @author Thibault Duchateau
 * @since 1.0.0
 */
public class Select2ConfigGenerator {

   /**
    * <p>
    * Generates a map that will be serialized to JSON for the component
    * initialization.
    * </p>
    * 
    * @param select
    *           The bean holding the different options.
    * @return a map that will be serialized to JSON.
    */
   public Map<String, Object> generateConfig(HtmlSelect select) {

      Map<String, Object> mainConf = new HashMap<String, Object>();

      Object placeholder = Select2Options.PLACEHOLDER.valueFrom(select.getSelectConfiguration().getOptions());
      Boolean allowClear = Select2Options.ALLOW_CLEAR.valueFrom(select.getSelectConfiguration().getOptions());
      Boolean multiple = Select2Options.MULTIPLE.valueFrom(select.getSelectConfiguration().getOptions());
      String width = Select2Options.WIDTH.valueFrom(select.getSelectConfiguration().getOptions());
      Boolean tags = Select2Options.TAGS.valueFrom(select.getSelectConfiguration().getOptions());

      if (placeholder != null) {
         mainConf.put("placeholder", placeholder);
      }

      if (allowClear != null) {
         mainConf.put("allowClear", allowClear);
      }

      if (multiple != null) {
         mainConf.put("multiple", multiple);
      }

      if (StringUtils.isNotBlank(width)) {
         mainConf.put("width", width);
      }

      if (tags != null) {
         mainConf.put("tags", tags);
      }

      return mainConf;
   }
}