package com.rhinode.global;

import java.util.Arrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class Console {

	public static Object log(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
		System.out.println(args[0]);
		return null;
	}
}
