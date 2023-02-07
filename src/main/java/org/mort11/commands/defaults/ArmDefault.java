package org.mort11.commands.defaults;

import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmDefault extends CommandBase {
	// TODO: implement

	private Arm arm;

	public ArmDefault() {
		arm = Arm.getInstance();
		addRequirements(arm);
	}

	@Override
	public void initialize() {
		arm.setArmPosition(0);
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
