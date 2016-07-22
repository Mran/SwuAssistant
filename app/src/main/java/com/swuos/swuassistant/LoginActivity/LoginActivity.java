package com.swuos.swuassistant.LoginActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.LoginActivity.presenter.ILoginPresenter;
import com.swuos.swuassistant.LoginActivity.presenter.LoginPresenterCompl;
import com.swuos.swuassistant.LoginActivity.view.ILoginView;
import com.swuos.swuassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_in_button)
    Button signInButton;

    ILoginPresenter iLoginPresenter;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    String muserName;
    String mpassWord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        iLoginPresenter = new LoginPresenterCompl(this, this);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this).setPositiveButton(R.string.i_know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).create();
    }

    @OnClick(R.id.sign_in_button)
    public void onClick(View view) {
      /*显示登陆过程窗口*/
        progressDialog.setMessage(this.getString(R.string.loging_and_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        muserName = username.getText().toString();
        mpassWord = password.getText().toString();
        iLoginPresenter.doLogin(muserName, mpassWord);

    }

    @Override
    public void onLoginResult(String result) {
        progressDialog.cancel();
        if ("success".equals(result)) {

            /*开启下一个窗口*/
            Intent intent = new Intent();
            setResult(Constant.LOGIN_RESULT_CODE, intent);
            finish();
        } else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }


}
