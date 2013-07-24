package com.barbogogo.leedreader;

import com.leed.reader.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {

	public static final String KEY_SERVER_LINK = "serverLinkPref";
	public static final String KEY_USERNAME = "usernamePref";
	public static final String KEY_PASSWORD = "passwordPref";
	public static final String KEY_CONNECTION_TYPE = "connectionType";

	private EditTextPreference mServerLinkPref;
	private EditTextPreference mUsernamePref;
	private EditTextPreference mTextPassword;
	private ListPreference mConnectionType;

	OnPreferenceChangeListener textChangeListener;
	OnPreferenceChangeListener passwordChangeListener;
	OnPreferenceChangeListener connectionTypeChangeListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.slide_up, R.anim.do_nothing);

		setTitle(getResources().getText(R.string.setting_title));

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		addPreferencesFromResource(R.layout.settings);

		mServerLinkPref = (EditTextPreference) getPreferenceScreen()
				.findPreference(KEY_SERVER_LINK);
		mUsernamePref = (EditTextPreference) getPreferenceScreen()
				.findPreference(KEY_USERNAME);
		mTextPassword = (EditTextPreference) getPreferenceScreen()
				.findPreference(KEY_PASSWORD);
		mConnectionType = (ListPreference) getPreferenceScreen()
				.findPreference(KEY_CONNECTION_TYPE);

		textChangeListener = new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				preference.setSummary(newValue.toString());
				return true;
			}
		};

		passwordChangeListener = new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				if (newValue.toString().isEmpty())
					preference
							.setSummary(R.string.setting_password_summary_notgiven);
				else
					preference
							.setSummary(R.string.setting_password_summary_given);
				return true;
			}
		};

		connectionTypeChangeListener = new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				if (newValue.toString().isEmpty()
						|| newValue.toString().equals("0")) {
					// preference.setDefaultValue("0");
					preference
							.setSummary(R.string.setting_connection_mode_online);
				} else if (newValue.toString().equals("2")) {
					preference
							.setSummary(R.string.setting_connection_mode_offline);
				}
				return true;
			}
		};

		mServerLinkPref.setOnPreferenceChangeListener(textChangeListener);
		mUsernamePref.setOnPreferenceChangeListener(textChangeListener);
		mTextPassword.setOnPreferenceChangeListener(passwordChangeListener);
		mConnectionType
				.setOnPreferenceChangeListener(connectionTypeChangeListener);

		displayPreferences();
	}

	public void displayPreferences() {
		// Post signature
		if (!(mServerLinkPref.getText() == null || mServerLinkPref.getText()
				.equals(""))) {
			mServerLinkPref.setSummary(mServerLinkPref.getText());
		} else {
			mServerLinkPref.setSummary(R.string.setting_server_link_summary);
		}

		if (!(mUsernamePref.getText() == null || mUsernamePref.getText()
				.equals(""))) {
			mUsernamePref.setSummary(mUsernamePref.getText());
		} else {
			mUsernamePref.setSummary(R.string.setting_username_summary);
		}

		if (!(mTextPassword.getText() == null || mTextPassword.getText()
				.equals(""))) {
			mTextPassword.setSummary(R.string.setting_password_summary_given);
		} else {
			mTextPassword
					.setSummary(R.string.setting_password_summary_notgiven);
		}

		if (mConnectionType.getValue().isEmpty()
				|| mConnectionType.getValue().equals("0")) {
			mConnectionType.setSummary(R.string.setting_connection_mode_online);
		} else if (mConnectionType.getValue().equals("2")) {
			mConnectionType
					.setSummary(R.string.setting_connection_mode_offline);
		}
	}

	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.do_nothing, R.anim.slide_down);
		setResult(RESULT_OK);
		super.onPause();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
