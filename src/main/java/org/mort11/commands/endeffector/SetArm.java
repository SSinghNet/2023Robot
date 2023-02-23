package org.mort11.commands.endeffector;

import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetArm extends CommandBase {
	private Arm arm;
	private double setpoint;

	public SetArm(double setpoint) {
		this.setpoint = setpoint;
		arm = Arm.getInstance();
		addRequirements(arm);
	}

	@Override
	public void initialize() {
		arm.setSetpoint(setpoint);
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return arm.nearSetpoint();
	}
}
