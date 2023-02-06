package org.mort11.util;

import java.util.HashMap;

import org.mort11.subsystems.Drivetrain;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Auto {
	private static Drivetrain drivetrain;

	private static HashMap<String, Command> eventMap;

	private static SwerveAutoBuilder autoBuilder;

	private static SendableChooser<Command> autoChooser;

	public static void init() {
		eventMap = new HashMap<String, Command>();
		addEvents();

		drivetrain = Drivetrain.getInstance();

		autoBuilder = new SwerveAutoBuilder(drivetrain::getPose, // Pose2d supplier
				drivetrain::resetPose, // Pose2d consumer, used to reset odometry at the beginning of auto
				drivetrain.driveKinematics, // SwerveDriveKinematics
				new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (used to create the X
													// and Y PID controllers)
				new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (used to create the
													// rotation controller)
				drivetrain::setModuleStates, // Module states consumer used to output to the drive subsystem
				eventMap, false, // Should the path be automatically mirrored depending on alliance color.
									// Optional, defaults to true
				drivetrain // The drive subsystem. Used to properly set the requirements of path following
							// commands
		);

		autoChooser = new SendableChooser<Command>();
		addAutoOptions();

		// put the auto chooser onto SmartDashboard
		SmartDashboard.putData(autoChooser);
	}

	public static void addAutoOptions() {
		// By default, the nothing option is selected
		autoChooser.setDefaultOption("nothing", null);
		autoChooser.addOption("Test", autoFromPathGroup("Test"));
	}

	public static void addEvents() {
		eventMap.put(null, null);
	}

	public static CommandBase autoFromPathGroup(String name) {
		return autoBuilder.fullAuto(PathPlanner.loadPathGroup(name, new PathConstraints(4, 3)));
	}

	/**
	 * @return selected auto from auto chooser
	 */
	public static Command getSelected() {
		return autoChooser.getSelected();
	}
}