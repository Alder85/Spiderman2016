package org.usfirst.frc.team2220.robot;
import edu.wpi.first.wpilibj.*;

public class TwilightTalon extends CANTalon{
	private double maxTemp;
	private double maxCurrent;
	private boolean disabled = false;
	private boolean tripped = false;
	private Timer timer = new Timer();
	private Timer resetTimer = new Timer();
	
	/**
	 * Cast of CANTalon class
	 * Checks maximum current and temperature, and stops motors if they exceed those
	 * @param port CAN Port Number
	 */
	public TwilightTalon(int port) {
		super(port);
		maxCurrent = 30.0;	//Stall Current for RS 775 12V + 10A
		maxTemp = 500.0;	//We don't use this
	}
	
	public void setMaxCurrent(double newCurrent) {
		maxCurrent = newCurrent;
	}
	
	public void setMaxTemp(double newTemp) {
		maxTemp = newTemp;
	}
	
	@Override
	public void set(double setpoint)
	{
		if(!disabled)
		{
			super.set(setpoint);
		}
	}
	
	@Override
	public void disable()
	{
		super.disable();
		disabled = true;
	}
	
	@Override
	public void enable()
	{
		super.enable();
		disabled = false;
	}
	
	public boolean isDisabled()
	{
		return disabled;
	}
	/**
	 * Tests whether Talon is within 'safe' levels
	 * @return Whether the test was passed
	 */
	public boolean test() {
		boolean test = true;
		if (isOverMaxCurrent()) 
			test = false;
		if(!test)
		{
			if(!tripped)
			{
				timer.reset();
				timer.start();
				tripped = true;
			}
			
			if(timer.get() > 0.15)
			{
				timer.stop();
				timer.reset();
				this.disable();
				disabled = true;
			}
			
		}
		else
		{
			tripped = false;
			timer.stop();
			timer.reset();
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
