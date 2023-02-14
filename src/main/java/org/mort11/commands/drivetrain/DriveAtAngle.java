package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveAtAngle extends CommandBase {
	private Drivetrain drivetrain;
	private double angle;
	private boolean relative;

	public DriveAtAngle(double angle) {
		drivetrain = Drivetrain.getInstance();
		this.angle = angle;

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
		// TODO: prob doesnt work
		drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(0.5, 0, 0, new Rotation2d(angle)));
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
