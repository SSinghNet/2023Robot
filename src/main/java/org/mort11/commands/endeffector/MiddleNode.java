// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Elevator;
import org.mort11.util.Constants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MiddleNode extends InstantCommand {
  private Elevator elevator;
  private Arm arm;

  public MiddleNode() {
    elevator = Elevator.getInstance();
    arm = Arm.getInstance();
    addRequirements(elevator, arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elevator.setSetpoint(Constants.Elevator.MIDDLE_NODE_POSITION);
    arm.setSetpoint(Constants.Arm.SCORING_POSITION);
  }
}
