package twoPotatoe;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import javax.swing.SwingUtilities;

public class MsgReceive {
	long lastTime = 0;
	TPMainFrame mf;
	int packetCount = 0;
	int packetVal; // value received.
	String msgBuffer = "";
	Formatter fmt = new Formatter();
	boolean runButtonState = true;
	double batteryPct = 42.0;
	double batteryVolt = 0.42;
	String gravityString = null;
	String xVal = null;
	String yVal = null;
	int testSliderVal = 0;
	int pingTime = 0;
	String pingHC = "";
	int motorState = 0;
	DecimalFormat df2 = new DecimalFormat("0.00");
	DecimalFormat df1 = new DecimalFormat("0.0");
	String valueArray[] = new String[TPMainFrame.N_MONITOR_LABELS];

	double tBatteryVolt = 0.0;
	int tValSet = 0;
	int tTpMode = 0;
	int tTpStatus = 0;
	long msgTime = 0;

	// flags & values of received dbg messages
	String tFactor = null;
	String uFactor = null;
	String vFactor = null;
	String wFactor = null;
	String xFactor = null;
	String yFactor = null;
	String zFactor = null;
	int[] valArray = new int[TPMainFrame.N_MONITOR_LABELS];

	// Class Constructor
	MsgReceive(TPMainFrame jf) {
		this.mf = jf;
	}

	/*********************************************************************
	 * 
	 * doMsg() New message from TwoPotatoe
	 * 
	 ********************************************************************/
	public void doMsg(int cmd, String val) {
		mf.msgTime = System.currentTimeMillis();
		switch (cmd) {
		case Common.SEND_MESSAGE:
			System.out.println(val);
			break;
		case Common.SEND_FPS:
			mf.tpSpeed = Double.valueOf(val);
			break;
		case Common.SEND_PITCH:
			mf.tpPitch = Double.valueOf(val);
			break;
		case Common.SEND_HEADING:
			mf.tpHeading =  Double.valueOf(val);
			break;
		case Common.SEND_SONAR:
			mf.tpSonarDistance = Double.valueOf(val);
			break;
		case Common.SEND_ROUTE_STEP:
			mf.tpRouteStep = Integer.valueOf(val);
			break;
		case Common.SEND_DUMP_DATA:
			mf.dataFile.doDataBlock(val);
			break;
		case Common.SEND_STATE:                 // always the last in a series
			mf.isStateReceived = true;
			mf.tpState = Integer.valueOf(val);
			mf.doTpSwingUpdate();               // only one guaranteed to be sent each time.
			break;
		case Common.SEND_BATT:
			mf.tpBatt = Double.valueOf(val)/100;
			break;
		case Common.SEND_MODE:
			mf.tpMode = Integer.valueOf(val);
			break;
		case Common.SEND_ROUTE_NAME:
			mf.tpRouteName = val;
			break;
		default:
//			System.out.println("Illegal message received: " + cmd);
			break;

		}
	}

	// /*********************************************************************
	// *
	// * newPacket() ----New packet received from Serial.
	// *
	// ********************************************************************/
	// public void newPacket(byte[] rcvArray, int lastOffset) {
	// mf.sonarDistance = ((double) get2Byte(rcvArray, Common.TP_SEND_SONAR)) /
	// 10.0;
	// mf.tpHeading = get2Byte(rcvArray, Common.TP_SEND_HEADING);
	// int val = get2Byte(rcvArray, Common.TP_SEND_VALUE);
	// switch (rcvArray[Common.TP_SEND_FLAG]) {
	// case Common.TP_SEND_FLAG_ANGLE:
	// mf.tpPitch= ((float) val) / 100.0;
	// break;
	// case Common.TP_SEND_FLAG_SPEED:
	// mf.tpSpeed = ((float) val) / 100.0;
	// mf.doTpSwingUpdate();
	// break;
	// case Common.TP_SEND_FLAG_MODE:
	// mf.tpMode = val;
	// break;
	// case Common.TP_SEND_FLAG_STATE:
	// mf.tpState = val;
	// break;
	// case Common.TP_SEND_FLAG_BATT:
	// mf.tpBatt = ((float) val) / 100.0;
	// break;
	// case Common.TP_SEND_FLAG_VALSET:
	// mf.tpValSet = val;
	// break;
	// case Common.TP_SEND_FLAG_DEBUG:
	// mf.tpDebugVal = val;
	// break;
	// case Common.TP_SEND_FLAG_DUMP:
	// mf.dataFile.doDataBlock(rcvArray);
	// break;
	// default:
	// System.out.println("Unknown flag byte received.");
	// }
	// mf.msgTime = System.currentTimeMillis();
	// // int debugVal = get2Byte(rcvArray, Common.TP_SEND_DEBUG);
	// // if (debugVal != Common.NO_DEBUG) {
	// // System.out.println("Debug: " + debugVal);
	// // }
	// // tBatteryVolt = (get2Byte(rcvArray, Common.TP_SEND_BATTERY) * 0.01);
	// // mf.valSetStatus = rcvArray[Common.TP_SEND_VALSET_STATUS];
	// // mf.tpState = rcvArray[Common.TP_SEND_TP_STATE_STATUS];
	//
	// // mf.msgSend.sendResponse(); // Reply to the packet
	// }

}
