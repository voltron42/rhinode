package com.rhinode;

import java.util.Scanner;

import com.rhinode.global.Native;
import com.rhinode.interpreter.JsInterpreter;

public class ActiveConsole {

	public static void main(String[] args) {
		JsInterpreter interp = new JsInterpreter();
		interp.open(ActiveConsole.class.getSimpleName());
		interp.applyGlobalNative(new Native().get());
		
		Scanner console = new Scanner(System.in);
		while(console.hasNextLine()) {
			String source = console.nextLine();
			try {
				System.out.println(interp.interpret(source));
			} catch(Throwable t) {
				System.err.println(t.getMessage());
			}
		}
	}
}
