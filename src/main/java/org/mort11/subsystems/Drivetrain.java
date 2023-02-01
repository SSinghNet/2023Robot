package org.mort11.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.SwerveModule;

import edu.wpi.first.math.controller.PIDController;
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

	private SwerveDriveKinematics driveKinematics;
	private SwerveDriveOdometry odometry;

	private ChassisSpeeds chassisSpeeds;

	private PIDController rotateToAngleController;

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

		odometry = new SwerveDriveOdometry(driveKinematics, Rotation2d.fromDegrees(navX.getFusedHeading()),
				new SwerveModulePosition[]{frontLeftModule.getPosition(), frontRightModule.getPosition(),
						backLeftModule.getPosition(), backRightModule.getPosition()});

		resetPose(new Pose2d(0, 0, new Rotation2d(0, 0)));

		rotateToAngleController = new PIDController(ROTATE_TO_ANGLE_KP, ROTATE_TO_ANGLE_KI, ROTATE_TO_ANGLE_KD);
		rotateToAngleController.enableContinuousInput(-180.0f, 180.0f);
		rotateToAngleController.setTolerance(ROTATE_TO_ANGLE_TOLERANCE);
	}

	/** Configures all the swerve drive modules */
	private void configModules() {
		ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

		frontLeftModule = new MkSwerveModuleBuilder()
				.withLayout(tab.getLayout("Front Left Module", BuiltInLayouts.kList).withSize(2, 4).withPosition(0, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2).withDriveMotor(MotorType.NEO, FRONT_LEFT_DRIVE)
				.withSteerMotor(MotorType.NEO, FRONT_LEFT_STEER).withSteerEncoderPort(FRONT_LEFT_STEER_ENCODER)
				.withSteerOffset(FRONT_LEFT_STEER_OFFSET).build();
		frontRightModule = new MkSwerveModuleBuilder()
				.withLayout(tab.getLayout("Front Right Module", BuiltInLayouts.kList).withSize(2, 4).withPosition(2, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2).withDriveMotor(MotorType.NEO, FRONT_RIGHT_DRIVE)
				.withSteerMotor(MotorType.NEO, FRONT_RIGHT_STEER).withSteerEncoderPort(FRONT_RIGHT_STEER_ENCODER)
				.withSteerOffset(FRONT_RIGHT_STEER_OFFSET).build();
		backLeftModule = new MkSwerveModuleBuilder()
				.withLayout(tab.getLayout("Back Left Module", BuiltInLayouts.kList).withSize(2, 4).withPosition(4, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2).withDriveMotor(MotorType.NEO, BACK_LEFT_DRIVE)
				.withSteerMotor(MotorType.NEO, BACK_LEFT_STEER).withSteerEncoderPort(BACK_LEFT_STEER_ENCODER)
				.withSteerOffset(BACK_LEFT_STEER_OFFSET).build();
		backRightModule = new MkSwerveModuleBuilder()
				.withLayout(tab.getLayout("Back Right Module", BuiltInLayouts.kList).withSize(2, 4).withPosition(6, 0))
				.withGearRatio(SdsModuleConfigurations.MK4I_L2).withDriveMotor(MotorType.NEO, BACK_RIGHT_DRIVE)
				.withSteerMotor(MotorType.NEO, BACK_RIGHT_STEER).withSteerEncoderPort(BACK_RIGHT_STEER_ENCODER)
				.withSteerOffset(BACK_RIGHT_STEER_OFFSET).build();
	}

	/** Sets the gyroscope angle to zero. */
	public void zeroGyroscope() {
		navX.zeroYaw();
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
	 * @return Pose2d
	 */
	public Pose2d getPose() {
		return odometry.getPoseMeters();
	}

	/**
	 * @param pose
	 */
	public void resetPose(Pose2d pose) {
		odometry.resetPosition(Rotation2d.fromDegrees(navX.getFusedHeading()),
				new SwerveModulePosition[]{frontLeftModule.getPosition(), frontRightModule.getPosition(),
						backLeftModule.getPosition(), backRightModule.getPosition()},
				pose);
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

	/**
	 * @param chassisSpeeds
	 */
	public void drive(ChassisSpeeds chassisSpeeds) {
		this.chassisSpeeds = chassisSpeeds;
	}

	public PIDController getRotateToAngleController() {
		return rotateToAngleController;
	}

	@Override
	public void periodic() {
		odometry.update(Rotation2d.fromDegrees(navX.getFusedHeading()),
				new SwerveModulePosition[]{frontLeftModule.getPosition(), frontRightModule.getPosition(),
						backLeftModule.getPosition(), backRightModule.getPosition()});

		SwerveModuleState[] states = driveKinematics.toSwerveModuleStates(chassisSpeeds);
		setModuleStates(states);
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
