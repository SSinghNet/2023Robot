package org.mort11.commands.control;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorControl extends CommandBase {
	private Elevator elevator;

	public ElevatorControl() {
		elevator = Elevator.getInstance();

		addRequirements(elevator);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {

	}
}
