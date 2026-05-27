package org.firstinspires.ftc.teamcode.opmodes.auto.paths;

import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class Routines{
    private Follower follower;

    private PathsAndPoses paths;

    private Intake intake;

    public Routines(PathsAndPoses paths){
        this.paths = paths;
    }

}
