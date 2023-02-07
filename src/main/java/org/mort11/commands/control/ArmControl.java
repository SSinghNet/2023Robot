package org.mort11.commands.control;

import org.mort11.subsystems.Arm;
import static org.mort11.util.Constants.Arm.*;

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
		// TODO: Set button

		/**
		 * Sets the position of the motor based on the button pressed, varying between Hybrid, Center, and Top.
		 */
		if (false) {
			arm.setArmPosition(HYBRID_LEVEL);

		} else if (false) {
			arm.setArmPosition(CENTER_LEVEL);

		} else if (false) {
			arm.setArmPosition(TOP_LEVEL);

		}
	}

	/**
	 * When the motor of the arm is withih the tolerance of the setpoint, the code is finished.
	 */
	@Override
	public boolean isFinished() {
		return arm.getArmController().atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
	}

}
