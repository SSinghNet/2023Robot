package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Floortake.*;

public class Floortake extends SubsystemBase {
	private static Floortake floortake;

	private CANSparkMax flipNeo;
	private CANSparkMax driveNeo;

	private boolean flipIn;

	private SimpleMotorFeedforward flipFeedforward;
	private PIDController flipController;

	private Floortake() {
		flipNeo = new CANSparkMax(FLIP, MotorType.kBrushless);
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);

		flipFeedforward = new SimpleMotorFeedforward(FLIP_KS, FLIP_KV, FLIP_KA);
		flipController = new PIDController(FLIP_KP, FLIP_KI, FLIP_KD);

		flipNeo.restoreFactoryDefaults();
		flipNeo.setIdleMode(IdleMode.kBrake);
		flipNeo.enableSoftLimit(SoftLimitDirection.kForward, false); // TODO: enable
		// flipNeo.setSoftLimit(SoftLimitDirection.kForward, FLIP_IN_POS);
		flipNeo.enableSoftLimit(SoftLimitDirection.kReverse, false);
		// flipNeo.setSoftLimit(SoftLimitDirection.kReverse, FLIP_OUT_POS); //TODO:
		// enable
		flipNeo.burnFlash();

		driveNeo.restoreFactoryDefaults();
		driveNeo.setIdleMode(IdleMode.kCoast);
		driveNeo.burnFlash();

		// setFlipState(true);
	}

	public void toggleFlip() {
		setFlipState(!flipIn);
	}

	public void setFlipState(boolean in) {
		flipIn = in;
		System.out.println("setting flip " + (in ? "in" : "out"));
	}

	private void setFlip() {
		double output = (flipFeedforward.calculate(0)
				+ flipController.calculate(flipNeo.getEncoder().getPosition(), flipIn ? FLIP_IN_POS : FLIP_OUT_POS));

		flipNeo.setVoltage(output);
	}

	public void setFlipSpeed(double speed) {
		flipNeo.set(speed);
	}

	// public void setDrive(boolean in) {
	// setDrive((in ? 0.257 : -1)); // TODO: check polarity
	// }

	public void setDrive(double speed) {
		driveNeo.set(speed);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("flip encoder", flipNeo.getEncoder().getPosition());
		SmartDashboard.putNumber("flip setpoint", flipController.getSetpoint());
		setFlip();
	}

	public static Floortake getInstance() {
		if (floortake == null) {
			floortake = new Floortake();
		}
		return floortake;
	}
}
