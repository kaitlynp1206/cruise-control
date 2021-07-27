/*
 * Copyright 2017 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.detector;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import static com.linkedin.kafka.cruisecontrol.KafkaCruiseControlUtils.maybeIncreasePartitionCount;

/**
 * A util class for provisions.
 */
public final class ProvisionerUtils {

  private ProvisionerUtils() {
  }

  /**
   * Determine the status of increasing the partition count
   *
   * @param adminClient AdminClient to handle partition update requests.
   * @param provisionerTopic Existing topic to add more partitions if needed
   * @return The state COMPLETE when true, COMPLETED_WITH_EXCEPTION when false
   */
  public static ProvisionerState partitionIncreaseStatus(AdminClient adminClient, NewTopic provisionerTopic) {
    if (maybeIncreasePartitionCount(adminClient, provisionerTopic)) {
      String summary = "Provisioning the partition with the topic " + provisionerTopic.name() + " was successfully completed.";
      return new ProvisionerState(ProvisionerState.State.COMPLETE, summary);
    } else {
      String summary = "Provisioning the partition with the topic " + provisionerTopic.name() + " was rejected.";
      return new ProvisionerState(ProvisionerState.State.COMPLETED_WITH_ERROR, summary);
    }
  }
}
