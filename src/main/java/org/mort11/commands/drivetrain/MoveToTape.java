package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Vision;
import org.mort11.util.Constants.Vision.Pipeline;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveToTape extends CommandBase {
	private Drivetrain drivetrain;
	private Vision vision;

	public MoveToTape() {
		drivetrain = Drivetrain.getInstance();
		vision = Vision.getInstance();

		addRequirements(drivetrain, vision);
	}

	@Override
	public void initialize() {
		vision.setPipeline(Pipeline.TAPE);
		drivetrain.getAprilTagXController().reset();
		drivetrain.getAprilTagYController().reset();
		drivetrain.getAprilTagOmegaController().reset();
	}

	@Override
	public void execute() {
		double x = -drivetrain.getTapeYController().calculate(vision.getY(), 10);
		double y = -drivetrain.getTapeXController().calculate(vision.getX(), 1);
		double omega = 0;

		drivetrain.drive(new ChassisSpeeds(x, y, omega));
	}

	@Override
	public boolean isFinished() {
		return false;
		// // !vision.hasTarget() ||
		// (drivetrain.getTapeXController().atSetpoint() &&
		// drivetrain.getTapeYController().atSetpoint());
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
