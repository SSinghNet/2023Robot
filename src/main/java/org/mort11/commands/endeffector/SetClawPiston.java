package org.mort11.commands.endeffector;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetClawPiston extends InstantCommand {
    private Claw claw;
    private boolean position;

    public SetClawPiston(boolean position) {
        claw = Claw.getInstance();

        this.position = position;

        addRequirements(claw);
    }

    @Override
    public void initialize() {
        claw.setPiston(position);
    }
}

