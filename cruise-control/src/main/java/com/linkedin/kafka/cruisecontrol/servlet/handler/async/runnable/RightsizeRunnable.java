/*
 * Copyright 2018 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.servlet.handler.async.runnable;

import com.linkedin.kafka.cruisecontrol.KafkaCruiseControl;
import com.linkedin.kafka.cruisecontrol.analyzer.ProvisionRecommendation;
import com.linkedin.kafka.cruisecontrol.analyzer.ProvisionStatus;
import com.linkedin.kafka.cruisecontrol.config.KafkaCruiseControlConfig;
import com.linkedin.kafka.cruisecontrol.config.constants.AnomalyDetectorConfig;
import com.linkedin.kafka.cruisecontrol.detector.Provisioner;
import com.linkedin.kafka.cruisecontrol.detector.ProvisionerState;
import com.linkedin.kafka.cruisecontrol.servlet.parameters.RightsizeParameters;
import com.linkedin.kafka.cruisecontrol.servlet.response.RightsizeResult;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.KAFKA_CRUISE_CONTROL_OBJECT_CONFIG;

/**
 * The async runnable for testing resizing
 */
public class RightsizeRunnable extends OperationRunnable {
  private static final String RECOMMENDER_UP = "Recommender-Under-Provisioned";
  protected final RightsizeParameters _parameters;

  public RightsizeRunnable(KafkaCruiseControl kafkaCruiseControl, OperationFuture future, RightsizeParameters parameters) {
    super(kafkaCruiseControl, future);
    _parameters = parameters;
  }

  @Override
  protected RightsizeResult getResult() throws Exception {
    KafkaCruiseControlConfig config = _kafkaCruiseControl.config();
    Map<String, Object> overrideConfigs;
    overrideConfigs = Collections.singletonMap(KAFKA_CRUISE_CONTROL_OBJECT_CONFIG, _kafkaCruiseControl);
    Provisioner provisioner = config.getConfiguredInstance(AnomalyDetectorConfig.PROVISIONER_CLASS_CONFIG,
                                                         Provisioner.class,
                                                         overrideConfigs);
    Map<String, ProvisionRecommendation> provisionRecommendation = new HashMap<>();
    ProvisionRecommendation recommendation =
        new ProvisionRecommendation.Builder(ProvisionStatus.UNDER_PROVISIONED).numBrokers(_parameters.brokerDiff())
                                                                              .numPartitions(_parameters.partitionCount())
                                                                              .topic(_parameters.topicName())
                                                                              .build();
    provisionRecommendation.put(RECOMMENDER_UP, recommendation);
    ProvisionerState rightsizeResults = provisioner.rightsize(provisionRecommendation);

    return new RightsizeResult(_parameters.brokerDiff(), _parameters.partitionCount(), _parameters.topicName(), rightsizeResults, config);
  }
}
