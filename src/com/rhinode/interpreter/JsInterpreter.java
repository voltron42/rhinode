package com.rhinode.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

import com.rhinode.util.FileReader;

public class JsInterpreter {
	
	public static class JsInterpreterClosedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public JsInterpreterClosedException() {
			super("JsInterpreter is closed");
		}
	}

	private Context ctx;
	private ScriptableObject global;
	private boolean open;
	private String sourceName;
	private int lineno;

	public JsInterpreter() {}
	
	public void open(String sourceName) {
		this.sourceName = sourceName;
		this.ctx = Context.enter();
		this.global = this.ctx.initStandardObjects();
		this.open = true;
		this.lineno = 1;
	}
	
	public void close() {
		Context.exit();
		this.global = null;
		this.open = false;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	private void checkOpen() {
		if (!open) {
			throw new JsInterpreterClosedException();
		}
	}
	
	public Object interpret(String source) {
		checkOpen();
		Object result = ctx.evaluateString(this.global, new String(source), this.sourceName, lineno++, null);
		return result;
	}
	
	public String stringifyResult(Object result) {
		checkOpen();
		return Context.toString(result);
	}
	
	public String jsonifyResult(Object result) {
		checkOpen();
		return JsType.jsonify(result);
	}
	
	private static enum JsType {
		STRING(String.class) {
			@Override
			protected String stringify(Object obj) {
				return '"' + String.valueOf(obj) + '"';
			}
		},
		NUMBER(Number.class) {
			@Override
			protected String stringify(Object obj) {
				return String.valueOf(obj);
			}
		},
		BOOLEAN(Boolean.class) {
			@Override
			protected String stringify(Object obj) {
				return String.valueOf(obj);
			}
		},
		ARRAY(NativeArray.class) {
			@Override
			protected String stringify(Object obj) {
				NativeArray array = (NativeArray) obj;
				Object[] ids = array.getIds();
				String[] list = new String[ids.length];
				for (Object idObj : ids) {
					Integer id = (Integer) idObj;
					list[id.intValue()] = JsType.jsonify(array.get(id.intValue(), array));
				}
				return Arrays.asList(list).toString();
			}
		},
		OBJECT(NativeObject.class) {
			@Override
			protected String stringify(Object obj) {
				NativeObject natObj = (NativeObject) obj;
				StringBuffer sb = new StringBuffer();
				sb.append('{');
				Object[] ids = natObj.getIds();
				for (Object idObj : ids) {
					sb.append('"');
					String id = (String) idObj;
					sb.append(id);
					sb.append('"');
					sb.append(':');
					sb.append(JsType.jsonify(natObj.get(id, natObj)));
					sb.append(',');
				}
				if (ids.length > 0) {
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append('}');
				return sb.toString();
			}
		},
		FUNCTION(Function.class) {
			@Override
			protected String stringify(Object obj) {
				return "\"<<Function>>\"";
			}
		},
		;
		
		private final Class<?> cls;

		JsType(Class<?> cls) {
			this.cls = cls;
		}
		
		protected abstract String stringify(Object obj);
		
		public static String jsonify(Object obj) {
			for (JsType type : JsType.values()) {
				if (type.cls.isInstance(obj)) {
					return type.stringify(obj);
				}
			}
			return null;
		}
	}
	
	public void applyGlobalNative(Map<String,Object> globalNative) {
		checkOpen();
        // Add native functionality to scope
        for (Entry<String, Object> gnObj : globalNative.entrySet()) {
        	this.global.defineProperty(gnObj.getKey(), gnObj.getValue(), ScriptableObject.PERMANENT);
        }
	}

	public Object interpret(String fileName,Map<String,Object> globalNative) {
		checkOpen();
		applyGlobalNative(globalNative);
		String source = new FileReader(fileName).read();
		return interpret(source);
	}

}
