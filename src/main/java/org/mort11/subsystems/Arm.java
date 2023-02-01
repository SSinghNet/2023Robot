package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

public class Arm extends SubsystemBase{
    private static Arm arm;

    private CANSparkMax driveNeo;

    private Arm() {
        driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
    }
    
    @Override
    public void periodic() {

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
