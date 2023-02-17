// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.elevator;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorSpeed extends CommandBase {
	Elevator elevator;
	double speed;

	public ElevatorSpeed(double speed) {
		elevator = Elevator.getInstance();
		this.speed = speed;
		addRequirements(elevator);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		elevator.setSpeed(speed);
	}

	@Override
	public void end(boolean interrupted) {
		elevator.setSpeed(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
