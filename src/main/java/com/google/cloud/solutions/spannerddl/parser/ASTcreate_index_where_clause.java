/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.solutions.spannerddl.parser;

import static com.google.cloud.solutions.spannerddl.diff.AstTreeUtils.validateChildrenClass;

import com.google.cloud.solutions.spannerddl.diff.AstTreeUtils;
import com.google.common.base.Joiner;
import java.util.List;
import java.util.stream.Collectors;

public class ASTcreate_index_where_clause extends SimpleNode {
  public ASTcreate_index_where_clause(int id) {
    super(id);
  }

  public ASTcreate_index_where_clause(DdlParser p, int id) {
    super(p, id);
  }

  /**
   * void create_index_where_clause() : {} { path() <IS> <NOT> <NULLL> (<AND> path() <IS> <NOT>
   * <NULLL>)* }
   */
  @Override
  public String toString() {
    validateChildrenClass(children, ASTpath.class);

    // convert paths object to "pathName IS NOT NULL" to make joining easier.
    List<String> paths =
        AstTreeUtils.getChildrenAssertType(children, ASTpath.class).stream()
            .map((ASTpath path) -> path.toString() + " IS NOT NULL")
            .collect(Collectors.toList());

    return "WHERE " + Joiner.on(" AND ").join(paths);
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
