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

import io.fares.bind.xjc.plugins.jackson.validators.FixedAttributeValueValidator;
import io.fares.bind.xjc.plugins.jackson.validators.FixedElementValueValidator;
import io.fares.bind.xjc.plugins.jackson.validators.TestValidator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FixedElementValueTest extends AbstractJacksonPluginTest {

  @Override
  public File getSchemaDirectory() {
    return new File(getBaseDir(), "src/test/resources/schemas/FixedElementValue");
  }

  @Override
  protected File getClassFile(File genDir) {
    return new File(genDir, "testfixedelement/Book.java");
  }

  @Override
  protected TestValidator getValidator() {
    return new FixedElementValueValidator();
  }

  @Override
  public List<String> getArgs() {
    final List<String> args = new ArrayList<>(super.getArgs());
    args.add("-Xfixed-value");
    return args;
  }

}
