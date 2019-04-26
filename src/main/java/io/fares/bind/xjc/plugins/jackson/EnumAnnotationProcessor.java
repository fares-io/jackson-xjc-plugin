/*
 * Copyright 2019 Niels Bertram
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.fares.bind.xjc.plugins.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.outline.EnumOutline;

public class EnumAnnotationProcessor {

  public static final String VALUE_GETTER = "value";

 public  static final String VALUE_SETTER = "fromValue";

  void process(JCodeModel model, EnumOutline enumOutline) {

    final JDefinedClass theClass = enumOutline.clazz;

    JMethod valueGetterMethod = theClass.getMethod(VALUE_GETTER, new JType[]{});
    JMethod valueSetterMethod = theClass.getMethod(VALUE_SETTER, new JType[]{model.ref(String.class)});

    if (valueGetterMethod != null && valueSetterMethod != null) {
      valueGetterMethod.annotate(JsonValue.class);
      valueSetterMethod.annotate(JsonCreator.class);
    }

  }

}
