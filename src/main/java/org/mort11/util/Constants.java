package org.mort11.util;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

import edu.wpi.first.math.util.Units;

public final class Constants {
	public final static int PCM = 26;

	public final static class ControlPorts {
		public final static int JOYSTICK = 0;
		public final static int THROTTLE = 1;
		public final static int XBOX_CONTROLLER = 2;
	}

	public final static class Drivetrain {
		public final static int FRONT_LEFT_DRIVE = 5;
		public final static int FRONT_LEFT_STEER = 6;
		public final static int FRONT_LEFT_STEER_ENCODER = 32;
		public final static double FRONT_LEFT_STEER_OFFSET = -Math.toRadians(251);

		public final static int FRONT_RIGHT_DRIVE = 3;
		public final static int FRONT_RIGHT_STEER = 4;
		public final static int FRONT_RIGHT_STEER_ENCODER = 33;
		public final static double FRONT_RIGHT_STEER_OFFSET = -Math.toRadians(201);

		public final static int BACK_LEFT_DRIVE = 2;
		public final static int BACK_LEFT_STEER = 1;
		public final static int BACK_LEFT_STEER_ENCODER = 30;
		public final static double BACK_LEFT_STEER_OFFSET = -Math.toRadians(135 + 359);

		public final static int BACK_RIGHT_DRIVE = 18;
		public final static int BACK_RIGHT_STEER = 19;
		public final static int BACK_RIGHT_STEER_ENCODER = 31;
		public final static double BACK_RIGHT_STEER_OFFSET = -Math.toRadians(344);

		// TODO: tune pid
		public final static double ROTATE_TO_ANGLE_KP = 0.1;
		public final static double ROTATE_TO_ANGLE_KI = 0;
		public final static double ROTATE_TO_ANGLE_KD = 0.01;
		public final static double ROTATE_TO_ANGLE_TOLERANCE = 1;

		public final static double BALANCE_KP = 0.025; // 0.033
		public final static double BALANCE_KI = 0.0; // 0.0002
		public final static double BALANCE_KD = 0.005; // 0.008
		public final static double BALANCE_TOLERANCE = 0.25;

		// TODO: tune pid
		public final static double ATX_KP = 0;
		public final static double ATX_KI = 0;
		public final static double ATX_KD = 0;
		public final static double ATX_TOLERANCE = 0;

		// TODO: tune pid
		public final static double ATY_KP = 0;
		public final static double ATY_KI = 0;
		public final static double ATY_KD = 0;
		public final static double ATY_TOLERANCE = 0;

		// TODO: tune pid
		public final static double ATOMEGA_KP = 0;
		public final static double ATOMEGA_KI = 0;
		public final static double ATOMEGA_KD = 0;
		public final static double ATOMEGA_TOLERANCE = 0;

		public final static double ODOMX_KP = 0.85;
		public final static double ODOMX_KI = 0;
		public final static double ODOMX_KD = 0;
		public final static double ODOMX_TOLERANCE = 0.05;

		public final static double ODOMY_KP = 0.85;
		public final static double ODOMY_KI = 0.0;
		public final static double ODOMY_KD = 0;
		public final static double ODOMY_TOLERANCE = 0.05;

		public final static double ODOMOMEGA_KP = 0.1;
		public final static double ODOMOMEGA_KI = 0;
		public final static double ODOMOMEGA_KD = 0.01;
		public final static double ODOMOMEGA_TOLERANCE = 0.1;

		// public final static double AUTO_XY_KP = 0.02;
		// public final static double AUTO_XY_KI = 0.025;
		// public final static double AUTO_XY_KD = 0.00;

		// public final static double AUTO_ROTATION_KP = 0.02;
		// public final static double AUTO_ROTATION_KI = 0.025;
		// public final static double AUTO_ROTATION_KD = 0.00;

		// public final static double AUTO_TOLERANCE = 1;
	}

	public final static class Elevator {
		public final static int ELEVATOR_MASTER = 7;
		public final static int ELEVATOR_FOLLOWER = 8;
		public final static double ELEVATOR_SPEED = .5;

		public final static double KP = 0.4; // 0.275
		public final static double KI = 0;
		public final static double KD = 0.035;

		public final static double KS = -0.017718;
		public final static double KV = 4.1738;
		public final static double KA = 0.24129;
		public final static double KG = 0.4651;

		public final static int LIMIT_SWITCH = 4;

		// TODO find positions
		public final static double FLOOR_POSITION = 0.2;
		public final static double SHELF_POSITION = 50;
		public final static double MIDDLE_NODE_POSITION = 54;
		public final static double UPPER_NODE_POSITION = 87;

		public final static float TOP_LIMIT = 90.0f;
		public final static float BOTTOM_LIMIT = 0.3f;
		public final static float RANGE = TOP_LIMIT - BOTTOM_LIMIT;
	}

	public final static class Arm {
		public final static int DRIVE = 27;
		// TODO: tune PID
		public final static double KP = 0.25; // 0.2
		public final static double KI = 0.4;
		public final static double KD = 0.1; // 0.055
		public final static double TOLERANCE = 0.7;

		public final static double KS = 0.41861;
		public final static double KV = 3.178;
		public final static double KA = 8.5554;

		// TODO find positions
		public final static double FLOOR_POSITION = -32;
		public final static double SCORING_POSITION = 3;
		public final static double REST_POSITION = -4.5;
		
		public final static double TOP_CLEAR = -6.5;
		public final static double BOTTOM_CLEAR = -20;

		public final static float BOTTOM_LIMIT = -40.0f;
		public final static float TOP_LIMIT = 2.5f;
	}

	public final static class Claw {
		public final static int DRIVE_MASTER = 34;
		public final static int DRIVE_FOLLOWER = 35;

		public final static int IR_SENSOR = 0;

		public final static int PISTON = 7;

		public final static double CONE_SPEED = 0.5;
		public final static double CUBE_SPEED = 0.1;
	}

	public final static class Wrist {
		public final static int DRIVE = 36;
		public final static int ENCODER = 9;

		public final static double KP = 0.17; // 0.1
		public final static double KI = 0;
		public final static double KD = 0.007;

		// TODO test positions
		public final static double DOWN_POSITION = 45;
		public final static double RIGHT_POSITION = 86;
		public final static double UP_POSITION = 45;
		public final static double LEFT_POSITION = 0;
	}

	public final static class Vision {
		public static enum Pipeline {
			DEFAULT(0), CONE(1), CUBE(2), APRIL_TAG(3);

			int id;

			Pipeline(int id) {
				this.id = id;
			}

			public int getId() {
				return id;
			}

			/**
			 * Gets id of pipeline for a specific April Tag ID, id 1 at pipeline 3, etc.
			 */
			public int getId(int ATID) {
				if (this.id == 3) {
					return 2 + ATID;
				}
				return id;
			}

			public static Pipeline getPipeline(int id) {
				for (Pipeline p : values()) {
					if (p.getId() == id) {
						return p;
					}
				}
				return DEFAULT;
			}
		}
	}

	public final static class RobotSpecs {
		// The left-to-right distance between the drivetrain wheels measured from center
		// to center.
		public static final double DRIVETRAIN_TRACKWIDTH_METERS = Units.inchesToMeters(23.75);
		// The front-to-back distance between the drivetrain wheels measured from center
		// to center.
		public static final double DRIVETRAIN_WHEELBASE_METERS = Units.inchesToMeters(23.75);

		public static final double MAX_VOLTAGE = 12.0;

		public static final double MAX_VELOCITY_METERS_PER_SECOND = (6380.0 / 60.0
				* SdsModuleConfigurations.MK4I_L2.getDriveReduction()
				* SdsModuleConfigurations.MK4I_L2.getWheelDiameter() * Math.PI) * 0.875;
		public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND
				/ Math.hypot(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0);

		public static final double MAX_VELOCITY_AUTO = 4;
		public static final double MAX_ACCELERATION_AUTO = 3;
	}

}
