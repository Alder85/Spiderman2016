package org.usfirst.frc.team2220.robot;


import edu.wpi.first.wpilibj.*;

public class Drivetrain {
	private ModuleRotation flM, frM, brM, blM;
	private TwilightTalon  flW, frW, brW, blW;
	public final int frontLeft = 1, frontRight = 2, backRight = 3, backLeft = 4;
	//switch this to rotational control soon
	///////////////////
	//		         //
    // 1(FL)   2(FR) //
    //               //
    // 4(BL)   3(BR) //
    //               //
	///////////////////
	
	public Drivetrain()
	{
		
	}
	
	public void goToDefault()
	{
		flM.goToDefault();
		frM.goToDefault();
		brM.goToDefault();
		blM.goToDefault();
	}
	/*
	 * sets the right wheels
	 */
	public void setRightWheels(double pow)
	{
		frW.set(pow);
		brW.set(pow);
	}
	
	/*
	 * sets the left wheels
	 */
	public void setLeftWheels(double pow)
	{
		flW.set(pow);
		blW.set(pow);
	}
	
	public void startXDrive()
	{
		flM.increment(-1);
		frM.increment(1);
		blM.increment(1);
		brM.increment(-1);
	}
	
	public void stopXDrive()
	{
		flM.increment(1);
		frM.increment(-1);
		blM.increment(-1);
		brM.increment(1);
	}
	
	public void turnInwards()
	{
		flM.increment(-1);
		frM.increment(-1);
		blM.increment(1);
		brM.increment(1);
	}
	
	public void turnOutwards()
	{
		flM.increment(1);
		frM.increment(1);
		blM.increment(-1);
		brM.increment(-1);
	}
	
	/*
	 * switch for figuring out which module
	 * maybe I want a module enum?
	 */
	public void incrementModule(int module, int turns)
	{
		switch(module)
		{
			case frontLeft:
				flM.increment(turns);
				break;
			case frontRight:
				frM.increment(turns);
				break;
			case backRight:
				brM.increment(turns);
				break;
			case backLeft:
				blM.increment(turns);
				break;
		}
	}


	
	/*
	 * negative numbers for backwards
	 */
	public void incrementAllModules(int turns)
	{
		flM.increment(turns);
		frM.increment(turns);
		brM.increment(turns);
		blM.increment(turns);
	}
	
	/*
	 * sets up all the modules
	 * works
	 */
	public void setModules(ModuleRotation fl, ModuleRotation fr, ModuleRotation br, ModuleRotation bl)
	{
		flM = fl;
		frM = fr;
		brM = br;
		blM = bl;
	}
	
	/*
	 * sets up all the wheels
	 * 
	 * CHANGE this to wheelrotation once that class works correctly
	 */
	public void setWheels(TwilightTalon fl, TwilightTalon fr,TwilightTalon br, TwilightTalon bl)
	{
		flW = fl;
		frW = fr;
		brW = br;
		blW = bl;
	}
}
