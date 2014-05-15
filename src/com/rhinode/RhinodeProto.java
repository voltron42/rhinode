package com.rhinode;

import com.rhinode.global.Native;
import com.rhinode.interpreter.JsInterpreter;

public class RhinodeProto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "res/hello.js";
		if (args.length > 0) {
			fileName = args[0];
		}
		
		JsInterpreter interp = new JsInterpreter();
		interp.open("<cmd>");
		
		String resultStr = interp.stringifyResult(interp.interpret(fileName, new Native().get()));
        
        // Convert the result to a string and print it.
        System.err.println(resultStr);
    }
}
