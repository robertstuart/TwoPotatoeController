package twoPotatoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/* This is a class that takes care 
 * of collecting data from a sequence 
 */
public class AlgorithmPwmSequence {
	String folderPath = "/TPData/";
	PrintStream inputStream = null;

	private TPMainFrame mf = null;
	boolean isActive = false; // only true while running
	private int row = 0;
	private int countdown = 0;
	private static String pwStr = "xx";

	AlgorithmPwmSequence(TPMainFrame mf, BlueTooth blueTooth, DataFile dataFile) {
		this.mf = mf;
		
	}
	
	// Called for every mode change.
	public void init(boolean isActive) {
		this.isActive = isActive;
//		mf.loadButton.setEnabled(isActive);
//		mf.startButton.setEnabled(isActive);
		
		if (isActive) {
//			mf.loadButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					mf.algorithmPWSequence.sequenceLoad();
//				}
//			});

		}
	}
	
	// Called when load button pressed.
	public void sequenceLoad() {
//		mf.cmdSend.send0(Common.CMD_SEQ_LOAD, false);
//		// reading file line by line in Java using BufferedReader
//		FileInputStream fis = null;
//		BufferedReader reader = null;
//
//		try {
//			fis = new FileInputStream("C:/TPData/sequencePW.csv");
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
//				int pwVal = Integer.valueOf(line.substring(comma1, comma2 - 1));
//				int dur = Integer.valueOf(line.substring(comma2, len));
//				
//				// System.out.println("pw: " + pwVal + "   dur: " + dur);
////				mf.cmdSend.sendInt((byte) ',', pwVal);
//				Thread.sleep(1);
////				mf.cmdSend.sendInt((byte) ',', dur);
//				Thread.sleep(1);
//			}
////			mf.cmdSend.sendInt((byte) ',', 4242); // end marker
//			fis.close();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//	}
//
//	public void sequenceEnd() {
//		mf.seqStatLabel.setText("Inactive");
//		mf.sequenceIsRunning = false;
//		if (mf.collectButton.isSelected()) {
//			mf.collectButton.doClick();
//		}
	}

}
