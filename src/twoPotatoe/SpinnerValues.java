package twoPotatoe;

import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;

public class SpinnerValues {
	public String label;
	public double value;
	public double min;
	public double max;
	public double inc;
	public String key;
	public ChangeListener cl;
	
	SpinnerValues(String label, double value, double min, double max, double inc, String key, ChangeListener cl) {
		this.label = label;
		this.value = value;
		this.min = min;
		this.max = max;
		this.inc = inc;
		this.key = key;
		this.cl = cl;
	}
}
