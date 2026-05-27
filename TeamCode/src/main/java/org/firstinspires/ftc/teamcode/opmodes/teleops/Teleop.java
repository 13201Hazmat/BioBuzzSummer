package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "Main TeleOp")
public class Teleop extends CommandOpMode {
    private Robot robot;
    private Prompter prompter = new Prompter(this);
    private Alliance allianceSelected;

    @Override
    public void init() {
        super.init();

        robot = new Robot(hardwareMap, Alliance.RED);

        prompter.prompt("alliance", new OptionPrompt<>("Select Alliance", Alliance.RED, Alliance.BLUE));

    }

    public void onPromptsComplete() {
        robot.alliance = prompter.get("alliance");

        telemetry.addData("Selected Alliance", robot.alliance);
        telemetry.update();
    }

    @Override
    public void init_loop() {
        prompter.run();
    }

    @Override
    public void loop() {
        super.loop();

        robot.launcher.periodic();


        telemetry.addData("Launcher Velocity", robot.launcher.getCurrentVelocity());
        telemetry.addData("Intake State", robot.intake.getState());
        telemetry.addData("Alliance", robot.alliance);
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}