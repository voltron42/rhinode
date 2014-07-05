package com.rhinode.global;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.rhinode.interpreter.JsInterpreter;
import com.rhinode.module.ModuleRegistry;
import com.rhinode.util.FileReader;

public class Require {

	private static final String PATH = "PATH";
	private static final String EXPORTS_NAME = "exports";
	private static final String MODULE_NAME = "module";
	public static final String CONTEXT_NAME = "require";
	
	public static Object require(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
		System.out.println("require 'this' obj fields: " + Arrays.asList(thisObj.getIds()));
		System.out.println("require 'funObj' obj fields: " + Arrays.asList(funObj.getIds()));
		String moduleName = String.valueOf(args[0]);
		Object module = ModuleRegistry.getRegistry().get(moduleName);
		if (null != module) {
			return module;
		}
		return getModuleFromFile(cx, thisObj, moduleName);
	}

	private static Object getModuleFromFile(Context cx, Scriptable thisObj,
			String fileName) {
		JsInterpreter interp = JsInterpreter.getInstance();
		ScriptableObject module = new ScriptableObject() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getClassName() {
				return "Object";
			}
		};
		interp.applyGlobalNative(MODULE_NAME, module);
		String oldPath = String.valueOf(thisObj.get(PATH, thisObj));
		FileReader requireReader = new FileReader(oldPath + File.separatorChar + fileName);
		String source = requireReader.read();
		String absolutePath = requireReader.getFile().getParentFile().getAbsolutePath();
		System.out.println("require absolutePath: " + absolutePath);
		ScriptableObject.putProperty(thisObj, PATH, absolutePath);
		interp.interpret(source,absolutePath);
		ScriptableObject.putProperty(thisObj, PATH, oldPath);
		return module.get(EXPORTS_NAME, module);
	}
}
