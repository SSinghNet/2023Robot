package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Claw.*;

public class Claw extends SubsystemBase {
    private static Claw claw;

    private CANSparkMax intakeNeo;
    private CANSparkMax wristNeo;

    private Claw() {
        intakeNeo = new CANSparkMax(INTAKE, MotorType.kBrushless);
        wristNeo = new CANSparkMax(WRIST, MotorType.kBrushless);
    }
    
    @Override
    public void periodic() {

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
