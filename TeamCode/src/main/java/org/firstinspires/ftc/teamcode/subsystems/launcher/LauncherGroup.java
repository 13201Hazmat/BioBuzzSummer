package org.firstinspires.ftc.teamcode.subsystems.launcher;

import static com.pedropathing.ivy.groups.Groups.*;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Spindexer;

public class LauncherGroup {
    private Launcher launcher;
    private LauncherGate launcherGate;
    private LauncherHood launcherHood;
    private Spindexer spindexer;

    public LauncherGroup(HardwareMap hardwareMap){
        launcher = new Launcher(hardwareMap);
        launcherGate = new LauncherGate(hardwareMap);
        launcherHood = new LauncherHood(hardwareMap);
        spindexer = new Spindexer(hardwareMap);
    }

    public Command cycle(){
        return sequential(
                launcherGate.openLauncherGate(),
                spindexer.openTriggerServo(),
                launcher.setVelocity(1000),
                spindexer.spinChambers(6)
        );
    }

    public Launcher getLauncher(){
        return launcher;
    }

    public Spindexer getSpindexer(){
        return spindexer;
    }

    public LauncherGate getLauncherGate(){
        return launcherGate;
    }

    public LauncherHood getLauncherHood(){
        return launcherHood;
    }


}
