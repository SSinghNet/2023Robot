package org.mort11.commands.auto;

import org.mort11.commands.drivetrain.Balance;
import org.mort11.commands.drivetrain.MoveToAprilTag;
import org.mort11.commands.drivetrain.MoveToPos;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.SetArm;
import org.mort11.commands.endeffector.SetArmAndElevator;
import org.mort11.commands.endeffector.SetClawPiston;
import org.mort11.commands.endeffector.TimedIntake;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Wrist;
import org.mort11.util.Constants;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceConeGrabConeCharge extends SequentialCommandGroup {
	private Drivetrain drivetrain;

	public PlaceConeGrabConeCharge() {
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);
		addCommands(
			new InstantCommand(() -> drivetrain.resetPose(0, 0, 0)),
			new ScoreCone(),
			// new ParallelCommandGroup(
			// 	SetArmAndElevator.rest(),
			// 	new MoveToPos(0, Units.inchesToMeters(43), 0)
			// ),
			// new SetClawPiston(true),
			// new ParallelDeadlineGroup( //finish intaking when path ends
			// 	new ParallelCommandGroup(
			// 		SetArmAndElevator.floor(),
			// 		new MoveToPos(Units.inchesToMeters(200), 0, 0)
			///		// new MoveToPos(Units.inchesToMeters(107), 0, 0)
			// 	),// 227
			// 	new TimedIntake(3, true, true)
				// ),
			new ParallelDeadlineGroup(
				new SequentialCommandGroup(
					new MoveToPos(0, Units.inchesToMeters(43), 0),
					new MoveToPos(Units.inchesToMeters(200), 0, 0),
					new MoveToPos(Units.inchesToMeters(6), 0, 0)
				),
				new SequentialCommandGroup(
					SetArmAndElevator.rest(),
					new SetClawPiston(true),
					SetArmAndElevator.floor(),
					new TimedIntake(5, true, true)
				)
			),
			new TimedIntake(0.5, true, true),
			new SetClawPiston(false),
			new WaitCommand(0.2),
			
			new ParallelCommandGroup(
				new TimedIntake(2, true, true),
				new InstantCommand(() -> Wrist.getInstance().setSetpoint(Constants.Wrist.RIGHT_POSITION)),
				new SetArm(Constants.Arm.REST_POSITION),
				new SequentialCommandGroup(
					new TimedDrive(1, -1.5, -1.75, 0),
								// new MoveToPos(Units.inchesToMeters(-40), Units.inchesToMeters(-60), 0),
					new WaitCommand(0.1),
					new TimedDrive(1.45, -1.8, 0, 0)
					// new MoveToPos(Units.inchesToMeters(-50), 0, 0)
				)
            ),
            new Balance()
			// SetArmAndElevator.middleNode(),
			// new SetClawPiston(true),
			// new WaitCommand(0.5),
			// new SetClawPiston(false),
			// new Rest()
			// new ParallelCommandGroup(
			// 	new SequentialCommandGroup(
			// 		new SetClawPiston(false),
			// 		new InstantCommand(() -> Wrist.getInstance().setSetpoint(Constants.Wrist.RIGHT_POSITION)),
			// 		SetArmAndElevator.rest(),
			// 		SetArmAndElevator.middleNode()
			// 	),
			// 	new SequentialCommandGroup(
			// 		new MoveToPos(Units.inchesToMeters(-190), 0.5, 0),
			// 		new MoveToAprilTag(6).withTimeout(1.5),
			// 		new MoveToPos(-0.25, Units.inchesToMeters(-31), 0)
			// 	)
			// ),
			// new SetClawPiston(true)
		);
			
	}
}
