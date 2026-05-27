package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ivy.Command;
import static com.pedropathing.ivy.commands.Commands.*;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.skeletonarmy.marrow.ftclib.RetryCommand;

public class Intake {
    private DcMotorEx intakeMotor;
    private IntakeState intakeState;

    public enum IntakeState {
        FORWARD,
        REVERSE,
        OFF
    }

    private boolean isOn = false;
    private double intakePower;

    private final double forwardSpeed = 1.0;
    private final double reverseSpeed = -1.0;
    private final double offSpeed = 0.0;

    public Intake(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeState = IntakeState.OFF;
        intakePower = 0.0;
    }

    public boolean getIsOn(){
        return isOn;
    }

    public IntakeState getState(){
        return intakeState;
    }

    public void setState(IntakeState intakeState){
        this.intakeState = intakeState;
    }

    public void changePower(){
        if(intakeState == IntakeState.FORWARD){
            intakePower = forwardSpeed;
        }
        else if(intakeState == IntakeState.REVERSE){
            intakePower = reverseSpeed;
        }
        else {
            intakePower = offSpeed;
        }
    }

    public boolean isAtPower(){
        if(intakeState == IntakeState.FORWARD && intakePower == forwardSpeed){
            isOn = true;
            return true;
        }
        else if(intakeState == IntakeState.REVERSE && intakePower == reverseSpeed){
            isOn = true;
            return true;
        }
        else if(intakeState == IntakeState.OFF && intakePower == offSpeed){
            isOn = false;
            return true;
        }
        return false;
    }

    public Command spinIntake(IntakeState intakeState){
        return Command.build()
                .setStart(() -> setState(intakeState)).then(instant(this::changePower))
                .setExecute(() -> intakeMotor.setPower(intakePower))
                .setDone(this::isAtPower)
                .requiring(intakeMotor);
    }
}
