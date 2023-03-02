package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Balance extends CommandBase {
	private Drivetrain drivetrain;

	public Balance() {
		drivetrain = Drivetrain.getInstance();

		addRequirements(drivetrain);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {

		// if (Math.abs(drivetrain.getNavX().getPitch()) > 0.5) {
		// drivetrain.drive(new ChassisSpeeds(0,
		// drivetrain.getBalanceController().calculate(-drivetrain.getPitch(), 0), 0));
		// } else if (Math.abs(drivetrain.getNavX().getRoll()) > 0.5) {
		// drivetrain.drive(new ChassisSpeeds(
		// drivetrain.getBalanceController().calculate(-drivetrain.getRoll(), 0), 0,
		// 0));
		// }
		drivetrain.drive(new ChassisSpeeds(
				-drivetrain.getBalanceControllerX().calculate(drivetrain.getRoll(), 0),
			0,
				// -drivetrain.getBalanceControllerY().calculate(drivetrain.getPitch(), 0),
				0)
				);

	}

	@Override
	public boolean isFinished() {
		// return Math.abs(drivetrain.getRoll()) < 0.5 &&
		// Math.abs(drivetrain.getPitch()) < 0.5;
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
