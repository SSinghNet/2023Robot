package org.mort11.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

import org.mort11.util.Constants.RobotSpecs;

public class Arm extends SubsystemBase {
	private static Arm arm;

	private TalonFX driveFalconMaster;
	private TalonFX driveFalconFollower;

	private ProfiledPIDController armController;
	private SimpleMotorFeedforward feedforward;

	/** target arm position in encoder units */
	private double setpoint;

	private Arm() {
		driveFalconMaster = new TalonFX(DRIVE_MASTER);
		driveFalconFollower = new TalonFX(DRIVE_FOLLOWER);

		driveFalconMaster.setNeutralMode(NeutralMode.Brake);
		driveFalconFollower.setNeutralMode(NeutralMode.Brake);

		// driveFalconMaster.configForwardSoftLimitEnable(false); // TODO: enable
		// driveFalconMaster.configForwardSoftLimitThreshold(TOP_LIMIT);
		// driveFalconMaster.configReverseSoftLimitEnable(false);

		// driveFalconMaster.setInverted(true);
		// driveFalconMaster.configReverseSoftLimitThreshold(BOTTOM_LIMIT); //TODO:
		// enable

		driveFalconFollower.follow(driveFalconMaster);
		driveFalconFollower.setInverted(InvertType.OpposeMaster); // TODO: most
		// likely

		// armController = new PIDController(KP, KI, KD);
		// armController.setTolerance(TOLERANCE);

		armController = new ProfiledPIDController(KP, KI, KD, new Constraints(500000, 250000));

		feedforward = new SimpleMotorFeedforward(KS, KV, KA);

		setpoint = REST_POSITION;
	}

	public ProfiledPIDController getArmController() {
		return armController;
	}

	public TalonFX getDriveFalconMaster() {
		return driveFalconMaster;
	}

	/**
	 * Uses close loop controller to set the voltage of of the motor that controls
	 * the position of the arm, based on a given target position
	 *
	 * @param setpoint
	 *            Value of the position we are targeting.
	 */
	public void setSetpoint(double setpoint) {
		// armController.reset();
		this.setpoint = setpoint;
	}

	public double getSetpoint() {
		return setpoint;
	}

	public boolean atSetpoint() {
		return armController.atSetpoint();
	}

	public boolean nearSetpoint() {
		return Math.abs(getPosition() - setpoint) < 1300;
	}

	/** @return whether arm is clear so it is safe to move elevator */
	public boolean isClear() {
		// return getPosition() < TOP_CLEAR && getPosition() > BOTTOM_CLEAR;
		return true;
	}

	private void setPosition(double setpoint) {
		double output = ((feedforward.calculate(0) + armController.calculate(getPosition(), setpoint))
				/ RobotSpecs.MAX_VOLTAGE); // INVERTED
		if (output >= 1){
			output = 0.1;
		} else if (output <= -1) {
			output = -0.1;
		}
		// double output = 0.1 * sin(getPositionDegrees()) +
		// armController.calculate(getPosition(), setpoint);
		driveFalconMaster.set(ControlMode.PercentOutput, output);

		SmartDashboard.putNumber("arm output", output);
	}

	public void setSpeed(double speed) {
		driveFalconMaster.set(ControlMode.PercentOutput, speed);
	}

	public double getPosition() {
		return driveFalconMaster.getSelectedSensorPosition();
	}

	public double getPositionDegrees() {
		// return -6.22548502 * getPosition() -60.27542694;
		return -6.274 * (getPosition() + 9.8);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Built-In Arm Encoder", getPosition());
		// SmartDashboard.putNumber("arm degrees", getPositionDegrees());
		SmartDashboard.putNumber("arm setpoint", setpoint);

		setPosition(setpoint);
	}

	/**
	 * Get the arm object
	 */
	public static Arm getInstance() {
		if (arm == null) {
			arm = new Arm();
		}
		return arm;
	}

}
