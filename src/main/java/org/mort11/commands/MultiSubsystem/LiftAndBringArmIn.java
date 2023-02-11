// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.MultiSubsystem;

import org.mort11.commands.arm.MoveArm;
import org.mort11.commands.elevator.MoveElevatorPos;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LiftAndBringArmIn extends ParallelCommandGroup {
  /** Creates a new LiftAndBringArmIn. */
  public LiftAndBringArmIn() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new MoveElevatorPos(Constants.Elevator.HIGH_PID_VALUE),new MoveArm());
  }
}
