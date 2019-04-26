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
package io.fares.bind.xjc.plugins.jackson.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.javaparser.ast.body.EnumDeclaration;

import static io.fares.bind.xjc.plugins.jackson.EnumAnnotationProcessor.VALUE_GETTER;
import static io.fares.bind.xjc.plugins.jackson.EnumAnnotationProcessor.VALUE_SETTER;

public class EnumValueAnnotationValidator extends TestValidator {

  public void visit(final EnumDeclaration n, final Void arg) {
    super.visit(n, arg);

    boolean foundValue = n.getMethods().stream()
      .filter(m -> m.isAnnotationPresent(JsonValue.class))
      .map(m -> m.getName().getIdentifier())
      .anyMatch(VALUE_GETTER::equals);

    boolean foundCreator = n.getMethods().stream()
      .filter(m -> m.isAnnotationPresent(JsonCreator.class))
      .map(m -> m.getName().getIdentifier())
      .anyMatch(VALUE_SETTER::equals);

    if (foundValue && foundCreator) foundIt();

  }

}
