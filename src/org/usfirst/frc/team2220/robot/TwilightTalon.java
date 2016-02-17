package org.usfirst.frc.team2220.robot;
import edu.wpi.first.wpilibj.*;

public class TwilightTalon extends CANTalon{
	private double maxTemp;
	private double maxCurrent;
	private boolean disabled = false;
	
	/**
	 * Cast of CANTalon class
	 * Checks maximum current and temperature, and stops motors if they exceed those
	 * @param port CAN Port Number
	 */
	public TwilightTalon(int port) {
		super(port);
		maxCurrent = 40.0;	//Normal Load Value
		maxTemp = 500.0;	//Could not find temp value, currently placeholder
	}
	
	public void setMaxCurrent(double newCurrent) {
		maxCurrent = newCurrent;
	}
	
	public void setMaxTemp(double newTemp) {
		maxTemp = newTemp;
	}
	
	public void set(double setpoint)
	{
		if(!disabled)
		{
			super.set(setpoint);
		}
	}
	
	/**
	 * Tests whether Talon is within 'safe' levels
	 * @return Whether the test was passed
	 */
	public boolean test() {
		boolean test = true;
		if (isOverMaxCurrent()) 
			test = false;
	//	if (isOverMaxTemp())
		//	test = false;
		if(!test)
		{
			this.disable();
			disabled = true;
		}
		return test;
	}
	
	public boolean isOverMaxCurrent() {
		double CurrCurrent = this.getOutputCurrent(); //Returns Amperes
		return CurrCurrent > maxCurrent;
	}
	
	public boolean isOverMaxTemp() {
		double CurrTemp = this.getTemperature(); //Returns Celsius
		return CurrTemp > maxTemp;
	}
	
	//TODO test
	public void stop() {
		this.disableControl();
	}
	
	/**
	 * prints warning to the console, not the SmartDashboard
	 */
	public void printWarning () {	//Checks to see if thresholds are passed
		if (isOverMaxCurrent()) {
			System.out.println("Currently over Maximum Current"); //insert method that prints to dashboard
		}
		if (isOverMaxTemp()) {
			System.out.println("Currently over Maximum Temperature"); //insert method that prints to dashboard
		}
	}
	
	/**
	 * String output for printing to the SmartDashboard OR Console
	 */
	public String toString() {
		double CurrTemp = this.getTemperature(); //Returns Celsius
		double CurrCurrent = this.getOutputCurrent(); //Returns Amperes
		String text = "Current Temperature: " + CurrTemp + ", Current Current: " + CurrCurrent;
		return text;
	}
	
}
