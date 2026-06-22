package org.firstinspires.ftc.teamcode.subsystems.launcher;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class LauncherGate {

    private final ServoImplEx launcherGateServo;

    public final double launcherOpenPosition = 1.0;
    public final double launcherClosePosition = 0.0;

    public LauncherGate(HardwareMap hardwareMap){
        launcherGateServo =
                hardwareMap.get(ServoImplEx.class, "launcherGateServo");
    }

    public Command setServoPosition(double targetPosition){
        return Command.build()
                .setStart(() ->
                        launcherGateServo.setPosition(targetPosition)
                )
                .setDone(() -> true)
                .requiring(launcherGateServo);
    }

    public Command openLauncherGate(){
        return setServoPosition(launcherOpenPosition);
    }

    public Command closeLauncherGate(){
        return setServoPosition(launcherClosePosition);
    }

    public double getPosition(){
        return launcherGateServo.getPosition();
    }
}