package twoPotatoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TPMainFrame implements ActionListener {
	
	int frameId = 0;
	int mode = Common.MODE_TP6;
	
	// Columns
	private static final int COL1_X = 20;
	private static final int COL1_X2 = 158;
	private static final int COL1_X3 = 190;
	private static final int COL2_X = COL1_X + 170;
	private static final int COL3_X = COL2_X + 110;
	private static final int COL4_X = COL3_X + 150;
	private static final int COL5_X = COL4_X + 50;
	private static final int COL6_X = COL5_X + 50;
	private static final int COL7_X = COL6_X + 25;
	private static final int COL8_X = COL7_X + 70;
	private static final int COL9_X = COL8_X + 70;
	private static final int COL1_LABEL_WIDTH = COL2_X - COL1_X - 4;
	private static final int COL1_TPLABEL_WIDTH = 130;
	private static final int COL1_TPV1LABEL_WIDTH = 50;
	private static final int COL1_TPV2LABEL_WIDTH = 40;
	private static final int COL2_SB_WIDTH = 65;
	private static final int COL3_TB_WIDTH = 140;
	private static final int COL3_RB_WIDTH = 150;
	private static final int COL4_VLABEL_WIDTH = 95;
	// ROWS
	private static final int TOP_MARGIN = 15;
	private static final int ROW_HEIGHT = 35;
	private static final int TB_HEIGHT = 25;

	private static final int NSEQUENCE = 10;
	public static final int N_MONITOR_LABELS = 10;
	public static final int N_SPINNERS = 7;

	// Values from tp
	public double tpPitch = 0.0;
	public double tpHeading = 0.0;
	public double tpSpeed = 0.0;
	public int tpMode = Common.MODE_UNKNOWN;
	public int tpState = 0;
	public double tpBatt = 0.0;
	public double tpSonarDistance = 0.0;
	public int tpRouteStep = 0;
	public int tpValSet = 0;
	public String tpRouteName = "";
	
	public boolean isStateReceived = false;
	
	private double oldTpPitch = 0.0;
	private double oldTpHeading = 0.0;
	private double oldTpSpeed = 0.0;
	private int oldTpMode = Common.MODE_UNKNOWN;
	private int oldTpState = 0;
	private double oldTpBatt = 0.0;
	private double oldTpSonarDistance = 0.0;
	private int oldTpRouteStep = 0;
	private int oldTpValSet = 0;
	private int oldTpDebugVal = 0;
	private String oldTpRouteName = "";

	public int Val = 4243;
	public int tpDebugVal = 4244;

	int modeStatus = Common.MODE_UNKNOWN; // Received from tp
	int valSet = Common.VAL_SET_A;
	int valSetStatus = Common.VAL_SET_A;  // status from TP
	static final String NO_CONNECTION = "No Connection";
	static final String CONNECTION = "Active";

	long tp4ValChangedTime = 0;
	boolean sequenceIsRunning = false;
	float joystickAngle = 0.0F;
	int rowY = TOP_MARGIN;
	long lastAliveMs = 0;
	boolean connectionActive = false;
	static TPMainFrame mf = null;
	float gamepadY = 0.0f;
	float sliderY = 0.0f;
	float yJoystickVal = 0.0F;
	float ySliderVal = 0.0F;
	float yVal = 0.0F;
	boolean controllerButtonStates[] = { false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false };

	SpinnerNumberModel secModels[] = new SpinnerNumberModel[NSEQUENCE];
	SpinnerNumberModel pwModels[] = new SpinnerNumberModel[NSEQUENCE];
	JInputJoystick gamePad = null;
	public DataFile dataFile = null;
	DecimalFormat df = new DecimalFormat("0.00");

//	XBee xBee = null;
	BlueTooth blueTooth = null;
	public int signalStrength = 0;
	JInputJoystick gameController = null;
	JLabel sonarLabel = null;
	JLabel sonarVLabel = null;
	JLabel speedLabel = null;
	JLabel speedVLabel = null;
	JLabel pitchLabel = null;
	JLabel pitchVLabel = null;
	JLabel headingLabel = null;
	JLabel headingVLabel = null;
	JLabel battLabel = null;
	JLabel battV1Label = null;
	JLabel battV2Label = null;
	JLabel tpStateLabel = null;
	JLabel tpStateV1Label = null;
	JLabel tpModeLabel = null;
	JLabel tpModeV1Label = null;
	JLabel debugLabel = null;
	JLabel debugVLabel = null;
	JLabel valsetLabel = null;
	JLabel valsetV1Label = null;
	JLabel routeNameValueLabel = null;
	
	
	JToggleButton ledTB = null;
	JToggleButton blueButton = null;
	JFrame mainFrame = null;
	JSpinner spinner = null;
	JToggleButton runTB = null;
	JButton resetButton = null;

	// Spinners for algorithms. Set by algorithm routines
	JLabel pwLabel = null;
	JLabel wsLabel = null;
	JLabel wsPwLabel = null;

	SpinnerModel oModel = null;
	SpinnerNumberModel taFactorModel = null;
	JLabel jLabel = null;
	JToggleButton jTB = null;
	JLabel eLabel = null;
	JToggleButton eTB = null;
	JLabel gamepadLabel = null;
	JLabel xGameLabel = null;
	JLabel yGameLabel = null;
	JToggleButton homeTB = null;
	JSpinner taFactorSpinner = null;
	JLabel aliveValueLabel = null;
	JLabel hcValueLabel = null;
	// right objects
	JButton dumpButton = null;
	JButton loadPSeqButton = null;
	JButton runPSeqButton = null;
	JButton runPSeqDumpButton = null;
	JButton loadTPSeqButton = null;
	JButton runTPSeqButton = null;
	JButton runTPSeqDumpButton = null;
	JButton prevRouteButton = null;
	JButton nextRouteButton = null;
	JSpinner pwSpinner = null;
	JSpinner wsSpinner = null;
	JSpinner wsPwSpinner = null;
	JLabel seqStatLabel = null;
	JTextArea textArea = null;
	JToggleButton startRouteTB = null;
	JButton loadRouteButton = null;
	JButton endStandButton = null;
	JFileChooser fc = null;
	long lastTick = 0;
	long total = 0;
	long tickRollover = 0;
	int count = 0;
	private JSlider ySlider;
	public JSlider testSlider;
	int timeSum = 0;
	MsgReceive msgReceive = null;

	// Algorithm classes
	AlgorithmPosition algorithmPosition = null;
	AlgorithmTpSpeed algorithmTpSpeed = null;
	AlgorithmTpSequence algorithmTpSequence = null;
	AlgorithmTp6 algorithmTp6 = null;
	AlgorithmPwmSequence algorithmPWSequence = null;
	AlgorithmPwmSpeed algorithmMotorPw = null;
	AlgorithmPulseSequence algorithmPulseSequence = null;

	// Mode buttons
	JRadioButton algorithmPosRB = null;
	JRadioButton algorithmTp6RB = null;
	JRadioButton algorithmTpSequenceRB = null;
	JRadioButton algorithmPwRB = null;
	JRadioButton algorithmTpSpeedRB = null;
	JRadioButton algorithmMTSequenceRB = null;
	JRadioButton algorithmPulseSequenceRB = null;
	JRadioButton valueSetARB = null;
	JRadioButton valueSetBRB = null;
	JRadioButton valueSetCRB = null;

	long msgTime = 0;
	double controllerX = 0.0;
	double controllerY = 0.0;
	double tSpinVal = 0.0;
	double uSpinVal = 0.0;
	double vSpinVal = 0.0;
	double wSpinVal = 0.0;
	double xSpinVal = 0.0;
	double ySpinVal = 0.0;
	double zSpinVal = 0.0;

	TPMainFrame() {

		mainFrame = new JFrame("TwoPotatoe Controller"); // Create main frame.
		mainFrame.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		blueTooth = new BlueTooth(this);//.open();
		msgReceive = new MsgReceive(this);
		blueTooth.initialize(msgReceive);
		dataFile = new DataFile(this);
//		xBee = new XBee();
//		xBee.initialize(msgReceive);
//		msgSend = new MsgSend(this, xBee);
		algorithmPosition = new AlgorithmPosition(this, blueTooth, dataFile);
		algorithmTpSpeed = new AlgorithmTpSpeed(this, blueTooth, dataFile);
		algorithmTpSequence = new AlgorithmTpSequence(this, blueTooth, dataFile);
		algorithmTp6 = new AlgorithmTp6(this, blueTooth, dataFile);
		algorithmPWSequence = new AlgorithmPwmSequence(this, blueTooth, dataFile);
		algorithmMotorPw = new AlgorithmPwmSpeed(this, blueTooth, dataFile);
		algorithmPulseSequence = new AlgorithmPulseSequence(this, blueTooth,
				dataFile);

		// Set static classes
		ScreenUpdate.mf = this;

		// Sets controller name.
		// window.setControllerName(joystick.getControllerName());

		/************************************* Left Row ***************************************/

		// Run button
		runTB = new JToggleButton("Run");
		Dimension runSize = runTB.getPreferredSize();
		runTB.setBounds(COL1_X, rowY, runSize.width, TB_HEIGHT);
		runTB.setMnemonic(KeyEvent.VK_SPACE);
		runTB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = runTB.isSelected() ? 1 : 0;
				blueTooth.send(Common.RCV_RUN, x);
			}
		});
		mainFrame.add(runTB);

		// Home button. Sets return to home state in tp
		homeTB = new JToggleButton("Home");
		Dimension homeSize = homeTB.getPreferredSize();
		homeTB.setBounds(COL1_X + runSize.width + 15, rowY, homeSize.width,
				TB_HEIGHT);
		homeTB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				cmdSend.send0(Common.CMD_HOME, homeTB.isSelected());
			}
		});
		mainFrame.add(homeTB);


		// Send a reset
		JButton resetButton = new JButton("Reset");
		Dimension resetSize = resetButton.getPreferredSize();
		resetButton.setBounds(COL1_X + homeSize.width + runSize.width + 30, rowY, resetSize.width, TB_HEIGHT);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mainFrame.add(resetButton);
		rowY += ROW_HEIGHT;

		// LED togglebutton
		ledTB = new JToggleButton("Lights");
		Dimension ledSize = ledTB.getPreferredSize();
		ledTB.setBounds(COL1_X, rowY, ledSize.width, TB_HEIGHT);
		ledTB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = ledTB.isSelected() ? 1 : 0;
				blueTooth.send(Common.RCV_LIGHTS, x);
			}
		});
		mainFrame.add(ledTB);

		// Bluetooth togglebutton
		blueButton = new JToggleButton("Blue passthrough");
		Dimension blueSize = blueButton.getPreferredSize();
		blueButton.setBounds(COL1_X + ledSize.width + 15, rowY, blueSize.width,
				TB_HEIGHT);
		blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (blueButton.isSelected()) {
//					msgSend.sendCmd(Common.TP_RCV_MSG_BLUE, 1);
//				} else {
//					msgSend.sendCmd(Common.TP_RCV_MSG_BLUE, 0);
//				}
			}
		});
		mainFrame.add(blueButton);
		rowY += ROW_HEIGHT;

		// rowY += ROW_HEIGHT;
		
		// Spin Buttons
		for (int i = 0; i < TPMainFrame.N_SPINNERS; i++) {
			JLabel label = new JLabel();
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBounds(COL1_X, rowY, COL1_LABEL_WIDTH, TB_HEIGHT);
			JSpinner spinner = new JSpinner();
			spinner.setBounds(COL2_X, rowY, COL2_SB_WIDTH, TB_HEIGHT);
			mainFrame.add(label);
			mainFrame.add(spinner);
			rowY += ROW_HEIGHT;
			ScreenUpdate.spinLabelArray[i] = label;
			ScreenUpdate.spinnerArray[i] = spinner;
		}
		// Game Pad name
		gamepadLabel = new JLabel("No Gamepad");
		gamepadLabel.setBounds(COL1_X, rowY, 120, TB_HEIGHT);
		xGameLabel = new JLabel("x.x");
		xGameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		xGameLabel.setBounds(COL1_X + 120, rowY, 30, TB_HEIGHT);
		yGameLabel = new JLabel("y.y");
		yGameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		yGameLabel.setBounds(COL1_X + 155, rowY, 30, TB_HEIGHT);
		mainFrame.add(gamepadLabel);
		mainFrame.add(xGameLabel);
		mainFrame.add(yGameLabel);
		rowY += ROW_HEIGHT;
		
		// Sonar
		sonarLabel = new JLabel("Sonar: ");
		sonarLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		sonarLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(sonarLabel);
		sonarVLabel = new JLabel();
		sonarVLabel.setBounds(COL1_X2, rowY, COL1_TPV1LABEL_WIDTH, TB_HEIGHT);
		sonarVLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(sonarVLabel);
		rowY += ROW_HEIGHT;
		
		// Speed pitch and heading
		speedLabel = new JLabel("Speed: ");
		speedLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(speedLabel);
		speedVLabel = new JLabel();
		speedVLabel.setBounds(COL1_X2, rowY, COL1_TPV1LABEL_WIDTH, TB_HEIGHT);
		speedVLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(speedVLabel);
		rowY += ROW_HEIGHT;
		pitchLabel = new JLabel("Pitch: ");
		pitchLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		pitchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(pitchLabel);
		pitchVLabel = new JLabel();
		pitchVLabel.setBounds(COL1_X2, rowY, COL1_TPV1LABEL_WIDTH, TB_HEIGHT);
		pitchVLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(pitchVLabel);
		rowY += ROW_HEIGHT;
		headingLabel = new JLabel("Heading: ");
		headingLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		headingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(headingLabel);
		headingVLabel = new JLabel();
		headingVLabel.setBounds(COL1_X2, rowY, COL1_TPV1LABEL_WIDTH, TB_HEIGHT);
		headingVLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(headingVLabel);
		rowY += ROW_HEIGHT;

		// Batteries
		battLabel = new JLabel("Battery: ");
		battLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		battLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(battLabel);
		battV1Label = new JLabel();
		battV1Label.setBounds(COL1_X2, rowY, COL1_TPV1LABEL_WIDTH, TB_HEIGHT);
		battV1Label.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(battV1Label);
		battV2Label = new JLabel();
		battV2Label.setBounds(COL1_X3+10, rowY, COL1_TPV2LABEL_WIDTH, TB_HEIGHT);
		battV2Label.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(battV2Label);
		rowY += ROW_HEIGHT;
		
		// TP State
		tpStateLabel = new JLabel("TP State:");
		tpStateLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		tpStateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(tpStateLabel);
		tpStateV1Label = new JLabel();
		tpStateV1Label.setBounds(COL1_X2, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		mainFrame.add(tpStateV1Label);
		rowY += ROW_HEIGHT;
		
		// Debug
		debugLabel = new JLabel("Debug:");
		debugLabel.setBounds(COL1_X, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		debugLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mainFrame.add(debugLabel);
		debugVLabel = new JLabel();
		debugVLabel.setBounds(COL1_X2, rowY, COL1_TPLABEL_WIDTH, TB_HEIGHT);
		mainFrame.add(debugVLabel);
		rowY += ROW_HEIGHT;

		// Alive
		JLabel aliveLabel = new JLabel("Connection:");
		Dimension aliveSize = aliveLabel.getPreferredSize();
		aliveLabel.setBounds(COL1_X, rowY, aliveSize.width, TB_HEIGHT);
		aliveValueLabel = new JLabel();
		aliveValueLabel.setBounds(COL1_X + aliveSize.width + 15, rowY,
				COL4_VLABEL_WIDTH + 44, TB_HEIGHT);
		aliveValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aliveValueLabel.setText(NO_CONNECTION);
		aliveValueLabel.setForeground(Color.RED);
		mainFrame.add(aliveLabel);
		mainFrame.add(aliveValueLabel);
		rowY += ROW_HEIGHT;


		/************************************* 2nd column ***************************************/
		rowY = TOP_MARGIN;

		// Algorithm radio buttons
		ButtonGroup algorithmBG = new ButtonGroup();
//		
//		algorithmPosRB = new JRadioButton("Position");
//		Dimension algorithmSize = algorithmPosRB.getPreferredSize();
//		algorithmPosRB.setBounds(COL3_X, rowY, COL3_RB_WIDTH,
//				algorithmSize.height);
//		algorithmPosRB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				setMode(Common.MODE_POSITION);
//			}
//		});
//		algorithmBG.add(algorithmPosRB);
//		mainFrame.add(algorithmPosRB);
//		rowY += algorithmSize.height;

		algorithmPwRB = new JRadioButton("PW Speed");
		Dimension algorithmSize = algorithmPwRB.getPreferredSize();
		algorithmPwRB.setBounds(COL3_X, rowY, COL3_RB_WIDTH,
				algorithmSize.height);
		algorithmPwRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(Common.MODE_PWM_SPEED);
			}
		});
		algorithmBG.add(algorithmPwRB);
		mainFrame.add(algorithmPwRB);
		rowY += algorithmSize.height;

		algorithmTpSpeedRB = new JRadioButton("TP Speed");
		algorithmTpSpeedRB.setBounds(COL3_X, rowY, COL3_RB_WIDTH,
				algorithmSize.height);
		algorithmTpSpeedRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(Common.MODE_TP_SPEED);
			}
		});
		algorithmBG.add(algorithmTpSpeedRB);
		mainFrame.add(algorithmTpSpeedRB);
		rowY += algorithmSize.height;

		algorithmTpSequenceRB = new JRadioButton("TP Seq");
		algorithmTpSequenceRB.setBounds(COL3_X, rowY, COL3_RB_WIDTH,
				algorithmSize.height);
		algorithmTpSequenceRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(Common.MODE_TP_SEQUENCE);
			}
		});
		algorithmBG.add(algorithmTpSequenceRB);
		mainFrame.add(algorithmTpSequenceRB);
		rowY += algorithmSize.height;

		algorithmTp6RB = new JRadioButton("TP6");
		algorithmTp6RB.setBounds(COL3_X, rowY, COL3_RB_WIDTH,
				algorithmSize.height);
		algorithmTp6RB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(Common.MODE_TP6);
			}
		});
		algorithmBG.add(algorithmTp6RB);
		mainFrame.add(algorithmTp6RB);
		rowY += algorithmSize.height;

		algorithmPulseSequenceRB = new JRadioButton("Pulse Seq");
		algorithmPulseSequenceRB.setBounds(COL3_X, rowY, COL3_RB_WIDTH,
		algorithmSize.height);
		algorithmPulseSequenceRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(Common.MODE_PULSE_SEQUENCE);
			}
		});
		algorithmBG.add(algorithmPulseSequenceRB);
		mainFrame.add(algorithmPulseSequenceRB);
		rowY += algorithmSize.height;

		// Misc buttons
		rowY += 30;
		
		// Dump data
		dumpButton = new JButton("Dump Data");
		Dimension dumpSize = dumpButton.getPreferredSize();
		dumpButton.setBounds(COL3_X - 30, rowY, dumpSize.width, TB_HEIGHT);
		dumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueTooth.send(Common.RCV_DUMP_START, 0);
			}
		});
		mainFrame.add(dumpButton);
		rowY += ROW_HEIGHT;
		rowY += ROW_HEIGHT;
		
		// Load pulse sequence button
		loadPSeqButton = new JButton("Load Pulse Seq");
		Dimension streamSize = loadPSeqButton.getPreferredSize();
		loadPSeqButton.setBounds(COL3_X - 30, rowY, streamSize.width, TB_HEIGHT);
		loadPSeqButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					algorithmPulseSequence.sequenceLoad();
				}
			});
		mainFrame.add(loadPSeqButton);
		rowY += ROW_HEIGHT;

		// Run pulse sequence button.
		runPSeqButton = new JButton("Run Pulse Seq");
		Dimension collectSize = runPSeqButton.getPreferredSize();
		runPSeqButton.setBounds(COL3_X - 30, rowY,
				collectSize.width, TB_HEIGHT);
		runPSeqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				msgSend.sendCmd(Common.TP_RCV_MSG_START_PW, 0);
			}
		});
		mainFrame.add(runPSeqButton);
		rowY += ROW_HEIGHT;

		// Run pulse sequence button & dump data.
		runPSeqDumpButton = new JButton("Run Pulse Seq & D");
		Dimension rdSize = runPSeqDumpButton.getPreferredSize();
		runPSeqDumpButton.setBounds(COL3_X - 30, rowY,
				rdSize.width, TB_HEIGHT);
		runPSeqDumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				msgSend.sendCmd(Common.TP_RCV_MSG_START_PW, 1);
			}
		});
		mainFrame.add(runPSeqDumpButton);
		rowY += ROW_HEIGHT;
		rowY += ROW_HEIGHT;
		
		// Load tp sequence button
		loadTPSeqButton = new JButton("Load TP Seq");
		Dimension seqSize = loadTPSeqButton.getPreferredSize();
		loadTPSeqButton.setBounds(COL3_X - 30, rowY, streamSize.width, TB_HEIGHT);
		loadTPSeqButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					algorithmTpSequence.sequenceLoad();
				}
			});
		mainFrame.add(loadTPSeqButton);
		rowY += ROW_HEIGHT;

		// Run TP sequence button.
		runTPSeqButton = new JButton("Run TP Seq");
		Dimension tpSize = runTPSeqButton.getPreferredSize();
		runTPSeqButton.setBounds(COL3_X - 30, rowY,
				tpSize.width, TB_HEIGHT);
		runTPSeqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				msgSend.sendCmd(Common.TP_RCV_MSG_START_TP, 0);
			}
		});
		mainFrame.add(runTPSeqButton);
		rowY += ROW_HEIGHT;

		// Run TP sequence button & dump data.
		runTPSeqDumpButton = new JButton("Run TP Seq & D");
		Dimension rtSize = runTPSeqDumpButton.getPreferredSize();
		runTPSeqDumpButton.setBounds(COL3_X - 30, rowY,
				rtSize.width, TB_HEIGHT);
		runTPSeqDumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				msgSend.sendCmd(Common.TP_RCV_MSG_START_TP, 1);
			}
		});
		mainFrame.add(runTPSeqDumpButton);
		rowY += ROW_HEIGHT;
		
		// Y Slider
		ySlider = new JSlider(JSlider.VERTICAL, 0, 200, 100);
		ySlider.setBounds(COL5_X, 25, 20, 200);
		ySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float val = ((float) (ySlider.getValue() - 100)) * +0.01F;
				setLeftY(false, val);
			}
		});
		ySlider.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				setLeftY(false, 0.0F);
				ySlider.setValue(100);
			}

			@Override
			public void mouseClicked(MouseEvent e) { /* TODO stub */
			}

			public void mouseEntered(MouseEvent arg0) { /* TODO stub */
			}

			public void mouseExited(MouseEvent arg0) { /* TODO stub */
			}

			public void mousePressed(MouseEvent arg0) { /* TODO stub */
			}
		});
		mainFrame.add(ySlider);
		testSlider = new JSlider(JSlider.VERTICAL, 0, 200, 100);
		testSlider.setBounds(COL5_X, 250, 20, 300);
		testSlider.getModel().setRangeProperties(0, 0, 0, 1000, false);
		mainFrame.add(testSlider);

		/************************************* 3nd Row ***************************************/

		//
		rowY = TOP_MARGIN;
		
		// Alive
		JLabel routeNameLabel = new JLabel("Route:");
		Dimension routeNameSize = routeNameLabel.getPreferredSize();
		routeNameLabel.setBounds(COL6_X, rowY, routeNameSize.width, TB_HEIGHT);
		routeNameValueLabel = new JLabel();
		routeNameValueLabel.setBounds(COL6_X + routeNameSize.width + 5, rowY,
				COL4_VLABEL_WIDTH + 44, TB_HEIGHT);
		routeNameValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		routeNameValueLabel.setText("");
		mainFrame.add(routeNameLabel);
		mainFrame.add(routeNameValueLabel);
		rowY += ROW_HEIGHT;

		
		prevRouteButton = new JButton("< Previous");
		Dimension prevRouteSize = prevRouteButton.getPreferredSize();
		prevRouteButton.setBounds(COL7_X, rowY, prevRouteSize.width, TB_HEIGHT);
		prevRouteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueTooth.send(Common.RCV_SET_ROUTE,0);
			}
		});
		mainFrame.add(prevRouteButton);

		nextRouteButton = new JButton("Next >");
		Dimension nextRouteSize = nextRouteButton.getPreferredSize();
		nextRouteButton.setBounds(COL7_X + nextRouteSize.width + 12, rowY,
				nextRouteSize.width, TB_HEIGHT);
		nextRouteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueTooth.send(Common.RCV_SET_ROUTE,1);
			}
		});
		mainFrame.add(nextRouteButton);
		rowY += ROW_HEIGHT;
		rowY += ROW_HEIGHT;

		final JToggleButton runRouteTB = new JToggleButton("Run Route");
		Dimension  runRouteSize =  runRouteTB.getPreferredSize();
		runRouteSize.setSize(140, runRouteSize.height);
		runRouteTB.setBounds(COL7_X, rowY,  runRouteSize.width, TB_HEIGHT);
		runRouteTB.setEnabled(true);
		runRouteTB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueTooth.send(Common.RCV_ROUTE, runRouteTB.isSelected() ? 1 : 0);
			}
		});
		mainFrame.add(runRouteTB);
		rowY += ROW_HEIGHT;
		
		endStandButton = new JButton("End Stand");
		endStandButton.setBounds(COL7_X, rowY, runRouteSize.width, TB_HEIGHT);
		endStandButton.setEnabled(true);
		endStandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueTooth.send(Common.RCV_ROUTE_ES, 0);
			}
		});
		mainFrame.add(endStandButton);
		rowY += ROW_HEIGHT;
		rowY += ROW_HEIGHT;

		loadRouteButton = new JButton("Load Route");
		loadRouteButton.setBounds(COL7_X, rowY, runRouteSize.width, TB_HEIGHT);
		loadRouteButton.setEnabled(true);
		
		fc = new JFileChooser("C:/TPRoute");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		loadRouteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		           int returnVal = fc.showOpenDialog(mainFrame);
		           
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                loadRoute(fc.getSelectedFile());
		                //This is where a real application would open the file.
			   } 
			}
		});
		mainFrame.add(loadRouteButton);
		rowY += ROW_HEIGHT;

		JButton deleteRouteButton = new JButton("Delete Route");
		deleteRouteButton.setBounds(COL7_X, rowY, runRouteSize.width, TB_HEIGHT);
		deleteRouteButton.setEnabled(true);
		deleteRouteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				algorithmTp5.loadRoute("Route4.csv");
			}
		});
		mainFrame.add(deleteRouteButton);
		rowY += ROW_HEIGHT;

//		JButton route5Button = new JButton("Load Route 5");
//		route5Button.setBounds(COL7_X, rowY, routeSize.width, TB_HEIGHT);
//		route5Button.setEnabled(true);
//		route5Button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
////				algorithmTp5.loadRoute("Route5.csv");
//			}
//		});
//		mainFrame.add(route5Button);
//		rowY += ROW_HEIGHT;
//		rowY += ROW_HEIGHT;
//		
//		startRouteTB = new JToggleButton("Route");
//		Dimension startRouteSize = route1Button.getPreferredSize();
//		startRouteTB.setBounds(COL7_X, rowY, startRouteSize.width, TB_HEIGHT);
//		startRouteTB.setEnabled(true);
//		startRouteTB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int x = startRouteTB.isSelected() ? 1 : 0;
//				blueTooth.send(Common.TP_RCV_MSG_ROUTE, x);
//			}
//		});
//		mainFrame.add(startRouteTB);
//		rowY += ROW_HEIGHT;
		
		rowY += ROW_HEIGHT;
		// Exit button
		rowY = 500;
		JButton exitButton = new JButton("Exit");
		Dimension exitSize = exitButton.getPreferredSize();
		exitButton.setBounds(COL8_X, rowY, exitSize.width, TB_HEIGHT);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueTooth.close();
				System.exit(0);
			}
		});
		mainFrame.add(exitButton);

		mainFrame.setSize(COL9_X + 50, rowY + 200);
//		valueSetARB.setSelected(true);
		setMode(mode);
//		if (modeStatus == Common.MODE_UNKNOWN) {
//			setMode(mode);
//		} else {
//			setMode(modeStatus);
//		}
		mainFrame.setVisible(true);
		startJoystickPoll();
	} // end constructor TPMainFrame()
	

	public void setBattery(JLabel vLabel, JLabel pLabel, double volt, double factor) {
		vLabel.setText(mf.df.format(volt) + "V");
		pLabel.setText(getPct(volt * factor) + "%");
		vLabel.repaint();
		pLabel.repaint();
	}

	private double[] volt = { 12.6f, 11.99f, 11.75f, 11.54f, 11.23f, 11.05f,
			10.83f, 10.63f, 10.35f, 9.0f };
	private double[] pct = { 100.0f, 83.0f, 71.0f, 60.0f, 34.0f, 20.0f, 07.0f,
			05.0f, 03.0f, 0.0f };

	// Convert battery voltage to percent
	int getPct(double batteryVolt) {
		if (volt[0] < batteryVolt) {
			return 100;
		}
		if (volt[volt.length - 1] > batteryVolt) {
			return 0;
		}
		for (int i = 1; i < volt.length; i++) {
			if (volt[i] < batteryVolt) {
				double rangeVolt = volt[i - 1] - volt[i];
				double rangePct = pct[i - 1] - pct[i];
				double voltPct = (batteryVolt - volt[i]) / rangeVolt;
				return (int) (pct[i] + (voltPct * rangePct));
			}
		}
		return -99;
	}

	public void setControllerName(String name) {
		gamepadLabel.setText(name);
	}

	public void setHatSwitch(float hat) {

	}

//	public void lastAlive() {
//		lastAliveMs = System.currentTimeMillis();
//	}

	// Called from tp message. Make thread safe.
//	public void aliveCheck() {
//		lastAliveMs = System.currentTimeMillis();
//	}

	// Timer calls actionPerformed
	public void startJoystickPoll() {
		PollGamepad.joystickInit(this);
		new Timer(50, this).start(); // 20 time/sec
	}

	// called by timer 20 times/sec
	// poll joystick & check status of tp connection
	public void actionPerformed(ActionEvent arg0) {
		
		PollGamepad.getGamepadValues(); // poll gamepad
		
		// Get the connection status.
		if ((System.currentTimeMillis() - msgTime) > 500) {
			if (connectionActive) {
				aliveValueLabel.setText(NO_CONNECTION);
				aliveValueLabel.setForeground(Color.RED);
				connectionActive = false;
			}
		} else {
			if (!connectionActive) {
				aliveValueLabel.setText(CONNECTION);
				aliveValueLabel.setForeground(Color.BLACK);
				connectionActive = true;
//				connectionActiveTime = System.currentTimeMillis();
			}
		}
		
		// Display tpState
		String stateStr = "0000000" + Integer.toBinaryString(tpState);
		int len = stateStr.length();
		stateStr = stateStr.substring(len - 8, len);
		String stateStr2 = stateStr.substring(0, 4);
		stateStr = stateStr.substring(4);
		
		String xs = df.format(controllerX);
		String ys = df.format(controllerY);
//		blueTooth.sendJoystickXY(xs, ys);
	}

	// Callback from getGamepadValues() which is polled in above method
	// Button definitions:
	//
	// 0
	public void buttonState(int button, boolean state) {
		// if (buttonStates[button] != state) {
		// System.out.println("Button " + button);
		// }
		switch (button) {
		// case 0: // Button 0, trigger on Attack joystick
		// if ((buttonStates[0] != state) && (state)) {
		// mf.runTB.doClick();
		// }
		// break;
		case 3: // Button 3, left button on top of joystick
//			if ((controllerButtonStates[3] != state) && (state)) {
//				mf.runTB.doClick(); // Click the Run button.
//			}
			break;

		default:
			break;
		}
		controllerButtonStates[button] = state;
	}

	// False if slider, True if joystick
	// TODO move this somewhere else
	public void setLeftY(boolean source, float y) {
		if (source) {
			gamepadY = y * 1.0F;
		} else {
			sliderY = y;
		}
		controllerY = gamepadY + sliderY;
		mf.yGameLabel.setText(mf.df.format(controllerY));
	}

	// Will be called with values between -1.0 and +1.0
	public void setRightX(float x) {
		if (controllerButtonStates[3] == true) {
			x = (float) 0.0;
		} else 
		controllerX = x;
		xGameLabel.setText(df.format(x));
	}

	void setMode(int newMode) {
		// Send the message to TP
		blueTooth.send(Common.RCV_MODE, newMode);
		// Deactivate the old mode
		switch (mode) {
		case Common.MODE_TP_SPEED:
			algorithmTpSpeed.init(false);
			break;
		case Common.MODE_TP_SEQUENCE:
			algorithmTpSequence.init(false);
			break;
		case Common.MODE_TP6:
			algorithmTp6.init(false);
			break;
		case Common.MODE_PWM_SPEED:
			algorithmMotorPw.init(false);
			break;
		case Common.MODE_PULSE_SEQUENCE:
			algorithmPulseSequence.init(false);
			break;
		}

		this.mode = newMode; // Causes Packet to change TP mode

		// Initialize the new mode
		switch (mode) {
		case Common.MODE_TP_SPEED:
			algorithmTpSpeed.init(true);
			break;
		case Common.MODE_TP_SEQUENCE:
			algorithmTpSequence.init(true);
			break;
		case Common.MODE_TP6:
			algorithmTp6RB.setSelected(true);
			algorithmTp6.init(true);
			break;
		case Common.MODE_PWM_SPEED:
			this.algorithmPwRB.setSelected(true);
			algorithmMotorPw.init(true);
			break;
		case Common.MODE_PULSE_SEQUENCE:
			this.algorithmPulseSequenceRB.setSelected(true);
			algorithmPulseSequence.init(true);
			break;
		}
	}

	void setVSet(int newSet) {
		// valSet = newSet;
		// cmdSend.sendInt(Common.CMD_VAL_SET, valSet);
		// double val = (double) ScreenUpdate.spinnerArray[0].getValue();
		// cmdSend.sendDouble(Common.CMD_T_SET, val);
		// val = (double) ScreenUpdate.spinnerArray[1].getValue();
		// cmdSend.sendDouble(Common.CMD_U_SET, val);
		// val = (double) ScreenUpdate.spinnerArray[2].getValue();
		// cmdSend.sendDouble(Common.CMD_V_SET, val);
		// val = (double) ScreenUpdate.spinnerArray[3].getValue();
		// cmdSend.sendDouble(Common.CMD_W_SET, val);
		// val = (double) ScreenUpdate.spinnerArray[4].getValue();
		// cmdSend.sendDouble(Common.CMD_X_SET, val);
		// val = (double) ScreenUpdate.spinnerArray[5].getValue();
		// cmdSend.sendDouble(Common.CMD_Y_SET, val);
		// val = (double) ScreenUpdate.spinnerArray[6].getValue();
		// cmdSend.sendDouble(Common.CMD_Z_SET, val);
	}

	
	// This sets all of the values sent from tp in the UI thread
	public void doTpSwingUpdate() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (oldTpPitch != tpPitch) {
					oldTpPitch = tpPitch;
		            pitchVLabel.setText(df.format(tpPitch));
				}
				if (oldTpHeading != tpHeading) {
					oldTpHeading = tpHeading;
		            headingVLabel.setText(df.format(tpHeading));
				}
				if (oldTpSpeed != tpSpeed) {
					oldTpSpeed = tpSpeed;
		            speedVLabel.setText(df.format(tpSpeed));
				}
				if (oldTpMode != tpMode) {
					
				}
				if (oldTpState != tpState) {
					oldTpState = tpState;
					tpStateV1Label.setText(String.valueOf(tpState));
				}
				if (oldTpBatt != tpBatt) {
					oldTpBatt = tpBatt;
		            setBattery(battV1Label, battV2Label, tpBatt, 0.75);
				}
				if (oldTpSonarDistance != tpSonarDistance) {
					oldTpSonarDistance = tpSonarDistance;
		               sonarVLabel.setText(df.format(tpSonarDistance));
				}
				if (oldTpRouteStep != tpRouteStep) {
					
				}
				if (oldTpValSet != tpValSet) {
					
				}
				if (oldTpDebugVal != tpDebugVal) {
					oldTpDebugVal = tpDebugVal;
		            debugVLabel.setText(Integer.toString(tpDebugVal));					
				}
				if (!oldTpRouteName.equals(tpRouteName)) {
					oldTpRouteName = tpRouteName;
					routeNameValueLabel.setText(tpRouteName); 					
				}
				
			} // End run()
		});

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY); // TODO does
																	// this do
																	// anything?
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mf = new TPMainFrame();
			}
		});
	} // End static main().

	private void loadRoute(File file) {
	    String line;
	    InputStream fis;
	    BufferedReader br;
		try {
			    fis = new FileInputStream(file);
			    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			    br = new BufferedReader(isr);
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		try {
			while ((line = br.readLine()) != null) {
				Thread.sleep(50);
				blueTooth.send(Common.RCV_ROUTE_DATA, line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	    try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/************************************************************
	 * isActive() Message received with 1/10 second?
	 ***********************************************************/
	public boolean isActive() {
		if ((msgTime + 100) > System.currentTimeMillis()) return true;
		else return false;
	}
	
	int get2Byte(byte[] array, int index) {
		int b1 = array[index];
		int b2 = array[index + 1];
		b1 = (b1 < 0) ? (b1 + 256) : b1;
		b2 = (b2 < 0) ? (b2 + 256) : b2;
		return (b1 << 8) + b2;
	}	
	int get4Byte(byte[] array, int index) {
		int b1 = array[index];
		int b2 = array[index + 1];
		int b3 = array[index + 2];
		int b4 = array[index + 3];
		b1 = (b1 < 0) ? (b1 + 256) : b1;
		b2 = (b2 < 0) ? (b2 + 256) : b2;
		b3 = (b3 < 0) ? (b3 + 256) : b3;
		b4 = (b4 < 0) ? (b4 + 256) : b4;
		return (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
	}
}
