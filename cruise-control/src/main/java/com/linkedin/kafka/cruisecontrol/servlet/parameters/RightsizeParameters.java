/*
 * Copyright 2018 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.servlet.parameters;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.linkedin.kafka.cruisecontrol.servlet.parameters.ParameterUtils.*;


public class RightsizeParameters extends AbstractParameters {
  protected static final SortedSet<String> CASE_INSENSITIVE_PARAMETER_NAMES;
  static {
    SortedSet<String> validParameterNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    validParameterNames.add(BROKER_DIFF);
    validParameterNames.add(PARTITION_COUNT);
    validParameterNames.add(TOPIC_NAME);
    validParameterNames.addAll(KafkaOptimizationParameters.CASE_INSENSITIVE_PARAMETER_NAMES);
    CASE_INSENSITIVE_PARAMETER_NAMES = Collections.unmodifiableSortedSet(validParameterNames);
  }
  protected int _brokerDiff;
  protected int _partitionCount;
  protected String _topicName;

  public RightsizeParameters() {
    super();
  }

  @Override
  protected void initParameters() throws UnsupportedEncodingException {
    super.initParameters();
    _brokerDiff = ParameterUtils.brokerDiff(_request);
    _partitionCount = ParameterUtils.partitionCount(_request);
    _topicName = ParameterUtils.topicName(_request);
  }

  public int brokerDiff() {
    return _brokerDiff;
  }

  public int partitionCount() {
    return _partitionCount;
  }

  public String topicName() {
    return _topicName;
  }

  @Override
  public SortedSet<String> caseInsensitiveParameterNames() {
    return CASE_INSENSITIVE_PARAMETER_NAMES;
  }
}
