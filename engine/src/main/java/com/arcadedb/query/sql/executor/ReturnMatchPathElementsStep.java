/*
 * Copyright © 2021-present Arcade Data Ltd (info@arcadedata.com)
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
 * SPDX-FileCopyrightText: 2021-present Arcade Data Ltd (info@arcadedata.com)
 * SPDX-License-Identifier: Apache-2.0
 */
package com.arcadedb.query.sql.executor;

import com.arcadedb.database.Identifiable;

import java.util.*;

/**
 * Created by luigidellaquila on 12/10/16.
 */
public class ReturnMatchPathElementsStep extends AbstractUnrollStep {

  public ReturnMatchPathElementsStep(final CommandContext context, final boolean profilingEnabled) {
    super(context, profilingEnabled);
  }

  @Override
  protected Collection<Result> unroll(final Result doc, final CommandContext iContext) {
    final List<Result> result = new ArrayList<>();
    for (final String s : doc.getPropertyNames()) {
      Object elem = doc.getProperty(s);
      if (elem instanceof Identifiable) {
        final ResultInternal newelem = new ResultInternal();
        newelem.setElement(((Identifiable) elem).asDocument());
        elem = newelem;
      }
      if (elem instanceof Result) {
        result.add((Result) elem);
      }
      //else...? TODO
    }
    return result;
  }

  @Override
  public String prettyPrint(final int depth, final int indent) {
    final String spaces = ExecutionStepInternal.getIndent(depth, indent);
    return spaces + "+ UNROLL $pathElements";
  }
}
