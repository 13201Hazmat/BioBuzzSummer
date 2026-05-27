package org.firstinspires.ftc.teamcode.subsystems.launcher;

import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.*;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    private DcMotorEx rightLauncherMotor;
    private DcMotorEx leftLauncherMotor;

    enum LauncherState{
        ON,
        OFF,
        IDLE
    }
    private LauncherState launcherState;

    private double currentVelocity = 0.0;
    private final double idleVelocity = 1000.0;
    /*
    * The threshold is the difference between the velocities of the
    * */
    private final double threshold = 20;

    public Launcher(HardwareMap hardwareMap){
        rightLauncherMotor = hardwareMap.get(DcMotorEx.class, "rightLauncherMotor");
        leftLauncherMotor = hardwareMap.get(DcMotorEx.class, "leftLauncherMotor");
        launcherState = LauncherState.IDLE;
    }

    public boolean atTargetVelocity(double targetVelocity){
        return Math.abs(targetVelocity - currentVelocity) <= threshold;
    }

    public Command setVelocity(double targetVelocity){
        return Command.build()
                .setExecute(() -> {
                    rightLauncherMotor.setVelocity(targetVelocity);
                    leftLauncherMotor.setVelocity(targetVelocity);
                }).then(instant(() -> launcherState = LauncherState.ON))
                .setDone(() -> atTargetVelocity(targetVelocity))
                .requiring(rightLauncherMotor, leftLauncherMotor);
    }

    public double getCurrentVelocity(){
        return currentVelocity;
    }

    public Command stopLauncher(){
        return Command.build()
                .setExecute(() -> {
                    rightLauncherMotor.setVelocity(0);
                    leftLauncherMotor.setVelocity(0);
                }).then(instant(() -> launcherState = LauncherState.OFF))
                .setDone(() -> atTargetVelocity(0))
                .requiring(rightLauncherMotor, leftLauncherMotor);
    }

    public Command setIdleVelocity(){
        return Command.build()
                .setExecute(() -> {
                    rightLauncherMotor.setVelocity(idleVelocity);
                    leftLauncherMotor.setVelocity(idleVelocity);
                }).then(instant(() -> launcherState = LauncherState.IDLE))
                .setDone(() -> atTargetVelocity(idleVelocity))
                .requiring(rightLauncherMotor, leftLauncherMotor);
    }

    public void periodic(){
        currentVelocity = (rightLauncherMotor.getVelocity() + leftLauncherMotor.getVelocity()) / 2;
    }

}
