package org.mort11.commands.auto;

import org.mort11.commands.drivetrain.Balance;
import org.mort11.commands.drivetrain.MoveToAprilTag;
import org.mort11.commands.drivetrain.MoveToPos;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;
import org.mort11.commands.endeffector.floortake.FloorIntake;
import org.mort11.commands.endeffector.floortake.Spit;
import org.mort11.commands.endeffector.floortake.Stow;
import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.util.Units;
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

		// int isBlue = SmartDashboard.getBoolean("isBlue", true) ? 1 : -1;

		// addCommands(
        //         new InstantCommand(() -> drivetrain.resetPose(0, 0, 0)),
        //         new WaitCommand(2),
		// 		new MoveToPos(0, isBlue * Units.inchesToMeters(43), 0),
		// 		new MoveToPos(Units.inchesToMeters(200), 0, 0),
		// 		new MoveToPos(Units.inchesToMeters(6), 0,0),
		// 		new WaitCommand(0.5),
		// 		new MoveToPos(Units.inchesToMeters(-190), isBlue * 0.5,0),
		// 		new MoveToAprilTag(6).withTimeout(1.5)
		// );

		addCommands(
			new ScoreCone(),
			new ParallelCommandGroup(
				SetArmAndElevator.rest(),
				new MoveToPos(Units.inchesToMeters(180), 0, 0, 1.75, 1).withTimeout(7)
			),
			new ParallelDeadlineGroup(
				new SequentialCommandGroup(
					new MoveToPos(Units.inchesToMeters(50), 0, 180, 1.75, 1).withTimeout(2),
					new WaitCommand(0.2)
				),
				new FloorIntake()
			),
			new ParallelDeadlineGroup(
				new SequentialCommandGroup(
					new MoveToPos(-3.6,0,180, 1.75, 2)
				),
				new Stow()
			),
			new ParallelDeadlineGroup(
				new Balance(),
				new Spit()
			)
		);

		

	}
}
