package org.mort11.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.mort11.commands.drivetrain.Balance;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.Floor;
import org.mort11.commands.endeffector.Rest;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.SetArm;
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
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
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

	private static HashMap<String, Command> eventMap;

	// private static SwerveAutoBuilder autoBuilder;

	private static SendableChooser<Command> autoChooser;

	public static void init() {
		eventMap = new HashMap<String, Command>();
		addEvents();

		drivetrain = Drivetrain.getInstance();

		// autoBuilder = new SwerveAutoBuilder(drivetrain::getPose, // Pose2d supplier
		// 		drivetrain::resetPose, // Pose2d consumer, used to reset odometry at the beginning of auto
		// 		drivetrain.driveKinematics, // SwerveDriveKinematics
		// 		new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (used to create the X
		// 											// and Y PID controllers)
		// 		new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (used to create the
		// 											// rotation controller)
		// 		drivetrain::setModuleStates, // Module states consumer used to output to the drive subsystem
		// 		eventMap, false, // Should the path be automatically mirrored depending on alliance color.
		// 							// Optional, defaults to true
		// 		drivetrain // The drive subsystem. Used to properly set the requirements of path following
		// 					// commands
		// );

		autoChooser = new SendableChooser<Command>();
		addAutoOptions();

		// put the auto chooser onto SmartDashboard
		SmartDashboard.putData(autoChooser);
	}

	public static void addAutoOptions() {
		// By default, the nothing option is selected
		autoChooser.setDefaultOption("nothing", null);

		// autoChooser.addOption("Test", autoFromPathGroup("Test"));

		autoChooser.addOption("One Cube Upper", new SequentialCommandGroup(new UpperNode(), new TimedIntake(1.5, false),
				new WaitCommand(1), new Rest()));
		autoChooser.addOption("One Cone Upper", new ScoreCone());

		autoChooser.addOption("Upper Cone, Engage",
				new SequentialCommandGroup(
					new ScoreCone(), new TimedDrive(2.4, 0.8, 0, 0), new Balance()));

		autoChooser.addOption("Upper Cone, Taxi, Engage",
				new SequentialCommandGroup(new ScoreCone(), new TimedDrive(2.6, 1.6, 0, 0), new WaitCommand(0.3),
						new TimedDrive(1.1, -1.6, 0, 0), new Balance()));

		autoChooser.addOption("Upper Cone, Taxi",
				new SequentialCommandGroup(new UpperNode(), new SetClawPiston(true), new WaitCommand(0.4),
						new SetClawPiston(false), new Rest(), new TimedDrive(2.8, 1.5, 0, 0), new Balance()));
		autoChooser.addOption("Upper Cone, Taxi, Engage (LEFT)", new SequentialCommandGroup(
				// new UpperNode(),
				// new SetClawPiston(true),
				// new WaitCommand(0.4),
				// new SetClawPiston(false),
				// new Rest(),
				new TimedDrive(3.5, 1.5, 0, 0), new TimedDrive(2.5, 0, -1, 0), new TimedDrive(1.3, -1, 0, 0),
				new Balance()));
		autoChooser.addOption("Upper Cube, Taxi, Engage (RIGHT)", new SequentialCommandGroup(
				// new UpperNode(),
				// new SetClawPiston(true),
				// new WaitCommand(0.4),
				// new SetClawPiston(false),
				// new Rest(),
				new TimedDrive(3.5, 1.5, 0, 0), new TimedDrive(2.5, 0, 1, 0), new TimedDrive(2.5, -1, 0, 0),
				new Balance()));

		autoChooser.addOption("Test PP", autoFromPath("Test", true));
		autoChooser.addOption("2m PP", autoFromPath("2m", true));

		autoChooser.addOption("Upper Cone Left Taxi Grab Second", 
				new SequentialCommandGroup(
					new ScoreCone(),
					new ParallelCommandGroup(
						new SetArm(-28),
						new TimedIntake(10, true),
						autoFromPath("Grab2nd", true)
					),
				new Rest()
			)
		);
		
	}

	public static void addEvents() {
		eventMap.put(null, null);
	}

	public static CommandBase autoFromPath(String name, boolean isFirstPath) {
		PathPlannerTrajectory traj = PathPlanner.loadPath(name, new PathConstraints(MAX_VELOCITY_AUTO, MAX_ACCELERATION_AUTO));		
 
		return new SequentialCommandGroup(
			new InstantCommand(() -> {
					// Reset odometry for the first path you run during auto
					if (isFirstPath) {
						drivetrain.resetPose(new Pose2d());
					}
			}),
			new PPSwerveControllerCommand(
				traj, 
				drivetrain::getPose, 
				drivetrain.driveKinematics, 
				drivetrain.getAutoXController(),
				drivetrain.getAutoYController(),
				drivetrain.getAutoRotationController(),
				drivetrain::setModuleStates,
				true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
				drivetrain 
				)
			);				
	}

	// public static CommandBase autoFromPathGroup(String name) {
	// 	return autoBuilder.fullAuto(
	// 			PathPlanner.loadPathGroup(name, new PathConstraints(MAX_VELOCITY_AUTO, MAX_ACCELERATION_AUTO)));
	// }

	// public static CommandBase autoFromPathGroup(ArrayList<PathPlannerTrajectory> paths) {
	// 	return autoBuilder.fullAuto(paths);
	// }

	// public static CommandBase autoFromPaths(ArrayList<String> names) {
	// 	return autoFromPathGroup(createPathGroup(names));
	// }

	public static ArrayList<PathPlannerTrajectory> createPathGroup(ArrayList<String> names) {
		ArrayList<PathPlannerTrajectory> paths = new ArrayList<PathPlannerTrajectory>();

		for (String n : names) {
			paths.add(PathPlanner.loadPath(n, new PathConstraints(MAX_VELOCITY_AUTO, MAX_ACCELERATION_AUTO)));
		}

		return paths;
	}

	/**
	 * @return selected auto from auto chooser
	 */
	public static Command getSelected() {
		return autoChooser.getSelected();
	}
}
