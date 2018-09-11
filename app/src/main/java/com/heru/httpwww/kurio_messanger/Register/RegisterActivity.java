package com.heru.httpwww.kurio_messanger.Register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.heru.httpwww.kurio_messanger.Model.register;
import com.heru.httpwww.kurio_messanger.R;
import com.heru.httpwww.kurio_messanger.Register.RegisterPresenter.registerPresenter;
import com.heru.httpwww.kurio_messanger.Register.RegisterView.RegisterView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.login)
    TextView login;
    String user, pass;
    private registerPresenter presenter;
    Activity activity = this;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi_activity);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        presenter = new registerPresenter(this);
    }


    @OnClick({R.id.registerButton, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                user = username.getText().toString();
                pass = password.getText().toString();

                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else if (!user.matches("[A-Za-z0-9]+")) {
                    username.setError("only alphabet or number allowed");
                } else if (user.length() < 5) {
                    username.setError("at least 5 characters long");
                } else if (pass.length() < 5) {
                    password.setError("at least 5 characters long");
                } else {
                    register reg = new register();
                    reg.setUsername(user);
                    reg.setPassword(pass);
                    presenter.receiveRegister(reg, context);
                }
                break;
            case R.id.login:
                presenter.intentToLogin(activity);
                break;
        }
    }

    @Override
    public void IntenToLogin(Intent intent) {
        startActivity(intent);
        activity.finish();
    }

    @Override
    public void onFailure(FirebaseError firebaseError) {

    }
}
