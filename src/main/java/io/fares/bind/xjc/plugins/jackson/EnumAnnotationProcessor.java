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
