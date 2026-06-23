package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

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
        robot.setAlliance(prompter.get("alliance"));

        telemetry.addData("Selected Alliance", robot.getAlliance());
        telemetry.update();
    }

    @Override
    public void init_loop() {
        prompter.run();
    }

    @Override
    public void loop() {
        super.loop();

        robot.getLauncherGroup().getLauncher().periodic();

        if(gamepad1.rightBumperWasPressed()){
            schedule(robot.getIntake().spinIntake(Intake.IntakeState.FORWARD));
        }
        if(gamepad1.leftBumperWasReleased()){
            schedule(robot.getIntake().spinIntake(Intake.IntakeState.OFF));
        }
//        if(gamepad1.dpadLeftWasPressed()){
//            robot.turret.setTargetPosition(-1);
//        }
//        if(gamepad1.dpadRightWasPressed()){
//            robot.turret.setTargetPosition(1);
//        }
        if(gamepad1.rightTriggerWasPressed()){
            schedule(robot.getLauncherGroup().cycle());
        }

        if(gamepad1.dpadRightWasPressed()){
            schedule(robot.getTurret().moveTo(1.0));
        }

        if(gamepad1.dpadLeftWasPressed()){
            schedule(robot.getTurret().moveTo(0.0));
        }

        telemetry.addData("Launcher Velocity", robot.getLauncherGroup().getLauncher().getCurrentVelocity());
        telemetry.addData("Intake State", robot.getIntake().getState());
        telemetry.addData("Alliance", robot.getAlliance());
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}