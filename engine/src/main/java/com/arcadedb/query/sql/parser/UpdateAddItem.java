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
/* Generated By:JJTree: Do not edit this line. OUpdateAddItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import java.util.*;

public class UpdateAddItem extends SimpleNode {

  protected Identifier left;
  protected Expression right;

  public UpdateAddItem(int id) {
    super(id);
  }

  public UpdateAddItem(SqlParser p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }


  public void toString(Map<Object, Object> params, StringBuilder builder) {
    left.toString(params, builder);
    builder.append(" = ");
    right.toString(params, builder);
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    UpdateAddItem that = (UpdateAddItem) o;

    if (left != null ? !left.equals(that.left) : that.left != null)
      return false;
    return right != null ? right.equals(that.right) : that.right == null;
  }

  @Override public int hashCode() {
    int result = left != null ? left.hashCode() : 0;
    result = 31 * result + (right != null ? right.hashCode() : 0);
    return result;
  }
}
/* JavaCC - OriginalChecksum=769679aa2d2d8df58a13210152b50a9d (do not edit this line) */
