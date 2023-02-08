// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.elevator;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveElevatorPos extends CommandBase {
	Elevator elevator;
	double target;

	public MoveElevatorPos(double target) {
		elevator = Elevator.getInstance();
		this.target = target;
		addRequirements(elevator);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		elevator.moveTo(target);
	}

	@Override
	public void end(boolean interrupted) {
		elevator.move(0);
	}

	@Override
	public boolean isFinished() {
		return elevator.atSetpoint();
	}
}
