package com.patronusstudio.BottleFlip.enums

enum class CreateTableSqlEnum {
    USER {
        override fun getCreateSql(): String {
            return "CREATE Table If Not Exists users(username VARCHAR(45) NOT NULL," +
                    "email VARCHAR(45) NOT NULL," +
                    "gender VARCHAR(1) NOT NULL," +
                    "password VARCHAR(45) NOT NULL," +
                    "userType VARCHAR(1) NOT NULL," +
                    "createdTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "token VARCHAR(255)," +
                    "PRIMARY KEY (username))"
        }
    },
    USER_GAME_INFO {
        override fun getCreateSql(): String {
            return "Create Table If not exists userGameInfo(username VARCHAR(45) REFERENCES users(username)," +
                    "bottleFlipCount INT,level INT,starCount INT,myPackages VARCHAR(255),myBottles VARCHAR(255),currentAvatar VARCHAR(10)," +
                    "buyedAvatars VARCHAR(255),achievement VARCHAR(255) PRIMARY KEY (username))"
        }
    },
    LEVELS {
        override fun getCreateSql(): String {
            return "CREATE TABLE IF NOT EXISTS levels(" +
                    "id INT AUTO_INCREMENT,level INT, star INT, winnerCount INT DEFAULT 0,\n" +
                    "PRIMARY KEY (id))"
        }
    },
    PACKAGES {
        override fun getCreateSql(): String {
            return "Create Table If Not Exists packages(" +
                    "id INT AUTO_INCREMENT," +
                    "username VARCHAR(45)," +
                    "name VARCHAR(45)," +
                    "description VARCHAR(45)," +
                    "imageUrl VARCHAR(255)," +
                    "createdTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "numberOfLike INT," +
                    "numberOfUnlike INT," +
                    "numberOfDownload INT," +
                    "questions BLOB," +
                    "version INT," +
                    "updatedTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "packageCategory INT" +
                    "PRIMARY KEY (id))"
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
                    "createdTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "imageUrl BLOB NULL," +
                    "numberOfDownload INT DEFAULT 0," +
                    "numberOfLike INT DEFAULT 0," +
                    "numberOfUnlike INT DEFAULT 0," +
                    "updatedTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "version INT DEFAULT 0," +
                    "PRIMARY KEY (id))"
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
    },
    PACKAGE_CATEGORIES_TYPE{
        override fun getCreateSql(): String {
            return "CREATE TABLE IF NOT EXISTS packageCategoriesType(id INT AUTO_INCREMENT,type VARCHAR(45),PRIMARY KEY(id))"
        }
    };

    abstract fun getCreateSql(): String
}