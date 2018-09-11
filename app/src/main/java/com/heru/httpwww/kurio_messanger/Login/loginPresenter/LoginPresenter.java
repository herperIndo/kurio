package com.heru.httpwww.kurio_messanger.Login.loginPresenter;

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
import com.heru.httpwww.kurio_messanger.Login.loginView.loginView;
import com.heru.httpwww.kurio_messanger.Main.MainActivity;
import com.heru.httpwww.kurio_messanger.Model.login;
import com.heru.httpwww.kurio_messanger.Register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {
    loginView view;

    public LoginPresenter(loginView loginView) {
        this.view = loginView;
    }

    public void receiveUserLogin(String s, String s1) {

    }

    public void IntentToRegister(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        view.IntentToRegister(intent);
    }

    public void receiveUserLogin(final Activity activity, final Context context, final login loginData) {
        String url = "https://kuriomessanger-4fb20.firebaseio.com/users.json";

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("null")) {
                    Toast.makeText(context, "user not found", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(loginData.getUsername())) {
                            Toast.makeText(context, "user not found", Toast.LENGTH_LONG).show();
                        } else if (obj.getJSONObject(loginData.getUsername()).getString("password").equals(loginData.getPassword())) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            intent.putExtra("user",loginData.getUsername());
                            view.intenToMain(intent);
                        } else {
                            Toast.makeText(context, "incorrect password", Toast.LENGTH_LONG).show();
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

}



