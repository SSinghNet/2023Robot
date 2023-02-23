package org.mort11.commands.endeffector;

import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArm extends CommandBase {
	private Arm arm;
	private double increment;

	public MoveArm(double increment) {
		this.increment = increment;
		arm = Arm.getInstance();
		addRequirements(arm);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		arm.setSetpoint(arm.getSetpoint() + increment);
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
