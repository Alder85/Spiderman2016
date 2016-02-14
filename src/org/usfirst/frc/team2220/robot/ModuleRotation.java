package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class ModuleRotation {
	private TwilightTalon talon;
	double offsetTarget = 0.0, target, quadOffset;
	int relativeAllowableError = 30, allowableError = 10;
	boolean sensorReversed = false;
	
	/*
	 * Constructor will, in the future, take more parameters
	 */
	public ModuleRotation(TwilightTalon Talon) {
		talon = Talon;
		//talon.setEncPosition(talon.getPulseWidthPosition()); //initialize to absolute
		talon.setFeedbackDevice(FeedbackDevice.PulseWidth);
		talon.changeControlMode(TalonControlMode.Position);
		
		//target = offsetTarget + getDesiredPosition();
		target = getDoublePosition();
		this.reverseSensor(false);
		
		talon.setProfile(0); //we aren't using multiple profiles yet
		
		
		//this.setP(0.15); //previously 0.15
		this.setI(0.001);
		talon.setAllowableClosedLoopErr(allowableError);	//how much error is allowable
		
		
		//talon.set(defaultTarget);
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
	
	
	
	void setOffset(double val)
	{
		offsetTarget = val;
	//	target = offsetTarget;
	}
	
	void goToDefault()
	{
		//talon.setPulseWidthPosition((int) this.getPosition());
		//talon.setPosition(talon.getPosition() % 1);
		
		target = offsetTarget + getDesiredPosition();
	//	talon.set(target);
	}
	
	
	
	/*
	 * Returns absolute position 0 to 4095
	 * uncomment constant to convert to degrees
	 */
	double getPosition() 
	{		
		if(sensorReversed)
			return talon.getPulseWidthPosition() * -1;
		return talon.getPulseWidthPosition();// & 0xFFF)/**0.087890625*/;
	}
	
	double getDoublePosition() 
	{
		return this.getPosition() / 4096;
	}
	
	double getModuloPosition() 
	{
		return this.getDoublePosition() % 1;
	}
	
	double getDesiredPosition()
	{
		double out = getModuloPosition();
		if(out <= 0.25)
			out = 0;
		else if(out >= 0.75)
			out = 1;
		else
			out = 0.5;
		return out + getIntegerPosition();
	}
	
	double getIntegerPosition() {
		 return this.getDoublePosition() - this.getModuloPosition();
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
		sensorReversed = reversed;
	}
	
	void reverseTalon(boolean reversed)
	{
		talon.reverseOutput(reversed);
	}
	
	boolean withinRange()
	{
		return (this.getError() < relativeAllowableError && this.getError() > -relativeAllowableError);
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
	
	
}
