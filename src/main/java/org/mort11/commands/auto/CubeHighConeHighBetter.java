package org.mort11.commands.auto;

import org.mort11.commands.drivetrain.MoveToAprilTag;
import org.mort11.commands.drivetrain.MoveToPos;
import org.mort11.commands.drivetrain.MoveToTape;
import org.mort11.commands.drivetrain.RotateToAngle;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.armelevator.SetArm;
import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;
import org.mort11.commands.endeffector.armelevator.SetElevator;
import org.mort11.commands.endeffector.clawwrist.SetClawPiston;
import org.mort11.commands.endeffector.clawwrist.SetWrist;
import org.mort11.commands.endeffector.clawwrist.TimedIntake;
import org.mort11.commands.endeffector.clawwrist.TimedIntake2;
import org.mort11.subsystems.Drivetrain;
import org.mort11.util.Constants;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CubeHighConeHighBetter extends SequentialCommandGroup {
	private Drivetrain drivetrain;

	public CubeHighConeHighBetter(boolean isBluee) {
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);

		int isBlue = isBluee ? 1 : -1;

		addCommands(new InstantCommand(() -> drivetrain.resetPose(0, 0, 0)),
				new ParallelDeadlineGroup(SetArmAndElevator.upperNode(), new TimedIntake(3, false, true)),
				new SetClawPiston(true), new TimedIntake2(0.2, 0.1),
				new SetArmAndElevator(Constants.Arm.REST_POSITION, Constants.Elevator.UPPER_NODE_POSITION - 8),
				new SetClawPiston(false),
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(
								new MoveToPos(0, isBlue * Units.inchesToMeters(12), 0).withTimeout(1),
								new MoveToPos(Units.inchesToMeters(189), 0, 0).withTimeout(3.3),
								new MoveToPos(Units.inchesToMeters(5), 0, 0).withTimeout(0.5)),
						new SequentialCommandGroup(SetArmAndElevator.rest(), // TODO: try parallel
								SetArmAndElevator.floor(), new SetClawPiston(true), new TimedIntake(5, true, true))),
				new SetClawPiston(false), new TimedIntake(0.5, true, true), // TODO maybe decrease
				new ParallelDeadlineGroup(new ParallelCommandGroup(
						// SetArmAndElevator.rest(),
						new SequentialCommandGroup(new SetArm(Constants.Arm.FLOOR_POSITION - 10000),
								new SetElevator(24), new WaitCommand(0.2), new SetArm(Constants.Arm.REST_POSITION)),
						new SequentialCommandGroup(new WaitCommand(0.2),
								new MoveToPos(Units.inchesToMeters(-180), 0, 0).withTimeout(2.5))),
						new TimedIntake(6, false, true)),
				new ParallelCommandGroup(
						new SequentialCommandGroup(
								new MoveToPos(0, isBlue * Units.inchesToMeters(-29), 0).withTimeout(1.1), // -24
								new RotateToAngle(0, false).withTimeout(1), new WaitCommand(0.2),
								new MoveToTape().withTimeout(1), new WaitCommand(0.2)),
						new SequentialCommandGroup(
								new SetArmAndElevator(Constants.Arm.REST_POSITION,
										Constants.Elevator.UPPER_NODE_POSITION + 2),
								new SetWrist(Constants.Wrist.RIGHT_POSITION))),
				new ParallelCommandGroup(new SequentialCommandGroup(new WaitCommand(0.3),
						new SetArmAndElevator(Constants.Arm.SCORING_POSITION,
								Constants.Elevator.UPPER_NODE_POSITION - 4)),
						new TimedDrive(1, -0.7, 0, 0)),
				new WaitCommand(0.2), new SetClawPiston(true), new TimedIntake(0.2, false, false),
				new SetArm(Constants.Arm.REST_POSITION), new SetClawPiston(false),
				new SetWrist(Constants.Wrist.LEFT_POSITION), SetArmAndElevator.rest());

	}
}
