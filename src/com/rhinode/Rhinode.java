package com.rhinode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Rhinode {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new File ("").getCanonicalPath());
		String fileName = "res/hello.js";
		if (args.length > 0) {
			fileName = args[0];
		}
		File scriptFile = new File(fileName);
		ByteArrayOutputStream scriptTextStream = new ByteArrayOutputStream();
		
		 // Creates and enters a Context. The Context stores information
        // about the execution environment of a script.
        Context cx = Context.enter();
        try {
        	IOUtils.copy(new FileInputStream(scriptFile), scriptTextStream);
            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed. Returns
            // a scope object that we use in later calls.
            Scriptable scope = cx.initStandardObjects();

            // Now evaluate the string we've colected.
            Object result = cx.evaluateString(scope, new String(scriptTextStream.toByteArray()), "<cmd>", 1, null);

            // Convert the result to a string and print it.
            System.err.println(Context.toString(result));

        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // Exit from the context.
            Context.exit();
        }
    }

}
