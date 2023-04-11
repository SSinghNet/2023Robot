package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveToPos extends CommandBase {
	private Drivetrain drivetrain;

	private Transform2d transform2d;
	private Constraints driveConstraints;

	private Pose2d newPose;

	/**
	 * Moves to position relative to the current position
	 *
	 * @param transform2d
	 *            transformation to make to the current pose
	 */
	public MoveToPos(Transform2d transform2d, Constraints driveConstraints) {
		this.transform2d = transform2d;
		this.driveConstraints = driveConstraints;
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);
	}

	/**
	 * Moves to position relative to the current position
	 *
	 * @param x
	 *            translation in the x direction (meters)
	 * @param y
	 *            translation in the y direction (meters)
	 * @param theta
	 *            rotation (degrees)
	 */
	public MoveToPos(double x, double y, double theta) {
		this(new Transform2d(new Translation2d(x, y), new Rotation2d(Math.toRadians(theta))), Drivetrain.defaultOdomConstraints());
	}
	
	 public MoveToPos(double x, double y, double theta, double driveVelocity, double driveAcceleration) {
		this(new Transform2d(new Translation2d(x, y), new Rotation2d(Math.toRadians(theta))), new Constraints(driveVelocity, driveAcceleration));
	}

	@Override
	public void initialize() {
		newPose = new Pose2d(drivetrain.getPose().getX() + transform2d.getX(),
				drivetrain.getPose().getY() + transform2d.getY(),
				(drivetrain.getPose().getRotation().plus(transform2d.getRotation())));

		drivetrain.getOdomXController().reset(drivetrain.getPose().getX());
		drivetrain.getOdomYController().reset(drivetrain.getPose().getY());
		drivetrain.getOdomOmegaController().reset();

		drivetrain.getOdomXController().setConstraints(driveConstraints);
		drivetrain.getOdomYController().setConstraints(driveConstraints);

	}

	@Override
	public void execute() {
		// drivetrain.getOdomXController().calculate(0, new State(), null)
		double x = drivetrain.getOdomXController().calculate(drivetrain.getPose().getX(), newPose.getX());
		x = drivetrain.getOdomXController().atSetpoint() ? 0 : x;
		double y = drivetrain.getOdomYController().calculate(drivetrain.getPose().getY(), newPose.getY());
		y = drivetrain.getOdomYController().atSetpoint() ? 0 : y;
		double omega = drivetrain.getOdomOmegaController().calculate(drivetrain.getPose().getRotation().getDegrees(),
				newPose.getRotation().getDegrees());
		omega = drivetrain.getOdomOmegaController().atSetpoint() ? 0 : omega;

		// drivetrain.drive(new ChassisSpeeds(x, y, omega));
		drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(x, y, omega, drivetrain.getGyroscopeRotation()));

		SmartDashboard.putNumber("x out", x);
		SmartDashboard.putNumber("y out", y);
		SmartDashboard.putNumber("omega out", omega);

		SmartDashboard.putNumber("x error", drivetrain.getOdomXController().getPositionError());
		SmartDashboard.putNumber("y error", drivetrain.getOdomYController().getPositionError());
		SmartDashboard.putNumber("omega error", drivetrain.getOdomOmegaController().getPositionError());
	}

	@Override
	public boolean isFinished() {
		return drivetrain.getOdomXController().atGoal() && drivetrain.getOdomYController().atGoal()
				&& drivetrain.getOdomOmegaController().atSetpoint();
	}


	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
		drivetrain.resetOdomControllerConstraints();
	}
}
