package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;

public class XBoxController extends Joystick{
	
	private Button[] buttonArray = new Button[13];
	
	/*
	 * Initalize button array
	 */
	public XBoxController(int in)
	{
		super(in);
		buttonArray[1] = Button.aButton;
		buttonArray[2] = Button.bButton;
		buttonArray[3] = Button.xButton;
		buttonArray[4] = Button.yButton;
		buttonArray[5] = Button.lBumper;
		buttonArray[6] = Button.rBumper;
		buttonArray[7] = Button.back;
		buttonArray[8] = Button.start;
		buttonArray[9] = Button.lStickPress;
		buttonArray[10] = Button.rStickPress; //triggers are axes
	}
	
	/*
	 * enum for all Button (not axes) types on xbox controller
	 */
	public enum Button {
	    aButton(1), bButton(2), xButton(3), yButton(4), lBumper(5), 
	    rBumper(6), back(7), start(8), lStickPress(9), rStickPress(10);

	    public int value;
	    public boolean pressed = false;
	    public boolean oldValue = false;

	    private Button(int value) {
	      this.value = value;
	    }
	}
	
	/*
	 * true only once when button is pressed
	 */
	public boolean onPress(Button x)
	{
		return (x.pressed && !x.oldValue);
	}
	
	/*
	 * true constantly while button is held
	 */
	public boolean whileHeld(Button x) 
	{
		return x.pressed;
	}
	
	/*
	 * true only once when button is released
	 */
	public boolean onRelease(Button x)
	{
		return (!x.pressed && x.oldValue);
	}
	
	/*
	 * Call this once per robot loop
	 * This will be able to differentiate pressed, held and released
	 */
	public void update()
	{
		for(int i = 1;i <= 10;i++)
		{
			buttonArray[i].oldValue = buttonArray[i].pressed;
			buttonArray[i].pressed = this.getRawButton(buttonArray[i].value);
		}
	}
}
