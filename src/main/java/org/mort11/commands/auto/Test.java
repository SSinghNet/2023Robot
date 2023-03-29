package org.mort11.commands.auto;

import org.mort11.commands.drivetrain.MoveToAprilTag;
import org.mort11.commands.drivetrain.MoveToPos;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.SetArm;
import org.mort11.commands.endeffector.SetArmAndElevator;
import org.mort11.commands.endeffector.SetClawPiston;
import org.mort11.commands.endeffector.TimedIntake;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Wrist;
import org.mort11.util.Constants;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Test extends SequentialCommandGroup {
	private Drivetrain drivetrain;

	public Test() {
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);

		int isBlue = SmartDashboard.getBoolean("isBlue", true) ? 1 : -1;

		addCommands(
                new InstantCommand(() -> drivetrain.resetPose(0, 0, 0)),
                new WaitCommand(2),
				new MoveToPos(0, isBlue * Units.inchesToMeters(43), 0),
				new MoveToPos(Units.inchesToMeters(200), 0, 0),
				new MoveToPos(Units.inchesToMeters(6), 0,0),
				new WaitCommand(0.5),
				new MoveToPos(Units.inchesToMeters(-190), isBlue * 0.5,0),
				new MoveToAprilTag(6).withTimeout(1.5)
		);

	}
}
