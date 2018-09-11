package com.heru.httpwww.kurio_messanger.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heru.httpwww.kurio_messanger.Login.loginPresenter.LoginPresenter;
import com.heru.httpwww.kurio_messanger.Login.loginView.loginView;
import com.heru.httpwww.kurio_messanger.Model.login;
import com.heru.httpwww.kurio_messanger.R;
import com.heru.httpwww.kurio_messanger.Register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements loginView {


    String user, pass;
    @BindView(R.id.username)
    EditText etUsername;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.register)
    TextView register;
    private LoginPresenter presenter;
    Activity activity = this;
    Context context = this;
    private login loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
    }


    @OnClick({R.id.loginButton, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButton:

                user = etUsername.getText().toString();
                pass = etPassword.getText().toString();

                if (user.equals("")) {
                    etUsername.setError("can't be blank");
                } else if (pass.equals("")) {
                    etPassword.setError("can't be blank");
                } else {
                    loginData = new login();
                    loginData.setUsername(user);
                    loginData.setPassword(pass);
                    presenter.receiveUserLogin(activity,context,loginData);
                }
                break;
            case R.id.register:
                presenter.IntentToRegister(activity);

                break;

        }
    }

    @Override
    public void IntentToRegister(Intent intent) {
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void intenToMain(Intent intent) {
        activity.startActivity(intent);
        activity.finish();
    }
}
