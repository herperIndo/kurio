package com.heru.httpwww.kurio_messanger.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.heru.httpwww.kurio_messanger.Main.MainPresenter.MainPresenter;
import com.heru.httpwww.kurio_messanger.Main.MainView.MainView;
import com.heru.httpwww.kurio_messanger.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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
    @BindView(R.id.listRoom)
    ListView listRoom;
    private Bundle extras;
    private String userName;
    Activity activity = this;
    Context context = this;
    MainPresenter presenter;
    Dialog dialog;
    ProgressDialog pd;
    int totalUsers = 0;
    ArrayList<String> al = new ArrayList<>();

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
        CheckRoom();
    }

    private void CheckRoom() {
        pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        String url = "https://kuriomessanger-4fb20.firebaseio.com/room.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    @OnClick({R.id.btnAddGroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddGroup:
                dialog = new Dialog(activity);
//                dialog.setCancelable(false);
                dialog.setContentView(R.layout.popup_add_room);
                final EditText etRoom = (EditText) dialog.findViewById(R.id.etRoom);
                Button btnOk = (Button) dialog.findViewById(R.id.btnSubmit);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String room = etRoom.getText().toString();
                        if (room.equals("")) {
                            Toast.makeText(context, "Please Input Room Name", Toast.LENGTH_LONG).show();
                        } else {
                            presenter.pushRoom(activity, context, room);
                        }
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void CreateRoom() {
        CheckRoom();
    }

    public void doOnSuccess(String s) {
        try {
            JSONObject obj = new JSONObject(s);

            Iterator it = obj.keys();
            String key;
            al.clear();
            for (int j = 0; j < obj.length(); j++) {
                key = it.next().toString();
                JSONObject obj2 = obj.getJSONObject(key);
                String tittle = obj2.getString("tittleRoom");
                al.add(tittle);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listRoom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        pd.dismiss();
    }
}
