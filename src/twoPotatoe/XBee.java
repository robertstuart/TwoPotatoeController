//package twoPotatoe;
//
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import gnu.io.CommPortIdentifier;
//import gnu.io.NoSuchPortException;
//import gnu.io.SerialPort;
//import gnu.io.SerialPortEvent;
//import gnu.io.SerialPortEventListener;
//
//import java.util.Enumeration;
//
//public class XBee implements SerialPortEventListener {
//	private static final int PACKET_DELIM = 0;  // Waiting for byte after delimiter
//	private static final int PACKET_MSB = 1; // msb length
//	private static final int PACKET_LSB = 2; // msb length
//	private static final int PACKET_API_ID = 3;
//	private static final int PACKET_RX = 4; // Data receive in progress
//	private static final int PACKET_MS = 5;  // Modem status in progress
//	private static final int PACKET_TXS = 6;  // Transmit Status in progress
//	private int packetInProgress = PACKET_DELIM;
//	private boolean escState = false;
//	
//	private MsgReceive cmd = null;
//	byte packetByteArray[] = new byte[Common.MAX_RF_PACKET_SIZE + 50];
//	private int packetByteCount = 0;
//	private int packetLength = 0;
//	private int packetSource = 0;
//	private int packetSignal = 0;
//	private int dataLength = 0;
//	private int dataPtr = 0;
//	public int signalStrength = 0;
//
//	SerialPort serialPort;
//	private static final String portNames[] = {"COM7","COM10","COM16"}; // Windows ports	
//	private InputStream input; // Buffered input stream from the port
//	private OutputStream output; // The output stream to the port 	
//	private static final int timeOut = 2000;  // Milliseconds to block while waiting for port open
//	private static final int dataRate = 57600; // 115200
//
//
//	public void initialize(MsgReceive cmd) {
//		this.cmd = cmd;
//		CommPortIdentifier portId = null;
//		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
//
//		// iterate through, looking for the port
//		while (portEnum.hasMoreElements()) {
//			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
//					.nextElement();
//			portId = null;
//			System.out.println("PortName: " + currPortId.getName());
//			for (String portName : portNames) {
//				if (currPortId.getName().equals(portName)) {
//					portId = currPortId;
//					System.out.println("Port: " + portName);
//					break;
//				}
//			}
//			if (portId != null) {
//				break;
//			}
//		}
//
//		if (portId == null) {
//			System.out.println("Could not find COM port.");
//			return;
//		}
//
//		try {
//			// open serial port, and use class name for the appName.
//			serialPort = (SerialPort) portId.open(this.getClass().getName(),
//					timeOut);
//
//			// set port parameters
//			serialPort.setSerialPortParams(dataRate, SerialPort.DATABITS_8,
//					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//
//			// open the streams
//			input = serialPort.getInputStream();
//			output = serialPort.getOutputStream();
//
//			// add event listeners
//			serialPort.addEventListener(this);
//			serialPort.notifyOnDataAvailable(true);
//		} catch (Exception e) {
//			System.err.println(e.toString());
//		}
//	}
//
//	/**
//	 * This should be called when you stop using the port. This will prevent
//	 * port locking on platforms like Linux.
//	 */
//	public synchronized void close() {
//		if (serialPort != null) {
//			serialPort.removeEventListener();
//			serialPort.close();
//		}
//	}
//
//	/**
//	 * Handle an event on the serial port. Read the data and send it to
//	 * CmdReceive.
//	 */
//	public synchronized void serialEvent(SerialPortEvent oEvent) {
//		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//			try {
//				while (input.available() > 0) {
//					int b = input.read();
//				    // Fix escape sequences
//				    if (packetInProgress != PACKET_DELIM) {
//				      if (escState) {
//				        b = b ^ 0x20;
//				        escState = false;
//				      }
//				      else if (b == 0x7D) {
//				        escState = true;
//				        return;
//				      }
//				    }
//					switch (packetInProgress) {
//					case PACKET_DELIM:
//						if (b == 0x7E) {
//							packetByteCount = 0;
//							packetInProgress = PACKET_MSB;
//						} else {
//							System.err.println("Expected deliminter 7E, got:" + b);
//						}
//						break;
//					case PACKET_MSB: // 1st byte after 7e?
//						packetLength = b * 256;
//						packetInProgress = PACKET_LSB;
//						break;
//					case PACKET_LSB:
//						packetLength += b;
//						packetInProgress = PACKET_API_ID;
//						break;
//					case PACKET_API_ID:
//					    switch (b) {
//					    case 0x81:
//							packetInProgress = PACKET_RX; // isRxPacketInProgress;
//							break;
//					    case 0x89:
//							packetInProgress = PACKET_TXS;
//							break;
//					    case 0x8A:
//							packetInProgress = PACKET_MS;
//							break;
//						default:
//							System.err.println("Unknown packet type: " + b);
//							packetInProgress = PACKET_DELIM;
//					    }
//					    break;
//					case PACKET_RX:
//						packetInProgress = doRx(b);
//					    break;
//					case PACKET_TXS:
//						packetInProgress = doTxs(b);
//					    break;
//					case PACKET_MS:
//						packetInProgress = doMs(b);
//					    break;
//					default:
//					    System.err.println("Bad packet state.");
//					}
//				}
//			} catch (Exception e) {
//				System.err.println(e.toString());
//			}
//		}
//	} // end serialEvent()
//
//	private int doMs(int b) {
//		if (packetByteCount++ < 2) {
//			return PACKET_MS;
//		}
//		return PACKET_DELIM;
//	}
//
//	private int doTxs(int b) {
//		  switch (packetByteCount++) {
//		  case 0: // Frame id
//			  return PACKET_TXS;
//		  case 1: // Status
////			  TPMainFrame.tpAck = (b == 0) ? true : false;
//			  return PACKET_TXS;
//		  }
//		  return PACKET_DELIM;
//	}
//
//	// Data packet in progress
//	private int doRx(int b) {
//		switch (packetByteCount++) {
//		case 0:
//			packetSource = b * 256;
//			break;
//		case 1:
//			packetSource += b;
//			break;
//		case 2:
//			signalStrength = b;
//			break;
//		case 3: // Options
//			dataLength = packetLength - 5;  // Subtract out non-data bytes.
//			dataPtr = 0;
//			break;
//		// Data or checksum after this point.
//		default:
//			if (dataPtr == dataLength)	{ // Checksum?
//				cmd.newPacket(packetByteArray, dataLength);
//				return PACKET_DELIM;
//			} else if (dataPtr <= 100) {
//				packetByteArray[dataPtr++] = (byte) b;
//			}
//		}
//		return PACKET_RX;
//	}
//	
//
//	public void send(byte[] array, int len) {
//		if (output == null) {
//			return;
//		}
//		try {
//			output.write(array, 0, len);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void send(byte b) {
//		if (output == null) {
//			return;
//		}
//		try {
//			output.write(b);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
