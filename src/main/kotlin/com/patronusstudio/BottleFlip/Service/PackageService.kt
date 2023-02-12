package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.*
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class PackageService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getPackagesFromUsername(username: String): BaseResponse {
        val sql = "Select * From packages Where username = \"$username\""
        val result = sqlRepo.getDataForListWithReturnList(sql, PackageModel::class)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromPackageName(packageName: String): BaseResponse {
        val sql = "Select * From packages Where name = \"$packageName\""
        val result = sqlRepo.getDataForObject(sql, PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(
                data = (result.data as PackageModel).parsePackageResponseModel(),
                status = HttpStatus.OK,
                message = null
            )
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromMostLike(): BaseResponse {
        val sql = "Select * from packages order by numberOfLike desc"
        val result = sqlRepo.getDataForListWithReturnList(sql, PackageModel::class)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = (result.data as List<PackageModel>).parsePackageResponseModel(), status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }    }

    fun getPackagesFromMostDownload(): BaseResponse {
        val sql = "Select * from packages order by numberOfDownload desc"
        val result = sqlRepo.getDataForListWithReturnList(sql, PackageModel::class)

        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = (result.data as List<PackageModel>).parsePackageResponseModel(), status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun insertPackage(model: PackageModel): BaseResponse {
        val insertSql = "INSERT INTO packages(" +
                "username,name,description,imageUrl,numberOfLike,numberOfUnlike,numberOfDownload," +
                "questions,version,updatedTime,packageCategory,correct_answers) VALUES(" +
                "\"${model.username}\",\"${model.name}\",\"${model.description}\",\"${model.imageUrl}\"," +
                "${model.numberOfLike},${model.numberOfUnlike},${model.numberOfDownload},\"${model.questions}\"" +
                ",${model.version},\"${Timestamp(System.currentTimeMillis())}\",${model.packageCategory},\"${model.correct_answers}\")"

        val insertResult = sqlRepo.setData(insertSql)
        return if (insertResult is BaseSealed.Succes) {
            SuccesResponse("Paket eklendi", HttpStatus.OK)
        } else {
            insertResult as BaseSealed.Error
            ErrorResponse(insertResult.data.toString(), HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getAllPackageCategories(): BaseResponse {
        val sql = "Select * From packageCategoriesType order by id asc"
        val categoryModel = sqlRepo.getDataForList(sql, PackageCategoriesTypeModel::class.java)
        if (categoryModel is BaseSealed.Error) {
            return ErrorResponse("Paket kategorileri çekilirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
        return SuccesResponse(data = (categoryModel as BaseSealed.Succes).data, status = HttpStatus.OK)
    }

    fun addPackageCategory(model: PackageCategoriesTypeModel): BaseResponse {
        val sql = "INSERT INTO packageCategoriesType(name,activeTextColor,activeBtnColor,passiveBtnColor," +
                "passiveTextColor,isSelected) VALUES (\"${model.name}\",\"${model.activeTextColor}\"," +
                "\"${model.activeBtnColor}\",\"${model.passiveBtnColor}\",\"${model.passiveTextColor}\"," +
                "\"${model.isSelected}\")"
        val result = sqlRepo.setData(sql)
        if (result is BaseSealed.Error) {
            return ErrorResponse("Kategori eklenirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
        return SuccesResponse("Ekleme başarılı", HttpStatus.OK)
    }

    fun updatePackageCategory(packageCategoryModel: PackageCategoriesTypeModel): BaseResponse {
        val sql =
            "UPDATE packageCategoriesType SET name = \"${packageCategoryModel.name}\"," +
                    "activeBtnColor = \"${packageCategoryModel.activeBtnColor}\"," +
                    "activeTextColor = \"${packageCategoryModel.activeTextColor}\"," +
                    "passiveBtnColor = \"${packageCategoryModel.passiveBtnColor}\"," +
                    "passiveTextColor = \"${packageCategoryModel.passiveTextColor}\"," +
                    "isSelected = \"${packageCategoryModel.isSelected}\"" +
                    "WHERE id = ${packageCategoryModel.id} "
        val result = sqlRepo.setData(sql)
        if (result is BaseSealed.Error) {
            return ErrorResponse("Kategori düzenlenirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
        return SuccesResponse("Paket kategorisi düzenlendi.", HttpStatus.OK)
    }

    fun getPackagesFromPackageCategories(packageCategory: Int): BaseResponse {
        val sql = "Select * From packages Where packageCategory = $packageCategory"
        val result = sqlRepo.getDataForListWithReturnList(sql, PackageModel::class)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = (result.data as List<PackageModel>).parsePackageResponseModel(), status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun updatePackageNumberOfDownload(packageId: Int): BaseResponse {
        val sql = "Select numberOfDownload From packages Where id = $packageId"
        val result = sqlRepo.getBasicData(sql)
        if (result is BaseSealed.Succes) {
            val numberOfDownload = (result.data as String).toInt() + 1
            val updateSql = "Update packages SET numberOfDownload = $numberOfDownload where id = $packageId"
            sqlRepo.setData(updateSql)
            return SuccesResponse(status = HttpStatus.OK)
        } else return ErrorResponse(status = HttpStatus.NOT_ACCEPTABLE, message = "İndirme sayısı güncellenemedi")
    }

    fun getAllPackages(): BaseResponse {
        val sql = "Select * from packages"
        val result= sqlRepo.getDataForListWithReturnList(sql, PackageModel::class)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = (result.data as List<PackageModel>).parsePackageResponseModel(), status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }
}

fun List<PackageModel>.parsePackageResponseModel(): List<PackageResponseModel> {
    val returnList = mutableListOf<PackageResponseModel>()
    this.forEach {
        val questionList = mutableListOf<QuestionModel>()
        val answers = it.correct_answers?.parseToQuestions()
        it.questions?.parseToQuestions()?.forEachIndexed { index: Int, question: String ->
            questionList.add(QuestionModel(question, answers?.get(index), null))
        }
        val packageResponseModel = PackageResponseModel().apply {
            this.id = it.id
            this.createdTime = it.createdTime
            this.username = it.username
            this.name = it.name
            this.description = it.description
            this.imageUrl = it.imageUrl
            this.numberOfLike = it.numberOfLike
            this.numberOfUnlike = it.numberOfUnlike
            this.numberOfDownload = it.numberOfDownload
            this.version = it.version
            this.updatedTime = it.updatedTime
            this.packageCategory = it.packageCategory
            this.questionList = questionList
        }
        returnList.add(packageResponseModel)
    }
    return returnList
}

fun PackageModel.parsePackageResponseModel(): PackageResponseModel {
    val questionList = mutableListOf<QuestionModel>()
    val answers = this.correct_answers?.parseToQuestions()
    this.questions?.parseToQuestions()?.forEachIndexed { index: Int, question: String ->
        questionList.add(QuestionModel(question, answers?.get(index), null))
    }
    val packageResponseModel = PackageResponseModel(questionList = listOf())
    packageResponseModel.id = this.id
    packageResponseModel.createdTime = this.createdTime
    packageResponseModel.username = this.username
    packageResponseModel.name = this.name
    packageResponseModel.description = this.description
    packageResponseModel.imageUrl = this.imageUrl
    packageResponseModel.numberOfLike = this.numberOfLike
    packageResponseModel.numberOfUnlike = this.numberOfUnlike
    packageResponseModel.numberOfDownload = this.numberOfDownload
    packageResponseModel.version = this.version
    packageResponseModel.updatedTime = this.updatedTime
    packageResponseModel.packageCategory = this.packageCategory
    packageResponseModel.questionList = questionList
    return packageResponseModel
}

fun String.parseToQuestions(): List<String> {
    val list = mutableListOf<String>()
    this.split(";").forEach {
        if (it != ";" && it.isNotEmpty() && it.isNotBlank()) list.add(it)
    }
    return list
}