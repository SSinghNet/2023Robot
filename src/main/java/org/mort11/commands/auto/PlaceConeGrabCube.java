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

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceConeGrabCube extends SequentialCommandGroup {
	private Drivetrain drivetrain;

	public PlaceConeGrabCube() {
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);
		addCommands(
			new InstantCommand(() -> drivetrain.resetPose(0, 0, -10)),
			
			new ClearArm(),
			new SetElevator(Constants.Elevator.FLOOR_POSITION),
			new SetArm(Constants.Arm.FLOOR_POSITION),
			
			// SetArmAndElevator.FLOOR,

			new SetClawPiston(true),
			new ParallelDeadlineGroup( //finish intaking when path ends
				new MoveToPos(Units.inchesToMeters(218), 0, 10), // 227
				new TimedIntake(3, false, true)
			),
			// new SetClawPiston(false),
			new SetArm(Constants.Arm.REST_POSITION));
	}
}
