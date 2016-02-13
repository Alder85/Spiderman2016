package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class ModuleRotation {
	private TwilightTalon talon;
	double defaultTarget = 0.0, target;
	int relativeAllowableError = 30, allowableError = 10;
	
	/*
	 * Constructor will, in the future, take more parameters
	 */
	public ModuleRotation(TwilightTalon Talon) {
		talon = Talon;
		talon.setEncPosition(talon.getPulseWidthPosition()); //initialize to absolute
		talon.setFeedbackDevice(FeedbackDevice.PulseWidth);
		talon.changeControlMode(TalonControlMode.Position);
		
		target = defaultTarget;
		this.reverseSensor(false);
		
		talon.setProfile(0); //we aren't using multiple profiles yet
		
		
		//this.setP(0.15); //previously 0.15
		this.setI(0.001);
		talon.setAllowableClosedLoopErr(allowableError);	//how much error is allowable
		
		
		//talon.set(defaultTarget);
	}
	
	void resetTalonPos()
	{
		talon.setEncPosition(talon.getPulseWidthPosition());
	}
	
	
	
	void setOffset(double val)
	{
		defaultTarget = val;
		target = defaultTarget;
	}
	
	void goToDefault()
	{
		//talon.setPulseWidthPosition((int) this.getPosition());
		//talon.setPosition(talon.getPosition() % 1);
		target = defaultTarget;
		talon.set(target);
	}
	
	
	void setPID(double p, double i, double d)
	{
		talon.setPID(p, i, d);
	}
	void setP(double in)
	{
		talon.setP(in);
	}
	
	void setI(double in)
	{
		talon.setI(in);
	}
	
	void setD(double in)
	{
		talon.setD(in);
	}
	
	/*
	 * Reverses the sensor
	 */
	void reverseSensor(boolean reversed)
	{
		talon.reverseSensor(reversed);
	}
	
	void reverseTalon(boolean reversed)
	{
		talon.reverseOutput(reversed);
	}
	
	boolean withinRange()
	{
		return (this.getError() < relativeAllowableError && this.getError() > -relativeAllowableError);
	}

	/*
	 * increments the motor position by an eighth-turn. inputting 2 gives a quarter turn, etc.
	 * Technically we refer to an 8th full turn as a quarter turn, as only 1/2 full is relevant 
	 * because the modules are symetrical
	 */
	void increment(int quarters) {		
		//target = talon.get() + (quarters * 0.125);
		talon.enable();
		target += quarters * 0.125;
		talon.set(target);
	}
	
	void set(double point)
	{
		talon.set(point);
	}
	
	double getError() {			
		return talon.getError();
	}
	
	void stop() {			
		talon.stop();
	}
	
	public String toString() {	
		return "";
	}
	
	/*
	 * Returns absolute position 0 to 4095
	 * uncomment constant to convert to degrees
	 */
	double getPosition() {		
		return (talon.getPulseWidthPosition() & 0xFFF)/**0.087890625*/;
	}
	
}
