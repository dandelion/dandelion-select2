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
package com.github.dandelion.select2.core.option;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.github.dandelion.core.option.BooleanProcessor;
import com.github.dandelion.core.option.Option;
import com.github.dandelion.core.option.StringProcessor;
import com.github.dandelion.select2.core.option.processor.PlaceholderProcessor;

/**
 * <p>
 * Registry of all possible {@link Option}s for the ddl-s2 component.
 * </p>
 * 
 * @author Thibault Duchateau
 * @since 1.0.0
 */
public final class Select2Options {

   /**
    * Map that associates each {@link Option} with its name in order to
    * facilitate the search for an {@link Option}. See
    * {@link #findByName(String)}.
    */
   private static final Map<String, Option<?>> OPTIONS_BY_NAME;

   public static final Option<Object> PLACEHOLDER = new Option<Object>("placeholder", new PlaceholderProcessor(), 100);
   public static final Option<Boolean> ALLOW_CLEAR = new Option<Boolean>("allowClear", new BooleanProcessor(), 100);
   public static final Option<Boolean> MULTIPLE = new Option<Boolean>("multiple", new BooleanProcessor(), 100);
   public static final Option<String> WIDTH = new Option<String>("width", new StringProcessor(), 100);
   public static final Option<Boolean> TAGS = new Option<Boolean>("tags", new BooleanProcessor(), 100);

   /**
    * <p>
    * Searches for the {@link Option} corresponding to the provided
    * {@code optionName}.
    * </p>
    * 
    * @param optionName
    *           The normalized name of the {@link Option} to search for.
    * @return the corresponding {@link Option}.
    */
   public static Option<?> findByName(String optionName) {

      String normalizedKey = optionName.trim().toLowerCase();
      if (OPTIONS_BY_NAME.containsKey(normalizedKey)) {
         return OPTIONS_BY_NAME.get(normalizedKey);
      }

      return null;
   }

   /**
    * <p>
    * Initializes the {@code internalConf} map to simplify the search of
    * options. All option names are normalized before being stored.
    * </p>
    */
   static {

      OPTIONS_BY_NAME = new HashMap<String, Option<?>>();

      Field[] fields = Select2Options.class.getDeclaredFields();

      // Filter only Options
      for (Field field : fields) {
         if (field.getType() == Option.class) {
            try {
               Option<?> ct = (Option<?>) field.get(null);
               OPTIONS_BY_NAME.put(ct.getName().trim().toLowerCase(), ct);
            }
            catch (Exception e) {
               // Should never happen
            }
         }
      }
   }
}