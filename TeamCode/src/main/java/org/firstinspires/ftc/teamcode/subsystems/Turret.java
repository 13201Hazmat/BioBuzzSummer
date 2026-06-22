package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.data.Alliance;

public class Turret {
    private ServoImplEx leftTurretServo;
    private ServoImplEx rightTurretServo;

    private static final double TURRET_MIN_DEGREES = -180;
    private static final double TURRET_MAX_DEGREES = 180;

    private Alliance currentAlliance;

    private double targetTurretAngle = 0;
    private double tolerance = 0.3;

    private boolean isAtCorrectTurretPosition;

    public Turret(HardwareMap hardwareMap, Alliance alliance){
        leftTurretServo = hardwareMap.get(ServoImplEx.class, "leftTurretServo");
        rightTurretServo = hardwareMap.get(ServoImplEx.class, "rightTurretServo");
        isAtCorrectTurretPosition = false;

        this.currentAlliance = alliance;
    }

    public double getLeftTurretPosition(){
        return leftTurretServo.getPosition();
    }

    public double getRightTurretPosition(){
        return rightTurretServo.getPosition();
    }

    public void setTargetPosition(double position){
        targetTurretAngle = position;
    }

    public boolean isAtTurretPosition(){
        if(Math.abs(rightTurretServo.getPosition() - targetTurretAngle) < tolerance && Math.abs(leftTurretServo.getPosition()) - targetTurretAngle < tolerance){
            isAtCorrectTurretPosition = true;
            return true;
        }
        isAtCorrectTurretPosition = false;
        return false;
    }

    private Command setTurretPosition(){
        return new CommandBuilder()
                .setExecute(() -> {
                    rightTurretServo.setPosition(targetTurretAngle);
                    leftTurretServo.setPosition(targetTurretAngle);
                })
                .setDone(this::isAtTurretPosition)
                .setEnd((endCondition)-> {
                    if(endCondition == EndCondition.INTERRUPTED){
                        //idk do something
                    }
                });
    }

    public void periodic(){
        isAtCorrectTurretPosition = isAtTurretPosition();
        rightTurretServo.setPosition(targetTurretAngle);
        leftTurretServo.setPosition(targetTurretAngle);
    }
}
