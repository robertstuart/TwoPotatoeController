package twoPotatoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScreenUpdate {
	public static int nSpinners;
	public static final String NONE = "NONE";  // Indicate no prefs value for spin button
	public static TPMainFrame mf;
	public static String[][] monitorParamArray;
	public static JLabel[] monitorLabelArray = new JLabel[TPMainFrame.N_MONITOR_LABELS];
	public static JLabel[] monitorValueArray = new JLabel[TPMainFrame.N_MONITOR_LABELS];
	public static String[] convertArray = new String[TPMainFrame.N_MONITOR_LABELS];
	public static JLabel[] spinLabelArray = new JLabel[TPMainFrame.N_SPINNERS];
	public static JSpinner[] spinnerArray = new JSpinner[TPMainFrame.N_SPINNERS];
	public static SpinnerValues[] spinValues;
	
	
	public static String header() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < TPMainFrame.N_MONITOR_LABELS; i++) {
				sb.append(monitorLabelArray[i].getText() + ",");
			
		}
		return sb.toString();
	}
	
	public static void spinners(SpinnerValues[] values) {
		spinValues = values;
		nSpinners = 0;
		for (int i = 0; i < TPMainFrame.N_SPINNERS; i++) {
			
			// Remove change listeners.
			JSpinner spinner = spinnerArray[i];
			ChangeListener cl[] = spinner.getChangeListeners();
			for (int k = 0; k < cl.length; k++) {
				spinner.removeChangeListener(cl[k]);
			}
			
			if (i < values.length) {
				SpinnerValues v = values[i];
				spinLabelArray[i].setText(v.label);
				spinner.addChangeListener(v.cl);
				spinner.setEnabled(true);
				SpinnerNumberModel model = new SpinnerNumberModel(v.value, v.min, v.max, v.inc);
				spinner.setModel(model);
				double factor = 0.0;
//				if (!v.key.equals(ScreenUpdate.NONE)) {
//					factor = mf.prefs.getDouble(v.key, 0.0);
//				}
//				model.setValue(factor);
				nSpinners++;
			} else {
				spinLabelArray[i].setText("");	
				spinner.setEnabled(false);
			}
		}
	}
	
    /******************************************************************************
     * 
     * convert(String conString)
     * 
     *      Takes an integer value as input from a received message from TP and
     *      converts it to a displayable value.  The "convString" contains the
     *      formatting and conversion values.  Convstring has the format "xx,yy"
     *      where xx indicates the number of digits to be displayed to the right
     *      of the decimal point and "yy" is the factor to multiply the incoming
     *      value by.
     *      
     ******************************************************************************/

	public static String convert(int val, String conString) {
		return String.valueOf(val);
	}
	
}
