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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dandelion.core.asset.generator.js.jquery.JQueryContent;
import com.github.dandelion.core.asset.generator.js.jquery.JQueryJsContentGenerator;
import com.github.dandelion.core.option.Option;
import com.github.dandelion.core.util.OptionUtils;
import com.github.dandelion.core.web.AssetRequestContext;
import com.github.dandelion.select2.core.Select2Component;
import com.github.dandelion.select2.core.generator.Select2JQueryContent;
import com.github.dandelion.select2.core.html.HtmlOption;
import com.github.dandelion.select2.core.html.HtmlSelect;
import com.github.dandelion.select2.core.option.Select2Options;

/**
 * <p>
 * JSP tag used for creating HTML select.
 * </p>
 * 
 * @author Thibault Duchateau
 * @since 1.0.0
 */
public class SelectTag extends TagSupport implements DynamicAttributes {

   private static final long serialVersionUID = 1528832829817373708L;

   protected static Logger logger = LoggerFactory.getLogger(SelectTag.class);

   protected String id;
   protected String itemValue;
   protected String itemLabel;

   protected Object data;

   /**
    * Map containing the staging options to be applied to the select at the end
    * of the tag processing.
    */
   private Map<Option<?>, Object> stagingOptions;

   /**
    * The map of dynamic attributes that will be set as-is on the select tag.
    */
   protected Map<String, String> dynamicAttributes;

   /**
    * The select tag to be built.
    */
   protected HtmlSelect select;

   protected Object currentObject;

   /**
    * The iterator to be used to feed the table with the provided DOM source.
    */
   private Iterator<Object> iterator;

   /**
    * The type of data source configured in the tag. Depending on this type, the
    * tag won't behaviour in the same way.
    */
   private String dataSourceType;

   /**
    * The current request.
    */
   protected HttpServletRequest request;

   /**
    * The current response.
    */
   protected HttpServletResponse response;

   public SelectTag() {
      this.stagingOptions = new HashMap<Option<?>, Object>();
   }

   @Override
   public int doStartTag() throws JspException {

      request = (HttpServletRequest) pageContext.getRequest();
      response = (HttpServletResponse) pageContext.getResponse();

      select = new HtmlSelect(id, request, response, null, dynamicAttributes);
      // HtmlOption op = new HtmlOption();
      // op.setValue("");
      // select.addOption(op);
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
         throw new JspException(e);
      }
      catch (InvocationTargetException e) {
         throw new JspException(e);
      }
      catch (NoSuchMethodException e) {
         throw new JspException(e);
      }
      return super.doStartTag();
   }

   @Override
   public int doEndTag() throws JspException {

      // At this point, all setters have been called and the staging
      // configuration map should have been filled with user configuration
      // The user configuration can now be applied to the default
      // configuration
      this.select.getSelectConfiguration().getOptions().putAll(this.stagingOptions);

      // Once all configuration are merged, they can be processed
      OptionUtils.processOptions(this.select.getSelectConfiguration().getOptions(), request);

      return setupHtmlGeneration();
   }

   @Override
   public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
      validateDynamicAttribute(localName, value);

      if (this.dynamicAttributes == null) {
         this.dynamicAttributes = new HashMap<String, String>();
      }

      this.dynamicAttributes.put(localName, (String) value);
   }

   protected int setupHtmlGeneration() throws JspException {

      JQueryContent datatableContent = new Select2JQueryContent(this.select);

      // Get the existing JavaScript generator or create it if it doesn't exist
      JQueryJsContentGenerator javascriptGenerator = (JQueryJsContentGenerator) AssetRequestContext.get(this.request)
            .getGenerator(Select2Component.COMPONENT_NAME);

      if (javascriptGenerator == null) {
         javascriptGenerator = new JQueryJsContentGenerator(datatableContent);
      }
      else {
         javascriptGenerator.appendContent(datatableContent);
      }

      // Update the asset request context with the enabled bundles and
      // Javascript generator
      AssetRequestContext.get(this.request).addBundles("ddl-s2").addGenerator(Select2Component.COMPONENT_NAME,
            javascriptGenerator);

      try {
         this.pageContext.getOut().println(this.select.toHtml());

      }
      catch (IOException e) {
         throw new JspException("Unable to generate the HTML markup for the table " + id, e);
      }

      return EVAL_PAGE;
   }

   public Map<Option<?>, Object> getStagingOptions() {
      return stagingOptions;
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

   /**
    * <p>
    * Validates the passed dynamic attribute.
    * </p>
    * <p>
    * The dynamic attribute must not conflict with other attributes and must
    * have a valid type.
    * </p>
    * 
    * @param localName
    *           Name of the dynamic attribute.
    * @param value
    *           Value of the dynamic attribute.
    */
   private void validateDynamicAttribute(String localName, Object value) {
      if (!(value instanceof String)) {
         throw new IllegalArgumentException(
               "The attribute " + localName + " won't be added to the table. Only string values are accepted.");
      }
   }

   public void setPlaceholder(String placeholder) {
      stagingOptions.put(Select2Options.PLACEHOLDER, placeholder);
   }

   public void setAllowClear(Boolean allowClear) {
      stagingOptions.put(Select2Options.ALLOW_CLEAR, allowClear);
   }

   public void setMultiple(Boolean multiple) {
      stagingOptions.put(Select2Options.MULTIPLE, multiple);
   }

   public void setWidth(String width) {
      stagingOptions.put(Select2Options.WIDTH, width);
   }

   public void setTags(Boolean tags) {
      stagingOptions.put(Select2Options.TAGS, tags);
   }
}
