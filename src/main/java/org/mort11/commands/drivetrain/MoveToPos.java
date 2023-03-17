package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveToPos extends CommandBase {
	private Drivetrain drivetrain;

	private Transform2d transform2d;

	private Pose2d newPose;

	public MoveToPos(Transform2d transform2d) {
		drivetrain = Drivetrain.getInstance();

		this.transform2d = transform2d;

		addRequirements(drivetrain);
	}

	@Override
	public void initialize() {
		newPose = new Pose2d(
				new Translation2d(drivetrain.getPose().getX() + transform2d.getX(),
						drivetrain.getPose().getY() + transform2d.getY()),
				(drivetrain.getPose().getRotation().plus(transform2d.getRotation())));

		System.out.println("old" + drivetrain.getPose().getX());
		System.out.println("old" + drivetrain.getPose().getY());
		System.out.println("old" + drivetrain.getPose().getRotation().getDegrees());

		System.out.println("new" + newPose.getX());
		System.out.println("new" + newPose.getY());
		System.out.println("new" + newPose.getRotation().getDegrees());

		drivetrain.getOdomXController().reset();
		drivetrain.getOdomYController().reset();
		drivetrain.getOdomOmegaController().reset();

	}

	@Override
	public void execute() {
		double x = drivetrain.getOdomXController().calculate(drivetrain.getPose().getX(), newPose.getX());
		x = drivetrain.getOdomXController().atSetpoint() ? 0 : x;
		double y = drivetrain.getOdomYController().calculate(drivetrain.getPose().getY(), newPose.getY());
		y = drivetrain.getOdomYController().atSetpoint() ? 0 : y;
		double omega = drivetrain.getOdomOmegaController().calculate(drivetrain.getPose().getRotation().getDegrees(),
				newPose.getRotation().getDegrees());
		omega = drivetrain.getOdomOmegaController().atSetpoint() ? 0 : omega;

		drivetrain.drive(new ChassisSpeeds(x, y, omega));

		SmartDashboard.putNumber("x out", x);
		SmartDashboard.putNumber("y out", y);
		SmartDashboard.putNumber("omega out", omega);
	}

	@Override
	public boolean isFinished() {
		return drivetrain.getOdomXController().atSetpoint() && drivetrain.getOdomYController().atSetpoint()
				&& drivetrain.getOdomOmegaController().atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
