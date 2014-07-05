package com.rhinode.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.rhinode.module.core.FileSystem;
import com.rhinode.util.KeyValuePair;

public class ModuleRegistry {

	private static final String CONFIGURATION_FILENAME = "res/registry.properties";
	
	private static ModuleRegistry registry = null;

	public static ModuleRegistry getRegistry() {
		if (null == registry) {
			registry = ModuleRegistry.load();
		}
		return registry;
	}

	private static ModuleRegistry load() {
		Map<String, Object> map = new HashMap<String, Object>(KeyValuePair.map(Arrays.asList(
			new KeyValuePair<String,Object>("fs",FileSystem.class)
		)));
		Properties registered = new Properties();
		try {
			registered.load(new FileInputStream(CONFIGURATION_FILENAME));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (Entry<Object, Object> entry : registered.entrySet()) {
			map.put(String.valueOf(entry.getKey()), new File(String.valueOf(entry.getValue())));
		}
		ModuleRegistry out = new ModuleRegistry();
		out.modules = map;
		return out;
	}
	
	private Map<String,Object> modules;

	public Object get(String moduleName) {
		Object module = modules.get(moduleName);
		if (null == module) {
			return null;
		}
		if (module instanceof Class) {
			return buildFrom((Class<?>)module);
		} else if (module instanceof File) {
			return buildFrom((File)module);
		} else {
			return module;
		}
	}

	private Object buildFrom(File module) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object buildFrom(Class<?> module) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
