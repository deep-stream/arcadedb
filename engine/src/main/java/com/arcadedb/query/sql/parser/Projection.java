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
/* Generated By:JJTree: Do not edit this line. OProjection.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Document;
import com.arcadedb.exception.CommandSQLParsingException;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;
import java.util.stream.*;

public class Projection extends SimpleNode {

  protected boolean distinct = false;

  List<ProjectionItem> items;
  // runtime
  private Set<String> excludes;

  public Projection(List<ProjectionItem> items, boolean distinct) {
    super(-1);
    this.items = items;
    this.distinct = distinct;
    //TODO make the whole class immutable!
  }

  public Projection(int id) {
    super(id);
  }

  public Projection(SqlParser p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public List<ProjectionItem> getItems() {
    return items;
  }

  public void setItems(List<ProjectionItem> items) {
    this.items = items;
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    if (items == null) {
      return;
    }
    boolean first = true;

    if (distinct) {
      builder.append("DISTINCT ");
    }
    // print * before
    for (ProjectionItem item : items) {
      if (item.isAll()) {
        if (!first) {
          builder.append(", ");
        }

        item.toString(params, builder);
        first = false;
      }
    }

    // and then the rest of the projections
    for (ProjectionItem item : items) {
      if (!item.isAll()) {
        if (!first) {
          builder.append(", ");
        }

        item.toString(params, builder);
        first = false;
      }
    }
  }

  public Result calculateSingle(CommandContext iContext, Result iRecord) {
    initExcludes(iContext);
    if (isExpand()) {
      throw new IllegalStateException("This is an expand projection, it cannot be calculated as a single result" + this);
    }

    if (items.size() == 0 || (items.size() == 1 && items.get(0).isAll()) && items.get(0).nestedProjection == null) {
      return iRecord;
    }

    ResultInternal result = new ResultInternal();
    for (ProjectionItem item : items) {
      if (item.exclude) {
        continue;
      }
      if (item.isAll()) {
        for (String alias : iRecord.getPropertyNames()) {
          if (this.excludes.contains(alias)) {
            continue;
          }
          Object val = item.convert(iRecord.getProperty(alias));
          if (item.nestedProjection != null) {
            val = item.nestedProjection.apply(item.expression, val, iContext);
          }
          result.setProperty(alias, val);
        }
        if (iRecord.getElement().isPresent()) {
          Document x = iRecord.getElement().get();
          if (!this.excludes.contains("@rid")) {
            result.setProperty("@rid", x.getIdentity());
          }
          if (!this.excludes.contains("@type")) {
            result.setProperty("@type", x.getType().getName());
          }
        }
      } else {
        result.setProperty(item.getProjectionAliasAsString(), item.execute(iRecord, iContext));
      }
    }

    for (String key : iRecord.getMetadataKeys()) {
      if (!result.getMetadataKeys().contains(key)) {
        result.setMetadata(key, iRecord.getMetadata(key));
      }
    }
    return result;
  }

  private void initExcludes(CommandContext iContext) {
    if (excludes == null) {
      this.excludes = new HashSet<String>();
      for (ProjectionItem item : items) {
        if (item.exclude) {
          this.excludes.add(item.getProjectionAliasAsString());
        }
      }
    }
  }

  public boolean isExpand() {
    return items != null && items.size() == 1 && items.get(0).isExpand();
  }

  public void validate() {
    if (items != null && items.size() > 1) {
      for (ProjectionItem item : items) {
        if (item.isExpand()) {
          throw new CommandSQLParsingException("Cannot execute a query with expand() together with other projections");
        }
      }
    }
  }

  public Projection getExpandContent() {
    Projection result = new Projection(-1);
    result.setItems(new ArrayList<>());
    result.getItems().add(this.getItems().get(0).getExpandContent());
    return result;
  }

  public Set<String> getAllAliases() {
    return items.stream().map(i -> i.getProjectionAliasAsString()).collect(Collectors.toSet());
  }

  public Projection copy() {
    Projection result = new Projection(-1);
    if (items != null) {
      result.items = items.stream().map(x -> x.copy()).collect(Collectors.toList());
    }
    result.distinct = distinct;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Projection that = (Projection) o;

    return items != null ? items.equals(that.items) : that.items == null;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public void extractSubQueries(SubQueryCollector collector) {
    if (items != null) {
      for (ProjectionItem item : items) {
        item.extractSubQueries(collector);
      }
    }
  }

  public boolean refersToParent() {
    for (ProjectionItem item : items) {
      if (item.refersToParent()) {
        return true;
      }
    }
    return false;
  }

  public Result serialize() {
    ResultInternal result = new ResultInternal();
    result.setProperty("distinct", distinct);
    if (items != null) {
      result.setProperty("items", items.stream().map(x -> x.serialize()).collect(Collectors.toList()));
    }
    return result;
  }

  public void deserialize(Result fromResult) {
    distinct = fromResult.getProperty("distinct");
    if (fromResult.getProperty("items") != null) {
      items = new ArrayList<>();

      List<Result> ser = fromResult.getProperty("items");
      for (Result x : ser) {
        ProjectionItem item = new ProjectionItem(-1);
        item.deserialize(x);
        items.add(item);
      }
    }
  }

  public boolean isCacheable() {
    if (items != null) {
      for (ProjectionItem item : items) {
        if (!item.isCacheable()) {
          return false;
        }
      }
    }
    return true;
  }
}
/* JavaCC - OriginalChecksum=3a650307b53bae626dc063c4b35e62c3 (do not edit this line) */
