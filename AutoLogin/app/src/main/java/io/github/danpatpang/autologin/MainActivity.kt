package io.github.danpatpang.autologin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
        var loginId = auto.getString("inputId", null);
        var loginPassword = auto.getString("inputPassword", null);

        if (loginId != null && loginPassword != null) {
            // 자동 로그인
            if (loginId.equals("danpatpang") && loginPassword.equals("123")) {
                Toast.makeText(this, loginId + " auto Login", Toast.LENGTH_SHORT).show();
                var intent = Intent(this, SubActivity::class.java);
                startActivity(intent);
                finish();
            }
        } else {
            // 일반적인 로그인
            inputButton.setOnClickListener {
                var id = inputId.text.toString();
                var password = inputPassword.text.toString();
                // 로그인 성공
                if(id.equals("danpatpang") && password.equals("123")) {
                    // 로그인 정보 저장
                    var editor= auto.edit();
                    editor.putString("inputId", id);
                    editor.putString("inputPassword", password);
                    editor.commit();

                    Toast.makeText(this, id + " Login", Toast.LENGTH_SHORT).show();
                    var intent = Intent(this, SubActivity::class.java);
                    startActivity(intent);
                    finish();
                }
                // 실패
                else {
                    Toast.makeText(this, "아닌뎁...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
