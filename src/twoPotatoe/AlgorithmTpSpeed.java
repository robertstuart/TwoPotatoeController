package twoPotatoe;

import java.text.DecimalFormat;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AlgorithmTpSpeed {
	SpinnerValues spinnerValues[] = {
			new SpinnerValues("Right Wheel:", 0.0, -10.0, 10.0, 0.1, ScreenUpdate.NONE, new ChangeListener() { // T
				public void stateChanged(ChangeEvent e) {
					mf.tSpinVal = (double) ((JSpinner) e.getSource()).getValue();					
					blueTooth.send(Common.RCV_T, ((int) (mf.tSpinVal * 1000f)));
				}
			}),
			new SpinnerValues("Left Wheel", 0.0, -10.0, 10.0, 0.1, ScreenUpdate.NONE, new ChangeListener() { // U
				public void stateChanged(ChangeEvent e) {
					mf.uSpinVal = (double) ((JSpinner) e.getSource()).getValue();					
					blueTooth.send(Common.RCV_U, ((int) (mf.uSpinVal * 1000f)));
				}
			}),
			new SpinnerValues("Motor Mode", 0.0, 0.0, 1.0, 1.0, ScreenUpdate.NONE, new ChangeListener() { // U
				public void stateChanged(ChangeEvent e) {
					mf.uSpinVal = (double) ((JSpinner) e.getSource()).getValue();					
					blueTooth.send(Common.RCV_V, ((int) (mf.uSpinVal)));
				}
			})
		};
		
		DecimalFormat df = new DecimalFormat("0.00");
		public boolean isActive = false;
		private TPMainFrame mf = null;
		private DataFile dataFile = null;
		private BlueTooth blueTooth;

		
		
	public AlgorithmTpSpeed(TPMainFrame mf, BlueTooth blueTooth, DataFile dataFile) {
		this.mf = mf;
		this.blueTooth = blueTooth;
		this.dataFile = dataFile;
	}
	
	public void init(boolean isActive) {
		this.isActive = isActive;		
		this.isActive = isActive;	
		if (isActive) {
			ScreenUpdate.spinners(this.spinnerValues);
		}
	}

}
