package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Vision;
import org.mort11.util.Constants.Vision.Pipeline;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveToAprilTag extends CommandBase {
	private Drivetrain drivetrain;
	private Vision vision;

	private int id;

	public MoveToAprilTag(int id) {
		drivetrain = Drivetrain.getInstance();
		vision = Vision.getInstance();

		this.id = id;

		addRequirements(drivetrain, vision);
	}

	@Override
	public void initialize() {
		vision.setPipeline(Pipeline.APRIL_TAG, id);
		drivetrain.getAprilTagXController().reset();
		drivetrain.getAprilTagYController().reset();
		drivetrain.getAprilTagOmegaController().reset();
	}

	@Override
	public void execute() {
		double x = -drivetrain.getAprilTagXController().calculate(vision.getCamTranZ(), -1.3);
		double y =
				// vision.getCamTranZ() > -1.5 ?
				-drivetrain.getAprilTagYController().calculate(vision.getCamTranX(), 0);
		// : 0;
		double omega =
				// vision.getCamTranZ() > -1.5 ?
				-drivetrain.getAprilTagOmegaController().calculate(vision.getCamTranYaw(), 0);
		// : 0;

		drivetrain.drive(new ChassisSpeeds(x, y, omega));
	}

	@Override
	public boolean isFinished() {
		return !vision.hasTarget()
				|| (drivetrain.getAprilTagXController().atSetpoint() && drivetrain.getAprilTagYController().atSetpoint()
						&& drivetrain.getAprilTagOmegaController().atSetpoint());
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
