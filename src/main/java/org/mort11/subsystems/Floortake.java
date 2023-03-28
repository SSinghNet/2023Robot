package org.mort11.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Floortake.*;

import org.mort11.util.Constants.RobotSpecs;

public class Floortake extends SubsystemBase {
    private static Floortake floortake;

    private TalonFX flipFalcon;
    private CANSparkMax driveNeo;

    private boolean flipIn;

    private SimpleMotorFeedforward flipFeedforward;
    private PIDController flipController;

    private Floortake() {
        flipFalcon = new TalonFX(FLIP);
        driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);

        flipFeedforward = new SimpleMotorFeedforward(FLIP_KS, FLIP_KV, FLIP_KA);
        flipController = new PIDController(FLIP_KP, FLIP_KI, FLIP_KD);
        
        flipFalcon.setNeutralMode(NeutralMode.Brake);
        flipFalcon.configForwardSoftLimitThreshold(FLIP_OUT_POS);
        flipFalcon.configReverseSoftLimitThreshold(FLIP_IN_POS);
        flipFalcon.configForwardSoftLimitEnable(true);
        flipFalcon.configReverseSoftLimitEnable(true);

        driveNeo.restoreFactoryDefaults();
        driveNeo.setIdleMode(IdleMode.kCoast);
        driveNeo.burnFlash();

        setFlip(true);
    }
    
    public void toggleFlip() {
        setFlip(!flipIn);
    }

    public void setFlip(boolean in) {
        flipIn = in;

        double output = (flipFeedforward.calculate(0) +
                flipController.calculate(flipFalcon.getSelectedSensorPosition(), in ? FLIP_IN_POS : FLIP_OUT_POS))
                / RobotSpecs.MAX_VOLTAGE; // Divide by Max Voltage to get a value between [-1, 1]

        flipFalcon.set(ControlMode.PercentOutput, output);
    }

    public void setDrive(boolean in) {
        driveNeo.set(DRIVE_SPEED * (in ? -1 : 1)); // TODO: check polarity
    }
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("flip encoder", flipFalcon.getSelectedSensorPosition());
    }
    
    public static Floortake getInstance() {
        if (floortake == null) {
            floortake = new Floortake();
        }
        return floortake;
    }
}
