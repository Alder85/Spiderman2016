
package org.usfirst.frc.team2220.robot;


//import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
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
    	
    	CANTalon testModule = new CANTalon(9);
    	testModule.setProfile(0);
    	testModule.setP(1);
    	testModule.setI(0.005);
    	
    	
    	
    	
    	testModule.reverseOutput(true);
    	
    	SmartDashboard dash = new SmartDashboard();
    //	testModule.changeControlMode(TalonControlMode.Position);
    	testModule.setFeedbackDevice(FeedbackDevice.PulseWidth);

        while (isOperatorControl() && isEnabled()) {
        		testModule.set(0.10); 

        	   	dash.putNumber("error", testModule.getError());
        		dash.putNumber("tickPos", testModule.get());
        		dash.putNumber("getPos", testModule.getPulseWidthPosition());
        		/*
        		dash.putNumber("analogPos", testModule.getAnalogInPosition());
        		dash.putNumber("analogRaw", testModule.getAnalogInRaw());
        		dash.putNumber("quadPos", testModule.getEncPosition());
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
