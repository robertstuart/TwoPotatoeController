package twoPotatoe;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;

public class PollGamepad {
	public static TPMainFrame mf = null;
	private static JInputJoystick joystick = null;

	public static boolean joystickInit(TPMainFrame mf) {
		PollGamepad.mf = mf;

		// Creates controller
		joystick = new JInputJoystick(Controller.Type.STICK,
				Controller.Type.GAMEPAD);

		// Checks if the controller was found.
		if (!joystick.isControllerConnected()) {
			mf.setControllerName("No joystick found!");
			joystick = null;
			return false;
		}

		// Sets controller name.
		mf.setControllerName(joystick.getControllerName());
		return true;
	}

	public static void getGamepadValues() {
		if (joystick == null) {
			return;
		}
		// Gets current state of joystick! And checks, if joystick is
		// disconnected, break while loop.
		if (!joystick.pollController()) {
			mf.setControllerName("Controller disconnected!");
			return;
		}

		// Left pad joystick
		mf.setLeftY(true, -joystick.getYAxisValue()); // true indicates
														// joystick
		// Right pad joystick
		mf.setRightX(joystick.getXAxisValue());

		// Sets controller buttons
		ArrayList<Boolean> buttonsValues = joystick.getButtonsValues();
		for (int i = 0; i < 9; i++) {
			mf.buttonState(i, buttonsValues.get(i));
		}
		
//		System.out.println("getX_LeftJoystick_Value()" + joystick.getX_LeftJoystick_Value()); //X joystick
//		System.out.println("getXAxisValue()" + joystick.getXAxisValue()); // X joystick
//		System.out.println("getY_LeftJoystick_Value()" + joystick.getY_LeftJoystick_Value()); // Y joystick
//		System.out.println("getYAxisValue()" + joystick.getYAxisValue()); // Y value
//		System.out.println("getZAxisValue()" + joystick.getZAxisValue()); // trim
//		System.out.println("getX_RightJoystick_Value()" + joystick.getX_RightJoystick_Value()); // trim
//		System.out.println("" + joystick.getZRotationValue()); // error
//		System.out.println("" + joystick.getXRotationValue()); // error
//		System.out.println("" + joystick.getYRotationValue()); // error
//		System.out.println("" + joystick.getY_RightJoystick_Value()); // error
	}
}
