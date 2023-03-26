package org.mort11.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.mort11.commands.auto.PlaceConeGrabConeCharge;
import org.mort11.commands.auto.PlaceConeGrabConeCommunity;
import org.mort11.commands.drivetrain.Balance;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.Rest;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.SetArm;
import org.mort11.commands.endeffector.SetArmAndElevator;
import org.mort11.commands.endeffector.SetClawPiston;
import org.mort11.commands.endeffector.SetElevator;
import org.mort11.commands.endeffector.TimedIntake;
import org.mort11.commands.endeffector.UpperNode;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.util.Constants.Arm;
import org.mort11.util.Constants.Elevator;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static org.mort11.util.Constants.RobotSpecs.*;

public class Auto {
	private static Drivetrain drivetrain;

	private static SendableChooser<Command> autoChooser;

	public static void init() {

		drivetrain = Drivetrain.getInstance();

		autoChooser = new SendableChooser<Command>();
		addAutoOptions();

		// put the auto chooser onto SmartDashboard
		SmartDashboard.putData(autoChooser);
	}

	public static void addAutoOptions() {
		// By default, the nothing option is selected
		autoChooser.setDefaultOption("nothing", null);

		autoChooser.addOption("Upper Cone", new SequentialCommandGroup(new ScoreCone(), new Rest()));

		autoChooser.addOption("Upper Cone, Engage",
				new SequentialCommandGroup(new ScoreCone(), new Rest(), new TimedDrive(2.7, 0.9, 0, 0), new Balance()));

		autoChooser.addOption("Upper Cone, Taxi, Engage",
				new SequentialCommandGroup(
					new ScoreCone(),
					SetArmAndElevator.rest(),
					new TimedDrive(3, 1.7, 0, 0),
					new WaitCommand(0.1),
					new TimedDrive(2, -1.8, 0, 0),
					new Balance()
				)
		);

		autoChooser.addOption("Upper Cone, Taxi",
				new SequentialCommandGroup(new ScoreCone(), new Rest(), new TimedDrive(3, 1.5, 0, 0)));
		// autoChooser.addOption("Upper Cone, Taxi, Engage (LEFT Blue)", new SequentialCommandGroup(
		// 		new ScoreCone(),
		// 		new TimedDrive(3.5, 1.5, 0, 0), new TimedDrive(2.5, 0, -1, 0), new TimedDrive(1.3, -1, 0, 0),
		// 		new Balance()));
		// autoChooser.addOption("Upper Cone, Taxi, Engage (RIGHT Blue)", new SequentialCommandGroup(
		// 		new ScoreCone(),
		// 		new TimedDrive(3.5, 1.5, 0, 0), new TimedDrive(2.5, 0, 1, 0), new TimedDrive(2.5, -1, 0, 0),
		// 		new Balance()));

		autoChooser.addOption("Upper Cone, Grab Cone, Go to Community", new PlaceConeGrabConeCommunity());
		autoChooser.addOption("Upper Cone, Grab Cone, Engage", new PlaceConeGrabConeCharge());

	}

	/**
	 * @return selected auto from auto chooser
	 */
	public static Command getSelected() {
		return autoChooser.getSelected();
	}
}
