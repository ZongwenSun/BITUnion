package cn.edu.bit.bitunion.entities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;

public class Decoder {

	public static Object decode(Object obj) {
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String name = field.getName();
			if (name.equalsIgnoreCase("this$0") || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			String upperName = name.substring(0, 1).toUpperCase() + name.substring(1);
			try {
				Method getMethod = cls.getDeclaredMethod("get" + upperName);
				Method setMethod = cls.getDeclaredMethod("set" + upperName, field.getType());
				if (field.getType().equals(String.class)) {
					String value = (String) getMethod.invoke(obj);
					setMethod.invoke(obj, URLDecoder.decode(value));
				} else {
					setMethod.invoke(obj, decode(getMethod.invoke(obj)));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return obj;
	}
}
