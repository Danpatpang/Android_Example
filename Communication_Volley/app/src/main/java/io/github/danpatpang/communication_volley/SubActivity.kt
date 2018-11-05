package io.github.danpatpang.communication_volley

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sub.*;

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        var intent = getIntent();
        result.text = intent.extras.getString("response");

        var queue = Volley.newRequestQueue(this);
        getButton.setOnClickListener {
            var url = "url 입력해주세요!";
            var getRequest = StringRequest(Request.Method.GET, url,
                Response.Listener {
                    response -> result.text = response;
                },
                Response.ErrorListener {
                    Toast.makeText(this, "통신 실패", Toast.LENGTH_SHORT).show();
                });

            queue.add(getRequest);
        }
    }
}
