package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.launcher.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.launcher.LauncherGate;
import org.firstinspires.ftc.teamcode.subsystems.launcher.LauncherHood;

public class Robot {
    public final Intake intake;
    public final Launcher launcher;
    public final LauncherGate launcherGate;
    public final LauncherHood launcherHood;
    public Alliance alliance;

    public Robot(HardwareMap hardwareMap, Alliance alliance) {
        this.intake = new Intake(hardwareMap);
        this.launcher = new Launcher(hardwareMap);
        this.launcherGate = new LauncherGate(hardwareMap);
        this.launcherHood = new LauncherHood(hardwareMap);

        this.alliance = alliance;
    }
}
