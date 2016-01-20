package com.example.swuassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.example.tool.Login;
import com.example.tool.TotalInfo;

import static com.example.swuassistant.Constant.*;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener
{


    // UI references.
    private EditText mUserNAmeView;
    private EditText mPasswordView;

    private String userNAme;
    private String password;
    private String response;
    private static ProgressDialog progressDialogLoading;
   private TotalInfo totalInfo=new TotalInfo();
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case Constant.ID_SUCCESE:
                    progressDialogLoading.cancel();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userName", userNAme);
                    intent.putExtra("password", password);
                    intent.putExtra("name",totalInfo.getName());
                    intent.putExtra("swuID",totalInfo.getSwuID());
                    startActivity(intent);
                    finish();
                    break;
                case Constant.ID_FAILED:
                   progressDialogLoading.setMessage("登陆失败");
                    progressDialogLoading.setCancelable(true);
                    progressDialogLoading.show();
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserNAmeView = (EditText) findViewById(R.id.username);
        progressDialogLoading = new ProgressDialog(LoginActivity.this);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);

    }


    private boolean isPasswordValid(String password)
    {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }


    @Override
    public void onClick(View v)
    {
        userNAme = mUserNAmeView.getText().toString();
        password = mPasswordView.getText().toString();
        progressDialogLoading.setTitle("登录");
        progressDialogLoading.setMessage("正在登录请稍后");
        progressDialogLoading.setCancelable(false);
        progressDialogLoading.show();
        if (v.getId() == R.id.email_sign_in_button)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    Login login = new Login();
                    response = login.doLogin(userNAme, password);
                    totalInfo=login.getTotalInfo();
                    Message message = new Message();
                    if (response.contains("Successed"))
                    {

                        message.what = Constant.ID_SUCCESE;
                        handler.sendMessage(message);
                    } else
                    {
                        message.what = Constant.ID_FAILED;
                        handler.sendMessage(message);
                    }
                }
            }).start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

