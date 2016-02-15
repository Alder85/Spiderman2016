package org.usfirst.frc.team2220.robot;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 2016 Macro, based off of last years-> https://github.com/DennisMelamed/FRC-Play-Record-Macro
 * Stores data from motors
 * TODO add more motors than just drivetrain
 * @author potatoe420
 *
 */
public class MacroRecorder {
	//this object writes values into the file we specify
		FileWriter writer;
		
		long startTime;
		//Number of file we're dealing with
		static final int autoNumber = 10;
		//only call this, makes sure we are reading/writing from the same file.
		static final String autoFile = new String("/home/lvuser/recordedAuto" + autoNumber + ".csv");
		
		public MacroRecorder() throws IOException
		{
				//Start Time
				startTime = System.currentTimeMillis();
				
				//put the filesystem location you are supposed to write to as a string 
				writer = new FileWriter(autoFile);
		}
		

		public void record(Drivetrain drivetrain) throws IOException
		{
			if(writer != null)
			{
				//Stores a time index associated with these values
				writer.append("" + (System.currentTimeMillis()-startTime));
				
				//Values on this line associated with a time go after this
				
				//drive motors, standard vBus power
				writer.append("," + drivetrain.getWheel(1));
				writer.append("," + drivetrain.getWheel(2));
				writer.append("," + drivetrain.getWheel(3));		
				writer.append("," + drivetrain.getWheel(4));
				
				//drive modules, using setpoints
				writer.append("," + drivetrain.getModuleDistanceFromStart(1));
				writer.append("," + drivetrain.getModuleDistanceFromStart(2));
				writer.append("," + drivetrain.getModuleDistanceFromStart(3));
				writer.append("," + drivetrain.getModuleDistanceFromStart(4) + "\n");
				
				/*
				 * Gotta have the \n delimiter on the end of this set of data otherwise nosuchelementexception
				 */
			}
		}
		
		
		//This stops stuff and puts it in the file
		public void end() throws IOException
		{
			if(writer !=null)
			{
				writer.flush();
				writer.close();
			}
		}
}
