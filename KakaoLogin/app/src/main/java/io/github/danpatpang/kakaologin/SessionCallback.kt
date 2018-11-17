package io.github.danpatpang.kakaologin

import android.util.Log
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException

// 재확인 필요
class SessionCallback : ISessionCallback {
    // 로그인 성공
    override fun onSessionOpened() {
        requestMe();
    }
    // 로그인 실패
    override fun onSessionOpenFailed(exception: KakaoException?) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.toString());
    }

    // 사용자 정보 요청
    fun requestMe() {
        val keys = arrayListOf<String>(
            "properties.nickname",
            "properties.profile_image",
            "kakao_account.email",
            "kakao_account.age_range",
            "kakao_account.gender"
        );

        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            // 요청 성공
            override fun onSuccess(result: MeV2Response?) {
                Log.e("SessionCallback :: ", "onSuccess");
                Log.e(keys[0], result?.nickname);
                Log.e(keys[1], result?.profileImagePath);
                Log.e(keys[2], result?.kakaoAccount?.email);
                Log.e(keys[3], result?.kakaoAccount?.ageRange.toString());
                Log.e(keys[4], result?.kakaoAccount?.gender.toString());
            }

            // 요청 실패
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=$errorResult";
                Log.e("실패", message);
            }

            // 세션이 닫힌 경우 (재로그인 / 토큰 발급 필요)
            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.toString());
            }

            // 미가입 상태는 더 이상 호출 불가.
//            override fun onNotSignedUp() {}
        })
    }

//    fun redirectMainActivity() {
//        var intent = Intent(MainActivity::class.java);
//    }
}