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
package com.github.dandelion.select2.core.option.processor;

import java.util.HashMap;
import java.util.Map;

import com.github.dandelion.core.option.AbstractOptionProcessor;
import com.github.dandelion.core.option.OptionProcessingContext;
import com.github.dandelion.core.util.StringUtils;
import com.github.dandelion.select2.core.option.Select2Options;

/**
 * <p>
 * Processor to be applied on the {@link Select2Options#PLACEHOLDER} option.
 * </p>
 * <p>
 * This processor can return either a {@link String} or a {@link Map}.
 * </p>
 * 
 * @author Thibault Duchateau
 * @since 1.0.0
 * @see Select2Options#PLACEHOLDER
 */
public class PlaceholderProcessor extends AbstractOptionProcessor {

   private static final String KEY_ID = "id";
   private static final String KEY_TEXT = "text";

   @Override
   protected Object getProcessedValue(OptionProcessingContext context) {

      String value = context.getValueAsString();

      if (StringUtils.isNotBlank(value)) {
         if (value.contains(",")) {
            Map<String, String> retval = new HashMap<String, String>();
            retval.put(KEY_ID, value.split(",")[0]);
            retval.put(KEY_TEXT, value.split(",")[1]);
            return retval;
         }
         else {
            return context.getValueAsString().trim();
         }
      }

      return null;
   }
}
