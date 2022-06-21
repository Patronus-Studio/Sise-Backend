package com.patronusstudio.BottleFlip

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenManager {

    //tokenin geçerlilik süresi 5 dakika milisaniye cinsinden
    private val expirationTime = 5 * 60 * 1000

    private val key by lazy {
        Keys.secretKeyFor(SignatureAlgorithm.HS256)
    }

    //usernameye göre token üretecek
    fun generateToken(username: String): String {
        val jws: String = Jwts.builder()
            .setSubject(username)
            //tokenin geçerlilik süresi
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            //kim oluşturdu
            .setIssuer("iamcodder")
            //ne zaman oluşturuldu
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(key)
            .compact()
        //token oluşturuldu ve geri döndürülüyor
        return jws
    }

    //aldığı tokenin doğru olup olmadını kontrol eder
    fun tokenValidate(token: String): Boolean {
        //user var mı?
        val isThereUser = getUsernameToken(token)
        //userin aldığı token geçerli mi?
        val isUserExpired = isExpired(token)
        return isThereUser != null && isUserExpired
    }

    //tokene göre user dönecek
    fun getUsernameToken(token: String): String {
        //burada aslında generate token ile oluşturduğumuz tokendeki bilgilere erişim sağlıyoruz.
        //bunun için claim oluşturuluyor. Örneğin generateTokendeki setSubject bir claim,
        //setIssuer bir claim. Kısaca orada atanan değerlere erişim sağlıyoruz
        val claims = getClaims(token)
        //yukarıda usernameyi tokenin içerisine eklemiştik, burada da token içerisinden
        // kullanıcı adını çıkartmış oluyoruz
        return claims.subject
    }

    fun isExpired(token: String): Boolean {
        val claims = getClaims(token)
        return claims.expiration.after(Date(System.currentTimeMillis()))
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).body
    }
}