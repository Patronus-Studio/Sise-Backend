package com.patronusstudio.BottleFlip.Utils

private val emailAddressRegex = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
fun isEmailValid(str: String): Boolean{
   return str.matches(emailAddressRegex)
}