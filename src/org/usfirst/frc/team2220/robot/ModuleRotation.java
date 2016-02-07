package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class ModuleRotation {
	private TwilightTalon talon;
	double target;
	
	public ModuleRotation(TwilightTalon Talon) {
		talon = Talon;
		talon.setEncPosition(talon.getPulseWidthPosition());
		talon.setFeedbackDevice(FeedbackDevice.PulseWidth);
		talon.reverseSensor(true);
		talon.setProfile(0);
		talon.changeControlMode(TalonControlMode.Position);
		target = 0.0;
		talon.setF(0.0);
		talon.setP(0.15);
		talon.setI(0.001);
		talon.setD(0.0);
		talon.setAllowableClosedLoopErr(30);	//How imprecise we'll allow the motor to be. lower numbers = more motor jiggling
		talon.set(target);
	}
	
	void increment(int quarters) {		//increments the motor position by an eighth-turn. inputting 2 gives a quarter turn, etc.
		target += quarters * 0.125;
		talon.set(target);
	}
	
	double getError() {			//Finds out how far from the intended position the motor is
		return talon.getError();
	}
	
	void stop() {			//Stops the motor
		talon.stop();
	}
	
	void print() {	//Not sure how to print to the dashboard from within a class
		
	}
	
	double getPosition() {		//Returns a value 0-4095. Uncomment the conversion for degrees.
		return (talon.getPulseWidthPosition() & 0xFFF)/**0.087890625*/;
	}
	
}
