
package org.usfirst.frc.team2220.robot;


//import edu.wpi.first.wpilibj.*;
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
    	TwilightTalon frWheel = new TwilightTalon(1);
    	TwilightTalon flWheel = new TwilightTalon(2);
    	TwilightTalon blWheel = new TwilightTalon(3);
    	TwilightTalon brWheel = new TwilightTalon(4);
    	
    	ModuleRotation frModule = new ModuleRotation(new TwilightTalon(5));
    	ModuleRotation flModule = new ModuleRotation(new TwilightTalon(6));
    	ModuleRotation blModule = new ModuleRotation(new TwilightTalon(7));
    	ModuleRotation brModule = new ModuleRotation(new TwilightTalon(8));
    	
    	Joystick xbox = new Joystick(0);
    	
    	SmartDashboard dash = new SmartDashboard();
     	//testModule.changeControlMode(TalonControlMode.Position);
    	//testModule.setFeedbackDevice(FeedbackDevice.EncFalling);
    	boolean oldButton0 = false;
    	boolean button0;
    	boolean oldButton1 = false;
    	boolean button1;
    	double leftAxis, rightAxis;
        while (isOperatorControl() && isEnabled()) {
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
