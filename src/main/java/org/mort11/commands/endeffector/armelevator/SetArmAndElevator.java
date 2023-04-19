package org.mort11.commands.endeffector.armelevator;

import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SetArmAndElevator extends SequentialCommandGroup {
	public SetArmAndElevator(double armSetpoint, double elevatorSetpoint) {
		addCommands(new SetArm(Constants.Arm.REST_POSITION).withTimeout(0.56), new SetElevator(elevatorSetpoint),
				new SetArm(armSetpoint));
	}

	public final static SetArmAndElevator floor() {
		return new SetArmAndElevator(Constants.Arm.FLOOR_POSITION, Constants.Elevator.FLOOR_POSITION);
	}

	public final static SetArmAndElevator rest() {
		return new SetArmAndElevator(Constants.Arm.REST_POSITION, Constants.Elevator.FLOOR_POSITION);
	}

	public final static SetArmAndElevator clamp() {
		return new SetArmAndElevator(Constants.Arm.CLAMP_POSITION, Constants.Elevator.FLOOR_POSITION);
	}

	public final static SetArmAndElevator middleNode() {
		return new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.MIDDLE_NODE_POSITION);
	}

	public final static SetArmAndElevator upperNode() {
		return new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.UPPER_NODE_POSITION);
	}

	public final static SetArmAndElevator shelf() {
		return new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.SHELF_POSITION);
	}

	public final static SetArmAndElevator zero() {
		return new SetArmAndElevator(0, 0);
	}
}
