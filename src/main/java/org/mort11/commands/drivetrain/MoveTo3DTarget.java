package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Vision;
import org.mort11.util.Constants.Vision.Pipeline;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveTo3DTarget extends CommandBase {
	private Drivetrain drivetrain;
	private Vision vision;

	private int id;

	public MoveTo3DTarget(int id) {
		drivetrain = Drivetrain.getInstance();
		vision = Vision.getInstance();

		this.id = id;

		addRequirements(drivetrain, vision);
	}
//supercalifragilisticexpialidocious
	@Override
	public void initialize() {
		vision.setPipeline(Pipeline.APRIL_TAG, id);
	}

	@Override
	public void execute() {
		drivetrain.drive(new ChassisSpeeds(-drivetrain.getAprilTagXController().calculate(vision.getCamTranZ(), 0),
				drivetrain.getAprilTagYController().calculate(vision.getCamTranX(), 0),
				-drivetrain.getAprilTagOmegaController().calculate(vision.getCamTranYaw(), 0)));
	}

	@Override
	public boolean isFinished() {
		return !vision.hasTarget() || vision.getATId() != id
				|| (drivetrain.getAprilTagXController().atSetpoint() && drivetrain.getAprilTagYController().atSetpoint()
						&& drivetrain.getAprilTagOmegaController().atSetpoint());
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
