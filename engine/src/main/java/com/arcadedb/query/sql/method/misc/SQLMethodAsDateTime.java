/*
 * Copyright 2023 Arcade Data Ltd
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.arcadedb.query.sql.method.misc;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.utility.DateUtils;

import java.util.*;

/**
 * Transforms a value to datetime. If the conversion is not possible, null is returned.
 *
 * @author Johann Sorel (Geomatys)
 * @author Luca Garulli (l.garulli--(at)--gmail.com)
 */
public class SQLMethodAsDateTime extends AbstractSQLMethod {

  public static final String NAME = "asdatetime";

  public SQLMethodAsDateTime() {
    super(NAME, 0, 1);
  }

  @Override
  public String getSyntax() {
    return "asDatetime([<format>])";
  }

  @Override
  public Object execute(final Object iThis, final Identifiable iCurrentRecord, final CommandContext context, final Object ioResult, final Object[] iParams) {
    if (iThis == null)
      return null;

    if (iThis instanceof Date)
      return iThis;
    else if (iThis instanceof Number)
      return new Date(((Number) iThis).longValue());

    final String format = iParams.length > 0 ? iParams[0].toString() : context.getDatabase().getSchema().getDateTimeFormat();
    final Object date = DateUtils.parse(iThis.toString(), format);

    return DateUtils.getDate(date, context.getDatabase().getSerializer().getDateTimeImplementation());
  }
}
