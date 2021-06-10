/*
 * Copyright 2018 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.servlet.handler.async.runnable;

import com.linkedin.cruisecontrol.servlet.response.CruiseControlResponse;
import com.linkedin.kafka.cruisecontrol.KafkaCruiseControl;
import com.linkedin.kafka.cruisecontrol.servlet.parameters.ResizeParameters;

/**
 * The async runnable for testing resizing
 */
public class ResizeRunnable extends OperationRunnable {

  public ResizeRunnable(KafkaCruiseControl kafkaCruiseControl, OperationFuture future, ResizeParameters parameters) {
    super(kafkaCruiseControl, future);
  }

  @Override
  protected CruiseControlResponse getResult() throws Exception {
    return null;
  }
}
