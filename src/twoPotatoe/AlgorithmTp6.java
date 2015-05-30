
package twoPotatoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import javax.swing.JSpinner;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 *     .
 */

public class AlgorithmTp6 implements ActionListener {
	SpinnerValues spinnerValues[] = {
			new SpinnerValues("Pitch COS:", 3.6, 0.0, 20.0, 0.1,"Tp4.t", new ChangeListener() {  // V
				public void stateChanged(ChangeEvent e) {
					double tSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_T_VAL, ((int) (tSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("COS Lpf:", 0.10, 0.0, 1.0, 0.01,  "Tp4.u", new ChangeListener() {  // W
				public void stateChanged(ChangeEvent e) {
					double uSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_U_VAL, ((int) (uSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("Speed Error to TA:", 2.0, 0.0, 10.0, 0.1, "Tp4.v", new ChangeListener() { // X
				public void stateChanged(ChangeEvent e) {
					double vSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_V_VAL, ((int) (vSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("AE to Fps Correction:", 0.18, 0.0, 1.0, 0.01, "Tp4.w", new ChangeListener() { // Y
				public void stateChanged(ChangeEvent e) {
					double wSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_W_VAL, ((int) (wSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("Fps Correction Lpf:", 0.1, 0.0, 0.5, 0.01, "Tp4.x", new ChangeListener() { // offset
				public void stateChanged(ChangeEvent e) {
					double xSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_X_VAL, ((int) (xSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("Accel angle comp:", 45.0, 0.0, 100.0, 01.0, "Tp4.y", new ChangeListener() { // offset
				public void stateChanged(ChangeEvent e) {
					double ySpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_Y_VAL, ((int) (ySpinVal * 100.0)));
				}
			}),
			new SpinnerValues("Accel Offset:", -2.60, -10.0, 10.0, 0.05, "Tp4.z", new ChangeListener() { // offset
				public void stateChanged(ChangeEvent e) {
					double zSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.TP_RCV_MSG_Z_VAL, ((int) (zSpinVal * 1000.0)));
				}
			}),
		};
		
	DecimalFormat df = new DecimalFormat("0.00");
	String fileName = "";

	public boolean isActive = false;
	TPMainFrame mf = null;
	BlueTooth blueTooth = null;


	public AlgorithmTp6(TPMainFrame mf, BlueTooth blueTooth, DataFile dataFile) {
		this.mf = mf;
		this.blueTooth = blueTooth;
	}

	public void init(boolean isActive) {
		this.isActive = isActive;	
		if (isActive) {
			ScreenUpdate.spinners(this.spinnerValues);
		}
	}
	
	public void loadRoute(String fileName) {
		this.fileName = fileName;
//		mf.msgSend.sendCmd(Common.TP_RCV_MSG_BLOCK, 1);
		Timer timer = new Timer(200, this); 
		timer.setInitialDelay(200);
		timer.setRepeats(false);
		timer.start(); 
	}
	
	// Called by timer to upload a new route.
	public void actionPerformed(ActionEvent arg0) {
//
//		// reading file line by line in Java using BufferedReader
//		FileInputStream fis = null;
//		BufferedReader reader = null;
//		
//		try {
//			fis = new FileInputStream("C:/TPRoute/" + fileName);
//			reader = new BufferedReader(new InputStreamReader(fis));
//			reader.readLine(); // skip header line
//			int lineCount = 0;
//
//			while (true) {
//				lineCount++;
//				String line = reader.readLine();
//				if (line == null) {
//					fis.close();
//					break;
//				}
//				int comma0 = line.indexOf(',') + 1;
//				int comma1 = line.indexOf(',', comma0) + 1;
//				int comma2 = line.indexOf(',', comma1) + 1;
//				int comma3 = line.indexOf(',', comma2) + 1;
//				byte rfBytes[] = new byte[6];
//				rfBytes[0] = Common.TP_BLOCK_ROUTE;
//				String cmdString = line.substring(0, comma1 - 1).trim().toUpperCase();
//				Double aVal = 0.0;
//				Double bVal = 0.0;
//
//				try {
//					aVal = Double.valueOf(line.substring(comma1, comma2 - 1));
//					bVal = Double.valueOf(line.substring(comma2, comma3 - 1));
//				} catch (Exception e2) {}
//				char c = cmdString.charAt(comma0);
//				rfBytes[1] = (byte) c;
//				switch (c) {
//				case 'R':
//					mf.msgSend.set2Byte(rfBytes, 2, (int) (aVal * 10));					
//					System.out.print("Line: " + lineCount + " ");
//					System.out.println(c + "   Seconds: " + aVal);
//					break;
//				case 'M':
//					if ((aVal < 0.0) || (aVal > 359.99)) {
//						aVal = 0.0;
//					}
//					mf.msgSend.set2Byte(rfBytes, 2, (int) (aVal * 10));
//					System.out.print("Line: " + lineCount + " ");
//					System.out.println(c + "   Degrees: " + aVal);
//					break;
//				case 'S':  // Speed
//					mf.msgSend.set2Byte(rfBytes, 2, (int) (aVal * 100)); // speed
//					mf.msgSend.set2Byte(rfBytes, 4, (int) (bVal / 10.0)); // ticks
//					System.out.print("Line: " + lineCount + " ");
//					System.out.println(c + "   Speed: " + aVal + "    Distance:" + bVal);
//					break;
//				case 'T':  // Turn
//					mf.msgSend.set2Byte(rfBytes, 2, (int) (aVal * 10.0)); // heading
//					mf.msgSend.set2Byte(rfBytes, 4, (int) (bVal * 10.0)); // radius
//					System.out.print("Line: " + lineCount + " ");
//					System.out.println(c + "   Heading: " + aVal + "    Radius:" + bVal);
//					break;
//				case 'Z':  // Zero speed
//					mf.msgSend.set2Byte(rfBytes, 2, (int) (aVal * 100)); // time, centiseconds
//					System.out.print("Line: " + lineCount + " ");
//					System.out.println(c + "   Time: " + aVal);
//					break;
//				case 'C':
//					break;
//				case 'B':
//					break;
//				case 'E':
//					break;
//					default:
//						System.out.println("Unknown command \"" + c + "\" in route.");
//				}
//				mf.msgSend.sendFrame(rfBytes, 6);
//				
//				if (c == 'E') break;
//			}
//			fis.close();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		mf.msgSend.sendCmd(Common.TP_RCV_MSG_BLOCK, 0);
//
	}

}


