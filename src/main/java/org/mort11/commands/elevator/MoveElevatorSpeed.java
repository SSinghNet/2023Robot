// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.elevator;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveElevatorSpeed extends CommandBase {
  /** Creates a new MoveElevator. */
  double speed = 0;
  Elevator elevator;
  public MoveElevatorSpeed(double speed) {
    elevator = Elevator.getInstance();
    this.speed = speed;
    addRequirements(elevator);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(speed == 0){
      System.out.println("In");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
