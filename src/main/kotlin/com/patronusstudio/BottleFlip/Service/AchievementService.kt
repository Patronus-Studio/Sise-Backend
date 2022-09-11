package com.patronusstudio.BottleFlip.Service

import com.google.gson.Gson
import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.*
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.AchievementEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AchievementService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun insertAchievement(model: AchievementModel): BaseResponse {
        val insertSql = "INSERT INTO achievement(award,title,content,imageUrl) VALUES(" +
                "${model.award}," +
                "\"${model.title}\"," +
                "\"${model.content}\"," +
                "\"${model.imageUrl}\")"
        val result = sqlRepo.setData(insertSql)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(status = HttpStatus.OK, data = result.data)
        } else ErrorResponse("Bir hatayla karşılaşıldı", HttpStatus.NOT_ACCEPTABLE)
    }

    fun getAllAchievements(): BaseResponse {
        val getAchievementsSql = "Select * from achievement order by id asc"
        val result = sqlRepo.getDataForList<AchievementModel>(getAchievementsSql,AchievementModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(status = HttpStatus.OK, data = result.data)
        } else ErrorResponse("Bir hatayla karşılaşıldı", HttpStatus.NOT_ACCEPTABLE)
    }

    fun getUserAwards(username: String): BaseResponse {
        val achSingleSql = "Select achievement from userGameInfo where username = \"$username\""
        val resultSingle = sqlRepo.getBasicData(achSingleSql)
        if (resultSingle is BaseSealed.Error) {
            return ErrorResponse(resultSingle.data.toString(), HttpStatus.NOT_ACCEPTABLE)
        }
        val achAllSql = "Select * from achievement"
        val resultAll = sqlRepo.getDataForList<AchievementModel>(achAllSql,AchievementModel::class.java)
        if (resultAll is BaseSealed.Error) {
            return ErrorResponse(resultAll.data.toString(), HttpStatus.NOT_ACCEPTABLE)
        }
        val singleResult = (resultSingle as BaseSealed.Succes).data.toString()
        val awards = singleResult.split(";").filter {
            it != ";" && it.isBlank().not()
        }
        val allList = mutableListOf<AchievementModel>()
        ((resultAll as BaseSealed.Succes).data as List<*>).forEach {
            allList.add(Gson().fromJson(Gson().toJson(it), AchievementModel::class.java))
        }
        allList.forEach {
            val isContains = awards.contains(it.id.toString())
            if (isContains) {
                it.userAchievementEnum = AchievementEnum.REACHED
            } else {
                it.userAchievementEnum = AchievementEnum.NOT_REACHED
            }
        }
        return SuccesResponse(null, HttpStatus.OK, allList)
    }


//    fun winAward(username: String,achievementId:Int):BaseResponse{
//        // müşterinin achievement idleri getirilecek
//        // bu id ile karşılaştırılacak
//        // eğer yoksa eklenecek ve onun karşılığındaki ödül profile eklenecek
//        //succes olursa müşteriye dönülecek
//    }

    fun winAward(username: String, winningAchievementId: Int): BaseResponse {
        val getAchievementSql = "SELECT * from userGameInfo where username = \"$username\""
        val getAchievementResult = sqlRepo.getDataForObject(getAchievementSql, UserGameInfo::class.java)
        if (getAchievementResult is BaseSealed.Error) {
            return ErrorResponse(getAchievementResult.data.toString(), HttpStatus.NOT_ACCEPTABLE)
        }
        val userModel = ((getAchievementResult as BaseSealed.Succes).data as UserGameInfo)
        val userAchievements = userModel.achievement?.split(";")?.filter {
                it != ";" && it.isBlank().not()
            }
        val hasUserAchievement = userAchievements?.find {
            it == winningAchievementId.toString()
        }
        if (hasUserAchievement != null)
            return ErrorResponse("Daha önce bu ödülü kazandınız.", HttpStatus.NOT_ACCEPTABLE)

        val getAllAchievementsSql = "Select * from achievement order by id asc"
        val result = sqlRepo.getDataForList<AchievementModel>(getAllAchievementsSql,AchievementModel::class.java)
        if (result is BaseSealed.Error) {
            return ErrorResponse(result.data.toString(), HttpStatus.NOT_ACCEPTABLE)
        }
        val allAchievement = (result as BaseSealed.Succes).data as List<AchievementModel>
        val findedAchievement: AchievementModel? = allAchievement.firstOrNull {
            it.id == winningAchievementId
        }
        if(findedAchievement == null){
            return ErrorResponse("Böyle bir ödül bulunamadı.", HttpStatus.NOT_ACCEPTABLE)
        }

        val getLevelsResult = sqlRepo.getDataForList("SELECT * from levels",LevelsModel::class.java)
        if(getLevelsResult is BaseSealed.Error)
            ErrorResponse("Böyle bir ödül bulunamadı.", HttpStatus.NOT_ACCEPTABLE)
        val levels = (getLevelsResult as BaseSealed.Succes).data as List<LevelsModel>
        val findedLevels = levels.find {
            ((userModel.level ?: 0) + 1) == it.level
        } ?: return ErrorResponse("Böyle bir level bulunamadı.", HttpStatus.NOT_ACCEPTABLE)

        //ödül idsini müşteriye ekleyeceğiz
        val newAchievement = if(userModel.achievement != null) userModel.achievement+ "$winningAchievementId;" else "$winningAchievementId;"
        //ödüldeki yıldızı müşteriye ekleyecez
        var newStarCount = (userModel.starCount ?: 0) + findedAchievement.award
        //müşteri leveli
        var newLevel = userModel.level

        //alacağı ödül level almasına yardımcı oldu mu?
        if(newStarCount > findedLevels.star){
            //müşteri yeni level alacak
            newLevel = findedLevels.level
            newStarCount += findedLevels.star
        }
        val insertAchievementSql = "UPDATE userGameInfo SET " +
                "achievement = \"$newAchievement\", " +
                "starCount = $newStarCount ," +
                "level = $newLevel " +
                "WHERE username = \"$username\""
        val insertSqlResult = sqlRepo.setData(insertAchievementSql)
        return if (insertSqlResult is BaseSealed.Succes) {
            val winAwardModel = WinAwardModel(newLevel ?: 0,newStarCount,newAchievement)
            SuccesResponse(status = HttpStatus.OK, data = winAwardModel)
        } else ErrorResponse("Bir hatayla karşılaşıldı", HttpStatus.NOT_ACCEPTABLE)
    }
}