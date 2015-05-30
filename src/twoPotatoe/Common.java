package twoPotatoe;

// This interface just holds the command constants.
interface Common {


     public static final int SEND_MESSAGE     = 129;
     public static final int SEND_FPS         = 130;
     public static final int SEND_PITCH       = 131;
     public static final int SEND_HEADING     = 132;
     public static final int SEND_SONAR       = 133;
     public static final int SEND_ROUTE_STEP  = 134;
     public static final int SEND_DUMP_DATA   = 135;
     public static final int SEND_STATE       = 136;
     public static final int SEND_BATT        = 137;
     public static final int SEND_MODE        = 138;

     public static final int RCV_JOYX         = 129;
     public static final int RCV_JOYY         = 130;
     public static final int RCV_RUN          = 131;
     public static final int RCV_LIGHTS       = 132;
     public static final int RCV_ROUTE        = 133;
     public static final int RCV_ROUTE_ES     = 134;
     public static final int RCV_DUMP_START   = 135;
     public static final int RCV_T            = 136;
     public static final int RCV_U            = 137;
     public static final int RCV_V            = 138;
     public static final int RCV_W            = 139;
     public static final int RCV_X            = 140;
     public static final int RCV_Y            = 141;
     public static final int RCV_Z            = 142;

     public static final int SEND_RCV_TERM    =   0;
	
   // Modes for TP
	public static final int MODE_XXXXXXXX =          0;  // Stepper motor behavior
	public static final int MODE_PWM_SPEED =         1;  // pw values sent from controller
	public static final int MODE_DRIVE =             2;  // Drive TP when it is down
	public static final int MODE_TP4 =               3;  // Interrupt-controlled motor speed
	public static final int MODE_TP_SPEED =          4;  // Motorspeed on TP - testing only
	public static final int MODE_TP_SEQUENCE =       5;  // sequences batch-loaded from controller
	public static final int MODE_PULSE_SEQUENCE =    6;
	public static final int MODE_PULSE =             7;
	public static final int MODE_TP5 =               8;
	public static final int MODE_TP6 =               9;
	public static final int MODE_TP7 =              10;
	public static final int MODE_UNKNOWN =          11;
	public static final int BLOCK_DATA =           100;
	
	// Motor Modes
	public static final int MM_DRIVE_BRAKE =         0;
	public static final int MM_DRIVE_COAST =         1;

	static final int MAX_RF_PACKET_SIZE = 100;
	static final long NO_DEBUG = 0x4242;  // If received, not displayed.

	// XBee data packet bytes. Constant indicates offset in packet byte array.
	
	// Message\ sent by TP - byte position
	public static final int TP_SEND_FLAG =             0;  // 1-byte, Flag and packet type
	public static final int TP_SEND_VALUE =            1;  // 2-byte, value
	public static final int TP_SEND_SONAR =            3;  // 2-byte, sonar distance
	public static final int TP_SEND_HEADING =          5;  // 2-byte, sonar distance
	public static final int TP_SEND_END =              7;  // offset after last value	
	
	// Flag byte in TP_SEND_XXX
	public static final int TP_SEND_FLAG_ANGLE =      0;  // 1-byte, Flag and packet type
	public static final int TP_SEND_FLAG_SPEED =      1;  
	public static final int TP_SEND_FLAG_MODE =       2; 
	public static final int TP_SEND_FLAG_STATE =      3;
	public static final int TP_SEND_FLAG_BATT =       4;
	public static final int TP_SEND_FLAG_VALSET =     7;
	public static final int TP_SEND_FLAG_DEBUG =      8;	
	public static final int TP_SEND_FLAG_DUMP =      25;
	

	// Message received by TP - byte position
	public static final int TP_RCV_MSG_TYPE =        0;  // 1-byte message type or packet type
	public static final int TP_RCV_MSG_VAL =         1;  // 2-byte message value
	public static final int TP_RCV_X =               3;  // 1-byte x joystick
	public static final int TP_RCV_Y =               4;  // 1-byte y joystick
	public static final int TP_RCV_MAX =             5;
	

	// Value sets
	public static final byte VAL_SET_A  =            0;
	public static final byte VAL_SET_B  =            1;
	public static final byte VAL_SET_C  =            2;

	//
	// Bits in the tpState byte set by TP
	public static final byte TP_STATE_RUNNING =      (byte) 0b0000_0001;  // Status: Motors are running.
	public static final byte TP_STATE_RUN_READY =    (byte) 0b0000_0010;  // on ground, not tipped, run state, PC or HC connected
	public static final byte TP_STATE_UPRIGHT =      (byte) 0b0000_0100;  // Status: tp is upright (not tipped over).
	public static final byte TP_STATE_ON_GROUND =    (byte) 0b0000_1000;  // Status: pressure sensor indicates standing.
	public static final byte TP_STATE_HC_ACTIVE =    (byte) 0b0001_0000;  // Status: Hand Controller connected
	public static final byte TP_STATE_PC_ACTIVE =    (byte) 0b0010_0000;  // Status: PC connected (not useful)
	public static final byte TP_STATE_ROUTE =        (byte) 0b0100_0000;  // Status: Route in progress.
	public static final byte TP_STATE_DUMPING =      (byte) 0b1000_0000;  // Data dumping in either direction
		
	// XBee MY addresses
	public static final int XBEE_TWO_POTATOE = 0x7770;
	public static final int XBEE_PC =          0x7771;
	public static final int XBEE_HC =          0x7772;

//	// ------------ Commands from tp to controller ------------
//	// Single byte 
//	public static final byte CMD_QUERY =                                    0;  // flag indicates isHcAlive
//	public static final byte CMD_SEQUENCE_END =                             1;
//	public static final byte CMD_UPLOAD_START =                             2;
//	public static final byte CMD_STREAM_STATUS =                            3;
////                                                                 // limit 16
	
	// Values that can go in the TP_RCV_MSG_TYPE byte
	public static final int TP_RCV_MSG_NULL =        0;  // no message
	public static final int TP_RCV_MSG_T_VAL =       1;  // 
	public static final int TP_RCV_MSG_U_VAL =       2;  // 
	public static final int TP_RCV_MSG_V_VAL =       3;  // 
	public static final int TP_RCV_MSG_W_VAL =       4;  // 
	public static final int TP_RCV_MSG_X_VAL =       5;  // 
	public static final int TP_RCV_MSG_Y_VAL =       6;  // 
	public static final int TP_RCV_MSG_Z_VAL =       7;  // 
	public static final int TP_RCV_MSG_BLUE =        8;  // 
	public static final int TP_RCV_MSG_HOME =        9;  // 
	public static final int TP_RCV_MSG_LIGHTS =     10;  // 1st 3 bits of val
	public static final int TP_RCV_MSG_M_MODE =     11;  // 
	public static final int TP_RCV_MSG_ROUTE_ES =   12;  // Route, end stand
	public static final int TP_RCV_MSG_ROTATE =     14;    
	public static final int TP_RCV_MSG_START_PW =   15;  // Run loaded pw sequence.
	public static final int TP_RCV_MSG_RESET =      16;  // Reset/Start/Stop something
	public static final int TP_RCV_MSG_MODE =       17;  // Reset/Start/Stop something
	public static final int TP_RCV_MSG_VALSET =     18;  // Reset/Start/Stop something
	public static final int TP_RCV_MSG_RUN =        19;  // Reset/Start/Stop something 
	public static final int TP_RCV_MSG_BLOCK =      20;  // Start block, don't transmit
	public static final int TP_RCV_MSG_ROUTE =      21;  // Route, Run/Halt
	public static final int TP_RCV_MSG_DSTART =     22;
	public static final int TP_RCV_MSG_START_TP =   23;

	// Block types.  Must be non-overlapping with TP_RCV_MSG_xxx
	public static final int TP_BLOCK_ZERO     =    100;  // Must be greater than this
	public static final int TP_BLOCK_ROUTE    =    101;
	public static final int TP_BLOCK_PULSE    =    102;
	public static final int TP_BLOCK_TP       =    103;

}
