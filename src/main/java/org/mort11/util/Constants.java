package org.mort11.util;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

import edu.wpi.first.math.util.Units;

public final class Constants {
	public final static int PCM = 0;

	public final static class ControlPorts {
		public final static int LEFT_JOYSTICK = 0;
		public final static int RIGHT_JOYSTICK = 1;
		public final static int XBOX_CONTROLLER = 2;
	}

	public final static class Drivetrain {
		public final static int FRONT_LEFT_DRIVE = 1;
		public final static int FRONT_LEFT_STEER = 2;
		public final static int FRONT_LEFT_STEER_ENCODER = 10;
		public final static double FRONT_LEFT_STEER_OFFSET = -Math.toRadians(187); // 253 //295

		public final static int FRONT_RIGHT_DRIVE = 7;
		public final static int FRONT_RIGHT_STEER = 8;
		public final static int FRONT_RIGHT_STEER_ENCODER = 11;
		public final static double FRONT_RIGHT_STEER_OFFSET = -Math.toRadians(252);

		public final static int BACK_LEFT_DRIVE = 3;
		public final static int BACK_LEFT_STEER = 4;
		public final static int BACK_LEFT_STEER_ENCODER = 9;
		public final static double BACK_LEFT_STEER_OFFSET = -Math.toRadians(136);

		public final static int BACK_RIGHT_DRIVE = 5;
		public final static int BACK_RIGHT_STEER = 6;
		public final static int BACK_RIGHT_STEER_ENCODER = 12;
		public final static double BACK_RIGHT_STEER_OFFSET = -Math.toRadians(344);

		// TODO: tune pid
		public final static double ROTATE_TO_ANGLE_KP = 0.02;
		public final static double ROTATE_TO_ANGLE_KI = 0;
		public final static double ROTATE_TO_ANGLE_KD = 0;
		public final static double ROTATE_TO_ANGLE_TOLERANCE = 0;

		// TODO: tune pid
		public final static double BALANCE_KP = 0;
		public final static double BALANCE_KI = 0;
		public final static double BALANCE_KD = 0;
		public final static double BALANCE_TOLERANCE = 0;

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
	}

	public final static class Elevator {
		// TODO ports
		public final static int ELEVATOR_MASTER = 0;
		public final static int ELEVATOR_FOLLOWER = 0;
		public final static double ELEVATOR_SPEED = .5;

		public final static int KP = 0;
		public final static int KI = 0;
		public final static int KD = 0;

		public final static int LIMIT_SWITCH = 0;
	}

	public final static class Arm {
		// TODO: port
		public final static int DRIVE = 0;

		// TODO: change constant values
		public final static double HYBRID_LEVEL = 0;
		public final static double CENTER_LEVEL = 0;
		public final static double TOP_LEVEL = 0;

		// TODO: tune PID
		public final static double KP = 0;
		public final static double KI = 0;
		public final static double KD = 0;
		public final static double TOLERANCE = 0;

	}

	public final static class Claw {
		// TODO: Ports
		public final static int DRIVE_MASTER = 0;
		public final static int DRIVE_FOLLOWER = 0;

		public final static int IR_SENSOR = 0;

		public final static int PISTON_FORWARD = 0;
		public final static int PISTON_BACKWARD = 0;

	}

	public final static class Wrist {
		// TODO: Ports
		public final static int DRIVE = 0;
		public final static int ENCODER = 0;

		public final static int KP = 0;
		public final static int KI = 0;
		public final static int KD = 0;
	}

	public final static class Vision {
		public static enum Pipeline {
			DEFAULT(0),
			CONE(1),
			CUBE(2),
			APRIL_TAG(3);
			
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

		public static final double MAX_VELOCITY_AUTO = 4;
		public static final double MAX_ACCELERATION_AUTO = 3;
	}


}
