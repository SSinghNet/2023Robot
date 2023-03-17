package org.mort11.commands.endeffector;

import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SetArmAndElevator extends SequentialCommandGroup {
    public final static SequentialCommandGroup FLOOR2 = new SequentialCommandGroup(new ClearArm(), new SetElevator(Constants.Elevator.FLOOR_POSITION), new SetArm(Constants.Arm.FLOOR_POSITION));
    
    public final static SetArmAndElevator FLOOR = new SetArmAndElevator(Constants.Arm.FLOOR_POSITION, Constants.Elevator.FLOOR_POSITION);
    public final static SetArmAndElevator REST = new SetArmAndElevator(Constants.Arm.REST_POSITION, Constants.Elevator.FLOOR_POSITION);
    public final static SetArmAndElevator MIDDLE_NODE = new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.MIDDLE_NODE_POSITION);
    public final static SetArmAndElevator UPPER_NODE = new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.UPPER_NODE_POSITION);
    public final static SetArmAndElevator SHELF = new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.SHELF_POSITION);
    public final static SetArmAndElevator ZERO = new SetArmAndElevator(0, 0);
    
    public SetArmAndElevator(double armSetpoint, double elevatorSetpoint) {
        addCommands(new ClearArm(), new SetElevator(elevatorSetpoint), new SetArm(armSetpoint));
    }
}
