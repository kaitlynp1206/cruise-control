/*
 * Copyright 2019 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.servlet.response;

import com.google.gson.Gson;
import com.linkedin.cruisecontrol.servlet.parameters.CruiseControlParameters;
import com.linkedin.kafka.cruisecontrol.config.KafkaCruiseControlConfig;
import com.linkedin.kafka.cruisecontrol.detector.ProvisionerState;
import java.util.HashMap;
import java.util.Map;



@JsonResponseClass
public class RightsizeResult extends AbstractCruiseControlResponse {
  @JsonResponseField
  protected static final String BROKER_DIFF = "broker-diff";
  @JsonResponseField
  protected static final String PARTITION_COUNT = "partition-count";
  @JsonResponseField
  protected static final String TOPIC_NAME = "topic-name";
  @JsonResponseField
  protected static final String PROVISION_STATE = "provision-state";
  @JsonResponseField
  protected static final String PROVISION_SUMMARY = "provision-summary";

  protected final int _brokerDiff;
  protected final int _partitionCount;
  protected String _topicName;
  protected String _provisionState;
  protected String _provisionSummary;

  public RightsizeResult(int brokerDiff, int partitionCount, String topicName, ProvisionerState provisionerState, KafkaCruiseControlConfig config) {
    super(config);
    _brokerDiff = brokerDiff;
    _partitionCount = partitionCount;
    _topicName = topicName;
    _provisionState = provisionerState.state().toString();
    _provisionSummary = provisionerState.summary();
  }

  protected String getJSONString() {
    Map<String, Object> jsonStructure = new HashMap<>(5);
    jsonStructure.put(BROKER_DIFF, _brokerDiff);
    jsonStructure.put(PARTITION_COUNT, _partitionCount);
    if (_topicName != null && !_topicName.isEmpty()) {
      jsonStructure.put(TOPIC_NAME, _topicName);
    }
    if (_provisionState != null && !_provisionState.isEmpty()) {
      jsonStructure.put(PROVISION_STATE, _provisionState);
    }
    if (_provisionSummary != null && !_provisionSummary.isEmpty()) {
      jsonStructure.put(PROVISION_SUMMARY, _provisionSummary);
    }
    Gson gson = new Gson();
    return gson.toJson(jsonStructure);
  }

  @Override
  protected void discardIrrelevantAndCacheRelevant(CruiseControlParameters parameters) {
    _cachedResponse = getJSONString();
    // Discard irrelevant response.
    _topicName = null;
    _provisionState = null;
    _provisionSummary = null;
  }
}
