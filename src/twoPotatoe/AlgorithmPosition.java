package twoPotatoe;

import java.lang.Math;
import java.text.DecimalFormat;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 *     .
 */

public class AlgorithmPosition {
	BlueTooth bluetooth = null;;
	SpinnerValues spinnerValues[] = {
			new SpinnerValues("Forward:", 0.0, 0.0, 100.0, 1.0,"Tp4.t", new ChangeListener() {  // V
				public void stateChanged(ChangeEvent e) {
					double tSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					bluetooth.send(Common.RCV_T, ((int) tSpinVal));
				}
			}),
			new SpinnerValues("Backward:", 0.0, 0.0, 100.0, 1.0,  "Tp4.u", new ChangeListener() {  // W
				public void stateChanged(ChangeEvent e) {
					double uSpinVal = (double) ((JSpinner) e.getSource()).getValue();
					bluetooth.send(Common.TP_RCV_MSG_U_VAL, ((int) uSpinVal));
				}
			}),
		};
		
	DecimalFormat df = new DecimalFormat("0.00");

	public boolean isActive = false;
	TPMainFrame mf = null;


	public AlgorithmPosition(TPMainFrame mf, BlueTooth blueTooth, DataFile dataFile) {
		this.bluetooth = bluetooth;
		this.mf = mf;
	}

	public void init(boolean isActive) {
		this.isActive = isActive;	
		if (isActive) {
			ScreenUpdate.spinners(this.spinnerValues);
		}
	}
}


