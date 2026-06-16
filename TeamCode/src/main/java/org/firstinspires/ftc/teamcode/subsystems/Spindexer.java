package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Spindexer {

    private final DcMotorEx spindexerMotor;
    private final ServoImplEx triggerServo;
    private final DigitalChannel magneticSensor;

    private static final int FULL_ROTATION_CIRCLE = 1000;
    private static final int ONE_CHAMBER_DISTANCE = (int) FULL_ROTATION_CIRCLE / 3;

    private static final double TRIGGER_OPEN_POSITION = 0;
    private static final double TRIGGER_CLOSE_POSITION = 0;

    public Spindexer(HardwareMap hardwareMap){
        spindexerMotor = hardwareMap.get(DcMotorEx.class, "spindexerMotor");
        spindexerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        triggerServo = hardwareMap.get(ServoImplEx.class, "triggerServo");

        magneticSensor = hardwareMap.get(DigitalChannel.class, "magneticSensor");
    }

    private boolean isAtCorrectPosition(){
        return spindexerMotor.getCurrentPosition() % ONE_CHAMBER_DISTANCE == 0 && magneticSensor.getState();
    }

    public Command spinChambers(int numberTimes){
        return Command.build()
                .setExecute(() -> spindexerMotor.setTargetPosition(numberTimes * ONE_CHAMBER_DISTANCE))
                .setDone(this::isAtCorrectPosition);
    }

    public boolean isAtCorrectPosition(double targetPosition){
        return triggerServo.getPosition() - targetPosition <= 0.067;
    }

    public Command moveTriggerServo(double targetPosition){
        return Command.build()
                .setExecute(() -> triggerServo.setPosition(targetPosition))
                .setDone(() -> isAtCorrectPosition(targetPosition));
    }

    public Command openTriggerServo(){
        return moveTriggerServo(TRIGGER_OPEN_POSITION);
    }

    public Command closeTriggerServo(){
        return moveTriggerServo(TRIGGER_CLOSE_POSITION);
    }

}
