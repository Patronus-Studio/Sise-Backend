package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.PackageCategoriesTypeModel
import com.patronusstudio.BottleFlip.Model.PackageModel
import com.patronusstudio.BottleFlip.Model.SuccesResponse
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
        val result = sqlRepo.getDataForList<PackageModel>(sql, PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromPackageName(packageName: String): BaseResponse {
        val sql = "Select * From packages Where name = \"$packageName\""
        val result = sqlRepo.getDataForList<PackageModel>(sql, PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromMostLike(): BaseResponse {
        val sql = "Select * from packages order by numberOfLike desc"
        val result = sqlRepo.getDataForList<PackageModel>(sql, PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromMostDownload(): BaseResponse {
        val sql = "Select * from packages order by numberOfDownload desc"
        val result = sqlRepo.getDataForList<PackageModel>(sql, PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun insertPackage(model: PackageModel): BaseResponse {
        val insertSql = "INSERT INTO packages(" +
                "username,name,description,imageUrl,numberOfLike,numberOfUnlike,numberOfDownload," +
                "questions,version,updatedTime,packageCategory) VALUES(" +
                "\"${model.username}\",\"${model.name}\",\"${model.description}\",\"${model.imageUrl}\"," +
                "${model.numberOfLike},${model.numberOfUnlike},${model.numberOfDownload},\"${model.questions}\"" +
                ",${model.version},\"${Timestamp(System.currentTimeMillis())}\",${model.packageCategory})"

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
        val result = sqlRepo.getDataForList<PackageModel>(sql, PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun updatePackageNumberOfDownload(packageId:Int):BaseResponse{
        val sql = "Select numberOfDownload From packages Where id = $packageId"
        val result = sqlRepo.getBasicData(sql)
        if(result is BaseSealed.Succes){
            val numberOfDownload = (result.data as String).toInt() + 1
            val updateSql = "Update packages SET numberOfDownload = $numberOfDownload where id = $packageId"
            sqlRepo.setData(updateSql)
            return SuccesResponse(status = HttpStatus.OK)
        }
        else return ErrorResponse(status = HttpStatus.NOT_ACCEPTABLE, message = "İndirme sayısı güncellenemedi")
    }
}