package edu.pitt.is17.finalproject.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

public class MainGUI {
	public static final String ROBOCODE_INSTALL_DIR = "/robocode";
	public static final String ROBOT_1_NAME = "edu.pitt.is17.finalproject.robots.MyRobot1";
	public static final String ROBOT_2_NAME = "edu.pitt.is17.finalproject.robots.MyRobot2";
	
	//Robot Properties
	//create new properties object and initialize to null
	Properties properties1 = null;
	Properties properties2 = null;

	//Robot 1
	double bulletEnergy1;
	int stepDistance1;
	int rightRotationAngle1;
	int leftRotationAngle1;
	//Robot2
	double bulletEnergy2;
	int stepDistance2;
	int rightRotationAngle2;
	int leftRotationAngle2;

	//save buttons
	private JButton saveButton1;
	private JButton saveButton2;

	//start battle button
	private JButton startBattle;


	private JFrame mainFrame;
	private JLabel robot1label;
	private JLabel robot2label;

	private JPanel robot1Panel;
	private JPanel robot2Panel;

	//start battle button

	//listener for all sliders
	//private listener = new ChangeListener();

	//Properties (Robot 1)
	private JLabel lblBulletEnergy1;
	private JSlider bulletEnergySlider1;
	private JTextField bulletEnergyTxt1;

	private JLabel lblStepDistance1;
	private JSlider stepDistanceSlider1;
	private JTextField stepDistanceTxt1;

	private JLabel lblRightRotationAngle1;
	private JSlider rightRotationSlider1;
	private JTextField rightRotationTxt1;

	private JLabel lblLeftRotationAngle1;
	private JSlider leftRotationAngleSlider1;
	private JTextField leftRotationTxt1;

	//Properties (Robot 2)
	private JLabel lblBulletEnergy2;
	private JSlider bulletEnergySlider2;
	private JTextField bulletEnergyTxt2;

	private JLabel lblStepDistance2;
	private JSlider stepDistanceSlider2;
	private JTextField stepDistanceTxt2;

	private JLabel lblRightRotationAngle2;
	private JSlider rightRotationSlider2;
	private JTextField rightRotationTxt2;

	private JLabel lblLeftRotationAngle2;
	private JSlider leftRotationAngleSlider2;
	private JTextField leftRotationTxt2;

	public MainGUI(){
		prepareGUI();
	}	


	public static void main(String[] args) {
		MainGUI frame = new MainGUI();
	}

	private void prepareGUI(){
		int mFwidth = 1000;
		int mFheight = 600;

		mainFrame = new JFrame("Robot Editor GUI");
		mainFrame.setSize(mFwidth, mFheight);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);

		loadProperties1();
		loadProperties2();

		/**
		 * Robot Panels
		 */
		//Robot 1 Panel bounds (x, y, width, height)
		int panel1x = 25;
		int panel1y = 50;
		int panel1Height = 400;
		int panel1Width = 400;

		//Robot 1 Panel
		robot1Panel = new JPanel();
		robot1Panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		robot1Panel.setBounds(panel1x, panel1y, panel1Width, panel1Height);
		robot1Panel.setLayout(null);
		mainFrame.add(robot1Panel);

		//Robot 1 Title Label
		robot1label = new JLabel("Robot #1", JLabel.RIGHT); //create label, name it
		robot1label.setBounds(panel1x, panel1y -40, 80, 50); //set bounds
		robot1label.setVisible(true);
		mainFrame.add(robot1label);

		//Robot 2 Panel bounds (x, y, width, height)
		int panel2x = panel1x + panel1Width + 120;
		int panel2y = panel1y;
		int panel2Height = panel1Width;
		int panel2Width = panel1Height;

		//Robot 2 Panel
		robot2Panel = new JPanel();
		robot2Panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		robot2Panel.setBounds(panel2x, panel2y, panel2Height, panel2Width);
		robot2Panel.setLayout(null);
		mainFrame.add(robot2Panel);

		//Robot 2 Title Label
		robot2label = new JLabel("Robot #2", JLabel.RIGHT);
		robot2label.setBounds(panel2x +5, panel2y -40, 80, 50);
		robot2label.setVisible(true);
		mainFrame.add(robot2label);

		//BUTTONS <<<<<<<<<<<<<<<>>>>>>>>>>>>>>>
		//save button 1
		saveButton1 = new JButton("Save");
		saveButton1.setBounds(panel1x, panel1y + panel1Height + 10, panel1Width, 30);
		saveButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveProperties1();
				JOptionPane.showMessageDialog(mainFrame, "Properties Saved");

			}});
		mainFrame.add(saveButton1);

		//save button 2
		saveButton2 = new JButton("Save");
		saveButton2.setBounds(panel2x, panel2y + panel2Height + 10, panel2Width, 30);
		saveButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveProperties2();
				JOptionPane.showMessageDialog(mainFrame, "Properties Saved");

			}});
		mainFrame.add(saveButton2);

		//start battle button
		startBattle = new JButton("Run Battle");
		startBattle.setBounds(mFwidth/2 - 65, mFheight-100, 100, 50);
		startBattle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runBattle();
			}});
		mainFrame.add(startBattle);

		/**
		 * Robot Property Labels, Sliders, Text Fields
		 */

		//Bullet Energy Label Robot 1 
		lblBulletEnergy1 = new JLabel("Bullet Energy", JLabel.LEFT);
		lblBulletEnergy1.setBounds(10,10,100,40);
		robot1Panel.add(lblBulletEnergy1);

		//Bullet Energy Slider Robot 1
		bulletEnergySlider1 = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		bulletEnergySlider1.setBounds(110, 10, 200, 40);
		bulletEnergySlider1.setMinorTickSpacing(1);
		bulletEnergySlider1.setMajorTickSpacing(4);
		bulletEnergySlider1.setPaintTicks(true);
		bulletEnergySlider1.setValue((int) bulletEnergy1);
		robot1Panel.add(bulletEnergySlider1);

		bulletEnergySlider1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setBulletEnergy1(source.getValue());
				bulletEnergyTxt1.setText(String.valueOf(source.getValue()));
			}
		});


		//Bullet Energy TextField Robot 1
		bulletEnergyTxt1 = new JTextField();
		bulletEnergyTxt1.setBounds(320, 10, 50, 25);
		bulletEnergyTxt1.setVisible(true);
		bulletEnergyTxt1.setText(String.valueOf(properties1.getProperty("bulletenergy")));;
		robot1Panel.add(bulletEnergyTxt1);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		bulletEnergyTxt1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				setBulletEnergyTxt1(source);
				int value = Integer.parseInt(source.getText());
				bulletEnergySlider1.setValue(value);
			}
		});

		//Step Distance Label Robot 1 
		lblStepDistance1 = new JLabel("Step Distance", JLabel.LEFT);
		lblStepDistance1.setBounds(10,50,100,40);
		robot1Panel.add(lblStepDistance1);

		//Step Distance Slider Robot 1
		stepDistanceSlider1 = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		stepDistanceSlider1.setBounds(110, 50, 200, 40);
		stepDistanceSlider1.setMinorTickSpacing(50);
		stepDistanceSlider1.setMajorTickSpacing(200);
		stepDistanceSlider1.setPaintTicks(true);
		stepDistanceSlider1.setValue(stepDistance1);
		robot1Panel.add(stepDistanceSlider1);

		stepDistanceSlider1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setStepDistance1(source.getValue());
				stepDistanceTxt1.setText(String.valueOf(source.getValue()));
			}
		});

		//Step Distance TextField Robot 1
		stepDistanceTxt1 = new JTextField();
		stepDistanceTxt1.setBounds(320, 50, 50, 25);
		stepDistanceTxt1.setVisible(true);
		stepDistanceTxt1.setText(String.valueOf(properties1.getProperty("stepdistance")));
		robot1Panel.add(stepDistanceTxt1);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		stepDistanceTxt1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setStepDistance1(value);
				stepDistanceSlider1.setValue(value);
			}
		});

		//Right Rotation Angle Label Robot 1 
		lblRightRotationAngle1 = new JLabel("Right Rotation", JLabel.LEFT);
		lblRightRotationAngle1.setBounds(10,90,100,40);
		robot1Panel.add(lblRightRotationAngle1);

		//Right Rotation Angle Slider Robot 1
		rightRotationSlider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		rightRotationSlider1.setBounds(110, 90, 200, 40);
		rightRotationSlider1.setMinorTickSpacing(5);
		rightRotationSlider1.setMajorTickSpacing(20);
		rightRotationSlider1.setPaintTicks(true);
		rightRotationSlider1.setValue(rightRotationAngle1);
		robot1Panel.add(rightRotationSlider1);

		rightRotationSlider1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setRightRotationAngle1(source.getValue());
				rightRotationTxt1.setText(String.valueOf(source.getValue()));
			}
		});

		//Right Rotation Angle TextField Robot 1
		rightRotationTxt1 = new JTextField();
		rightRotationTxt1.setBounds(320, 90, 50, 25);
		rightRotationTxt1.setVisible(true);
		rightRotationTxt1.setText(String.valueOf(properties1.getProperty("rightrotationangle")));
		robot1Panel.add(rightRotationTxt1);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		rightRotationTxt1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setRightRotationAngle1(value);
				rightRotationSlider1.setValue(value);
			}
		});

		//Left Rotation Angle Label Robot 1 
		lblLeftRotationAngle1 = new JLabel("Left Rotation", JLabel.LEFT);
		lblLeftRotationAngle1.setBounds(10,130,100,40);
		robot1Panel.add(lblLeftRotationAngle1);

		//Left Rotation Angle Slider Robot 1
		leftRotationAngleSlider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		leftRotationAngleSlider1.setBounds(110, 130, 200, 40);
		leftRotationAngleSlider1.setMinorTickSpacing(5);
		leftRotationAngleSlider1.setMajorTickSpacing(20);
		leftRotationAngleSlider1.setPaintTicks(true);
		leftRotationAngleSlider1.setValue(leftRotationAngle1);
		robot1Panel.add(leftRotationAngleSlider1);

		leftRotationAngleSlider1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setLeftRotationAngle1(source.getValue());
				leftRotationTxt1.setText(String.valueOf(source.getValue()));
			}
		});

		//Left Rotation Angle TextField Robot 1
		leftRotationTxt1 = new JTextField();
		leftRotationTxt1.setBounds(320, 130, 50, 25);
		leftRotationTxt1.setVisible(true);
		leftRotationTxt1.setText(String.valueOf(properties1.getProperty("leftrotationangle")));
		robot1Panel.add(leftRotationTxt1);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		leftRotationTxt1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setLeftRotationAngle1(value);
				leftRotationAngleSlider1.setValue(value);
			}
		});


		//ROBOT 2 <<<<<<<<<<<<<<<>>>>>>>>>>>>>>>

		//Bullet Energy Label Robot 2 
		lblBulletEnergy2 = new JLabel("Bullet Energy", JLabel.LEFT);
		lblBulletEnergy2.setBounds(10,10,100,40);
		robot2Panel.add(lblBulletEnergy2);

		//Bullet Energy Slider Robot 2
		bulletEnergySlider2 = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		bulletEnergySlider2.setBounds(110, 10, 200, 40);
		bulletEnergySlider2.setMinorTickSpacing(1);
		bulletEnergySlider2.setMajorTickSpacing(4);
		bulletEnergySlider2.setPaintTicks(true);
		bulletEnergySlider2.setValue((int)rightRotationAngle2);
		robot2Panel.add(bulletEnergySlider2);

		bulletEnergySlider2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setBulletEnergy2(source.getValue());
				bulletEnergyTxt2.setText(String.valueOf(source.getValue()));
			}
		});

		//Bullet Energy TextField Robot 2
		bulletEnergyTxt2 = new JTextField();
		bulletEnergyTxt2.setBounds(320, 10, 50, 25);
		bulletEnergyTxt2.setVisible(true);
		bulletEnergyTxt2.setText(String.valueOf(properties2.getProperty("bulletenergy")));
		robot2Panel.add(bulletEnergyTxt2);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		bulletEnergyTxt2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setBulletEnergy2(value);
				bulletEnergySlider2.setValue(value);
			}
		});

		//Step Distance Label Robot 2 
		lblStepDistance2 = new JLabel("Step Distance", JLabel.LEFT);
		lblStepDistance2.setBounds(10,50,100,40);
		robot2Panel.add(lblStepDistance2);

		//Step Distance Slider Robot 2
		stepDistanceSlider2 = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		stepDistanceSlider2.setBounds(110, 50, 200, 40);
		stepDistanceSlider2.setMinorTickSpacing(50);
		stepDistanceSlider2.setMajorTickSpacing(200);
		stepDistanceSlider2.setPaintTicks(true);
		stepDistanceSlider2.setValue(stepDistance2);
		robot2Panel.add(stepDistanceSlider2);

		stepDistanceSlider2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setStepDistance2(source.getValue());
				stepDistanceTxt2.setText(String.valueOf(source.getValue()));
			}
		});

		//Step Distance TextField Robot 2
		stepDistanceTxt2 = new JTextField();
		stepDistanceTxt2.setBounds(320, 50, 50, 25);
		stepDistanceTxt2.setVisible(true);
		stepDistanceTxt2.setText(String.valueOf(properties2.getProperty("stepdistance")));
		robot2Panel.add(stepDistanceTxt2);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		stepDistanceTxt2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setStepDistance2(value);
				stepDistanceSlider2.setValue(value);
			}
		});


		//Right Rotation Angle Label Robot 2 
		lblRightRotationAngle2 = new JLabel("Right Rotation", JLabel.LEFT);
		lblRightRotationAngle2.setBounds(10,90,100,40);
		robot2Panel.add(lblRightRotationAngle2);

		//Right Rotation Angle Slider Robot 2
		rightRotationSlider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		rightRotationSlider2.setBounds(110, 90, 200, 40);
		rightRotationSlider2.setMinorTickSpacing(5);
		rightRotationSlider2.setMajorTickSpacing(20);
		rightRotationSlider2.setPaintTicks(true);
		rightRotationSlider2.setValue(rightRotationAngle2);
		robot2Panel.add(rightRotationSlider2);

		rightRotationSlider2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setRightRotationAngle2(source.getValue());
				rightRotationTxt2.setText(String.valueOf(source.getValue()));
			}
		});

		//Right Rotation Angle TextField Robot 2
		rightRotationTxt2 = new JTextField();
		rightRotationTxt2.setBounds(320, 90, 50, 25);
		rightRotationTxt2.setVisible(true);
		rightRotationTxt2.setText(properties2.getProperty("rightrotationangle"));
		robot2Panel.add(rightRotationTxt2);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		rightRotationTxt2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setRightRotationAngle2(value);
				rightRotationSlider2.setValue(value);
			}
		});


		//Left Rotation Angle Label Robot 2 
		lblLeftRotationAngle2 = new JLabel("Left Rotation", JLabel.LEFT);
		lblLeftRotationAngle2.setBounds(10,130,100,40);
		robot2Panel.add(lblLeftRotationAngle2);


		//Left Rotation Angle Slider Robot 2
		leftRotationAngleSlider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		leftRotationAngleSlider2.setBounds(110, 130, 200, 40);
		leftRotationAngleSlider2.setMinorTickSpacing(5);
		leftRotationAngleSlider2.setMajorTickSpacing(20);
		leftRotationAngleSlider2.setPaintTicks(true);
		leftRotationAngleSlider2.setValue(leftRotationAngle2);
		robot2Panel.add(leftRotationAngleSlider2);

		leftRotationAngleSlider2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setLeftRotationAngle2(source.getValue());
				leftRotationTxt2.setText(String.valueOf(source.getValue()));
			}
		});

		//Left Rotation Angle TextField Robot 2
		leftRotationTxt2 = new JTextField();
		leftRotationTxt2.setBounds(320, 130, 50, 25);
		leftRotationTxt2.setVisible(true);
		leftRotationTxt2.setText(properties2.getProperty("leftrotationangle"));
		robot2Panel.add(leftRotationTxt2);

		//action listener for text field, MUST PRESS ENTER TO SAVE CHANGES 
		leftRotationTxt2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setLeftRotationAngle2(value);
				leftRotationAngleSlider2.setValue(value);
			}
		});


		robot1Panel.repaint();
		robot2Panel.repaint();

		//Exit GUI when exit button clicked
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
	public void loadProperties1(){
		//create new InputStream to read properties file
		InputStream inputS = null;

		try {

			//Loads the properties file MyRobot1 and finds the strings for each property 
			properties1 = new Properties();
			inputS = new FileInputStream(new File("src/config/MyRobot1.properties"));
			properties1.load(inputS);
			String bulletEnergyS = properties1.getProperty("bulletenergy");
			String stepDistanceS = properties1.getProperty("stepdistance");
			String rightRotationAngleS = properties1.getProperty("rightrotationangle");
			String leftRotationAngleS = properties1.getProperty("leftrotationangle");

			//parse those strings into appropriate data types, and assign to Robot's properties 
			bulletEnergy1 = Double.parseDouble(bulletEnergyS);
			stepDistance1 = Integer.parseInt(stepDistanceS);
			rightRotationAngle1 = Integer.parseInt(rightRotationAngleS);
			leftRotationAngle1 = Integer.parseInt(leftRotationAngleS);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadProperties2(){
		//create new InputStream to read properties file
		InputStream inputS = null;


		try {
			//Loads the properties file MyRobot1 and finds the strings for each property 
			properties2 = new Properties();
			inputS = new FileInputStream(new File("src/config/MyRobot2.properties"));
			properties2.load(inputS);
			String bulletEnergyS = properties2.getProperty("bulletenergy");
			String stepDistanceS = properties2.getProperty("stepdistance");
			String rightRotationAngleS = properties2.getProperty("rightrotationangle");
			String leftRotationAngleS = properties2.getProperty("leftrotationangle");

			//parse those strings into appropriate data types, and assign to Robot's properties 
			bulletEnergy2 = Double.parseDouble(bulletEnergyS);
			stepDistance2 = Integer.parseInt(stepDistanceS);
			rightRotationAngle2 = Integer.parseInt(rightRotationAngleS);
			leftRotationAngle2 = Integer.parseInt(leftRotationAngleS);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveProperties1(){
		try {

			properties1.setProperty("bulletenergy", String.valueOf(bulletEnergy1));
			properties1.setProperty("stepdistance", String.valueOf(stepDistance1));
			properties1.setProperty("rightrotationangle", String.valueOf(rightRotationAngle1));
			properties1.setProperty("leftrotationangle", String.valueOf(leftRotationAngle1));
			File f = new File("src/config/MyRobot1.properties");
			OutputStream out = new FileOutputStream(f);
			properties1.store(out, null);
		}
		catch (Exception e ) {
			e.printStackTrace();
		}
	}

	public void saveProperties2(){
		try {
			//set properties 
			properties1.setProperty("bulletenergy", String.valueOf(bulletEnergy2));
			properties1.setProperty("stepdistance", String.valueOf(stepDistance2));
			properties1.setProperty("rightrotationangle", String.valueOf(rightRotationAngle2));
			properties1.setProperty("leftrotationangle", String.valueOf(leftRotationAngle2));
			File f = new File("src/config/MyRobot2.properties");
			OutputStream out = new FileOutputStream(f);
			properties1.store(out, null);
		}
		catch (Exception e ) {
			e.printStackTrace();
		}
	}


	public static void runBattle(){
		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File(ROBOCODE_INSTALL_DIR));
		// Add our own battle listener to the RobocodeEngine
		engine.addBattleListener(new BattleAdaptor(){
			// Called when the battle is completed successfully with battle results
			public void onBattleCompleted(BattleCompletedEvent e) {
				System.out.println("-- Battle has completed --");

				// Print out the sorted results with the robot names
				System.out.println("Battle results:");
				for (robocode.BattleResults result : e.getSortedResults()) {
					System.out.println(" " + result.getTeamLeaderName() + ": " + result.getScore());
				}
			}
			// Called when the game sends out an information message during the battle
			public void onBattleMessage(BattleMessageEvent e) {
				System.out.println("Msg> " + e.getMessage());
			}
			// Called when the game sends out an error message during the battle
			public void onBattleError(BattleErrorEvent e) {
				System.out.println("Err> " + e.getError());
			}

		});
		// Show the Robocode battle view
		engine.setVisible(true);
		// Setup the battle specification
		int numberOfRounds = 5;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
		RobotSpecification[] selectedRobots = engine.getLocalRepository(ROBOT_1_NAME+"*,"+ROBOT_2_NAME+"*");

		System.out.println("Robots selected");

		BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

		System.out.println("Inactivity time:"+battleSpec.getInactivityTime());
		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();
		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}


	public double getBulletEnergy1() {
		return bulletEnergy1;
	}


	public void setBulletEnergy1(double bulletEnergy1) {
		this.bulletEnergy1 = bulletEnergy1;
	}


	public int getStepDistance1() {
		return stepDistance1;
	}


	public void setStepDistance1(int stepDistance1) {
		this.stepDistance1 = stepDistance1;
	}


	public int getRightRotationAngle1() {
		return rightRotationAngle1;
	}


	public void setRightRotationAngle1(int rightRotationAngle1) {
		this.rightRotationAngle1 = rightRotationAngle1;
	}


	public int getLeftRotationAngle1() {
		return leftRotationAngle1;
	}


	public void setLeftRotationAngle1(int leftRotationAngle1) {
		this.leftRotationAngle1 = leftRotationAngle1;
	}


	public double getBulletEnergy2() {
		return bulletEnergy2;
	}


	public void setBulletEnergy2(double bulletEnergy2) {
		this.bulletEnergy2 = bulletEnergy2;
	}


	public int getStepDistance2() {
		return stepDistance2;
	}


	public void setStepDistance2(int stepDistance2) {
		this.stepDistance2 = stepDistance2;
	}


	public int getRightRotationAngle2() {
		return rightRotationAngle2;
	}


	public void setRightRotationAngle2(int rightRotationAngle2) {
		this.rightRotationAngle2 = rightRotationAngle2;
	}


	public int getLeftRotationAngle2() {
		return leftRotationAngle2;
	}


	public void setLeftRotationAngle2(int leftRotationAngle2) {
		this.leftRotationAngle2 = leftRotationAngle2;
	}


	public JTextField getBulletEnergyTxt1() {
		return bulletEnergyTxt1;
	}


	public void setBulletEnergyTxt1(JTextField bulletEnergyTxt1) {
		this.bulletEnergyTxt1 = bulletEnergyTxt1;
	}


	public JTextField getStepDistanceTxt1() {
		return stepDistanceTxt1;
	}


	public void setStepDistanceTxt1(JTextField stepDistanceTxt1) {
		this.stepDistanceTxt1 = stepDistanceTxt1;
	}


	public JTextField getRightRotationTxt1() {
		return rightRotationTxt1;
	}


	public void setRightRotationTxt1(JTextField rightRotationTxt1) {
		this.rightRotationTxt1 = rightRotationTxt1;
	}


	public JTextField getLeftRotationTxt1() {
		return leftRotationTxt1;
	}


	public void setLeftRotationTxt1(JTextField leftRotationTxt1) {
		this.leftRotationTxt1 = leftRotationTxt1;
	}


	public JTextField getBulletEnergyTxt2() {
		return bulletEnergyTxt2;
	}


	public void setBulletEnergyTxt2(JTextField bulletEnergyTxt2) {
		this.bulletEnergyTxt2 = bulletEnergyTxt2;
	}


	public JTextField getStepDistanceTxt2() {
		return stepDistanceTxt2;
	}


	public void setStepDistanceTxt2(JTextField stepDistanceTxt2) {
		this.stepDistanceTxt2 = stepDistanceTxt2;
	}


	public JTextField getRightRotationTxt2() {
		return rightRotationTxt2;
	}


	public void setRightRotationTxt2(JTextField rightRotationTxt2) {
		this.rightRotationTxt2 = rightRotationTxt2;
	}


	public JTextField getLeftRotationTxt2() {
		return leftRotationTxt2;
	}


	public void setLeftRotationTxt2(JTextField leftRotationTxt2) {
		this.leftRotationTxt2 = leftRotationTxt2;
	}
}
