package org.mort11.commands.arm;

import org.mort11.subsystems.Arm;
import static org.mort11.util.Constants.Arm.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArm extends CommandBase {
	private Arm arm;

	private double position;

	public MoveArm(double position) {
		arm = Arm.getInstance();

		this.position = position;

		addRequirements(arm);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		arm.setArmPosition(position);
	}

	/**
	 * When the motor of the arm is withih the tolerance of the setpoint, the code
	 * is finished.
	 */
	@Override
	public boolean isFinished() {
		return arm.getArmController().atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
	}

}
