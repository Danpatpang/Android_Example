package io.github.danpatpang.createaccount

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
import java.util.regex.Pattern

class CreateAccountActivity : AppCompatActivity() {
    private var signUpName: String = "";
    private var signUpEmail: String = "";
    private var signUpPassword: String = "";

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
                onSignUpFailed();
            } else {
                var url = "자신의 서버 ip를 입력해주세요.";
                var duplicationCheck: Boolean = false;
                val postRequest = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        Log.e("response", response);
                        if (response.equals("true")) {
                            duplicationCheck = true;
                        }
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

                // DB 전송(이메일 확인)
                if (duplicationCheck) {
                    onSignUpSuccess();
                }
            }
        }

        link_login.setOnClickListener {
            startMainActivity();
        }
    }

    fun onSignUpFailed() {
        Toast.makeText(this, "회원가입 실패...", Toast.LENGTH_SHORT).show();
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
