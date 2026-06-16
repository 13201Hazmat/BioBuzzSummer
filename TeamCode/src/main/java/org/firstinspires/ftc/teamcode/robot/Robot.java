package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.launcher.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.launcher.LauncherGate;
import org.firstinspires.ftc.teamcode.subsystems.launcher.LauncherGroup;
import org.firstinspires.ftc.teamcode.subsystems.launcher.LauncherHood;

public class Robot {
    public final Intake intake;
    public final LauncherGroup launcherGroup;
    public final Turret turret;
    public Alliance alliance;

    public Robot(HardwareMap hardwareMap, Alliance alliance) {
        this.intake = new Intake(hardwareMap);
        this.launcherGroup = new LauncherGroup(hardwareMap);
        this.alliance = alliance;

        this.turret = new Turret(hardwareMap, Alliance.BLUE);

    }
}
