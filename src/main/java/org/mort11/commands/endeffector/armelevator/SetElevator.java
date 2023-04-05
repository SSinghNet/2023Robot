package org.mort11.commands.endeffector.armelevator;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetElevator extends CommandBase {
	private Elevator elevator;
	private double setpoint;

	public SetElevator(double setpoint) {
		this.setpoint = setpoint;
		elevator = Elevator.getInstance();
		addRequirements(elevator);
	}

	@Override
	public void initialize() {
		elevator.setSetpoint(setpoint);
	}

	@Override
	public void execute() {}

	@Override
	public void end(boolean interrupted) {}

	@Override
	public boolean isFinished() {
		return elevator.nearSetpoint();
	}
}
