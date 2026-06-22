package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.groups.Groups.*;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Spindexer {

    private final DcMotorEx spindexerMotor;
    private final ServoImplEx rightTriggerServo;
    private final ServoImplEx leftTriggerServo;
    private final DigitalChannel magneticSensor;

    private static final int FULL_ROTATION_CIRCLE = 1000;
    private static final int ONE_CHAMBER_DISTANCE = (int) FULL_ROTATION_CIRCLE / 3;

    private static final double RIGHT_TRIGGER_OPEN_POSITION = 0;
    private static final double RIGHT_TRIGGER_CLOSE_POSITION = 0;

    private static final double LEFT_TRIGGER_OPEN_POSITION = 0;
    private static final double LEFT_TRIGGER_CLOSE_POSITION = 0;

    public Spindexer(HardwareMap hardwareMap){
        spindexerMotor = hardwareMap.get(DcMotorEx.class, "spindexerMotor");
        spindexerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightTriggerServo = hardwareMap.get(ServoImplEx.class, "rightTriggerServo");
        leftTriggerServo = hardwareMap.get(ServoImplEx.class, "leftTriggerServo");

        magneticSensor = hardwareMap.get(DigitalChannel.class, "magneticSensor");
    }

    private boolean isAtCorrectPosition(){
        int current = spindexerMotor.getCurrentPosition();
        return !spindexerMotor.isBusy() && magneticSensor.getState();
    }

    public Command spinChambers(int numberTimes){
        return Command.build()
                .setStart(() -> {
                    int target = spindexerMotor.getCurrentPosition() + (numberTimes * ONE_CHAMBER_DISTANCE);
                    spindexerMotor.setTargetPosition(target);
                })
                .setDone(this::isAtCorrectPosition);
    }

    private boolean isLeftTriggerAtPosition(double targetPosition){
        return Math.abs(targetPosition - leftTriggerServo.getPosition()) <= 0.05;
    }

    private boolean isRightTriggerAtPosition(double targetPosition){
        return Math.abs(targetPosition - rightTriggerServo.getPosition()) <= 0.05;
    }

    private Command setRightTriggerPosition(double targetPosition){
        return Command.build()
                .setStart(() -> rightTriggerServo.setPosition(targetPosition))
                .setDone(() -> isRightTriggerAtPosition(targetPosition));
    }

    private Command setLeftTriggerPosition(double targetPosition){
        return Command.build()
                .setStart(() -> leftTriggerServo.setPosition(targetPosition))
                .setDone(() -> isLeftTriggerAtPosition(targetPosition));
    }

    public Command setTriggerPositions(double left, double right){
        return setRightTriggerPosition(right).with(setLeftTriggerPosition(left));
    }

    public Command openTriggers(){
        return setTriggerPositions(LEFT_TRIGGER_OPEN_POSITION, RIGHT_TRIGGER_OPEN_POSITION);
    }

    public Command closeTriggers(){
        return setTriggerPositions(LEFT_TRIGGER_CLOSE_POSITION, RIGHT_TRIGGER_CLOSE_POSITION);
    }

}
