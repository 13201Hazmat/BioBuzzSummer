package org.firstinspires.ftc.teamcode.opmodes.auto.runnable;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.prompts.MultiOptionPrompt;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.opmodes.teleops.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "Auto")
public class AllAuto extends CommandOpMode {

    private Robot robot;
    private Prompter prompter = new Prompter(this);
    private Alliance allianceSelected;

    private int numberBalls = 0;

    @Override
    public void init() {

        robot = new Robot(hardwareMap, Alliance.RED);

        prompter.prompt("alliance", new OptionPrompt<>("Select Alliance", Alliance.RED, Alliance.BLUE));
        prompter.prompt("numberballs", new MultiOptionPrompt<>(
                "Select Number of balls",
                true,
                true,
                1,
                0, 3, 6, 9, 12, 15, 18));

    }

    public void onPromptsComplete() {
        robot.alliance = prompter.get("alliance");
        numberBalls = prompter.get("numberballs");

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
