package io.github.danpatpang.createaccount

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
import kotlinx.android.synthetic.main.activity_create_account.*;
import org.json.JSONObject
import java.util.regex.Pattern

class CreateAccountActivity : AppCompatActivity() {
    private var signUpName: String = "";
    private var signUpEmail: String = "";
    private var signUpPassword: String = "";
    private var url = "http://c61298ad.ngrok.io/signup";
    private var duplicationCheck: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        var queue = Volley.newRequestQueue(this);

        btn_signup.setOnClickListener {
            signUpName = input_signup_name.text.toString();
            signUpEmail = input_signup_email.text.toString();
            signUpPassword = input_signup_password.text.toString();

            // 유효성 검사
            if (!isValid()) {
                Toast.makeText(this, "올바른 형식을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                btn_signup.isEnabled = false;

                // 서버로 데이터를 보낸 후 응답 값으로 email 중복 확인
                val postRequest = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        val result: Boolean = JSONObject(response).getBoolean("check");
                        duplicationCheck = result;
                        Log.e("dup2", duplicationCheck.toString() + "1");
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        var params = HashMap<String, String>();
                        params.put("name", signUpName);
                        params.put("email", signUpEmail);
                        params.put("password", signUpPassword);
                        return params;
                    }
                }
                queue.add(postRequest);

                // 비동기 문제 때문에 progressDialog를 사용하여 1초 후 실행.
                var progressDialog = ProgressDialog(this, R.style.AppTheme_Blue_Dialog);
                progressDialog.isIndeterminate = true;
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                // 1초뒤 서버 결과 확인 (delay 지연 때문에 항상 바꿔줘야 함)
                android.os.Handler().postDelayed(
                    object : Runnable {
                        override fun run() {
                            Log.e("dup2", duplicationCheck.toString() + "1");
                            if (duplicationCheck) {
                                onSignUpSuccess();
                            } else {
                                onSignUpFailed();
                            }
                            progressDialog.dismiss();
                            btn_signup.isEnabled = true;
                        }
                    }, 2000
                );
            }
        }

        link_login.setOnClickListener {
            startMainActivity();
        }
    }

    fun onSignUpFailed() {
        Toast.makeText(this, "이미 가입된 이메일입니다...", Toast.LENGTH_SHORT).show();
    }

    fun onSignUpSuccess() {
        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    fun isValid(): Boolean {
        var check: Boolean = true;

        // 이름 확인
        if (!Pattern.matches("^(?=.*[a-zA-Z\\d가-힣]).{1,6}$", signUpName)) {
            input_signup_name.setError("이름은 최대 6자까지 가능합니다.");
            check = false;
        } else {
            input_signup_name.setError(null);
        }

        // 이메일 형식 확인
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()) {
            input_signup_email.setError("올바른 이메일 형식을 입력해주세요.");
            check = false;
        } else {
            input_signup_email.setError(null);
        }

        // 비밀번호 확인
        if (!Pattern.matches("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~`!@#$%\\^&*()-]).{6,14}$", signUpPassword)) {
            input_signup_password.setError("특수문자 포함 6-14자리를 입력해주세요.");
            check = false;
        } else {
            input_signup_password.setError(null);
        }

        return check;
    }

    // MainActivity로 이동
    fun startMainActivity() {
        var intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }
}
