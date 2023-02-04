package org.mort11.commands.control;

import org.mort11.subsystems.Arm;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmControl extends CommandBase {
	private Arm arm;
	private static double targetPosition;

	public ArmControl() {
		arm = Arm.getInstance();

		addRequirements(arm);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		//TODO: Set buttons
		if (false) {
			arm.setArmPosition(Constants.Arm.HYBRID_LEVEL);
			targetPosition = Constants.Arm.HYBRID_LEVEL;

		} else if (false) {
			arm.setArmPosition(Constants.Arm.CENTER_LEVEL);
			targetPosition = Constants.Arm.CENTER_LEVEL;

		} else if (false) {
			arm.setArmPosition(Constants.Arm.TOP_LEVEL);
			targetPosition = Constants.Arm.TOP_LEVEL;
		}
	}

	@Override
	public boolean isFinished() {
		if (arm.driveNeo.getEncoder().getPosition() > (targetPosition - 5) && 
		arm.driveNeo.getEncoder().getPosition() < (targetPosition + 5)) {
			return true;
		}
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		arm.driveNeo.setVoltage(0);
	}

}
