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

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.fares.bind.xjc.plugins.jackson.validators.TestValidator;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJacksonPluginTest extends RunXJC2Mojo {

  AbstractXJC2Mojo mojoUnderTest;

  @Override
  public void testExecute() throws Exception {

    super.testExecute();
    // need to load test.Context from sources and see if we have a extension list prop
    File genDir = mojoUnderTest.getGenerateDirectory();

    final File clazzFile = getClassFile(genDir);

    assertNotNull(clazzFile);
    assertTrue(clazzFile.exists());
    assertTrue(clazzFile.isFile());

    try {

      CompilationUnit unit = StaticJavaParser.parse(clazzFile);

      TestValidator validator = getValidator();
      validator.visit(unit, null);

      assertTrue("Could not validate", validator.isFound());

    } catch (FileNotFoundException e) {
      fail("not expecting parse failures");
    }

  }

  @Override
  protected void configureMojo(AbstractXJC2Mojo mojo) {
    super.configureMojo(mojo);
    mojo.setForceRegenerate(true);
    mojo.setDebug(false);
    this.mojoUnderTest = mojo;
  }

  @Override
  public List<String> getArgs() {
    final List<String> args = new ArrayList<>(super.getArgs());
    args.add("-extension");
    args.add("-Xjackson");
    return args;
  }

  protected abstract File getClassFile(File genDir);

  protected abstract TestValidator getValidator();

}
