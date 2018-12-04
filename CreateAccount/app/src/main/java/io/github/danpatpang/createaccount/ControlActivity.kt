package io.github.danpatpang.createaccount

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_control.*;

class ControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        var storage = getSharedPreferences("tokenStore", Activity.MODE_PRIVATE);

        accessToken.text = storage.getString("accessToken",null);
        refreshToken.text = storage.getString("refreshToken",null);

        btn_logout.setOnClickListener {
            var tokenHandler = storage.edit();
            tokenHandler.clear();
            tokenHandler.commit();

            accessToken.text = storage.getString("accessToken",null);
            refreshToken.text = storage.getString("refreshToken",null);
        }
    }
}
