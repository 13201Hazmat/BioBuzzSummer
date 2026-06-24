package org.firstinspires.ftc.teamcode.camera;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Arducam {
    private AprilTagProcessor a;
    private VisionPortal v;

    private List<Integer> t = List.of(21, 22);

    private Position p = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private YawPitchRollAngles o = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, 0, 0, 0);

    public Arducam(HardwareMap h){
        a = new AprilTagProcessor.Builder()
                .setCameraPose(p, o)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();
        v = new VisionPortal.Builder()
                .setCamera(h.get(WebcamName.class, "Arducam"))
                .addProcessor(a)
                .build();
    }

    public Pose2D getFTCPose() {
        for (AprilTagDetection d : a.getDetections()) {
            if (d.metadata != null) {
                if (t.contains(d.id)) {
                    return new Pose2D(
                            DistanceUnit.INCH,
                            d.robotPose.getPosition().x,
                            d.robotPose.getPosition().y,
                            AngleUnit.DEGREES,
                            d.robotPose.getOrientation().getYaw(AngleUnit.DEGREES)
                    );
                }

            }
        }
        return null;
    }

    public Pose getPedroPose(){
        Pose2D f = getFTCPose();
        if (f == null) return null;

        double x = f.getX(DistanceUnit.INCH) + 72;
        double y = f.getY(DistanceUnit.INCH) + 72;
        double h = f.getHeading(AngleUnit.DEGREES) - 90;

        return new Pose(x, y, Math.toRadians(h));
    }

    public List<AprilTagDetection> getDetections() {
        return a.getDetections();
    }

    public void resume() {
        v.resumeStreaming();
    }
    public void stop() {
        v.stopStreaming();
    }
}
