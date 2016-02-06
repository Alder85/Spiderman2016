
package org.usfirst.frc.team2220.robot;


//import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
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
    	int absPosition = testModule.getPulseWidthPosition();
    	testModule.setEncPosition(absPosition);
    	testModule.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	testModule.reverseSensor(false);
    	
    	testModule.setProfile(0);
    	testModule.setF(0.0);
    	testModule.setP(0.1);
    	testModule.setI(0.0);
    	testModule.setD(0.0);
    	
    	//testModule.setPulseWidthPosition(0);
    	
    	
    	//testModule.reverseOutput(true);
    	
    	SmartDashboard dash = new SmartDashboard();
     	//testModule.changeControlMode(TalonControlMode.Position);
    	//testModule.setFeedbackDevice(FeedbackDevice.EncFalling);
    	
        while (isOperatorControl() && isEnabled()) {
        		testModule.set(-0.1); 
        	   	dash.putNumber("error", testModule.getError());
        		dash.putNumber("tickPos", testModule.get());
        		dash.putNumber("pulseWidthPos", testModule.getPulseWidthPosition());
        		dash.putString("devicePresent", testModule.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) + "");
        		//QuadEncoder, AnalogEncoder, AnalogPot, EncRising, EncFalling 
        	//	dash.putNumber("PWM velocity", testModule.getPulseWidthVelocity());
        		dash.putNumber("analogRaw", testModule.getAnalogInRaw());
        		dash.putNumber("quadPos", testModule.getEncPosition());
        		


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
