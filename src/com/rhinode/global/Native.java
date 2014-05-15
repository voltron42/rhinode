package com.rhinode.global;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.ScriptableObject;

public class Native {

	private static final Class<?>[] classes = {Console.class};

	public Native() {}
	
	public Map<String, Object> get() {
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
