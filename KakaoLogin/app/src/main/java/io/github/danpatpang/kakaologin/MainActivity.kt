package io.github.danpatpang.kakaologin

import android.content.Context
import android.content.pm.PackageInstaller
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    private val context:Context ?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // custom 버튼 실행 시, 카카오 로그인 실행.
//        btn_kakao_login.setOnClickListener {
//            com_kakao_login.performClick();
//        }

        // custom 버튼에 직접 세션을 오픈하여 실행.
        btn_kakao_login.setOnClickListener {
            var session = Session.getCurrentSession();
            session.addCallback(SessionCallback());
            session.open(AuthType.KAKAO_LOGIN_ALL, this);
        }
    }

}
