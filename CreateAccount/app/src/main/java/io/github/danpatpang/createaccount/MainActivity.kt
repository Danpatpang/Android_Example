package io.github.danpatpang.createaccount

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*;
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var email: String = "";
    private var password: String = "";
    private val url = "http://603be062.ngrok.io/signin";
    private var loginResult = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var storage = getSharedPreferences("tokenStore", Activity.MODE_PRIVATE);
        var queue = Volley.newRequestQueue(this);

        // 로그인
        btn_login.setOnClickListener {
            email = input_email.text.toString();
            password = input_password.text.toString();

            if (!isValid()) {
                onLoginFailed();
            } else {
                btn_login.isEnabled = false;

                val postRequest = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        loginResult = JSONObject(response).getBoolean("check");

                        // 로그인 성공일 경우 Token값 가져오기
                        if(loginResult) {
                            val accessToken: String = JSONObject(response).getString("accessToken");
                            val refreshToken: String = JSONObject(response).getString("refreshToken");

                            // 토큰 저장
                            var tokenHandler = storage.edit();
                            tokenHandler.putString("accessToken", accessToken);
                            tokenHandler.putString("refreshToken", refreshToken);
                            tokenHandler.commit();
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        var params = HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                }
                queue.add(postRequest);

                // 비동기 문제 때문에 progressDialog를 사용하여 1초 후 실행.
                var progressDialog = ProgressDialog(this, R.style.AppTheme_Blue_Dialog);
                progressDialog.isIndeterminate = true;
                progressDialog.setMessage("Checking Account...");
                progressDialog.show();

                // 1초뒤 서버 결과 확인
                android.os.Handler().postDelayed(
                    object : Runnable {
                        override fun run() {
                            if (loginResult) {
                                onLoginSuccess();
                            } else {
                                onLoginFailed();
                            }
                            progressDialog.dismiss();
                            btn_login.isEnabled = true;
                        }
                    }, 2000
                );
            }
        }

        link_signup.setOnClickListener {
            var intent = Intent(this, CreateAccountActivity::class.java);
            startActivity(intent);
        }
    }

    fun onLoginSuccess() {
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
        var intent = Intent(this, ControlActivity::class.java);
        startActivity(intent);
        finish();
    }

    fun onLoginFailed() {
        Toast.makeText(this, "로그인 실패...", Toast.LENGTH_SHORT).show();
    }

    // 입력 유효성 검증 (이메일만)
    fun isValid(): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Enter a valid email address.");
            return false;
        } else {
            input_email.setError(null);
        }

        return true;
    }
}
