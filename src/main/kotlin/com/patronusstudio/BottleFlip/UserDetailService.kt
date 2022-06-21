package com.patronusstudio.BottleFlip

import com.patronusstudio.BottleFlip.Repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    // TODO: 22.06.2022 DBDEN KULLANICI VERİLERİ ÇEKİLECEK
    override fun loadUserByUsername(username: String?): UserDetails {
//        val sql = "SELECT * From users Where username = \"$username\""
//        val res = userRepo.getData(sql)
//        return when (res) {
//            is BaseSealed.Succes -> User(username, res.data["password"].toString(), arrayListOf())
//            is BaseSealed.Error -> throw Exception("No user found")
//        }
        val map = mapOf<String, String>(Pair("iamcodder", passwordEncoder.encode("babameraba")))
        if (map.containsKey(username)) {
            return User(username, map.get(username), arrayListOf())
        }
        throw Exception("")
    }

    //TODO REFRESH TOKEN EKLENECEK
}