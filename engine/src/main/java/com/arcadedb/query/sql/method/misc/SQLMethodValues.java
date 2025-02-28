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
package com.arcadedb.query.sql.method.misc;

import com.arcadedb.database.Document;
import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;

import java.util.*;

/**
 * Returns the values of a map.
 *
 * @author Luca Garulli (l.garulli--(at)--gmail.com)
 */
public class SQLMethodValues extends AbstractSQLMethod {

  public static final String NAME = "values";

  public SQLMethodValues() {
    super(NAME);
  }

  @Override
  public Object execute(final Object iThis, final Identifiable iCurrentRecord, final CommandContext iContext, final Object ioResult, final Object[] iParams) {
    if (ioResult instanceof Map)
      return ((Map<?, ?>) ioResult).values();
    else if (ioResult instanceof Document)
      return List.of(((Document) ioResult).toMap().values());
    else if (ioResult instanceof Result) {
      final Result res = (Result) ioResult;
      return res.toMap().values();
    }
    return null;
  }
}
