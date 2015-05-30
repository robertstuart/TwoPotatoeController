package twoPotatoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

/********************************
 * 
 * Pulses the motor on and off base on input from
 * and Exel file named: sequenceTP.xlsx
 *
 ********************************/
public class AlgorithmTpSequence {
	private static final int ENC_VALS_SIZE = 1000;

	String folderPath = "/TPData/";
	PrintStream inputStream = null;
	int[] encVals = new int[ENC_VALS_SIZE];
	int encValSize = 1000;
	private TPMainFrame mf = null;
	private BlueTooth blueTooth = null;
	private DataFile dataFile = null;
	boolean isActive = false; // only true while running

	AlgorithmTpSequence(TPMainFrame mf, BlueTooth blueTooth, DataFile dataFile) {
		this.mf = mf;
		this.dataFile = dataFile;
		this.blueTooth = blueTooth;
	}
	
	// Called for every mode change.
	public void init(boolean isActive) {
		this.isActive = isActive;
		mf.loadButton.setEnabled(isActive);
		mf.startButton.setEnabled(isActive);
		
//		if (isActive) {
//			mf.loadButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					mf.algorithmPulseSequence.sequenceLoad();
//				}
//			});
//
//		}
	}
	
	// Called when load button pressed.
	public void sequenceLoad() {
//		msgSend.sendCmd(Common.TP_RCV_MSG_BLOCK, 1);
//		// reading file line by line in Java using BufferedReader
//		FileInputStream fis = null;
//		BufferedReader reader = null;
//		byte sendArray[] = new byte[Common.MAX_RF_PACKET_SIZE + 1];
//
//		try {
//			Thread.sleep(100);
//			fis = new FileInputStream("C:/TPData/sequenceTP.csv");
//			// TODO Auto-generated catch block
//			reader = new BufferedReader(new InputStreamReader(fis));
//			reader.readLine(); // skip header line
//
//			while (true) {
//				String line = reader.readLine();
//				if (line == null) {
//					fis.close();
//					break;
//				}
//				int comma1 = line.indexOf(',') + 1;
//				if (comma1 <= 1) {
//					break;
//				}
//				int comma2Index = line.indexOf(',', comma1);
//				int comma2 = comma2Index + 1;
//				if ((comma2 - comma1) <= 1) {
//					break;
//				}
//				int len = line.length();
//				int comma3Index = line.indexOf(',', comma2);
//				if (comma3Index != -1) {
//					len = comma3Index;
//				}
//				int pulseLen = Integer.valueOf(line.substring(comma1, comma2 - 1));
//				int mState = Integer.valueOf(line.substring(comma2, len));
//				
//				System.out.println("Fps: " + pulseLen + "   motor: " + mState);
//			    sendArray[0] = Common.TP_BLOCK_PULSE;
////			    sendArray[1] = (byte) mState;
//				msgSend.set1Byte(sendArray, 1, mState);
//				msgSend.set2Byte(sendArray, 2, (pulseLen / 100));
//				msgSend.sendFrame(sendArray, 4);
//			}
//			fis.close();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		// Zero values to end sequence blocks;
//	    sendArray[0] = Common.TP_BLOCK_PULSE;
////	    sendArray[1] = (byte) mState;
//		msgSend.set1Byte(sendArray, 1, 0);
//		msgSend.set2Byte(sendArray, 2, 0);
//		msgSend.sendFrame(sendArray, 4);
//	}
//	
//	// Called when start command sent to TP
//	public void start() {
//		encValSize = 0;
//	}
//
//	// Read the encoder values sent from TP
//	// Asynchronous event!!!
//	public void addEncode(String str) {
//		int val = 0;
//		try {
//			val = Integer.valueOf(str);
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (encValSize < ENC_VALS_SIZE) {
//			encVals[encValSize++] = val;
//System.out.println("Enc: " + val);
//		}
//		if (val == -1) {  // End of sequence?
//			dataFile.encData(encVals, encValSize - 1);
//			if (mf.runPSeqButton.isSelected()) {
//				mf.runPSeqButton.doClick();
//			}
//
//		}
	}

}
	
