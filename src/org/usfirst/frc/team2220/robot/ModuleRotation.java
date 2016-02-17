package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class ModuleRotation {
	private TwilightTalon talon;
	double offsetTarget = 0.0, target, startPos;
	int relativeAllowableError = 30, allowableError = 10;
	boolean sensorReversed = false;
	boolean isRightWheel = false;
	
	/**
	 * Constructor will, in the future, take more parameters
	 */
	public ModuleRotation(TwilightTalon Talon) {
		talon = Talon;
		//talon.setEncPosition(talon.getPulseWidthPosition()); //initialize to absolute
		talon.setFeedbackDevice(FeedbackDevice.PulseWidth);
		talon.changeControlMode(TalonControlMode.Position);
		
		//target = offsetTarget + getDesiredPosition();
		target = getDoublePosition();
		startPos = target;
		this.reverseSensor(false);
		
		talon.setProfile(0); //we aren't using multiple profiles yet
		
		
		//this.setP(0.15); //previously 0.15
		this.setI(0.001);
		talon.setAllowableClosedLoopErr(allowableError);	//how much error is allowable
		
		
		//talon.set(defaultTarget);
	}
	
	/**
	 * Gets relative distance from start, so modules can start differently from when macro was recorded
	 * 
	 * For the macro
	 */
	public double distanceFromStart()
	{
		return target - startPos;
	}
	
	/*
	 * this is me thinking, ignore
	 * TODO remove my thinking
	 * START AT 2.0
	 * TARGET 2.5
	 * distanceFromStart = 0.5
	 * 
	 * START AT 3.0
	 * TARGET SHOULD BE 3.5
	 * talon.set startPos + in
	 */
	/**
	 * Recieves a value previously taken from distanceFromStart(), then adds in to the startPos
	 * For the Macro
	 */
	public void setDistanceFromStart(double in)
	{
		talon.set(startPos + in);
	}
	
	/**
	 * WIP
	 * If this doesn't work, I will need to make my own "follower" mode
	 */
	void setFollower(int deviceID)
	{
		talon.changeControlMode(TalonControlMode.Follower);
		talon.set(deviceID);
	}
	/**
	 * Used for follower mode, WIP
	 */
	int getDeviceID()
	{
		return talon.getDeviceID();
	}
	
	/**
	 * This is a bit complicated, but essentially on the drivetrain when motors are mounted right side versus left side
	 * they are mirrored, and simply reversing talon or sensor direction isn't always enough to get them to function
	 * properly, especially with the variable startup position the Mechanical team requested.
	 * This will reverse some commands that go to the rightWheel, relative to quarterTurns
	 * I may or may not have to use this for the macro as well.
	 */
	void setRightWheel(boolean in)
	{
		isRightWheel = in;
	}
	/**
	 * increments the motor position by an eighth-turn. inputting 2 gives a quarter turn, etc.
	 * Technically we refer to an 8th full turn as a quarter turn, as only 1/2 full is relevant 
	 * because the modules are symetrical
	 */
	void incrementQuarters(int quarters) {		
		//target = talon.get() + (quarters * 0.125);
		talon.enable();
		if(!isRightWheel)
			target += quarters * 0.125;
		else
			target -= quarters * 0.125;
		talon.set(target);
	}
	
	/**
	 * same as incrementQuarters, except it's 8ths
	 * except 8ths is actually 16ths of a full turn, but we call them 8ths because of the symmetry
	 */
	void incrementEights(int eights)
	{
		
	}
	
	
	/**
	 * We don't need this anymore!
	 * We just assume the wheel start flat and use that as the offset.
	 * @deprecated
	 */
	void setOffset(double val)
	{
		offsetTarget = val;
	//	target = offsetTarget;
	}
	
	/**
	 * We don't really use this because of the startup assumption that the wheels are flat
	 * @deprecated
	 */
	void goToDefault()
	{
		//talon.setPulseWidthPosition((int) this.getPosition());
		//talon.setPosition(talon.getPosition() % 1);
		
		target = offsetTarget + getDesiredPosition();
	//	talon.set(target);
	}
	
	
	
	/**
	 * Returns absolute position 0 to 4095, does not loop, so technically (-infinity, infinity)
	 * uncomment constant to convert to degrees
	 * uncomment 0xFFF to loop so you only get 0 to 4095
	 */
	double getPosition() 
	{		
		if(sensorReversed)
			return talon.getPulseWidthPosition() * -1;
		return talon.getPulseWidthPosition();// & 0xFFF)/***0.087890625*/;
	}
	
	/**
	 * Scales 0 to 4095 to 0 to 1
	 */
	double getDoublePosition() 
	{
		return this.getPosition() / 4096;
	}
	
	/**
	 * Gets distance from last full turn
	 */
	double getModuloPosition() 
	{
		return this.getDoublePosition() % 1;
	}
	
	/**
	 * Figures out which was to turn to be flat
	 * Not really used right now
	 */
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
	
	/**
	 * 
	 * @return the integer position, in full turns
	 */
	double getIntegerPosition() {
		 return this.getDoublePosition() - this.getModuloPosition();
	}
	
	/**
	 * sets the PID
	 * @param p proportional constant
	 * @param i integral constant
	 * @param d derivative constant
	 */
	void setPID(double p, double i, double d)
	{
		talon.setPID(p, i, d);
	}
	/**
	 * @param in proportional constant
	 */
	void setP(double in)
	{
		talon.setP(in);
	}
	/**
	 * @param in integral constant
	 */
	void setI(double in)
	{
		talon.setI(in);
	}
	
	/**
	 * @param in derivative constant
	 */
	void setD(double in)
	{
		talon.setD(in);
	}

	
	/**
	 * Reverses the sensor
	 */
	void reverseSensor(boolean reversed)
	{
		talon.reverseSensor(reversed);
		sensorReversed = reversed;
	}
	
	/**
	 * reverses the talon
	 */
	void reverseTalon(boolean reversed)
	{
		talon.reverseOutput(reversed);
	}
	
	/**
	 * @return If the module is within the allowable error
	 */
	boolean withinRange()
	{
		return (this.getError() < relativeAllowableError && this.getError() > -relativeAllowableError);
	}


	/**
	 * @param point sets the talon to this point
	 */
	void set(double point)
	{
		talon.set(point);
	}
	
	/**
	 * @return the error of the PID loop
	 */
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
