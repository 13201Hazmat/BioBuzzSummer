package org.firstinspires.ftc.teamcode.tele;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.camera.Arducam;
import org.firstinspires.ftc.teamcode.camera.KalmanFilter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class KalmanTest extends LinearOpMode {
    private Arducam a;
    private KalmanFilter k;
    Pose last;

    @Override
    public void runOpMode() {
        a = new Arducam(hardwareMap);
        k = new KalmanFilter();
        Follower f = Constants.createFollower(hardwareMap);

        last = new Pose(112, 80, Math.toRadians(90));

        f.setPose(last);
        k.init(last);

        while (opModeInInit()) {
            f.update();
        }

        waitForStart();

        while (opModeIsActive()){
            Pose current = f.getPose();
            f.update();

            k.predict(k.delta(current, last));
            last = current;

            Pose cameraPose = a.getPedroPose();
            if (cameraPose != null) {
                k.update(cameraPose);
            }

            Pose newPose = k.getPose();
            f.setPose(newPose);
            telemetry.addData("filtered x", newPose.getX());
            telemetry.addData("filtered y", newPose.getY());
            telemetry.addData("filtered h", newPose.getHeading());
            telemetry.update();
        }

    }
}
