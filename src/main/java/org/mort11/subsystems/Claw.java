package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Claw.*;

import org.mort11.util.Constants;

public class Claw extends SubsystemBase {
	private static Claw claw;

	private CANSparkMax intakeNeoMaster;
	private CANSparkMax intakeNeoFollower;

	private DigitalInput irSensor;

	private DoubleSolenoid piston;

	private Claw() {
		intakeNeoMaster = new CANSparkMax(DRIVE_MASTER, MotorType.kBrushless);
		intakeNeoFollower = new CANSparkMax(DRIVE_FOLLOWER, MotorType.kBrushless);

		intakeNeoFollower.follow(intakeNeoMaster, false);

		irSensor = new DigitalInput(IR_SENSOR);

		piston = new DoubleSolenoid(Constants.PCM, PneumaticsModuleType.REVPH, PISTON_FORWARD, PISTON_BACKWARD);
		piston.set(Value.kForward);
	}

	public void setSpeed(double speed) {
		if (SmartDashboard.getBoolean("FastSpeed", false)) {
			intakeNeoMaster.set(speed);
		} else {
			intakeNeoMaster.set(speed * 0.1);
		}
	}

	public void setPiston(DoubleSolenoid.Value value) {
		piston.set(value);
	}

	public void togglePiston() {
		piston.toggle();
	}

	public DoubleSolenoid.Value getPiston() {
		return piston.get();
	}

	public boolean getIrSensor() {
		return !irSensor.get(); // is inverted to be true when there is an object present
	}

	@Override
	public void periodic() {
		SmartDashboard.putBoolean("Claw IR Sensor", getIrSensor());
		SmartDashboard.putString("Claw Piston", getPiston().toString());
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
