package io.github.danpatpang.kakaologin

import android.app.Application
import android.content.Context
import com.kakao.auth.*


class GlobalApplication : Application() {
    /**
     * 설명 필요
     */
    companion object {
        private var obj : GlobalApplication ?= null;
        private lateinit var context: Context;

        fun getGlobalApplicationContext(): GlobalApplication {
            return obj!!
        }
    }

    private class KakaoSDKAdapter : KakaoAdapter() {
        // 세션의 옵션 설정.
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                // 로그인시 인증받을 타입.
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL);
                }

                // CPU 절약을 위한 타이머 설정.
                override fun isUsingWebviewTimer(): Boolean {
                    return false;
                }

                // 로그인시 암호화?
                override fun isSecureMode(): Boolean {
                    return false;
                }

                // 카카오와 제휴된 앱에서 사용됨. (일반인은 필요 X)
                // default로 INDIVIDUAL 사용.
                override fun getApprovalType(): ApprovalType {
                    return ApprovalType.INDIVIDUAL;
                }

                // 입력폼에 데이터를 저장할 것인가.
                override fun isSaveFormData(): Boolean {
                    return true;
                }
            }
        }

        // 애플리케이션이 가지고 있는 정보를 얻오옴.
        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig {
                getGlobalApplicationContext();
            };
        }
    }

    override fun onCreate() {
        super.onCreate();
        obj = this;
        context = this;
        KakaoSDK.init(KakaoSDKAdapter());
    }
}