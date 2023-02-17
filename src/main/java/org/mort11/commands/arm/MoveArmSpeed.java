package org.mort11.commands.arm;

import org.mort11.subsystems.Arm;
import static org.mort11.util.Constants.Arm.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArmSpeed extends CommandBase {
	private Arm arm;

	private double speed;

	public MoveArmSpeed(double speed) {
		arm = Arm.getInstance();

		this.speed = speed;

		addRequirements(arm);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		arm.setSpeed(speed);
	}

	/**
	 * When the motor of the arm is withih the tolerance of the setpoint, the code
	 * is finished.
	 */
	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		arm.setSpeed(0);
	}

}
