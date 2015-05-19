package com.zsxj.datareport2.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.gson.Gson;
import com.zsxj.datareport2.model.Warehouse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sen on 15-5-4.
 */
public class Utils {

	/**
	 * Util method for show include_progress bar.
	 *
	 * @param contentView  Content view
	 * @param progressView ProgressBar
	 * @param show         Show Progressbar or vice versa
	 */
	public static void showProgress(View contentView, View progressView, boolean show) {
		contentView.setVisibility(show ? View.GONE : View.VISIBLE);
		progressView.setVisibility(show ? View.VISIBLE : View.GONE);
		int shortAnimTiem = contentView.getResources().getInteger(android.R.integer.config_shortAnimTime);
		if (show) {
			progressView.setAlpha(0);
			progressView.animate().setDuration(shortAnimTiem).alpha(1);
		} else {
			contentView.setAlpha(0);
			contentView.animate().setDuration(shortAnimTiem).alpha(1);
		}
	}

	/**
	 * Converts a hexadecimal character to an integer.
	 *
	 * @param ch    A character to convert to an integer digit
	 * @param index The index of the character in the source
	 * @return An integer
	 * @throws DecoderException Thrown if ch is an illegal hex character
	 */
	protected static int toDigit(final char ch, final int index) throws DecoderException {
		final int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new DecoderException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	/**
	 * Restore the hexadecimal string representation of a byte array to original state.
	 *
	 * @param hex Hexadecimal string represent a byte array.
	 * @return A byte array represented by the hexadecimal string.
	 * @throws DecoderException if the byte array's length is not even.
	 */
	public static byte[] HexStringToBytes(String hex) throws DecoderException {
		char[] data = hex.toCharArray();

		final int len = data.length;

		if ((len & 0x01) != 0) {
			throw new DecoderException("Odd number of characters.");
		}

		final byte[] out = new byte[len >> 1];

		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	/**
	 * Get a message's md5 bytes array.
	 *
	 * @param message Message to be digest
	 * @return Bytes array digested from message
	 */
	public static byte[] md5Bytes(String message) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(message.getBytes("UTF-8"));
			return digest.digest();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Used to build output as Hex
	 */
	private static final char[] DIGITS_LOWER =
		{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * Represent a byte array as a hexadecimal string.
	 *
	 * @param data Byte array to be represented as a hexadecimal string.
	 * @return A hexadecimal string represent the byte array.
	 */
	public static String bytesToHexString(final byte[] data) {
		final char[] out = new char[data.length * 2];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < data.length; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[(0x0F & data[i])];
		}
		return new String(out);
	}

	public static void showMsgDialog(Context context, String msg) {
		new AlertDialog.Builder(context)
			.setMessage(msg)
			.setPositiveButton(android.R.string.ok, null)
			.show();
	}

	/**
	 * Read in's data to a string.
	 *
	 * @param in      InputStream to be read.
	 * @param charset In's encoded charset.
	 * @return A string read from in.
	 * @throws IOException if reading be interrupted.
	 */
	public static String toString(InputStream in, Charset charset) throws IOException {
		StringWriter sw = new StringWriter();
		if (charset == null) {
			charset = Charset.defaultCharset();
		}
		InputStreamReader reader = new InputStreamReader(in, charset);
		char[] buffer = new char[1024 * 4];
		int n;
		while ((n = reader.read(buffer)) != -1) {
			sw.write(buffer, 0, n);
		}
		return sw.toString();
	}

	public static String toString(InputStream in) throws IOException {
		return toString(in, null);
	}

	public static String toJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	public static List<String> collectCheckedWarehouseNos(List<Warehouse> warehouses) {
		List<String> nos = new ArrayList<>();
		for (Warehouse warehouse : warehouses) {
			if (warehouse.checked) {
				nos.add(warehouse.warehouseNo);
			}
		}
		return nos;
	}

	public static <T> List<T> toList(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, new ListOfJson<T>(clazz));
	}

	public static final String DATE_PATTERN = "YYYY-MM-dd";

	public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN);
}
