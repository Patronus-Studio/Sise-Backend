package com.patronusstudio.BottleFlip.Service

import com.google.gson.Gson
import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.AchievementModel
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.AchievementEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AchievementService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun insertAchievement(model: AchievementModel):BaseResponse{
        val insertSql = "INSERT INTO achievement(award,title,content,imageUrl) VALUES(" +
                "${model.award}," +
                "\"${model.title}\"," +
                "\"${model.content}\"," +
                "\"${model.imageUrl}\")"
        val result = sqlRepo.setData(insertSql)
        return if(result is BaseSealed.Succes){
            SuccesResponse(status = HttpStatus.OK, data = result.data)
        }
        else ErrorResponse("Bir hatayla karşılaşıldı",HttpStatus.NOT_ACCEPTABLE)
    }

    fun getAllAchievements():BaseResponse{
        val getAchievementsSql = "Select * from achievement order by id asc"
        val result = sqlRepo.getDataForList(getAchievementsSql)
        return if(result is BaseSealed.Succes){
            SuccesResponse(status = HttpStatus.OK, data = result.data)
        }
        else ErrorResponse("Bir hatayla karşılaşıldı",HttpStatus.NOT_ACCEPTABLE)
    }

    fun getUserAwards(username:String):BaseResponse{
        val achSingleSql = "Select achievement from userGameInfo where username = \"$username\""
        val resultSingle = sqlRepo.getBasicData(achSingleSql)
        if(resultSingle is BaseSealed.Error){
            return  ErrorResponse(resultSingle.data.toString(),HttpStatus.NOT_ACCEPTABLE)
        }
        val achAllSql = "Select * from achievement"
        val resultAll = sqlRepo.getDataForList(achAllSql)
        if(resultAll is BaseSealed.Error){
            return  ErrorResponse(resultAll.data.toString(),HttpStatus.NOT_ACCEPTABLE)
        }
        val singleResult = (resultSingle as BaseSealed.Succes).data.toString()
        val awards = singleResult.split(";").filter {
            it != ";" && it.isBlank().not()
        }
        val allList = mutableListOf<AchievementModel>()
        ((resultAll as BaseSealed.Succes).data as List<*>).forEach {
            allList.add(Gson().fromJson(Gson().toJson(it),AchievementModel::class.java))
        }
        allList.forEach {
            val isContains = awards.contains(it.id.toString())
            if(isContains){
                it.userAchievementEnum = AchievementEnum.REACHED
            }
            else {
                it.userAchievementEnum = AchievementEnum.NOT_REACHED
            }
        }
        return SuccesResponse(null,HttpStatus.OK,allList)
    }


//    fun winAward(username: String,achievementId:Int):BaseResponse{
//        // müşterinin achievement idleri getirilecek
//        // bu id ile karşılaştırılacak
//        // eğer yoksa eklenecek ve onun karşılığındaki ödül profile eklenecek
//        //succes olursa müşteriye dönülecek
//    }

//    fun winAward(username: String,achievementId:Int):BaseResponse{
//        val getAchievementSql = "SELECT achievement from userGameInfo where username = \"$username\""
//        sqlRepo.getBasicData(sqlRepo)
//    }
}