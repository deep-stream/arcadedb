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
 */
/* Generated By:JJTree: Do not edit this line. OFromClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;

public class FromClause extends SimpleNode {

  FromItem item;

  public FromClause(int id) {
    super(id);
  }

  public FromClause(SqlParser p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    if (item != null) {
      item.toString(params, builder);
    }
  }


  public FromItem getItem() {
    return item;
  }

  public void setItem(FromItem item) {
    this.item = item;
  }

  public FromClause copy() {
    FromClause result= new FromClause(-1);
    result.item = item.copy();
    return result;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    FromClause that = (FromClause) o;

    return item != null ? item.equals(that.item) : that.item == null;
  }

  @Override public int hashCode() {
    return item != null ? item.hashCode() : 0;
  }

  public Result serialize() {
    ResultInternal result = new ResultInternal();
    result.setProperty("item", item.serialize());
    return result;
  }

  public void deserialize(Result fromResult) {
    item = new FromItem(-1);
    item.deserialize(fromResult.getProperty("item"));
  }

  public boolean isCacheable() {
    return item.isCacheable();
  }
}
/* JavaCC - OriginalChecksum=051839d20dabfa4cce26ebcbe0d03a86 (do not edit this line) */
