package io.github.danpatpang.communication_volley

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var queue = Volley.newRequestQueue(this);
        submit.setOnClickListener {
            var data = text.text.toString();
            // http 꼭 붙일 것;;
            var url = "url 입력해주세요!"

            // object를 왜 써줘야 하는지 모르겠음.
            val postRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> {
                    response ->
                    var intent = Intent(this, SubActivity::class.java);
                    intent.putExtra("response", response);
                    startActivity(intent);
                    finish();
                },
                Response.ErrorListener {
                    Toast.makeText(this, "통신 실패", Toast.LENGTH_SHORT).show();
                }) {
                // 전송할 데이터 입력
                override fun getParams(): MutableMap<String, String> {
                    var params = HashMap<String, String>();
                    params.put("data", data);
                    return params;
                }
            }
            queue.add(postRequest);
        }
    }
}
