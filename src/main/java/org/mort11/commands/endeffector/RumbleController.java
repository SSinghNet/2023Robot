// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.endeffector;

import java.util.function.BooleanSupplier;

import org.mort11.util.Control;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RumbleController extends CommandBase {
  private Timer timer;
  private double time;
  private double rumbleValue;

  /** Creates a new RumbleController. */
  public RumbleController(double time, double rumbleValue, BooleanSupplier condition) {
    this.time = time;
    this.rumbleValue = condition.getAsBoolean() ? rumbleValue : 0;
    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Control.setControllerRumble(rumbleValue);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Control.setControllerRumble(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() < time;
  }
}
