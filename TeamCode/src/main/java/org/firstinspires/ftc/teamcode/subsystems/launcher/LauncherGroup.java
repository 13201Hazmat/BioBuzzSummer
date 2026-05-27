package org.firstinspires.ftc.teamcode.subsystems.launcher;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class LauncherGroup {
    private Launcher launcher;
    private LauncherGate launcherGate;
    private LauncherHood launcherHood;

    public LauncherGroup(HardwareMap hardwareMap){
        launcher = new Launcher(hardwareMap);
        launcherGate = new LauncherGate(hardwareMap);
        launcherHood = new LauncherHood(hardwareMap);
    }


}
