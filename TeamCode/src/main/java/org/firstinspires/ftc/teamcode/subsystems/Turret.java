package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.data.Alliance;

public class Turret {
    private ServoImplEx leftTurretServo;
    private ServoImplEx rightTurretServo;

    private double targetTurretAngle = 0;
    private final double tolerance = 0.05; // servo positions are 0-1, 0.3 was way too loose

    private Alliance alliance;

    public Turret(HardwareMap hardwareMap, Alliance alliance){
        leftTurretServo = hardwareMap.get(ServoImplEx.class, "leftTurretServo");
        rightTurretServo = hardwareMap.get(ServoImplEx.class, "rightTurretServo");

        this.alliance = alliance;
    }

    public boolean isAtTurretPosition(){
        return Math.abs(rightTurretServo.getPosition() - targetTurretAngle) < tolerance
                && Math.abs(leftTurretServo.getPosition() - targetTurretAngle) < tolerance;
    }

    public Command moveTo(double position){
        return new CommandBuilder()
                .setStart(() -> {
                    targetTurretAngle = position;
                    rightTurretServo.setPosition(position);
                    leftTurretServo.setPosition(position);
                })
                .setDone(this::isAtTurretPosition)
                .requiring(leftTurretServo, rightTurretServo);
    }

    public Alliance getAlliance(){
        return alliance;
    }
}