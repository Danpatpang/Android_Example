package io.github.danpatpang.createaccount

import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.util.*
import kotlin.text.Charsets.UTF_8

class JWTUtils {
    private fun decodingToken(token: String): String {
        val payload = token.split(".")[1];
        return payload;
    }

    private fun getExp(payload: String): Long {
        // utf-8 => UTF_8
        val decodeBytes = Base64.decode(payload, Base64.URL_SAFE);
        var exp: Long = JSONObject(decodeBytes.toString(UTF_8)).getLong("exp");
        exp *= 1000;

        return exp;
    }

    /**
     * Token 만료 기간 확인
     * 만료 전 : exp > now (compareResult : 1) true
     * 만료 : exp < now (compareResult: -1) false
     */
    fun isValidToken(token: String): Boolean {
        val nowDate = Date().time;
        val expDate = getExp(decodingToken(token));

        var compareResult = expDate.compareTo(nowDate);

        return compareResult == 1;
    }
}