
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

    public double deadZone(double val, double zone)
    {
    	if(val < zone && val > -zone)
    		return 0;
    	return val;
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
    	
    	TwilightTalon collector = new TwilightTalon(10);
    	TwilightTalon rightShooter = new TwilightTalon(9);
    	TwilightTalon leftShooter  = new TwilightTalon(12);
    	
    	
    	flModule.reverseTalon(true);
    	blModule.reverseTalon(true);
    	brModule.reverseTalon(true);
    	
    	brModule.reverseSensor(true);
    	frModule.reverseSensor(true);
    	
    	frModule.setOffset(0.37);
    	brModule.setOffset(0.04);
    	
    	flModule.setOffset(-0.14);
    	blModule.setOffset(0.19);
    	
    	double allTuning = 2.5;
    	frModule.setP(allTuning);
    	brModule.setP(allTuning);
    	flModule.setP(allTuning);
    	blModule.setP(allTuning);
    	
    	Drivetrain drivetrain = new Drivetrain();
    	
    	drivetrain.setModules(flModule, frModule, brModule, blModule);
    	drivetrain.setWheels(flWheel, frWheel, brWheel, blWheel);
    	
    	//LEFt need to be flipped
    	//xbox start 0,0 top left so flip right
    	XBoxController xbox = new XBoxController(0);
    	
    	SmartDashboard dash = new SmartDashboard();
     	//testModule.changeControlMode(TalonControlMode.Position);
    	//testModule.setFeedbackDevice(FeedbackDevice.EncFalling);
    	
    	double leftAxis, rightAxis;
    	double[] maxVal = new double[4];
    	double wheelDZ = 0.15;
    	double[] temp = new double[4];
        while (isOperatorControl() && isEnabled()) {
        	xbox.update();
        	dash.putNumber("backRightErr", talon5.getError());
        	dash.putNumber("backLeftErr", talon4.getError());
        	dash.putNumber("frontRightErr", talon7.getError());
        	dash.putNumber("frontLeftErr", talon2.getError());
        	dash.putNumber("encPos", brModule.getPosition());
        	dash.putNumber("powBR", talon5.get());
        	dash.putNumber("powBL", talon4.get());
        	dash.putNumber("powFR", talon7.get());
        	temp[3] = talon2.getOutputCurrent();
        	if(temp[3] > maxVal[3])
        		maxVal[3] = temp[3];
        	dash.putNumber("powFL", maxVal[3]);
        	
        	leftAxis = deadZone(xbox.getRawAxis(1) * 0.8, wheelDZ);
        	rightAxis = deadZone(xbox.getRawAxis(5) * -0.8, wheelDZ);
        	
        	
        	
        	drivetrain.setLeftWheels(leftAxis);
        	drivetrain.setRightWheels(rightAxis);
        	if(xbox.onPress(Button.lBumper))
        		drivetrain.goToDefault();
        	
        	if(xbox.onPress(Button.aButton)) 
        		drivetrain.incrementAllModules(1);
        	
        	if(xbox.onPress(Button.bButton))
        		drivetrain.incrementAllModules(-1);
        	/*
        	if(xbox.onPress(Button.aButton))
        		drivetrain.incrementModule(1, 1);
        	
        	if(xbox.onPress(Button.bButton))
        		drivetrain.incrementModule(2, 1);
        	
        	if(xbox.onPress(Button.xButton))
        		drivetrain.incrementModule(3, 1);
        	
        	if(xbox.onPress(Button.yButton))
        		drivetrain.incrementModule(4, 1);
        		*/
        	
        	 /*
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
        
        	
        	if(xbox.onPress(Button.xButton))
        	{
        		blModule.increment(1);
        	}
        	
        	if(xbox.onPress(Button.yButton))
        	{
        		brModule.increment(1);
        	}
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
