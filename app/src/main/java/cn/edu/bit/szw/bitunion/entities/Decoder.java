package cn.edu.bit.szw.bitunion.entities;

import java.io.ObjectOutputStream;
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
			if (name.equalsIgnoreCase("this$0")
					|| Modifier.isFinal(field.getModifiers())) {
				continue;
			}
            try {
                Object value = field.get(obj);
                if (value != null) {

                    if (field.getType().equals(String.class)) {
                        if (value != null) {
                            field.set(obj, URLDecoder.decode((String)value));
                        }
                    } else {
                        field.set(obj, Decoder.decode(field.get(obj)));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
		}

		return obj;
	}
}
