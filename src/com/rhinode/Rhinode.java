package com.rhinode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.ScriptableObject;

import com.rhinode.global.Console;
import com.rhinode.interpreter.JsInterpreter;

public class Rhinode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "res/hello.js";
		if (args.length > 0) {
			fileName = args[0];
		}
		
		String resultStr = JsInterpreter.interpret(fileName, getGlobalNative(Console.class));
        
        // Convert the result to a string and print it.
        System.err.println(resultStr);
    }

	private static Map<String, Object> getGlobalNative(Class<?>...classes) {
		Map<String, Object> out = new HashMap<String, Object>();
		for (Class<?> cls : classes) {
			ScriptableObject obj = new ScriptableObject() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getClassName() {
					return "Object";
				}
			};
			
			for (Method method : Console.class.getDeclaredMethods()) {
				if (Modifier.isStatic(method.getModifiers())) {
					FunctionObject fn = new FunctionObject(method.getName(),method,obj);
					obj.defineProperty(method.getName(), fn, ScriptableObject.PERMANENT);
				}
			}
			
			out.put(cls.getSimpleName().toLowerCase(), obj);
		}
		return out;
	}
}
