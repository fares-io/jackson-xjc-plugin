/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package io.fares.bind.xjc.plugins.jackson;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.Outline;
import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.xml.sax.ErrorHandler;

import javax.xml.namespace.QName;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JacksonPlugin extends AbstractParameterizablePlugin {

  public static final String NS = "http://jaxb2-commons.dev.java.net/basic/jackson";

  private EnumAnnotationProcessor enumAnnotationProcessor = new EnumAnnotationProcessor();

  @Override
  public String getOptionName() {
    return "Xjackson";
  }

  @Override
  public String getUsage() {
    return "  -Xjackson          : enable jackson annotation generation";
  }

  @Override
  public List<String> getCustomizationURIs() {
    return Collections.singletonList(NS);
  }

  @Override
  public Collection<QName> getCustomizationElementNames() {
    return Collections.emptyList();
  }

  @Override
  public void postProcessModel(Model model, ErrorHandler errorHandler) {

    for (final CClassInfo classInfo : model.beans().values()) {
      postProcessClassInfo(model, classInfo);
    }

  }

  private void postProcessClassInfo(final Model model, final CClassInfo classInfo) {
    // no-op for now
  }

  @Override
  public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {

    JCodeModel model = outline.getCodeModel();

    for (final EnumOutline enumOutline : outline.getEnums()) {
      enumAnnotationProcessor.process(model, enumOutline);
    }

    return true;

  }


}
