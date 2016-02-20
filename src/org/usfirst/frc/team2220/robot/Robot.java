
package org.usfirst.frc.team2220.robot;


//import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team2220.robot.XBoxController.Button;
import org.usfirst.frc.team2220.robot.XBoxController.TriggerButton;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    
    TwilightTalon talon7 = new TwilightTalon(7);
	ModuleRotation frModule = new ModuleRotation(talon7);
	
	TwilightTalon talon2 = new TwilightTalon(2);
	ModuleRotation flModule = new ModuleRotation(talon2);
	
	TwilightTalon talon4 = new TwilightTalon(4);
	ModuleRotation blModule = new ModuleRotation(talon4);
	
	TwilightTalon talon5 = new TwilightTalon(5);
	ModuleRotation brModule = new ModuleRotation(talon5);
	
	SmartDashboard dash = new SmartDashboard();
	//TwilightTalon[] talons = new TwilightTalon[16];
	TwilightTalon frWheel = new TwilightTalon(8);
	TwilightTalon flWheel = new TwilightTalon(1);
	TwilightTalon blWheel = new TwilightTalon(3);
	TwilightTalon brWheel = new TwilightTalon(6);
	
	
	
	
	
	TwilightTalon collector = new TwilightTalon(10);
	TwilightTalon rightShooter = new TwilightTalon(9);
	TwilightTalon leftShooter  = new TwilightTalon(12);
	
	//POSITIVE SPINS counterclockwise
	TwilightTalon collectorExtender = new TwilightTalon(11);
	//counterclockwise for backward
	//clockwise for forwards
	
	Drivetrain drivetrain = new Drivetrain();
	
	
	
	//LEFt need to be flipped
	//xbox start 0,0 top left so flip right
	XBoxController driverController = new XBoxController(0);
	XBoxController manipulatorController = new XBoxController(1);
	
	
 	//testModule.changeControlMode(TalonControlMode.Position);
	//testModule.setFeedbackDevice(FeedbackDevice.EncFalling);
	
	
    /**
     * TeleOp
     * @TODO
     */
    public void operatorControl() {
    	
    	DigitalInput rearCollector  = new DigitalInput(0);
    	DigitalInput frontCollector = new DigitalInput(1);
    	//this sometimes gives an exception
    	//we must figure this out
    	
    	collectorExtender.enableBrakeMode(true);
    	
    	frWheel.setMaxCurrent(100);
    	flWheel.setMaxCurrent(100);
    	brWheel.setMaxCurrent(100);
    	blWheel.setMaxCurrent(100);
    	collector.setMaxCurrent(60);
    	rightShooter.setMaxCurrent(70);
    	leftShooter.setMaxCurrent(70);
    	
    	flModule.reverseTalon(true);
    	blModule.reverseTalon(true);

    	frModule.reverseTalon(true);
    	brModule.reverseTalon(false);
    	
    	frModule.setRightWheel(true);
    	brModule.setRightWheel(true);
    	
    	frModule.reverseSensor(false);
    	brModule.reverseSensor(false);

    
    	
    	double allTuning = 2.5;
    	
    	frModule.setP(allTuning);
    	brModule.setP(allTuning);
    	flModule.setP(allTuning);
    	blModule.setP(allTuning);
    	
    	drivetrain.setModules(flModule, frModule, brModule, blModule);
    	drivetrain.setWheels(flWheel, frWheel, brWheel, blWheel);
    	
    	int turnQuarters = 2;
    	double leftAxis, rightAxis;
    	double[] maxVal = new double[4];
    	double wheelDZ = 0.15;
    	double[] temp = new double[4];
    	
        while (isOperatorControl() && isEnabled()) {
			/////////////////////////
			//  Primary Controller //
			/////////////////////////
        	driverController.update();
        	
			/////////////////////////
			//       Modules       //
			/////////////////////////
        	if(driverController.onPress(TriggerButton.lTrigger))
        		drivetrain.incrementAllModules(-1);
        	
        	if(driverController.onPress(TriggerButton.rTrigger))
        		drivetrain.incrementAllModules(1);
        	
        	if(driverController.onPress(Button.lBumper))
        		drivetrain.turnOutwards();
        	
        	if(driverController.onPress(Button.rBumper))
        		drivetrain.turnInwards();
        	
			/////////////////////////
			//     Drive Wheels    //
			/////////////////////////
        	leftAxis = deadZone(driverController.getRawAxis(1), wheelDZ);
        	rightAxis = deadZone(driverController.getRawAxis(5) * -1, wheelDZ);
        	
        	drivetrain.setLeftWheels(leftAxis);
        	drivetrain.setRightWheels(rightAxis);
        	
			//////////////////////////
			// Secondary Controller //
			//////////////////////////
			manipulatorController.update();
			
			/////////////////////////
			//      Collector      //
			/////////////////////////
			if(manipulatorController.whileHeld(TriggerButton.lTrigger))
				collector.set(1.0);
			else if(manipulatorController.whileHeld(TriggerButton.rTrigger))
				collector.set(-1.0);
			else
				collector.set(0);
			
			/////////////////////////
			//    Shooter Wheels   //
			/////////////////////////
			if(manipulatorController.onPress(Button.aButton))
			{
        		rightShooter.set(1.0);
        		leftShooter.set(-1.0);
        	}
        	else
        	{
        		rightShooter.set(0);
        		leftShooter.set(0);
        	}
			
			if(manipulatorController.getPOV() != -1)
			{
				if(manipulatorController.getPOV() == 0)
				{
					if(frontCollector.get())
	        			collectorExtender.set(-1.0);
	        		else
	        			collectorExtender.set(0);
				}
				else if(manipulatorController.getPOV() == 180)
				{
					if(rearCollector.get())
	        			collectorExtender.set(1.0);
	        		else
	        			collectorExtender.set(0);
				}
				else
	        	{
	        		collectorExtender.set(0);
	        	}
			}
			else
        	{
        		collectorExtender.set(0);
        	}
			
			
			//dash.putNumber("manipPov", manipulatorController.getPOV());
        	
        	/*
        	xbox.update();
        	secondController.update();
        	if(secondController.whileHeld(Button.aButton))
        	{
        		if(frontCollector.get())
        		{
        			collectorExtender.set(-1.0);
        		}
        		else
        		{
        			collectorExtender.set(0);
        		}
        	}
        	else if(secondController.whileHeld(Button.bButton))
        	{
        		if(rearCollector.get())
        		{
        			collectorExtender.set(1.0);
        		}
        		else
        		{
        			collectorExtender.set(0);
        		}
        	}
        	else
        	{
        		collectorExtender.set(0);
        	}
        	       	        	
        	leftAxis = deadZone(xbox.getRawAxis(1), wheelDZ);
        	rightAxis = deadZone(xbox.getRawAxis(5) * -1, wheelDZ);
        	
        	
        	drivetrain.setLeftWheels(leftAxis);
        	drivetrain.setRightWheels(rightAxis);
        	
        	if(xbox.whileHeld(Button.aButton))
        	{
        		double tempTune = 1;
        		turnQuarters = 1;
            	frModule.setP(tempTune);
            	brModule.setP(tempTune);
            	flModule.setP(tempTune);
            	blModule.setP(tempTune);
        	}
        	else
        	{
        		double tempTune = 2.5;
        		turnQuarters = 2;
            	frModule.setP(tempTune);
            	brModule.setP(tempTune);
            	flModule.setP(tempTune);
            	blModule.setP(tempTune);
        	}
        	
        	if(xbox.onPress(Button.lBumper))
        		drivetrain.turnInwards();
        	
        	if(xbox.onPress(Button.rBumper))
        		drivetrain.turnOutwards();
        
        	if(xbox.whileHeld(Button.xButton))
        	{
        		collector.set(1.0);
        	}
        	else if(xbox.whileHeld(Button.start))
        	{
        		collector.set(-1.0);
        	}
        	else
        	{
        		collector.set(0);
        	}
        	
        	if(xbox.whileHeld(Button.yButton))
        	{
        		rightShooter.set(1.0);
        		leftShooter.set(-1.0);
        	}
        	else
        	{
        		rightShooter.set(0);
        		leftShooter.set(0);
        	}
        	*/
        	
        	
        	/////////////////////////
        	//   Print Everything  //
        	/////////////////////////
        	
        	dash.putBoolean("frontCollector", !frontCollector.get());
        	dash.putBoolean("rearCollector", !rearCollector.get());
        	
        	dash.putNumber("backRightErr", talon5.getError());
        	dash.putNumber("backLeftErr", talon4.getError());
        	dash.putNumber("frontRightErr", talon7.getError());
        	dash.putNumber("frontLeftErr", talon2.getError());
        	
        	dash.putNumber("brPos", brModule.getDoublePosition());
        	dash.putNumber("blPos", blModule.getDoublePosition());
        	dash.putNumber("frPos", frModule.getDoublePosition());
        	dash.putNumber("flPos", flModule.getDoublePosition());
        	
        	dash.putNumber("pos2", brModule.getModuloPosition());
        	dash.putNumber("pos3", brModule.getDesiredPosition());

        	dash.putBoolean("withinStopBR", brModule.withinRange());
        	dash.putBoolean("withinStopBL", blModule.withinRange());
        	dash.putBoolean("withinStopFR", frModule.withinRange());
        	dash.putBoolean("withinStopFL", flModule.withinRange());
        	
        	
        	dash.putNumber("powBR", talon5.getOutputCurrent());
        	dash.putNumber("powBL", talon4.getOutputCurrent());
        	dash.putNumber("powFR", talon7.getOutputCurrent());
        	dash.putNumber("powFL", talon2.getOutputCurrent());
        	
        	
			/////////////////////////
			//     Max Currents    //
			/////////////////////////
        	temp[0] = talon5.getOutputCurrent();
        	if(temp[0] > maxVal[0])
        		maxVal[0] = temp[0];
        	dash.putNumber("maxBR", maxVal[0]);
        	
        	temp[1] = talon4.getOutputCurrent();
        	if(temp[1] > maxVal[1])
        		maxVal[1] = temp[1];
        	dash.putNumber("maxBL", maxVal[1]);
        	
        	temp[2] = talon7.getOutputCurrent();
        	if(temp[2] > maxVal[2])
        		maxVal[2] = temp[2];
        	dash.putNumber("maxFR", maxVal[2]);
        	
        	temp[3] = talon2.getOutputCurrent();
        	if(temp[3] > maxVal[3])
        		maxVal[3] = temp[3];
        	dash.putNumber("maxFL", maxVal[3]);
			/////////////////////////
			//   Test All Modules  //
			/////////////////////////
        	frWheel.test();
        	flWheel.test();
        	blWheel.test();
        	brWheel.test();
        	talon7.test();
        	talon2.test();
        	talon4.test();
        	talon5.test();
        	collector.test();
        	rightShooter.test();
        	leftShooter.test();
        	
       

            Timer.delay(0.005);		// wait for a motor update time
        }
    }
    public void practice()
    {
    	while(isEnabled())
    	{
	    	dash.putNumber("backRightErr", talon5.getError());
	    	dash.putNumber("backLeftErr", talon4.getError());
	    	dash.putNumber("frontRightErr", talon7.getError());
	    	dash.putNumber("frontLeftErr", talon2.getError());
    	}
    }

    /**
     * Test mode
     * @TODO diagnostics
     */
    public void test() {
    	
    }
}
