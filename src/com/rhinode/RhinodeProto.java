package com.rhinode;

import java.io.File;

import com.rhinode.global.Native;
import com.rhinode.interpreter.JsInterpreter;
import com.rhinode.util.FileReader;

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
		interp.open();
		interp.applyGlobalNative();
		FileReader fileReader = new FileReader(fileName);
		File file = fileReader.getFile();
		String absolutePath = file.getParentFile().getAbsolutePath();
		System.out.println("absolutePath: " + absolutePath);
		interp.applyGlobalNative("PATH", absolutePath);
		String src = fileReader.read();
		String resultStr = interp.stringifyResult(interp.interpret(src,absolutePath));
        
        // Convert the result to a string and print it.
        System.err.println(resultStr);
    }
}
