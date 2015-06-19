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
     public static final int SEND_VALSET      = 139;
     public static final int SEND_ROUTE_NAME  = 140;

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
     public static final int RCV_SET_ROUTE    = 148;
     public static final int RCV_ROUTE_DATA   = 149;
     public static final int RCV_NEXT_ROUTE   = 150;
     public static final int RCV_DELETE_ROUTE = 151;
     public static final int RCV_MODE         = 152;


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

	static final long NO_DEBUG = 0x4242;  // If received, not displayed.

	
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
	
	public static final int TP_RCV_MSG_START_TP =   23;

	// Block types.  Must be non-overlapping with TP_RCV_MSG_xxx
	public static final int TP_BLOCK_ZERO     =    100;  // Must be greater than this
	public static final int TP_BLOCK_ROUTE    =    101;
	public static final int TP_BLOCK_PULSE    =    102;
	public static final int TP_BLOCK_TP       =    103;

}
