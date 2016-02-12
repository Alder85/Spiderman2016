
package org.usfirst.frc.team2220.robot;


//import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team2220.robot.XBoxController.Button;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is 2220's test code
 */
//potatoe potatoe 
public class Robot extends SampleRobot {
    SmartDashboard dashboard;

    public Robot() {
        
    }

    /**
     * Autonomous 
     * @TODO
     */
    public void autonomous() {
       
    }

    /**
     * TeleOp
     * @TODO
     */
    public void operatorControl() {
    	
    	TwilightTalon frWheel = new TwilightTalon(8);
    	TwilightTalon flWheel = new TwilightTalon(1);
    	TwilightTalon blWheel = new TwilightTalon(3);
    	TwilightTalon brWheel = new TwilightTalon(6);
    	
    	TwilightTalon talon7 = new TwilightTalon(7);
    	ModuleRotation frModule = new ModuleRotation(talon7);
    	
    	TwilightTalon talon2 = new TwilightTalon(2);
    	ModuleRotation flModule = new ModuleRotation(talon2);
    	
    	TwilightTalon talon4 = new TwilightTalon(4);
    	ModuleRotation blModule = new ModuleRotation(talon4);
    	
    	TwilightTalon talon5 = new TwilightTalon(5);
    	ModuleRotation brModule = new ModuleRotation(talon5);
    	
    	flModule.reverseTalon(true);
    	blModule.reverseTalon(true);
    	
    	brModule.reverseSensor(true);
    	frModule.reverseSensor(true);
    	
    	frModule.setOffset(0.29);
    	brModule.setOffset(-0.08);
    	
    	frModule.setP(0.4);
    	brModule.setP(0.4);
    	
    	//LEFt need to be flipped
    	//xbox start 0,0 top left so flip right
    	XBoxController xbox = new XBoxController(0);
    	
    	SmartDashboard dash = new SmartDashboard();
     	//testModule.changeControlMode(TalonControlMode.Position);
    	//testModule.setFeedbackDevice(FeedbackDevice.EncFalling);
    	
    	double leftAxis, rightAxis;
    	
        while (isOperatorControl() && isEnabled()) {
        	xbox.update();
        	dash.putNumber("encErr", talon5.getError());
        	leftAxis = xbox.getRawAxis(1) * 0.5;
        	rightAxis = xbox.getRawAxis(5) * -0.5;
        	blWheel.set(leftAxis);
        	flWheel.set(leftAxis);
        	
        	brWheel.set(rightAxis);
        	frWheel.set(rightAxis);
        	
        	if(xbox.onPress(Button.lBumper))
        	{
        		flModule.goToDefault();
        		frModule.goToDefault();
        		blModule.goToDefault();
        		brModule.goToDefault();
        	}
        	
        	if(xbox.onPress(Button.start))
        	{
        		flModule.increment(1);
        		frModule.increment(1);
        		brModule.increment(1);
        		blModule.increment(1);
        	}
        	if(xbox.onPress(Button.back))
        	{
        		flModule.increment(-1);
        		frModule.increment(-1);
        		brModule.increment(-1);
        		blModule.increment(-1);
        	}
        	if(xbox.onPress(Button.aButton))
        	{
        		flModule.increment(1);
        	}
        	
        	if(xbox.onPress(Button.bButton))
        	{
        		frModule.increment(1);
        	}
        	
        	if(xbox.onPress(Button.xButton))
        	{
        		blModule.increment(1);
        	}
        	
        	if(xbox.onPress(Button.yButton))
        	{
        		brModule.increment(1);
        	}
        	/*
        	if(xbox.whileHeld(Button.xButton))
        	{
        		blWheel.set(0.2);
        		frWheel.set(0.2);
        		flWheel.set(0.2);
        		brWheel.set(0.2);
        	}
        	else
        	{
        		blWheel.set(0);
        		frWheel.set(0);
        		flWheel.set(0);
        		brWheel.set(0);
        	}
        	*/
        	
        	
        	/*
        	button0 = xbox.getRawButton(1);
        	if(button0 && !oldButton0)
        	{
        		frModule.increment(1);
        		flModule.increment(1);
        		blModule.increment(1);
        		brModule.increment(1);
        	}
        	oldButton0 = button0;
        	
        	button1 = xbox.getRawButton(2);
        	if(button1 && !oldButton1)
        	{
        		frModule.increment(-1);
        		flModule.increment(-1);
        		blModule.increment(-1);
        		brModule.increment(-1);
        	}
        	oldButton1 = button1;
        		
        	leftAxis = xbox.getRawAxis(2);
        	rightAxis = xbox.getRawAxis(5) * -1;
        	
        	frWheel.set(rightAxis);
        	brWheel.set(rightAxis);
        	
        	flWheel.set(leftAxis);
        	blWheel.set(leftAxis);
        	*/
        	
        	


            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Test mode
     * @TODO diagnostics
     */
    public void test() {
    }
}
