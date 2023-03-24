package org.mort11.commands.endeffector;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetClawSpeed extends InstantCommand {
    private Claw claw;
    private boolean in;

    public SetClawSpeed(boolean in) {
        this.in = in;
        claw = Claw.getInstance();
        addRequirements(claw);
    }

    @Override
    public void initialize() {
        claw.setSpeed(in);
    }

    
}
