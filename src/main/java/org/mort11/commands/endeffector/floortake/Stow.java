package org.mort11.commands.endeffector.floortake;

import org.mort11.subsystems.Floortake;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Stow extends CommandBase {
    private Floortake floortake;

    public Stow() {
        floortake = Floortake.getInstance();
        addRequirements(floortake);
    }

    @Override
    public void initialize() {
        floortake.setFlipState(true);
        floortake.setDrive(Constants.Floortake.PASSIVE_SPEED);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        floortake.setDrive(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
