package com.heru.httpwww.kurio_messanger.Register.RegisterPresenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.heru.httpwww.kurio_messanger.Login.LoginActivity;
import com.heru.httpwww.kurio_messanger.Model.register;
import com.heru.httpwww.kurio_messanger.Register.RegisterView.RegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class registerPresenter {
    RegisterView view;
    Firebase userRef;

    public registerPresenter(RegisterView view) {
        this.view = view;
    }

    public void receiveRegister(final register reg, final Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        String url = "https://kuriomessanger-4fb20.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://kuriomessanger-4fb20.firebaseio.com/users");
                if (s.equals("null")) {
                    reference.child(reg.getUsername()).child("password").setValue(reg.getPassword());
                    Toast.makeText(context, "registration successful", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(reg.getUsername())) {
                            reference.child(reg.getUsername()).child("password").setValue(reg.getPassword());
                            Toast.makeText(context, "registration successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "username already exists", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                pd.dismiss();

            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);

    }


    ////        userRef.createUser(user, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
//            @Override
//            public void onSuccess(Map<String, Object> stringObjectMap) {
//                String uid = stringObjectMap.get("uid").toString();
//                userRef = new Firebase("https://kuriomessanger-4fb20.firebaseio.com/Users/" + uid);
//            }
//
//            @Override
//            public void onError(FirebaseError firebaseError) {
//                view.onFailure(firebaseError);
//            }
//        });


    public void intentToLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        view.IntenToLogin(intent);
    }
}
