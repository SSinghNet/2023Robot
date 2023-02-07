package org.mort11.commands.defaults;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorDefault extends CommandBase {
	// TODO: implement

	private Elevator elevator;

	public ElevatorDefault() {
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
