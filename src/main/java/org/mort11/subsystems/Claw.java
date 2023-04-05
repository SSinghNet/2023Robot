package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Claw.*;

import org.mort11.util.Constants;

import static org.mort11.util.Constants.*;

public class Claw extends SubsystemBase {
	private static Claw claw;

	private CANSparkMax intakeNeoMaster;
	private CANSparkMax intakeNeoFollower;

	private DigitalInput irSensor;

	private Solenoid piston;

	private Claw() {
		intakeNeoMaster = new CANSparkMax(DRIVE_MASTER, MotorType.kBrushless);
		intakeNeoFollower = new CANSparkMax(DRIVE_FOLLOWER, MotorType.kBrushless);

		intakeNeoMaster.restoreFactoryDefaults();
		intakeNeoMaster.setIdleMode(IdleMode.kCoast);
		intakeNeoMaster.burnFlash();
		
		intakeNeoFollower.restoreFactoryDefaults();
		intakeNeoFollower.setIdleMode(IdleMode.kCoast);
		intakeNeoFollower.burnFlash();

		// intakeNeoFollower.follow(intakeNeoMaster, false);

		irSensor = new DigitalInput(IR_SENSOR);

		piston = new Solenoid(PCM, PneumaticsModuleType.CTREPCM, PISTON);
		piston.set(false);

	}

	public void setSpeed(double speed) {
		intakeNeoMaster.set(-speed);
		intakeNeoFollower.set(speed);
		// System.out.println(speed);
	}

	public void setSpeed(boolean in) {
		if (SmartDashboard.getBoolean("FastSpeed", false)) {
			setSpeed(in ? -Constants.Claw.CONE_SPEED : Constants.Claw.CONE_SPEED);
		} else {
			setSpeed(in ? -Constants.Claw.CUBE_SPEED : Constants.Claw.CUBE_SPEED);
		}
	}

	public void setPiston(boolean value) {
		piston.set(value);
	}

	public void togglePiston() {
		piston.toggle();
	}

	public boolean getPiston() {
		return piston.get();
	}

	public boolean getIrSensor() {
		return !irSensor.get(); // is inverted to be true when there is an object present
	}

	@Override
	public void periodic() {
		SmartDashboard.putBoolean("Claw IR Sensor", getIrSensor());
		SmartDashboard.putBoolean("Claw Piston", getPiston());

	}

	/**
	 * Get the claw object
	 */
	public static Claw getInstance() {
		if (claw == null) {
			claw = new Claw();
		}
		return claw;
	}

}
