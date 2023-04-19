package org.mort11.util;

import org.mort11.commands.auto.CubeHighConeHigh;
import org.mort11.commands.auto.TestL;
import org.mort11.commands.auto.CubeHighConeHighBetter;
import org.mort11.commands.auto.ConeHighCubeFloorEngage;
import org.mort11.commands.auto.ConeHighCubeFloorScoreCube;
import org.mort11.commands.drivetrain.Balance;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Auto {
	private static SendableChooser<Command> autoChooser;
	private static SendableChooser<Boolean> isBlue;

	/**
	 * Create autonomous commands and chooser
	 */
	public static void init() {
		autoChooser = new SendableChooser<Command>();
		isBlue = new SendableChooser<Boolean>();
		addAutoOptions();

		// put the auto chooser onto SmartDashboard
		SmartDashboard.putData(autoChooser);
		isBlue.setDefaultOption("Blue", true);
		isBlue.addOption("Red", false);

		SmartDashboard.putData("isBlue", isBlue);
	}

	public static void addAutoOptions() {
		// By default, the nothing option is selected
		autoChooser.setDefaultOption("nothing", null);

		autoChooser.addOption("Taxi", new TimedDrive(3, 1.5, 0, 0));

		autoChooser.addOption("Upper Cone", new SequentialCommandGroup(new ScoreCone(), SetArmAndElevator.rest()));

		autoChooser.addOption("Upper Cone, Engage", new SequentialCommandGroup(new ScoreCone(),
				SetArmAndElevator.rest(), new TimedDrive(2.7, 0.9, 0, 0), new Balance()));

		autoChooser.addOption("Upper Cone, Taxi, Engage",
				new SequentialCommandGroup(new ScoreCone(), SetArmAndElevator.rest(), new TimedDrive(3, 1.7, 0, 0),
						new WaitCommand(0.1), new TimedDrive(2, -1.8, 0, 0), new Balance()));

		autoChooser.addOption("Upper Cone, Taxi",
				new SequentialCommandGroup(new ScoreCone(), SetArmAndElevator.rest(), new TimedDrive(3, 1.5, 0, 0)));

		autoChooser.addOption("CLEAN Upper cube, Upper cone (BLUE)", new CubeHighConeHigh(true));
		autoChooser.addOption("CLEAN Upper cube, Upper cone (RED)", new CubeHighConeHigh(false));
		autoChooser.addOption("CENTER Upper cone, Floor cube, Engage", new ConeHighCubeFloorEngage());
		autoChooser.addOption("BUMP Upper Cone, Ground Cube", new ConeHighCubeFloorScoreCube());
		// autoChooser.addOption("test L", new TestL(true));
		autoChooser.addOption("test R (blue)", new CubeHighConeHighBetter(true));
		autoChooser.addOption("test R (red)", new CubeHighConeHighBetter(false));

	}

	public static Boolean getIsBlue() {
		return isBlue.getSelected() != null ? isBlue.getSelected() : true;
	}

	/**
	 * @return selected auto from auto chooser
	 */
	public static Command getSelected() {
		return autoChooser.getSelected();
	}
}
