package com.heru.httpwww.kurio_messanger.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.heru.httpwww.kurio_messanger.Main.MainPresenter.MainPresenter;
import com.heru.httpwww.kurio_messanger.Main.MainView.MainView;
import com.heru.httpwww.kurio_messanger.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvUser)
    TextView tvUser;
    @BindView(R.id.btnAddGroup)
    LinearLayout btnAddGroup;
    private Bundle extras;
    private String userName;
    Activity activity = this;
    Context context = this;
    MainPresenter presenter;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("user");
        }
        tvUser.setText(userName);
        Firebase.setAndroidContext(this);
    }

    @OnClick({R.id.btnAddGroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddGroup:
                dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.popup_add_room);
                dialog.show();
                final EditText etRoom = (EditText)dialog.findViewById(R.id.etRoom);
                RelativeLayout btnOk = (RelativeLayout) dialog.findViewById(R.id.btnSubmit);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String room = etRoom.getText().toString();
                        if (room.equals("")) {
                            Toast.makeText(context,"Please Input Room Name",Toast.LENGTH_LONG).show();
                        }else{
                            presenter.pushRoom(activity, context);
                        }
                    }
                });
                RelativeLayout btnCancel = (RelativeLayout) dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

}
