package org.swe.android.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.ActivationCode;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.helpers.NetworkHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YongjiLi on 4/19/16.
 */
public class ActivationCodeGeneratorActivity extends Activity {

    private Button buttonGenerate;
    private ConnectivityManager mConnectivityManager;
    private ActivationCode activationCode = new ActivationCode();
    private String key;
    private EditText editTextCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_activation_code);

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);


        editTextCode = (EditText) findViewById(R.id.edit_text_code);

        addListenerOnButtonCancel();
        addListenerOnButtonCode();
    }


    //Generate an activation code
    public void addListenerOnButtonCode() {

        buttonGenerate = (Button) findViewById(R.id.button_code_generate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    key = new SimpleDateFormat("yyyyMMdd").format(new Date()) +
                            Long.toString(System.currentTimeMillis());
                    activationCode.setKey(key);
                    activationCode.setIsNew(true);
                    activationCode.setDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                    new GenerateKeyTask().execute(activationCode);

                }
            }
        });
    }


    //Insert this activation code into the database
    private class GenerateKeyTask extends AsyncTask<ActivationCode, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(ActivationCode... code) {
            return SurveyDataSource.generateActivationKey(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), activationCode);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_generate_key_success),
                        Toast.LENGTH_LONG).show();

                editTextCode.setText(activationCode.getKey());

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_generate_key),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addListenerOnButtonCancel() {
        Button buttonCancel = (Button) findViewById(R.id.button_code_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

}