package io.github.danpatpang.createaccount

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    private var email: String = "";
    private var password: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = input_email.text.toString();
        password = input_password.text.toString();

        // 로그인
        btn_login.setOnClickListener {
            login();
        }

        link_signup.setOnClickListener {
            var intent = Intent(this, CreateAccountActivity::class.java);
            startActivity(intent);
            finish();
        }
    }

    fun login() {
        if (!isValid()) {
            onLoginFailed();
            return;
        }

        btn_login.isEnabled = false;
//        // 수정 필요
//        var progressDialog = ProgressDialog(this, R.style.AppTheme_Dark);
//        progressDialog.isIndeterminate = true;
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
//        android.os.Handler().postDelayed(
//            Runnable() {
//                fun run() {
//                    onLoginSuccess();
//                    progressDialog.dismiss();
//                }
//            }
//        , 3000);

        onLoginSuccess();
    }

    fun onLoginSuccess() {
        btn_login.isEnabled = true;
        finish();
    }

    fun onLoginFailed() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        btn_login.isEnabled = true;
    }

    // 입력 유효성 검증 (추후 정규표현식)
    fun isValid(): Boolean {
        // android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()??
        if (email.isEmpty() || android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Enter a valid email address.");
            return false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length < 6 || password.length > 12) {
            input_password.setError("between 6 and 12 alphanumeric characters.");
            return false;
        } else {
            input_password.setError(null);
        }

        return true;
    }
}
