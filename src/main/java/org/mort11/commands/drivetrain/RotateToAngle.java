package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateToAngle extends CommandBase {
	private Drivetrain drivetrain;
	private double angle;
	
	/** whether angle is relative to current position */
	private boolean relative;

	/**
	 * @param angle
	 *            angle in getDegrees
	 * @param relative
	 *            whether angle is relative to current position
	 */
	public RotateToAngle(double angle, boolean relative) {
		drivetrain = Drivetrain.getInstance();
		this.angle = angle;
		this.relative = relative;

		addRequirements(drivetrain);
	}

	@Override
	public void initialize() {
		if (relative) {
			angle += drivetrain.getGyroscopeRotation().getDegrees();
		}
	}

	@Override
	public void execute() {
		drivetrain.drive(new ChassisSpeeds(0, 0, drivetrain.getRotateToAngleController()
				.calculate(drivetrain.getGyroscopeRotation().getDegrees(), angle)));
	}

	@Override
	public boolean isFinished() {
		return drivetrain.getRotateToAngleController().atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
