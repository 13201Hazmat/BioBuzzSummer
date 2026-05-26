package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class Robot {
    public final Intake intake;

    public final Alliance alliance;

    public Robot(HardwareMap hardwareMap, Alliance alliance) {
        this.intake = new Intake(hardwareMap);
        this.alliance = alliance;
    }
}
