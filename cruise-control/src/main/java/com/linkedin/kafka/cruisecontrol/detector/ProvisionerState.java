/*
 * Copyright 2017 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information.
 */

package com.linkedin.kafka.cruisecontrol.detector;

/**
 * A class to indicate how a provision is handled
 */
public class ProvisionerState {
  private State _state;
  private String _summary;
  private final long _createdMs;
  private long _updatedMs;

  public ProvisionerState(State state, String summary) {
    _state = state;
    _summary = summary;
    _createdMs = System.currentTimeMillis();
    _updatedMs = _createdMs;
  }

  /**
   * @return The state of the provisioning action.
   */
  public State state() {
    return _state;
  }

  /**
   * @return The summary of the provisioning action status.
   */
  public String summary() {
    return _summary;
  }

  /**
   * @return The time the provisioner state was created in milliseconds.
   */
  public long createdMs() {
    return _createdMs;
  }

  /**
   * @return The status update time of the provision state in milliseconds.
   */
  public long updateMs() {
    return _updatedMs;
  }

  /**
   * Set the state of the provisioning action
   *
   * @param state The new state of the provisioning action.
   */
  public void setState(State state) {
    _state = state;
    _updatedMs = System.currentTimeMillis();
  }

  /**
   * Set the summary of the provisioning action
   *
   * @param summary The new summary of the provisioning action status.
   */
  public void setSummary(String summary) {
    _summary = summary;
    _updatedMs = System.currentTimeMillis();
  }

  public enum State {
    COMPLETE, COMPLETED_WITH_ERROR, IN_PROGRESS
  }
}
