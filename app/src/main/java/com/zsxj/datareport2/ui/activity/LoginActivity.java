package com.zsxj.datareport2.ui.activity;

import android.content.Intent;
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
import com.zsxj.datareport2.model.IpResult;
import com.zsxj.datareport2.model.LicenseResult;
import com.zsxj.datareport2.model.LoginResult;
import com.zsxj.datareport2.model.WarehouseResult;
import com.zsxj.datareport2.network.GetIpInterface;
import com.zsxj.datareport2.network.HttpCallback;
import com.zsxj.datareport2.network.RequestHelper;
import com.zsxj.datareport2.utils.DecoderException;
import com.zsxj.datareport2.utils.DefaultPrefs_;
import com.zsxj.datareport2.utils.LoginPrefs_;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.math.BigInteger;
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
	@Pref
	DefaultPrefs_ mDefaultPrefs;

	@Bean
	RequestHelper mRequestHelper;

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
		if (!TextUtils.isEmpty(username)) mUsernameField.setText(username);
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
	public void onEventMainThread(HttpErrorEvent httpErrorEvent) {
		super.onEventMainThread(httpErrorEvent);
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

	public void onEventMainThread(IpResult result) {
		mRequestHelper.init(result.ip);
		mRequestHelper.queryLicense();
	}

	public void onEventMainThread(LicenseResult result) {
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
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);


		// Calculate sign
		String password = mPasswordField.getText().toString();
		byte[] pwdMd5Bytes = Utils.md5Bytes(password);
		String pwdMd5Str = Utils.bytesToHexString(pwdMd5Bytes);

		byte[] tail = Utils.md5Bytes("auth" + sid + username + pwdMd5Str + timestamp);
		byte[] xBytes = new byte[tail.length + pwdMd5Bytes.length];
		System.arraycopy(pwdMd5Bytes, 0, xBytes, 0, pwdMd5Bytes.length);
		System.arraycopy(tail, 0, xBytes, pwdMd5Bytes.length, tail.length);

		BigInteger x = new BigInteger(1, xBytes);
		BigInteger e = new BigInteger("65537");
		BigInteger n = new BigInteger(1, mPkBytes);

		BigInteger result = x.modPow(e, n);
		byte[] buff = result.toByteArray();
		if (buff[0] == 0) {
			byte[] tmp = new byte[buff.length - 1];
			System.arraycopy(buff, 1, tmp, 0, tmp.length);
			buff = tmp;
		}
		String sign = Utils.bytesToHexString(buff);
		mRequestHelper.login(sid, username, timestamp, sign);
	}

	public void onEventMainThread(LoginResult result) {
		String sid = mSidField.getText().toString();
		String username = mUsernameField.getText().toString();
		String currentLogin = sid + ":" + username;
		String lastLogin = mLoginPrefs.lastLogin().get();

		mLoginPrefs.edit()
			.sid().put(sid)
			.username().put(username)
			.lastLogin().put(currentLogin)
			.apply();

		if (!currentLogin.equals(lastLogin)) {
			mDefaultPrefs.clear();
		}

		String warehouses = mDefaultPrefs.warehouses().get();
		if (TextUtils.isEmpty(warehouses)) {
			mRequestHelper.queryWarehouses();
		} else {
			gotoMain();
		}
	}

	public void onEventMainThread(WarehouseResult result) {
		mDefaultPrefs.warehouses().put(Utils.toJson(result.warehouses));
		gotoMain();
	}

	private void gotoMain() {
		MainActivity_.intent(this)
			.flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
			.start();
	}

	private void gotoTest() {
		Intent intent = new Intent(this, TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}