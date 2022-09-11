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

        override fun getDefaultInsertSql(vararg data: String): String {
            return "INSERT INTO users(username,email,gender,password,userType,token) VALUES(" +
                    "\"${data[0]}\",\"${data[1]}\",\"${data[2]}\",\"${data[3]}\",\"${data[4]}\",\"${data[5]}\")"
        }
    },
    USER_GAME_INFO {
        override fun getCreateSql(): String {
            return "Create Table If not exists userGameInfo(username VARCHAR(45) REFERENCES users(username)," +
                    "bottleFlipCount INT,level INT,starCount INT,myPackages VARCHAR(255),myBottles VARCHAR(255),currentAvatar VARCHAR(10)," +
                    "buyedAvatars VARCHAR(255),achievement VARCHAR(255) PRIMARY KEY (username))"
        }

        override fun getDefaultInsertSql(vararg data: String): String {
            return "Insert into userGameInfo(username,bottleFlipCount,level,starCount,myPackages,myBottles,currentAvatar, buyedAvatars,achievement)" +
                    " VALUES(\"${data[0]}\",0,0,0,null,null,\"0\",null,null)"
        }
    },
    LEVELS {
        override fun getCreateSql(): String {
            return "CREATE TABLE IF NOT EXISTS levels(" +
                    "id INT AUTO_INCREMENT,level INT, star INT, winnerCount INT,\n" +
                    "PRIMARY KEY (id))"
        }

        override fun getDefaultInsertSql(vararg data: String): String {
            return ""
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
                    "updatedTime datetime DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id))"
        }

        override fun getDefaultInsertSql(vararg data: String): String {
            return "INSERT INTO packages(" +
                    "username,name,description,imageUrl,numberOfLike,numberOfUnlike,numberOfDownload," +
                    "questions,version,updatedTime) VALUES(" +
                    "\"${data[0]}\",\"${data[1]}\",\"${data[2]}\",\"${data[3]}\",${data[4]}," +
                    "${data[5]},${data[6]},\"${data[7]}\",${data[8]},\"${data[9]}\")"
        }
    },
    BOTTLES {
        override fun getCreateSql(): String {
            return "CREATE TABLE IF NOT EXISTS bottles(" +
                    "id INT AUTO_INCREMENT," +
                    "username VARCHAR(45) REFERENCES users(username)," +
                    "name VARCHAR(45)," +
                    "description VARCHAR(45)," +
                    "luckRatio INT," +
                    "bottleType INT," +
                    "createdTime datetime DEFAULT CURRENT_TIMESTAMP," +
                    "imageUrl BLOB NULL," +
                    "numberOfDownload INT DEFAULT 0," +
                    "numberOfLike INT DEFAULT 0," +
                    "numberOfUnlike INT DEFAULT 0," +
                    "updatedTime datetime DEFAULT CURRENT_TIMESTAMP," +
                    "version INT DEFAULT 0," +
                    "PRIMARY KEY (id))"
        }

        override fun getDefaultInsertSql(vararg data: String): String {
            return "INSERT INTO bottles(username,name,description,luckRatio,bottleType,imageUrl,numberOfDownload," +
                    "numberOfLike,numberOfUnlike,version) VALUES(\"${data[0]}\",\"${data[1]}\",\"${data[2]}\"," +
                    "\"${data[3]}\",\"${data[4]}\",\"${data[5]}\",0,0,0,1)"
        }
    },
    ACHIEVEMENT {
        override fun getCreateSql(): String {
            return "CREATE TABLE IF NOT EXISTS achievement(" +
                    "id INT AUTO_INCREMENT," +
                    "award INT NOT NULL, " +
                    "title VARCHAR (55) NOT NULL, " +
                    "content VARCHAR(255) NOT NULL, " +
                    "imageUrl VARCHAR(255) NOT NULL, " +
                    "winnerCount INT DEFAULT 0, " +
                    "PRIMARY KEY (id))"
        }

        override fun getDefaultInsertSql(vararg data: String): String {
            return ""
        }
    };

    abstract fun getCreateSql(): String
    abstract fun getDefaultInsertSql(vararg data: String): String
}