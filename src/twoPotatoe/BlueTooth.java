package twoPotatoe;


import java.io.IOException;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortEventListener;

public class BlueTooth implements SerialPortEventListener {
	private MsgReceive msgReceive = null;
	private int inBuff[];
    static SerialPort serialPort = null;
    private byte msgStr[] = new byte[100];
    private int msgStrPtr = 0;
    private int msgCmd = 0;
    private boolean isMessageInProgress = true;
    TPMainFrame mf = null;

    BlueTooth(TPMainFrame jf) {
		this.mf = jf;
	}

    public void initialize(MsgReceive msgReceive) {
    	this.msgReceive = msgReceive;
    	serialPort = new SerialPort("COM25");
       	    try {
    	        serialPort.openPort();//Open serial port
    	        
    	        serialPort.setParams(SerialPort.BAUDRATE_115200, 
    	                             SerialPort.DATABITS_8,
    	                             SerialPort.STOPBITS_1,
    	                             SerialPort.PARITY_NONE);
    	        serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);

    	        System.out.println("BlueTooth opened: COM25");
       	    }
    	    catch (SerialPortException ex) {
    	    	serialPort = null;
    	    	System.out.println("BlueTooth open failed.");
    	        System.out.println(ex);
    	    }
    }
    
    public void close() {
    	try {
    		serialPort.closePort();
    	} catch (SerialPortException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}//Close serial port
    }
    
   public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR() && event.getEventValue() > 0) {
        	
            try {
            	inBuff = serialPort.readIntArray();
            	if (inBuff == null) return;
           }
            catch (SerialPortException ex) {
                System.out.println("Error in receiving string from Bluetooth: " + ex);
            }
        	for (int i = 0; i < inBuff.length; i++) {
        		int c = inBuff[i];
        		if (c >= 128) {
//System.out.println();
        		  msgStrPtr = 0;
        		  msgCmd = c;
        		  isMessageInProgress = true;
        		}
        		else {
        			if (isMessageInProgress) {
//System.out.print(Character.valueOf((char) c));
        				if (msgStrPtr >= msgStr.length) {
        					isMessageInProgress = false;
        				} else if (c == 0) {
        					msgReceive.doMsg(msgCmd, new String(msgStr, 0, msgStrPtr));
        					isMessageInProgress = false;
        				} else {
            				msgStr[msgStrPtr++] = (byte) c;
        				}
        			}
        		}
        	}
        }
   }
        

   public void sendJoystickXY(String x, String y) {
	   send(Common.RCV_JOYX, x);
	   send(Common.RCV_JOYY, y);
   }
	
	
	/*********************************************************************
	 * 
	 * send() ---- Send a String message
	 * 
	 ********************************************************************/
	public void send(int cmd, String msg) {
		if (!waitForSend()) {
			return;
		}
		byte buff[] = msg.getBytes();
			try {
				serialPort.writeByte((byte) cmd);
				if (buff.length > 0) {
					serialPort.writeBytes(buff);
				}
				serialPort.writeByte((byte) 0);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	/*********************************************************************
	 * 
	 * send() ---- Send an int message
	 * 
	 ********************************************************************/
	public void send(int cmd, int val) {
		if (!waitForSend()) {
			return;
		}
		String s = String.valueOf(val);
			try {
				serialPort.writeByte((byte) cmd);
				serialPort.writeString(s);
				serialPort.writeByte((byte) 0);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	/*********************************************************************
	 * 
	 * send() ---- Send a Double message
	 * 
	 ********************************************************************/
	public void send(int cmd, Double val) {
		if (!waitForSend()) {
			return;
		}
		String s = String.valueOf(val);
			try {
				serialPort.writeByte((byte) cmd);
				serialPort.writeString(s);
				serialPort.writeByte((byte) 0);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private boolean isPortActive() {
		if (serialPort == null) return false;
		int x = 0;
		try {
			x = serialPort.getOutputBufferBytesCount();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((x > 50) || (x < 0)) return false;
		return true;
	}
	
	// Wait for State message before sending.
	// Returns false if timed out or error
	private boolean waitForSend() {
		if (isPortActive() == false) {
			return false;
		}
		mf.isStateReceived = false;
		long startTime = System.currentTimeMillis();
		do {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (mf.isStateReceived == true) {
				return true;
			}
		} while ((startTime + 200) > System.currentTimeMillis());
		return false; // time out
		
	}
}
