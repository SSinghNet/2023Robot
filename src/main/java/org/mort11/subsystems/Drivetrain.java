package org.mort11.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.SwerveModule;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Drivetrain.*;
import static org.mort11.util.Constants.RobotSpecs.*;

public class Drivetrain extends SubsystemBase {
	private static Drivetrain drivetrain;

	private AHRS navX;

	private SwerveModule frontLeftModule;
	private SwerveModule frontRightModule;
	private SwerveModule backLeftModule;
	private SwerveModule backRightModule;

	public SwerveDriveKinematics driveKinematics;
	private SwerveDrivePoseEstimator odometry;

	private ChassisSpeeds chassisSpeeds;

	private PIDController rotateToAngleController;
	private PIDController balanceControllerX;
	private PIDController balanceControllerY;

	private PIDController aprilTagXController;
	private PIDController aprilTagYController;
	private PIDController aprilTagOmegaController;

	private PIDController odomXController;
	private PIDController odomYController;
	private PIDController odomOmegaController;

	private Field2d field;

	private Drivetrain() {
		navX = new AHRS(SerialPort.Port.kMXP);

		configModules();

		chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);

		driveKinematics = new SwerveDriveKinematics(
				// Front left
				new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
				// Front right
				new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0),
				// Back left
				new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
				// Back right
				new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0));

		odometry = new SwerveDrivePoseEstimator(driveKinematics, getGyroscopeRotation(), getModulePositions(),
				new Pose2d());

		resetPose(new Pose2d(0, 0, new Rotation2d(0, 0)));

		rotateToAngleController = new PIDController(ROTATE_TO_ANGLE_KP, ROTATE_TO_ANGLE_KI, ROTATE_TO_ANGLE_KD);
		rotateToAngleController.enableContinuousInput(-180.0f, 180.0f);
		rotateToAngleController.setTolerance(ROTATE_TO_ANGLE_TOLERANCE);

		balanceControllerX = new PIDController(BALANCE_KP, BALANCE_KI, BALANCE_KD);
		balanceControllerX.setTolerance(BALANCE_TOLERANCE);

		balanceControllerY = new PIDController(BALANCE_KP, BALANCE_KI, BALANCE_KD);
		balanceControllerY.setTolerance(BALANCE_TOLERANCE);

		aprilTagXController = new PIDController(ATX_KP, ATX_KI, ATX_KD);
		aprilTagXController.setTolerance(ATX_TOLERANCE);
		aprilTagYController = new PIDController(ATY_KP, ATY_KI, ATY_KD);
		aprilTagYController.setTolerance(ATY_TOLERANCE);
		aprilTagOmegaController = new PIDController(ATOMEGA_KP, ATOMEGA_KI, ATOMEGA_KD);
		aprilTagOmegaController.setTolerance(ATOMEGA_TOLERANCE);

		odomXController = new PIDController(ODOMX_KP, ODOMX_KI, ODOMX_KD);
		odomXController.setTolerance(ODOMX_TOLERANCE);
		odomYController = new PIDController(ODOMY_KP, ODOMY_KI, ODOMY_KD);
		odomYController.setTolerance(ODOMY_TOLERANCE);
		odomOmegaController = new PIDController(ODOMOMEGA_KP, ODOMOMEGA_KI, ODOMOMEGA_KD);
		odomOmegaController.setTolerance(ODOMOMEGA_TOLERANCE);

		field = new Field2d();
		Shuffleboard.getTab("test2").add("fieldTHING", field).withWidget(BuiltInWidgets.kField);
		Shuffleboard.getTab("test2").add("odometry x", odometry.getEstimatedPosition().getX());
		Shuffleboard.getTab("test2").add("odometry y", odometry.getEstimatedPosition().getY());
		Shuffleboard.getTab("test2").add("odometry theta", odometry.getEstimatedPosition().getRotation().getDegrees());
	}

	/** Configures all the swerve drive modules */
	private void configModules() {
		// ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

		frontLeftModule = new MkSwerveModuleBuilder()
				// .withLayout(tab.getLayout("Front Left Module",
				// BuiltInLayouts.kList).withSize(2, 4).withPosition(0, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2)
				// .withDriveMotor(MotorType.NEO, FRONT_LEFT_DRIVE)
				// .withSteerMotor(MotorType.NEO, FRONT_LEFT_STEER)
				.withDriveMotor(MotorType.FALCON, FRONT_LEFT_DRIVE).withSteerMotor(MotorType.FALCON, FRONT_LEFT_STEER)
				.withSteerEncoderPort(FRONT_LEFT_STEER_ENCODER).withSteerOffset(FRONT_LEFT_STEER_OFFSET).build();
		frontRightModule = new MkSwerveModuleBuilder()
				// .withLayout(tab.getLayout("Front Right Module",
				// BuiltInLayouts.kList).withSize(2, 4).withPosition(2, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2)
				// .withDriveMotor(MotorType.NEO, FRONT_RIGHT_DRIVE)
				// .withSteerMotor(MotorType.NEO, FRONT_RIGHT_STEER)
				.withDriveMotor(MotorType.FALCON, FRONT_RIGHT_DRIVE).withSteerMotor(MotorType.FALCON, FRONT_RIGHT_STEER)
				.withSteerEncoderPort(FRONT_RIGHT_STEER_ENCODER).withSteerOffset(FRONT_RIGHT_STEER_OFFSET).build();
		backLeftModule = new MkSwerveModuleBuilder()
				// .withLayout(tab.getLayout("Back Left Module",
				// BuiltInLayouts.kList).withSize(2, 4).withPosition(4, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2)
				// .withDriveMotor(MotorType.NEO, BACK_LEFT_DRIVE)
				// .withSteerMotor(MotorType.NEO, BACK_LEFT_STEER)
				.withDriveMotor(MotorType.FALCON, BACK_LEFT_DRIVE).withSteerMotor(MotorType.FALCON, BACK_LEFT_STEER)
				.withSteerEncoderPort(BACK_LEFT_STEER_ENCODER).withSteerOffset(BACK_LEFT_STEER_OFFSET).build();
		backRightModule = new MkSwerveModuleBuilder()
				// .withLayout(tab.getLayout("Back Right Module",
				// BuiltInLayouts.kList).withSize(2, 4).withPosition(6, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2)
				// .withDriveMotor(MotorType.NEO, BACK_RIGHT_DRIVE)
				// .withSteerMotor(MotorType.NEO, BACK_RIGHT_STEER)
				.withDriveMotor(MotorType.FALCON, BACK_RIGHT_DRIVE).withSteerMotor(MotorType.FALCON, BACK_RIGHT_STEER)
				.withSteerEncoderPort(BACK_RIGHT_STEER_ENCODER).withSteerOffset(BACK_RIGHT_STEER_OFFSET).build();

	}

	public AHRS getNavX() {
		return navX;
	}

	public PIDController getBalanceControllerX() {
		return balanceControllerX;
	}

	public PIDController getBalanceControllerY() {
		return balanceControllerY;
	}

	/** Sets the gyroscope angle to zero. */
	public void zeroGyroscope() {
		navX.zeroYaw();
		resetPose(new Pose2d(getPose().getX(), getPose().getY(), new Rotation2d(0)));
	}

	/**
	 * @return Rotation2d
	 */
	public Rotation2d getGyroscopeRotation() {
		if (navX.isMagnetometerCalibrated()) {
			// We will only get valid fused headings if the magnetometer is calibrated
			return Rotation2d.fromDegrees(navX.getFusedHeading());
		}

		// We have to invert the angle of the NavX so that rotating the robot
		// counter-clockwise makes the angle increase.
		return Rotation2d.fromDegrees(360.0 - navX.getYaw());
	}

	/**
	 * @return SwerveDriveKinematics
	 */
	public SwerveDriveKinematics getDriveKinematics() {
		return driveKinematics;
	}

	/**
	 * @return Pose2d (meters, radians)
	 */
	public Pose2d getPose() {
		return odometry.getEstimatedPosition();
	}

	/**
	 * @param pose
	 */
	public void resetPose(Pose2d pose) {
		odometry.resetPosition(getGyroscopeRotation(), getModulePositions(), pose);
	}

	/**
	 * Resets pose with x, y, and theta values
	 * @param x (meters)
	 * @param y (meters)
	 * @param theta (degrees)
	 */
	public void resetPose(double x, double y, double theta){
		resetPose(
			new Pose2d(x, y, new Rotation2d(Math.toRadians(theta)))
		);
	}

	/**
	 * @param states
	 */
	public void setModuleStates(SwerveModuleState[] states) {
		SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_VELOCITY_METERS_PER_SECOND);

		frontLeftModule.set(states[0].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
				states[0].angle.getRadians());
		frontRightModule.set(states[1].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
				states[1].angle.getRadians());
		backLeftModule.set(states[2].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
				states[2].angle.getRadians());
		backRightModule.set(states[3].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
				states[3].angle.getRadians());
	}

	public SwerveModulePosition[] getModulePositions() {
		return new SwerveModulePosition[]{frontLeftModule.getPosition(), frontRightModule.getPosition(),
				backLeftModule.getPosition(), backRightModule.getPosition()};
	}

	/**
	 * @param chassisSpeeds
	 */
	public void drive(ChassisSpeeds chassisSpeeds) {
		this.chassisSpeeds = chassisSpeeds;
	}

	public PIDController getRotateToAngleController() {
		return rotateToAngleController;
	}

	public double getPitch() {
		return navX.getPitch() - 0.3;
	}

	public double getRoll() {
		return navX.getRoll() + 1;
	}

	public PIDController getAprilTagXController() {
		return aprilTagXController;
	}

	public PIDController getAprilTagYController() {
		return aprilTagYController;
	}

	public PIDController getAprilTagOmegaController() {
		return aprilTagOmegaController;
	}

	public PIDController getOdomXController() {
		return odomXController;
	}

	public PIDController getOdomYController() {
		return odomYController;
	}

	public PIDController getOdomOmegaController() {
		return odomOmegaController;
	}

	@Override
	public void periodic() {
		odometry.update(getGyroscopeRotation(), getModulePositions());

		SwerveModuleState[] states = driveKinematics.toSwerveModuleStates(chassisSpeeds);
		setModuleStates(states);

		SmartDashboard.putNumber("Angle", getGyroscopeRotation().getDegrees());
		// SmartDashboard.putNumber("Pitch", getPitch());
		// SmartDashboard.putNumber("Roll", getRoll());

		field.setRobotPose(getPose());

		// odometry.addVisionMeasurement(Vision.getInstance().getPose(),
		// Vision.getInstance().getLatency());
	}

	/**
	 * Get the drivetrain object
	 */
	public static Drivetrain getInstance() {
		if (drivetrain == null) {
			drivetrain = new Drivetrain();
		}
		return drivetrain;
	}
}
