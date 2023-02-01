package org.mort11.util;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

import edu.wpi.first.math.util.Units;

public final class Constants {
	public final static class ControlPorts {
		public final static int LEFT_JOYSTICK = 0;
		public final static int RIGHT_JOYSTICK = 1;
		public final static int XBOX_CONTROLLER = 2;
	}

	public final static class Drivetrain {
		public final static int FRONT_LEFT_DRIVE = 1;
		public final static int FRONT_LEFT_STEER = 2;
		public final static int FRONT_LEFT_STEER_ENCODER = 10;
		public final static double FRONT_LEFT_STEER_OFFSET = -Math.toRadians(252);

		public final static int FRONT_RIGHT_DRIVE = 7;
		public final static int FRONT_RIGHT_STEER = 8;
		public final static int FRONT_RIGHT_STEER_ENCODER = 11;
		public final static double FRONT_RIGHT_STEER_OFFSET = -Math.toRadians(260);

		public final static int BACK_LEFT_DRIVE = 3;
		public final static int BACK_LEFT_STEER = 4;
		public final static int BACK_LEFT_STEER_ENCODER = 9;
		public final static double BACK_LEFT_STEER_OFFSET = -Math.toRadians(302);

		public final static int BACK_RIGHT_DRIVE = 5;
		public final static int BACK_RIGHT_STEER = 6;
		public final static int BACK_RIGHT_STEER_ENCODER = 12;
		public final static double BACK_RIGHT_STEER_OFFSET = -Math.toRadians(272);

		public final static double ROTATE_TO_ANGLE_KP = 0;
		public final static double ROTATE_TO_ANGLE_KI = 0;
		public final static double ROTATE_TO_ANGLE_KD = 0;
		public final static double ROTATE_TO_ANGLE_TOLERANCE = 0;
	}

	public final static class Elevator {
		public final static int DRIVE_MASTER = 0;
		public final static int DRIVE_FOLLOWER = 0;
	}

	public final static class Arm {
		public final static int DRIVE = 0;
	}

	public final static class Claw {
		public final static int INTAKE = 0;
		public final static int WRIST = 0;
	}

	public final static class RobotSpecs {
		// The left-to-right distance between the drivetrain wheels measured from center
		// to center.
		public static final double DRIVETRAIN_TRACKWIDTH_METERS = Units.inchesToMeters(24);
		// The front-to-back distance between the drivetrain wheels measured from center
		// to center.
		public static final double DRIVETRAIN_WHEELBASE_METERS = Units.inchesToMeters(24);

		public static final double MAX_VOLTAGE = 12.0;

		public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0
				* SdsModuleConfigurations.MK4I_L2.getDriveReduction()
				* SdsModuleConfigurations.MK4I_L2.getWheelDiameter() * Math.PI;
		public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND
				/ Math.hypot(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0);
	}
}
