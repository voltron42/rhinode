package com.rhinode.global;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.ScriptableObject;

public class Native {

	private static final Class<?>[] globalClasses = {Require.class};
	private static final Class<?>[] encapsulatedClasses = {Console.class};
	
	public static void applyTo(ScriptableObject scope, Class<?>...classes) {
		for (Class<?> cls : classes) {
			apply(scope,cls);
		}
	}
	
	public static void applyEncapsulated(ScriptableObject scope, String encapsulationName, Class<?>...classes) {
		ScriptableObject obj = new ScriptableObject() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getClassName() {
				return "Object";
			}
		};
		applyTo(obj,classes);
		scope.defineProperty(encapsulationName, obj, ScriptableObject.PERMANENT);
	}
	
	public static void applyGlobal(ScriptableObject scope) {
		applyTo(scope, Require.class);
		applyEncapsulated(scope, "console", Console.class);
	}
	
	private static void apply(ScriptableObject scope, Class<?> cls) {
		for (Method method : cls.getDeclaredMethods()) {
			if (Modifier.isStatic(method.getModifiers())) {
				FunctionObject fn = new FunctionObject(method.getName(),method,scope);
				scope.defineProperty(method.getName(), fn, ScriptableObject.PERMANENT);
			}
		}
	}

}
