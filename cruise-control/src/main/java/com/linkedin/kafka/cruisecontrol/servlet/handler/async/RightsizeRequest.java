/*
 * Copyright 2018 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.servlet.handler.async;

import com.linkedin.kafka.cruisecontrol.servlet.handler.async.runnable.OperationFuture;
import com.linkedin.kafka.cruisecontrol.servlet.handler.async.runnable.RightsizeRunnable;
import com.linkedin.kafka.cruisecontrol.servlet.parameters.RightsizeParameters;
import java.util.Map;

import static com.linkedin.cruisecontrol.common.utils.Utils.validateNotNull;
import static com.linkedin.kafka.cruisecontrol.servlet.parameters.ParameterUtils.RIGHTSIZE_PARAMETER_OBJECT_CONFIG;


public class RightsizeRequest extends AbstractAsyncRequest {
  protected RightsizeParameters _parameters;

  public RightsizeRequest() {
    super();
  }

  @Override
  protected OperationFuture handle(String uuid) {
    OperationFuture future = new OperationFuture("Rightsize");
    pending(future.operationProgress());
    _asyncKafkaCruiseControl.sessionExecutor().submit(new RightsizeRunnable(_asyncKafkaCruiseControl, future, _parameters));
    return future;
  }

  @Override
  public RightsizeParameters parameters() {
    return _parameters;
  }

  @Override
  public String name() {
    return RightsizeRequest.class.getSimpleName();
  }

  @Override
  public void configure(Map<String, ?> configs) {
    super.configure(configs);
    _parameters = (RightsizeParameters) validateNotNull(configs.get(RIGHTSIZE_PARAMETER_OBJECT_CONFIG),
                                                     "Parameter configuration is missing from the request.");
  }
}
