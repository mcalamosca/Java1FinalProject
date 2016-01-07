package edu.pitt.is17.finalproject.robots;
/**
 * Robot 2 
 * @author Micah Calamosca
 * Final Project
 * INFSCI 0017
 * 
 * Random movements, shoots at scanned robots
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import robocode.*;

import robocode.ScannedRobotEvent;

public class MyRobot2 extends Robot {

	double bulletEnergy;
	int stepDistance;
	int rightRotationAngle;
	int leftRotationAngle;

	
	//http://java2novice.com/java-collections-and-util/properties/file-system/	
	public void loadProperties(){
		//create new InputStream to read properties file
		InputStream inputStream = null;
		
		//create new properties object and initialize to null
		Properties properties = null;
		try {
			//Loads the properties file MyRobot2 and finds the strings for each property
			properties = new Properties();
			inputStream = new FileInputStream(new File("src/config/MyRobot2.properties"));
			properties.load(inputStream);
			String bulletEnergyS = properties.getProperty("bulletenergy");
			String stepDistanceS = properties.getProperty("stepdistance");
			String rightRotationAngleS = properties.getProperty("righttotationangle");
			String leftRotationAngleS = properties.getProperty("leftrotationangle");

            //parse those strings into appropriate data types, and assign to Robot's properties 
			System.out.println(bulletEnergyS);
			System.out.println(stepDistanceS);
			System.out.println(rightRotationAngleS);
			System.out.println(leftRotationAngleS);

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

	//custom fire mode to calculate distance first
	public void distFire(double robotDistance) {
		if (robotDistance > 200 || getEnergy() < 15) {
			fire(1);
		} else if (robotDistance > 50) {
			fire(2);
		} else {
			fire(3);
		}
	}

	public void run() {
		// Robot random movement loops
	    while(true) {
	        //Sends Robot on random paths
	        double distance = Math.random()*250;
	        double angle = Math.random()*60;
	        turnRight(angle);
	        ahead(distance);
	        ahead(100);
	        turnGunRight(90);
	        back(100);
	        turnGunRight(90);
	    }
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// Call our custom firing method
		distFire(e.getDistance());
	}
}
