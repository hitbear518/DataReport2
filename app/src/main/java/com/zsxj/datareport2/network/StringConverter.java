package com.zsxj.datareport2.network;


import com.zsxj.datareport2.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public class StringConverter implements Converter {

	@Override
	public Object fromBody(TypedInput body, Type type) throws ConversionException {
		String text = null;
		try {
			text = Utils.toString(body.in());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			JSONObject jsonObject = new JSONObject(text);
			text = jsonObject.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
			text = "json ex";
		}
		return text;
	}

	@Override
	public TypedOutput toBody(Object object) {
		return null;
	}
}
