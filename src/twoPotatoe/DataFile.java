package twoPotatoe;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;


public class DataFile {

	TPMainFrame mf = null;
	String folderPath = "/TPData/";
	PrintStream outStream = null;
	boolean accept = false;
	boolean isDataBlockInProgress = false;
	ArrayDeque<String> list = new ArrayDeque<String>();
	private int vCount = 0;
	
	DataFile(TPMainFrame mf) {
		this.mf = mf;
	}
	// Open a new file for writing the data
	public void open(String header) { 
		if (outStream != null) {
			Log.logTrace("Attempt to open existing open file.", true);
		}
		// Create a unique file name from the current date
		String suffix = "";
		File outputFile;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		
		while (true) {
			int count = 0;
			outputFile = new File(folderPath + sdf.format(date) + suffix + ".csv");
			if (!outputFile.exists()) {
				break;
			}
			suffix = "(" + ++count + ")";
		}
		try {
			outStream = new PrintStream(outputFile);
			outStream.println(header);
			accept = true;
		} catch (IOException e) {
			System.out.println("Error creating data file:" + e);
		}
		
		return;
	}
	
	// Close the file on exit
	public void close() { // Close the file on exit
		if (outStream != null) {
			outStream.close();
		}
		outStream = null;
		accept = false;
	}
	
	public void addElement(String str) {
		if (outStream != null) {
			list.addFirst(str);
		}		
	}
	
	// Write out one line of comma-separated value-strings from the ArrayList
	void writeLine() {
		if (outStream == null) {
			return;
		}
		
		// Don't collect if sequence not running or motor not in running state.
		switch (mf.mode) {
		case Common.MODE_TP_SEQUENCE:
			if (!mf.sequenceIsRunning) {
				list.clear();
				return;
			}
			break;
		case Common.MODE_TP4:
//			if (mf.runState != Common.STATE_RUNNING) {
//				list.clear();
//				return;
//			}
			break;
		case Common.MODE_PULSE_SEQUENCE:
			if ((mf.tpState & Common.TP_STATE_RUNNING) == 0) {
				list.clear();
				return;
			}
			break;
		default:
			break;
		} // end switch
		
		// Write out the line.
		java.util.Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			outStream.print(str);
			if (it.hasNext()) {
				outStream.print(",");
			}
		}
		outStream.println("");
		list.clear();
	}
	
	// Called with pulse data
	public void encData(int[] encVals, int size) {
		if (!accept) {
			return;
		}
		int lastEncVal =  0;
		double speed = 0.0;
		for (int i = 0; i < size; i++) {
			int encVal = encVals[i];
			if (i > 0) {
			    lastEncVal = encVals[i - 1];
				speed = 750.0 / ((double) (encVal - lastEncVal));
			}
			outStream.println(encVal + "," + speed);
		}
	}
	
	
	// Data block arrived from TP
	void doDataBlock(String csvLine) {
		if (!isDataBlockInProgress) {
			close();
			open("a, b, c, d, e, f, g");
			isDataBlockInProgress = true;
			vCount = 0;
		}
		outStream.println(csvLine);
		vCount++;
		if (csvLine.substring(0,7).startsWith("1234567")) {
            System.out.println("close");
			this.close();
			isDataBlockInProgress = false;
			System.out.println(vCount);
			return;
		}
		if ((vCount % 100) == 0) {
			System.out.println(vCount);
		}
	}
}
