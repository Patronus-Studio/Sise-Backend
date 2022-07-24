package com.patronusstudio.BottleFlip.enums

enum class CreateTableSqlEnum {
    USER {
        override fun getCreateSql(): String {
            return "CREATE Table If Not Exists users(username VARCHAR(45) NOT NULL," +
                    "email VARCHAR(45) NOT NULL," +
                    "gender VARCHAR(1) NOT NULL," +
                    "password VARCHAR(45) NOT NULL," +
                    "userType VARCHAR(1) NOT NULL," +
                    "createdTime datetime DEFAULT CURRENT_TIMESTAMP," +
                    "token VARCHAR(255)," +
                    "PRIMARY KEY (username))"
        }

        override fun getInsertSql(vararg data: String): String {
            return "INSERT INTO users(username,email,gender,password,userType,token) VALUES(" +
                    "\"${data[0]}\",\"${data[1]}\",\"${data[2]}\",\"${data[3]}\",\"${data[4]}\",\"${data[5]}\")"
        }

        override fun createTableErrorMessage(): String {
            return "Tablo oluşturulurken bir hata oluştu. Ekibimiz şuan hatayı incelemektedir." +
                    "Lütfen bir süre sonra tekrar deneyin."
        }

        override fun insertDataErrorMessage(): String {
            return "Verileriniz kaydedilirken bir hatayla karşılaşıldı. Ekibimiz şuan hatayı incelemektedir." +
                    "Lütfen bir süre sonra tekrar deneyin."
        }

    },
    USER_GAME_INFO {
        override fun getCreateSql(): String {
            return "Create Table If not exists userGameInfo(username VARCHAR(45) REFERENCES users(username)," +
                    "bottleFlipCount INT,level INT,starCount INT," +
                    "PRIMARY KEY (username))"
        }

        override fun getInsertSql(vararg data: String): String {
            return "Insert into userGameInfo(username,bottleFlipCount,level,starCount) VALUES(\"${data[0]}\",0,0,0)"
        }

        override fun createTableErrorMessage(): String {
            return "Tablo oluşturulurken bir hata oluştu. Ekibimiz şuan hatayı incelemektedir." +
                    "Lütfen bir süre sonra tekrar deneyin."
        }

        override fun insertDataErrorMessage(): String {
            return "Verileriniz kaydedilirken bir hatayla karşılaşıldı. Ekibimiz şuan hatayı incelemektedir." +
                    "Lütfen bir süre sonra tekrar deneyin."
        }
    },
    PACKAGES {
        override fun getCreateSql(): String {
            return "Create Table If Not Exists packages(" +
                    "id INT AUTO_INCREMENT," +
                    "username VARCHAR(45) REFERENCES users(username)," +
                    "name VARCHAR(45)," +
                    "description VARCHAR(45)," +
                    "imageUrl VARCHAR(255)," +
                    "createdTime datetime DEFAULT CURRENT_TIMESTAMP," +
                    "numberOfLike INT," +
                    "numberOfUnlike INT," +
                    "numberOfDownload INT," +
                    "questions BLOB," +
                    "version INT," +
                    "lastUpdatedDate datetime DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id))"
        }

        override fun getInsertSql(vararg data: String): String {
            return "INSERT INTO packages(" +
                    "username,name,description,imageUrl,numberOfLike,numberOfUnlike,numberOfDownload," +
                    "questions,version,lastUpdatedDate) VALUES(" +
                    "\"${data[0]},\"${data[1]},\"${data[2]},\"${data[3]},\"${data[4]}," +
                    "\"${data[5]},\"${data[6]},\"${data[7]},\"${data[8]})"
        }

        override fun createTableErrorMessage(): String {
            return "Tablo oluşturulurken bir hata oluştu. Ekibimiz şuan hatayı incelemektedir." +
                    "Lütfen bir süre sonra tekrar deneyin."
        }

        override fun insertDataErrorMessage(): String {
            return "Verileriniz kaydedilirken bir hatayla karşılaşıldı. Ekibimiz şuan hatayı incelemektedir." +
                    "Lütfen bir süre sonra tekrar deneyin."
        }

    };

    abstract fun getCreateSql(): String
    abstract fun getInsertSql(vararg data: String): String
    abstract fun createTableErrorMessage(): String
    abstract fun insertDataErrorMessage(): String
}