/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.ops;

import java.util.List;

import org.apache.drill.exec.memory.BufferAllocator;
import org.apache.drill.exec.proto.UserBitShared.MinorFragmentProfile;

import com.codahale.metrics.MetricRegistry;
import com.google.common.collect.Lists;

public class FragmentStats {
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FragmentStats.class);


  private List<OperatorStats> operators = Lists.newArrayList();
  private final long startTime;

  public FragmentStats(MetricRegistry metrics) {
    this.startTime = System.currentTimeMillis();
  }

  public void addMetricsToStatus(MinorFragmentProfile.Builder prfB) {

    prfB.setStartTime(startTime);
    prfB.setEndTime(System.currentTimeMillis());

    for(OperatorStats o : operators){
      prfB.addOperatorProfile(o.getProfile());
    }
  }

  public OperatorStats getOperatorStats(OpProfileDef profileDef, BufferAllocator allocator){
    OperatorStats stats = new OperatorStats(profileDef, allocator);
    if(profileDef.operatorType != -1){
      operators.add(stats);
    }
    return stats;
  }

  public void addOperatorStats(OperatorStats stats) {
    operators.add(stats);
  }

}
