package edu.pitt.is17.finalproject.gui;

import java.awt.Font;
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
/**
 * Robot Settings GUI
 * @author Micah Calamosca
 * INFSCI 0017
 * Final Project
 * 
 * Allows user to adjust settings of their two robots
 * and then runs a battle between both of them
 */

@SuppressWarnings("serial")
public class RobotGUI extends JFrame {
	//Constants
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
	
	//GUI Elements, Setup

	//Panels
	private JFrame mainFrame;
	private JPanel pnlRobot1;
	private JPanel pnlRobot2;

	//Labels
	private JLabel lblVersus;
	//Robot1 Panel
	private JLabel lblRobot1;
	private JLabel lblBulletEnergy1;
	private JLabel lblStepDistance1;
	private JLabel lblRightRotationAngle1;
	private JLabel lblLeftRotationAngle1;

	//Robot2 Panel
	private JLabel lblRobot2;
	private JLabel lblBulletEnergy2;
	private JLabel lblStepDistance2;
	private JLabel lblRightRotationAngle2;
	private JLabel lblLeftRotationAngle2;

	//Sliders
	private JSlider sldbulletEnergy1;
	private JSlider sldStepDistance1;
	private JSlider sldRightRotationAngle1;
	private JSlider sldLeftRotationAngle1;
	private JSlider sldBulletEnergy2;
	private JSlider sldStepDistance2;
	private JSlider sldRightRotationAngle2;
	private JSlider sldLeftRotationAngle2;

	//Text Fields
	private JTextField txtBulletEnergy1;
	private JTextField txtStepDistance1;
	private JTextField txtRightRotation1;
	private JTextField txtLeftRotation1;
	private JTextField txtBulletEnergy2;
	private JTextField txtStepDistance2;
	private JTextField txtRightRotation2;
	private JTextField txtLeftRotation2;

	//Save buttons
	private JButton saveButton1;
	private JButton saveButton2;

	//start battle button
	private JButton startBattle;

	public RobotGUI(){
		initComponents();
	}

	public static void main(String[] args) {
		RobotGUI robotGUI = new RobotGUI();
	}

	private void initComponents(){
		//Main Window
		int mFwidth = 1000;
		int mFheight = 600;

		mainFrame = new JFrame("Robot Editor GUI");
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setSize(mFwidth, mFheight);
		mainFrame.setLayout(null);
		
		loadProperties1();
		loadProperties2();

		//PANELS <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>

		//Robot 1 Panel bounds (x, y, width, height)
		int panel1x = 25;
		int panel1y = 50;
		int panel1Height = 400;
		int panel1Width = 400;

		//Robot 2 Panel bounds (x, y, width, height)
		int panel2x = panel1x + panel1Width + 120;
		int panel2y = panel1y;
		int panel2Height = panel1Width;
		int panel2Width = panel1Height;

		//Initialize

		//Robot 1 Panel
		pnlRobot1 = new JPanel();
		pnlRobot1.setLayout(null);
		pnlRobot1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		pnlRobot1.setBounds(panel1x, panel1y, panel1Width, panel1Height);

		//Robot 2 Panel
		pnlRobot2 = new JPanel();
		pnlRobot2.setLayout(null);
		pnlRobot2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		pnlRobot2.setBounds(panel2x, panel2y, panel2Height, panel2Width);

		//LABELS <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//Initialize
		lblVersus = new JLabel("v/s");	
		lblRobot1 = new JLabel("Robot #1", JLabel.RIGHT);
		lblRobot2 = new JLabel("Robot #2", JLabel.RIGHT);

		lblBulletEnergy1 = new JLabel("Bullet Energy", JLabel.LEFT);
		sldbulletEnergy1 = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		lblStepDistance1 = new JLabel("Step Distance", JLabel.LEFT);
		lblRightRotationAngle1 = new JLabel("Right Rotation", JLabel.LEFT);
		lblLeftRotationAngle1 = new JLabel("Left Rotation", JLabel.LEFT);

		lblBulletEnergy2 = new JLabel("Bullet Energy", JLabel.LEFT);
		lblStepDistance2 = new JLabel("Step Distance", JLabel.LEFT);
		lblRightRotationAngle2 = new JLabel("Right Rotation", JLabel.LEFT);
		lblLeftRotationAngle2 = new JLabel("Left Rotation", JLabel.LEFT);

		// Settings
		lblVersus.setBounds(mFwidth/2 -38, mFheight/2 -25, 150, 30);
		lblRobot1.setBounds(panel1x, panel1y -40, 80, 50);
		lblRobot2.setBounds(panel2x +5, panel2y -40, 80, 50);

		// Font and Size
		lblVersus.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

		//Label Placement
		lblBulletEnergy1.setBounds(10,10,100,40);
		lblStepDistance1.setBounds(10,50,100,40);
		lblRightRotationAngle1.setBounds(10,90,100,40);
		lblLeftRotationAngle1.setBounds(10,130,100,40);

		lblBulletEnergy2.setBounds(10,10,100,40);
		lblStepDistance2.setBounds(10,50,100,40);
		lblRightRotationAngle2.setBounds(10,90,100,40);
		lblLeftRotationAngle2.setBounds(10,130,100,40);

		//SLIDERS <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//Initialize
		sldbulletEnergy1 = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		sldStepDistance1 = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		sldRightRotationAngle1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldLeftRotationAngle1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldBulletEnergy2 = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		sldStepDistance2 = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		sldRightRotationAngle2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldLeftRotationAngle2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		
		//Settings
		sldbulletEnergy1.setMinorTickSpacing(1);
		sldbulletEnergy1.setMajorTickSpacing(4);
		sldbulletEnergy1.setPaintTicks(true);
		sldbulletEnergy1.setValue((int) bulletEnergy1);
		sldStepDistance1.setMinorTickSpacing(50);
		sldStepDistance1.setMajorTickSpacing(200);
		sldStepDistance1.setPaintTicks(true);
		sldStepDistance1.setValue(stepDistance1);
		sldRightRotationAngle1.setMinorTickSpacing(5);
		sldRightRotationAngle1.setMajorTickSpacing(20);
		sldRightRotationAngle1.setPaintTicks(true);
		sldRightRotationAngle1.setValue(rightRotationAngle1);
		sldLeftRotationAngle1.setMinorTickSpacing(5);
		sldLeftRotationAngle1.setMajorTickSpacing(20);
		sldLeftRotationAngle1.setPaintTicks(true);
		sldLeftRotationAngle1.setValue(leftRotationAngle1);

		sldBulletEnergy2.setMinorTickSpacing(1);
		sldBulletEnergy2.setMajorTickSpacing(4);
		sldBulletEnergy2.setPaintTicks(true);
		sldBulletEnergy2.setValue((int)rightRotationAngle2);
		sldStepDistance2.setMinorTickSpacing(50);
		sldStepDistance2.setMajorTickSpacing(200);
		sldStepDistance2.setPaintTicks(true);
		sldStepDistance2.setValue(stepDistance2);
		sldRightRotationAngle2.setMinorTickSpacing(5);
		sldRightRotationAngle2.setMajorTickSpacing(20);
		sldRightRotationAngle2.setPaintTicks(true);
		sldRightRotationAngle2.setValue((int)rightRotationAngle2);
		sldLeftRotationAngle2.setMinorTickSpacing(5);
		sldLeftRotationAngle2.setMajorTickSpacing(20);
		sldLeftRotationAngle2.setPaintTicks(true);
		sldLeftRotationAngle2.setValue(leftRotationAngle2);

		//Panel Placement
		sldbulletEnergy1.setBounds(110, 10, 200, 40);
		sldStepDistance1.setBounds(110, 50, 200, 40);
		sldRightRotationAngle1.setBounds(110, 90, 200, 40);
		sldLeftRotationAngle1.setBounds(110, 130, 200, 40);

		sldBulletEnergy2.setBounds(110, 10, 200, 40);
		sldStepDistance2.setBounds(110, 50, 200, 40);
		sldRightRotationAngle2.setBounds(110, 90, 200, 40);
		sldLeftRotationAngle2.setBounds(110, 130, 200, 40);
		
		//Event Listeners <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>
		sldbulletEnergy1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setBulletEnergy1(source.getValue());
				txtBulletEnergy1.setText(String.valueOf(source.getValue()));
			}
		});

		sldStepDistance1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setStepDistance1(source.getValue());
				txtStepDistance1.setText(String.valueOf(source.getValue()));
			}
		});

		sldRightRotationAngle1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setRightRotationAngle1(source.getValue());
				txtRightRotation1.setText(String.valueOf(source.getValue()));
			}
		});

		sldLeftRotationAngle1.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setLeftRotationAngle1(source.getValue());
				txtLeftRotation1.setText(String.valueOf(source.getValue()));
			}
		});

		sldBulletEnergy2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setBulletEnergy2(source.getValue());
				txtBulletEnergy2.setText(String.valueOf(source.getValue()));
			}
		});

		sldStepDistance2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setStepDistance2(source.getValue());
				txtStepDistance2.setText(String.valueOf(source.getValue()));
			}
		});

		sldRightRotationAngle2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setRightRotationAngle2(source.getValue());
				txtRightRotation2.setText(String.valueOf(source.getValue()));
			}
		});
		
		sldLeftRotationAngle2.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				setLeftRotationAngle2(source.getValue());
				txtLeftRotation2.setText(String.valueOf(source.getValue()));
			}
		});

		//TEXTFIELDS <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//Initialize
		txtBulletEnergy1 = new JTextField();
		txtStepDistance1 = new JTextField();
		txtRightRotation1 = new JTextField();
		txtLeftRotation1 = new JTextField();
		
		txtBulletEnergy2 = new JTextField();
		txtStepDistance2 = new JTextField();
		txtRightRotation2 = new JTextField();
		txtLeftRotation2 = new JTextField();

		//Placement
		txtBulletEnergy1.setBounds(320, 10, 50, 25);
		txtStepDistance1.setBounds(320, 50, 50, 25);
		txtRightRotation1.setBounds(320, 90, 50, 25);
		txtLeftRotation1.setBounds(320, 130, 50, 25);

		txtBulletEnergy2.setBounds(320, 10, 50, 25);
		txtStepDistance2.setBounds(320, 50, 50, 25);
		txtRightRotation2.setBounds(320, 90, 50, 25);
		txtLeftRotation2.setBounds(320, 130, 50, 25);
		
		//Set Initial Values
		txtBulletEnergy1.setText(String.valueOf(properties1.getProperty("bulletenergy")));;
		txtStepDistance1.setText(String.valueOf(properties1.getProperty("stepdistance")));
		txtRightRotation1.setText(String.valueOf(properties1.getProperty("rightrotationangle")));
		txtLeftRotation1.setText(String.valueOf(properties1.getProperty("leftrotationangle")));

		txtBulletEnergy2.setText(String.valueOf(properties2.getProperty("bulletenergy")));
		txtStepDistance2.setText(String.valueOf(properties2.getProperty("stepdistance")));
		txtRightRotation2.setText(properties2.getProperty("rightrotationangle"));
		txtLeftRotation2.setText(properties2.getProperty("leftrotationangle"));


		//Event Listeners
		//Robot 1
		txtBulletEnergy1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				setBulletEnergy1(source);
				int value = Integer.parseInt(source.getText());
				sldbulletEnergy1.setValue(value);
			}
		});
		txtStepDistance1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setStepDistanceTxt1(value);
				sldStepDistance1.setValue(value);
			}
		});
		txtRightRotation1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setRightRotationAngle1(value);
				sldRightRotationAngle1.setValue(value);
			}
		});
		txtLeftRotation1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setLeftRotationAngle1(value);
				sldLeftRotationAngle1.setValue(value);
			}
		});
		//Robot 2
		txtBulletEnergy2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setBulletEnergy2(value);
				sldBulletEnergy2.setValue(value);
			}
		});
		txtStepDistance2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setStepDistance2(value);
				sldStepDistance2.setValue(value);
			}
		});
		txtRightRotation2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setRightRotationAngle2(value);
				sldRightRotationAngle2.setValue(value);
			}
		});
		txtLeftRotation2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTextField source = (JTextField) e.getSource();
				int value = Integer.parseInt(source.getText());
				setLeftRotationAngle2(value);
				sldLeftRotationAngle2.setValue(value);
			}
		});

		//Buttons <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//save button 1
		saveButton1 = new JButton("Save");
		saveButton1.setBounds(panel1x, panel1y + panel1Height + 10, panel1Width, 30);

		//save button 2
		saveButton2 = new JButton("Save");
		saveButton2.setBounds(panel2x, panel2y + panel2Height + 10, panel2Width, 30);

		//start battle button
		startBattle = new JButton("Run Battle");
		startBattle.setBounds(mFwidth/2 - 65, mFheight-100, 100, 50);

		//button action listeners
		saveButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveActionPerformed(e);
				JOptionPane.showMessageDialog(mainFrame, "Properties Saved");

			}});
		saveButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveAction2Performed(e);
				JOptionPane.showMessageDialog(mainFrame, "Properties Saved");

			}});
		startBattle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runBattle();
			}});

		//ADD TO LAYOUT <<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// Adding Components
		mainFrame.add(pnlRobot1);
		mainFrame.add(pnlRobot2);
		mainFrame.add(lblVersus);
		mainFrame.add(lblRobot1);
		mainFrame.add(lblRobot2);
		mainFrame.add(startBattle);
		mainFrame.add(saveButton1);
		mainFrame.add(saveButton2);


		// Adding Robot 1 Components
		pnlRobot1.add(lblBulletEnergy1);
		pnlRobot1.add(lblStepDistance1);
		pnlRobot1.add(lblRightRotationAngle1);
		pnlRobot1.add(lblLeftRotationAngle1);		
		pnlRobot1.add(sldbulletEnergy1);
		pnlRobot1.add(sldStepDistance1);
		pnlRobot1.add(sldRightRotationAngle1);
		pnlRobot1.add(sldLeftRotationAngle1);
		pnlRobot1.add(txtBulletEnergy1);
		pnlRobot1.add(txtStepDistance1);
		pnlRobot1.add(txtRightRotation1);
		pnlRobot1.add(txtLeftRotation1);

		// Adding Robot 1 Components
		pnlRobot2.add(lblBulletEnergy2);
		pnlRobot2.add(lblStepDistance2);
		pnlRobot2.add(lblRightRotationAngle2);
		pnlRobot2.add(lblLeftRotationAngle2);		
		pnlRobot2.add(sldBulletEnergy2);
		pnlRobot2.add(sldStepDistance2);
		pnlRobot2.add(sldRightRotationAngle2);
		pnlRobot2.add(sldLeftRotationAngle2);
		pnlRobot2.add(txtBulletEnergy2);
		pnlRobot2.add(txtStepDistance2);
		pnlRobot2.add(txtRightRotation2);
		pnlRobot2.add(txtLeftRotation2);

		mainFrame.setVisible(true);


//Getters and Setters
	}
	protected void setBulletEnergy1(JTextField source) {
		// TODO Auto-generated method stub
		
	}

	protected void setStepDistanceTxt1(int value) {
		// TODO Auto-generated method stub
		
	}

	protected void setBulletEnergyTxt1(JTextField source) {
		// TODO Auto-generated method stub
		
	}

	protected void btnSaveAction2Performed(ActionEvent evt) {
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

	protected void btnSaveActionPerformed(ActionEvent evt) {
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


}

