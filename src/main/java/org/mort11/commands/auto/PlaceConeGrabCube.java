package org.mort11.commands.auto;

import org.mort11.commands.drivetrain.MoveToPos;
import org.mort11.commands.endeffector.ClearArm;
import org.mort11.commands.endeffector.Floor;
import org.mort11.commands.endeffector.SetArm;
import org.mort11.commands.endeffector.SetArmAndElevator;
import org.mort11.commands.endeffector.SetClawPiston;
import org.mort11.commands.endeffector.SetElevator;
import org.mort11.commands.endeffector.TimedIntake;
import org.mort11.subsystems.Drivetrain;
import org.mort11.util.Constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceConeGrabCube extends SequentialCommandGroup {
	private Drivetrain drivetrain;

	public PlaceConeGrabCube() {
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);
		addCommands(new InstantCommand(
				() -> drivetrain.resetPose(new Pose2d(new Translation2d(0, 0), new Rotation2d(Math.toRadians(-10))))),
				new ClearArm(), new SetElevator(Constants.Elevator.FLOOR_POSITION),
				new SetArm(Constants.Arm.FLOOR_POSITION), new SetClawPiston(true), new ParallelCommandGroup(
						// new TimedIntake(2, false),
						new MoveToPos(new Transform2d(new Translation2d(Units.inchesToMeters(218), 0),
								new Rotation2d(Math.toRadians(10)))) // 227
				), new SetClawPiston(false), new SetArm(Constants.Arm.REST_POSITION));
	}
}
