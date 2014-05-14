package com.rhinode.interpreter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class JsInterpreter {

	public static String interpret(String fileName,Map<String,Object> globalNative) {
		File scriptFile = new File(fileName);
		ByteArrayOutputStream scriptTextStream = new ByteArrayOutputStream();
		
		 // Creates and enters a Context. The Context stores information
	    // about the execution environment of a script.
	    Context cx = Context.enter();
	    String resultStr = null;
	    try {
	    	IOUtils.copy(new FileInputStream(scriptFile), scriptTextStream);
	        // Initialize the standard objects (Object, Function, etc.)
	        // This must be done before scripts can be executed. Returns
	        // a scope object that we use in later calls.
	        ScriptableObject scope = cx.initStandardObjects();
	        
	        // Add native functionality to scope
	        for (Entry<String, Object> global : globalNative.entrySet()) {
	        	scope.defineProperty(global.getKey(), global.getValue(), ScriptableObject.PERMANENT);
	        }
	        
	        // Now evaluate the string we've colected.
	        Object result = cx.evaluateString(scope, new String(scriptTextStream.toByteArray()), "<cmd>", 1, null);
	        
	        resultStr = Context.toString(result);
	        
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
		return resultStr;
	}

}
