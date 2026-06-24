package org.firstinspires.ftc.teamcode.tele;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.camera.Arducam;
import org.firstinspires.ftc.teamcode.camera.KalmanFilter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class LocalizationTest extends LinearOpMode {
    private Arducam a;

    @Override
    public void runOpMode() {
        a = new Arducam(hardwareMap);
        Follower f = Constants.createFollower(hardwareMap);

        Pose last = new Pose(112, 80, Math.toRadians(90));

        f.setPose(last);

        while (opModeInInit()) {
            f.update();
        }

        waitForStart();

        while (opModeIsActive()){
            f.update();


            Pose newPose = a.getPedroPose();

            if (gamepad1.dpad_down) {
                a.stop();
            } else if (gamepad1.dpad_up) {
                a.resume();
            }

            if (newPose != null){
                f.setPose(newPose);
                telemetry.addData("new x", newPose.getX());
                telemetry.addData("new y", newPose.getY());
                telemetry.addData("new h", newPose.getHeading());
            }


            telemetry.update();
        }

    }
}
