package edu.pitt.is17.finalproject.robots;

import robocode.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Robot 1 
 * @author Micah Calamosca
 * Final Project
 * INFSCI 0017
 * 
 * 
 * Sits still and waits for scanned robots to shoot at
 */
public class MyRobot1 extends Robot {

	double bulletEnergy;
	int stepDistance;
	int rightRotationAngle;
	int leftRotationAngle;


	/**
	 * Default Constructor
	 */
	public MyRobot1(){
		//load robot properties
		loadProperties();
	}



	//http://java2novice.com/java-collections-and-util/properties/file-system/	
	public void loadProperties(){
		//create new InputStream to read properties file
		InputStream inputS = null;

		//create new properties object and initialize to null
		Properties properties = null;
		try {

			//Loads the properties file MyRobot1 and finds the strings for each property 
			properties = new Properties();
			inputS = new FileInputStream(new File("src/config/MyRobot1.properties"));
			properties.load(inputS);
			String bulletEnergyS = properties.getProperty("bulletenergy");
			String stepDistanceS = properties.getProperty("stepdistance");
			String rightRotationAngleS = properties.getProperty("rightrotationangle");
			String leftRotationAngleS = properties.getProperty("leftrotationangle");

			//parse those strings into appropriate data types, and assign to Robot's properties 
			bulletEnergy = Double.parseDouble(bulletEnergyS);
			stepDistance = Integer.parseInt(stepDistanceS);
			rightRotationAngle = Integer.parseInt(rightRotationAngleS);
			leftRotationAngle = Integer.parseInt(leftRotationAngleS);

			System.out.println(bulletEnergy);
			System.out.println(stepDistance);
			System.out.println(rightRotationAngle);
			System.out.println(leftRotationAngle);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}





	public void run() {
		//scan for enemy robots 
		// Loop forever
		while (true) {
			turnGunRight(10); // Scans automatically
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}
}