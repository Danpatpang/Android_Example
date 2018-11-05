package io.github.danpatpang.autologin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sub.*;

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        logout.setOnClickListener {
            // 저장된 정보 초기화
            var auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
            var editor = auto.edit();
            editor.clear();
            editor.commit();

            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
            finish();
        }
    }
}
