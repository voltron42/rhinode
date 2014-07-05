package com.rhinode;

import java.util.Arrays;
import java.util.List;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import com.rhinode.interpreter.JsInterpreter;

public class JsonProto {

	public static void main(String[] args) {
		List<Pair<String,Class<?>>> data = Arrays.asList(
			new Pair<String,Class<?>>("{}",NativeObject.class),
			new Pair<String,Class<?>>("[]",NativeArray.class),
			new Pair<String,Class<?>>("[{x:true,y:function(a){return}},{a:3},{j:[5,4,3,2,'!']},'hello world']",NativeArray.class),
			new Pair<String,Class<?>>("''",String.class),
			new Pair<String,Class<?>>("0",Number.class),
			new Pair<String,Class<?>>("true",Boolean.class),
			new Pair<String,Class<?>>("function(){}",Function.class)
		);
		JsInterpreter interp = new JsInterpreter();
		interp.open();
		for (Pair<String,Class<?>> pair : data) {
			try {
				Object result = interp.interpret("(" + pair.t + ")",JsonProto.class.getSimpleName());
				System.out.println("toString: " + result);
				String ctxStr = interp.stringifyResult(result);
				System.out.println("toCtxStr: " + ctxStr);
				String json = interp.jsonifyResult(result);
				System.out.println("toJson: " + json);
				Class<?> resultClass = result.getClass();
				System.out.println(resultClass);
				Class<?> superClass = resultClass.getSuperclass();
				while (superClass != null) {
					System.out.println(superClass);
					superClass = superClass.getSuperclass();
				}
				System.out.println(pair.k.isInstance(result));
				if (result instanceof BaseFunction) {
					BaseFunction fn = (BaseFunction) result;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			System.out.println();
		}
	}

	private static class Pair<T,K> {
		T t;
		K k;
		
		Pair(T t, K k) {
			this.t = t;
			this.k = k;
		}
	}
}
