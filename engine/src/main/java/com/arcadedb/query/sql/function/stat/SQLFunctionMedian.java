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
package com.arcadedb.query.sql.function.stat;

/**
 * Computes the median for a field. Nulls are ignored in the calculation.
 * <p>
 * Extends and forces the {@link SQLFunctionPercentile} with the 50th percentile.
 *
 * @author Fabrizio Fortino
 */
public class SQLFunctionMedian extends SQLFunctionPercentile {

  public static final String NAME = "median";

  public SQLFunctionMedian() {
    super(NAME);
    this.quantiles.add(.5);
  }

  @Override
  public String getSyntax() {
    return NAME + "(<field>)";
  }
}
