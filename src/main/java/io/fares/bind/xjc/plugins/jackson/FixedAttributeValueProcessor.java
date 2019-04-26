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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.codemodel.*;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.xsom.XSAttributeUse;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

public class FixedAttributeValueProcessor {

  void process(ClassOutline classOutline) {

    JDefinedClass implClass = classOutline.getImplClass();

    for (FieldOutline field : classOutline.getDeclaredFields()) {

      JFieldVar jField = findFixedField(implClass, field);

      if (jField != null) {

        CAttributePropertyInfo propertyInfo = (CAttributePropertyInfo) field.getPropertyInfo();

        QName xmlName = propertyInfo.getXmlName();

        String attrName = xmlName.getLocalPart();
        String cap = attrName.substring(0, 1).toUpperCase() + attrName.substring(1);

        // create a new getter for the static value
        JMethod method = implClass.method(JMod.PUBLIC, jField.type(), "get" + cap);

        // return the static field we need for Jackson serialisation
        method.body()._return(JExpr.ref(jField.name()));

        // make sure the method is not processed by JAXB
        method.annotate(XmlTransient.class);

        // make sure Jackson knows its read-only
        JAnnotationUse jsonPropertyUse = method.annotate(JsonProperty.class);
        jsonPropertyUse.param("access", JsonProperty.Access.READ_ONLY);

      }

    }

  }

  private JFieldVar findFixedField(JDefinedClass implClass, FieldOutline field) {

    if (field.getPropertyInfo() instanceof CAttributePropertyInfo &&
      field.getPropertyInfo().getSchemaComponent() instanceof XSAttributeUse) {

      XSAttributeUse attributeUse = (XSAttributeUse) field.getPropertyInfo().getSchemaComponent();

      JFieldVar jField = implClass.fields().get(field.getPropertyInfo().getName(true));

      if (attributeUse.getFixedValue() != null &&
        jField != null &&
        jField.mods().getValue() == (JMod.FINAL | JMod.STATIC | JMod.PUBLIC)
      ) {
        return jField;
      }

    }

    return null;

  }


}
