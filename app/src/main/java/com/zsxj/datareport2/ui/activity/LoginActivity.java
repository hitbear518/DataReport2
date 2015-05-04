package com.zsxj.datareport2.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.OkHttpClient;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.TestActivity;
import com.zsxj.datareport2.event.HttpErrorEvent;
import com.zsxj.datareport2.model.HttpResult;
import com.zsxj.datareport2.model.IpResult;
import com.zsxj.datareport2.model.LicenseResult;
import com.zsxj.datareport2.model.LoginResult;
import com.zsxj.datareport2.network.GetIpInterface;
import com.zsxj.datareport2.network.HttpCallback;
import com.zsxj.datareport2.network.ServerInterface;
import com.zsxj.datareport2.network.ServerInterfaceSingleton;
import com.zsxj.datareport2.utils.DecoderException;
import com.zsxj.datareport2.utils.LoginPrefs_;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;

@EActivity(R.layout.activity_login)
public class LoginActivity extends HttpSubscriberActivity {

	@ViewById(R.id.toolbar)
	Toolbar mToolbar;
	@ViewById(R.id.login_form)
	ScrollView mLoginForm;
	@ViewById(R.id.sid_field)
	EditText mSidField;
	@ViewById(R.id.username_field)
	EditText mUsernameField;
	@ViewById(R.id.password_field)
	EditText mPasswordField;
	@ViewById(R.id.sign_in_button)
	Button mSignInButton;
	@ViewById(R.id.progress)
	ProgressBar mProgress;

	@Pref
	LoginPrefs_ mLoginPrefs;

	private String mHost;
	private ServerInterface mServerInterface;
	private byte[] mPkBytes;

	@AfterViews
	void init() {
		setSupportActionBar(mToolbar);
		restoreFromPrefs();
	}

	private void restoreFromPrefs() {
		String sid = mLoginPrefs.sid().get();
		String username = mLoginPrefs.username().get();
		if (!TextUtils.isEmpty(sid)) mSidField.setText(sid);
		if (!TextUtils.isEmpty(username)) mSidField.setText(sid);
	}

	@EditorAction(R.id.password_field)
	boolean passwordEditorAction(int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			attemptLogin();
			return true;
		} else {
			return false;
		}
	}

	@Click(R.id.sign_in_button)
	void attemptLogin() {
		// Check for filed empty.
		if (!checkFieldEmpty(mSidField) && !checkFieldEmpty(mUsernameField) && !checkFieldEmpty(mPasswordField)) {
			Utils.showProgress(mLoginForm, mProgress, true);
			getIp();
		}
	}

	private boolean checkFieldEmpty(final EditText field) {
		// Reset error.
		if (TextUtils.isEmpty(field.getText().toString())) {
			showFieldError(field, getString(R.string.error_field_required));
			return true;
		} else {
			return false;
		}
	}

	private void showFieldError(EditText field, String errMsg) {
		field.setError(null);
		field.setError(errMsg);
		field.requestFocus();
	}

	@Override
	public void onEvent(HttpResult httpResult) {
		super.onEvent(httpResult);
		switch (httpResult.getRequestType()) {
		case IP:
			ipAvailable((IpResult) httpResult);
			break;
		case LICENCE:
			licenseAvailable((LicenseResult) httpResult);
			break;
		case LOGIN:
			loginSuccess();
			break;
		}
	}

	@Override
	public void onEvent(HttpErrorEvent httpErrorEvent) {
		super.onEvent(httpErrorEvent);
		Utils.showProgress(mLoginForm, mProgress, false);
	}

	private void getIp() {
		OkHttpClient client = new OkHttpClient();
		client.networkInterceptors().add(new StethoInterceptor());
		client.setConnectTimeout(8, TimeUnit.SECONDS);
		client.setWriteTimeout(8, TimeUnit.SECONDS);
		client.setReadTimeout(8, TimeUnit.SECONDS);
		RestAdapter ipRestAdapter = new RestAdapter.Builder()
			.setEndpoint("http://erp.wangdian.cn")
			.setClient(new OkClient(client))
			.build();
		GetIpInterface getIpInterface = ipRestAdapter.create(GetIpInterface.class);

		String sid = mSidField.getText().toString();
		getIpInterface.getIp(sid, new HttpCallback<IpResult>() {
			@Override
			public void success(IpResult result, Response response) {
				if (result.code == 0) {
					EventBus.getDefault().postSticky(result);
				} else {
					Logger.i("Incorrect HttpResult: code=" + result.code + ", message=" + result.message);
					showFieldError(mSidField, result.message);
					Utils.showProgress(mLoginForm, mProgress, false);
				}
			}
		});
	}

	private void ipAvailable(IpResult result) {
		if (mHost == null || !result.ip.equals(mHost)) {
			mHost = result.ip;
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			settings.edit().putString(getString(R.string.pref_key_host), mHost).commit();
			ServerInterfaceSingleton.reset();
			mServerInterface = ServerInterfaceSingleton.get(getApplicationContext());
		}
		mServerInterface.getLicense(new HttpCallback<LicenseResult>());
	}

	public void licenseAvailable(LicenseResult result) {
		try {
			mPkBytes = Utils.HexStringToBytes(result.publicKey);
			login();
		} catch (DecoderException e) {
			Logger.e("Public key error: " + e.getMessage(), e);
			Toast.makeText(getApplicationContext(), R.string.error_get_license_failed, Toast.LENGTH_SHORT).show();
			Utils.showProgress(mLoginForm, mProgress, false);
		}
	}

	private void login() {
		String sid = mSidField.getText().toString();
		String username = mUsernameField.getText().toString();
		String password = mPasswordField.getText().toString();
		byte[] pwdMd5Bytes = Utils.md5Bytes(password);
		String pwdMd5Str = Utils.bytesToHexString(pwdMd5Bytes);
		Map<String, String> params = new HashMap<>();
		params.put("sid", sid);
		params.put("nick", username);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		params.put("timestamp", timestamp);

		// Calculate sign
		byte[] tail = Utils.md5Bytes("auth" + sid + username + pwdMd5Str + timestamp);
		byte[] xBytes = new byte[tail.length + pwdMd5Bytes.length];
		System.arraycopy(pwdMd5Bytes, 0, xBytes, 0, pwdMd5Bytes.length);
		System.arraycopy(tail, 0, xBytes, pwdMd5Bytes.length, tail.length);

		BigInteger x = new BigInteger(1, xBytes);
		Logger.d("x: " + x.toString());
		BigInteger e = new BigInteger("65537");
		BigInteger n = new BigInteger(1, mPkBytes);
		Logger.d("pk:" + n);

		BigInteger result = x.modPow(e, n);
		byte[] buff = result.toByteArray();
		if (buff[0] == 0) {
			byte[] tmp = new byte[buff.length - 1];
			System.arraycopy(buff, 1, tmp, 0, tmp.length);
			buff = tmp;
		}
		String sign = Utils.bytesToHexString(buff);
		params.put("sign", sign);

		mServerInterface.login(params, new HttpCallback<LoginResult>());
	}

	private void loginSuccess() {
		String sid = mSidField.getText().toString();
		String username = mUsernameField.getText().toString();

		// Check last login user
		String lastLogin = mLoginPrefs.lastLogin().get();
		if (!TextUtils.isEmpty(lastLogin) && !(sid + ":" + username).equals(lastLogin)) {
			PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
		}


		// Save user login data;
		mLoginPrefs.edit()
			.sid().put(sid)
			.username().put(username)
			.lastLogin().put(sid + ":" + username)
			.apply();

		gotoMain();
	}

	private void gotoMain() {
		Intent intent = new Intent(this, TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(this, TestActivity.class);
		startActivity(intent);
	}
}