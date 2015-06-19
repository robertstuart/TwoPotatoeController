
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
					blueTooth.send(Common.RCV_T, ((int) (tSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("COS Lpf:", 0.10, 0.0, 1.0, 0.01,  "Tp4.u", new ChangeListener() {  // W
				public void stateChanged(ChangeEvent e) {
					double uSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.RCV_U, ((int) (uSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("Speed Error to TA:", 2.0, 0.0, 10.0, 0.1, "Tp4.v", new ChangeListener() { // X
				public void stateChanged(ChangeEvent e) {
					double vSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.RCV_V, ((int) (vSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("AE to Fps Correction:", 0.18, 0.0, 1.0, 0.01, "Tp4.w", new ChangeListener() { // Y
				public void stateChanged(ChangeEvent e) {
					double wSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.RCV_W, ((int) (wSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("Fps Correction Lpf:", 0.1, 0.0, 0.5, 0.01, "Tp4.x", new ChangeListener() { // offset
				public void stateChanged(ChangeEvent e) {
					double xSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.RCV_X, ((int) (xSpinVal * 1000.0)));
				}
			}),
			new SpinnerValues("Gyro Yaw Offset:", 130.0, -1000.0, 1000.0, 010.0, "Tp4.y", new ChangeListener() { // offset
				public void stateChanged(ChangeEvent e) {
					double ySpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.RCV_Y, ySpinVal);
				}
			}),
			new SpinnerValues("Accel Offset:", -2.60, -10.0, 10.0, 0.05, "Tp4.z", new ChangeListener() { // offset
				public void stateChanged(ChangeEvent e) {
					double zSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					blueTooth.send(Common.RCV_Z, ((int) zSpinVal));
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
	}

}


