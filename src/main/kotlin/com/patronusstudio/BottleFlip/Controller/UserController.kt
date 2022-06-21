package com.patronusstudio.BottleFlip.Controller

//@RestController
//class UserController {
//
//    @Autowired
//    private lateinit var userRepo: UserRepo
//
//    @PostMapping("/login")
//    fun login(@RequestParam username: String, @RequestParam password: String): BaseModel {
//        val sql = "SELECT * From users Where username = \"$username\" and password = \"$password\""
//        val res = userRepo.getData(sql)
//        return when (res) {
//            is BaseSealed.Succes -> {
//                val mappter= ObjectMapper()
//                return mappter.convertValue(res.data,UserModel::class.java)
//            }
//            is BaseSealed.Error -> ErrorModel(false, res.data["error"] as String)
//        }
//    }
//
//    @PostMapping("/register")
//    fun register(
//        @RequestParam username: String,
//        @RequestParam password: String,
//        @RequestParam email: String,
//        @RequestParam gender: String
//    ) {
//        //aynı nickle kayıtlı birisi var mı?
//        //aynı email ile kayıtlı birisi var mı?
//        // varsa bu email daha önce kaydedildi.
//        //
//    }
//
//    //kullanıcı adı kontrolü eklenecek
//    @PostMapping("usernameControl")
//    fun usernameControl(@RequestParam username: String) {
//        val sqlQuery = "SELECT * from users where username = \"$username\""
//        val map = userRepo.getData(sqlQuery)
//    }
//}