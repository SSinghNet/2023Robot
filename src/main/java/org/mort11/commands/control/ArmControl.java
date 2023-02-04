package org.mort11.commands.control;

import org.mort11.subsystems.Arm;
import org.mort11.util.Constants;

import com.revrobotics.CANSparkMax;

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
		// TODO: Set buttons
		if (false) {
			arm.setArmPosition(Constants.Arm.HYBRID_LEVEL);

		} else if (false) {
			arm.setArmPosition(Constants.Arm.CENTER_LEVEL);

		} else if (false) {
			arm.setArmPosition(Constants.Arm.TOP_LEVEL);

		}
	}

	@Override
	public boolean isFinished() {
		return arm.getArmController().atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
	}

}
