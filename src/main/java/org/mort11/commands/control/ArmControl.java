package org.mort11.commands.control;

import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmControl extends CommandBase {
	private Arm arm;

	public ArmControl() {
		arm = Arm.getInstance();

		addRequirements(arm);
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
