package org.firstinspires.ftc.teamcode.subsystems.launcher;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LauncherHood {

    private final Servo launcherHoodServo;

    public final double hoodOpenLimit = 1.0;
    public final double hoodCloseLimit = 0.0;

    public LauncherHood(HardwareMap hardwareMap){
        launcherHoodServo =
                hardwareMap.get(Servo.class, "launcherGateServo");
    }

    public Command setServoPosition(double targetPosition){
        return Command.build()
                .setStart(() ->
                        launcherHoodServo.setPosition(targetPosition)
                )
                .setDone(() -> true)
                .requiring(launcherHoodServo);
    }

    public Command openLauncherGate(){
        return setServoPosition(hoodOpenLimit);
    }

    public Command closeLauncherGate(){
        return setServoPosition(hoodCloseLimit);
    }

    public double getPosition(){
        return launcherHoodServo.getPosition();
    }
}