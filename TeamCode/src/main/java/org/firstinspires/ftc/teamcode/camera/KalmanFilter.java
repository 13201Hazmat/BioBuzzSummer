package org.firstinspires.ftc.teamcode.camera;

import com.pedropathing.geometry.Pose;

public class KalmanFilter {
    private double x,y,h;
    private double pX, pY,pH;

    private final double Q = 0.01;  //odometry noise
    private final double R = 0.01;  //camera noise

    public void init(Pose p){
        x = p.getX();
        y = p.getY();
        h = p.getHeading();
        pX = 1; pY = 1; pH = 1;
    }
    public Pose delta(Pose c, Pose l){
        double dx = c.getX() - l.getX();
        double dy = c.getY() - l.getY();
        double dh = c.getHeading() - l.getHeading();

        return new Pose(dx, dy, dh);
    }
    public void predict(Pose delta){
        x += delta.getX();
        y += delta.getY();
        h += delta.getHeading();

        pX += Q;
        pY += Q;
        pH += Q;
    }

    public void update(Pose c){
        double Kx = pX / (pX + R);
        double Ky = pY / (pY + R);
        double Kh = pH / (pH + R);

        x += Kx * (c.getX() - x);
        y += Ky * (c.getY() - y);
        h += Kh * (c.getHeading() - h);

        pX *= (1 - Kx);
        pY *= (1 - Ky);
        pH *= (1 - Kh);
    }

    public Pose getPose(){
        return new Pose(x,y,h);
    }
}
