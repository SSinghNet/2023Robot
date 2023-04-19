package org.mort11.commands.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BalanceBang extends CommandBase {
	private Drivetrain drivetrain;

	private int count;

	public BalanceBang() {
		drivetrain = Drivetrain.getInstance();

		addRequirements(drivetrain);

		count = 0;
	}

	@Override
	public void initialize() {
		count = 0;
	}

	@Override
	public void execute() {
		double output = -drivetrain.bangBangOutput(drivetrain.getRoll());
		if (output == 0) {
			count++;
		} else {
			count = 0;
		}

		System.out.println(output);

		drivetrain.drive(new ChassisSpeeds(output, 0, 0));
	}

	@Override
	public boolean isFinished() {
		return count > 25;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
	}
}
