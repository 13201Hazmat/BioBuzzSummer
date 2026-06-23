package org.firstinspires.ftc.teamcode.subsystems.launcher;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class LauncherHood {

    private final ServoImplEx launcherHoodServo;

    public final double hoodOpenLimit = 1.0;
    public final double hoodCloseLimit = 0.0;

    public LauncherHood(HardwareMap hardwareMap){
        launcherHoodServo = hardwareMap.get(ServoImplEx.class, "launcherHoodServo");
    }

    public Command setServoPosition(double targetPosition){
        return Command.build()
                .setStart(() ->
                        launcherHoodServo.setPosition(targetPosition)
                )
                .setDone(() -> true)
                .requiring(launcherHoodServo);
    }

    public Command openLauncherHood(){
        return setServoPosition(hoodOpenLimit);
    }

    public Command closeLauncherHood(){
        return setServoPosition(hoodCloseLimit);
    }

    public double getPosition(){
        return launcherHoodServo.getPosition();
    }
}