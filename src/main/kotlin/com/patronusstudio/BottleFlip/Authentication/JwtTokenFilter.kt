package com.patronusstudio.BottleFlip.Authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//her bir istekte jwt token var mı ona bakacak
@Component
class JwtTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenManager: TokenManager

    private lateinit var username: String
    private lateinit var token: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        /**
        "Bearer 123hab1231"
         */
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.contains("Bearer")) {
            //burada bearerden sonraki tokeni aldık.
            token = authHeader.substring(7)
            try {
                username = tokenManager.getUsernameToken(token)
            } catch (e: Exception) {
                throw Exception(e.localizedMessage)
            }
        }
        //kullanıcı daha önce bu tokenle login olmuş mu?
        val userBeforeRegister = SecurityContextHolder.getContext().authentication == null
        //burada login yap dememiz lazım
        if (this::username.isInitialized && this::token.isInitialized && userBeforeRegister) {

            if (tokenManager.tokenValidate(token)) {
                val upassToken = UsernamePasswordAuthenticationToken(username, null, arrayListOf())
                upassToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = upassToken
            }
        }
        filterChain.doFilter(request, response)
    }
}