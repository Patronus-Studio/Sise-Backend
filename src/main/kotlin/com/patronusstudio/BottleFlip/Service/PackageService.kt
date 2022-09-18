package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.PackageCategoriesTypeModel
import com.patronusstudio.BottleFlip.Model.PackageModel
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.CreateTableSqlEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PackageService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getPackagesFromUsername(username: String): BaseResponse {
        val sql = "Select * From packages Where username = \"$username\""
        val result = sqlRepo.getDataForList<PackageModel>(sql,PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromPackageName(packageName:String):BaseResponse{
        val sql = "Select * From packages Where name = \"$packageName\""
        val result = sqlRepo.getDataForList<PackageModel>(sql,PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromMostLike():BaseResponse{
        val sql = "Select * from packages order by numberOfLike desc"
        val result = sqlRepo.getDataForList<PackageModel>(sql,PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getPackagesFromMostDownload():BaseResponse{
        val sql = "Select * from packages order by numberOfDownload desc"
        val result = sqlRepo.getDataForList<PackageModel>(sql,PackageModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun insertPackage(model: PackageModel): BaseResponse {
        val sql = CreateTableSqlEnum.PACKAGES.getCreateSql()
        val packageTable = sqlRepo.setData(sql)
        return if (packageTable is BaseSealed.Succes) {
            val insertSql = CreateTableSqlEnum.PACKAGES.getDefaultInsertSql(
                model.username ?: "", model.name ?: "", model.description ?: "", model.imageUrl ?: "",
                model.numberOfLike.toString(), model.numberOfUnlike.toString(),
                model.numberOfDownload.toString(), model.questions ?: "",
                model.version.toString(), LocalDateTime.now().toString()
            )
            val insertResult = sqlRepo.setData(insertSql)
            if (insertResult is BaseSealed.Succes) {
                SuccesResponse("Paket eklendi", HttpStatus.OK)
            } else {
                insertResult as BaseSealed.Error
                ErrorResponse(insertResult.data.toString(), HttpStatus.NOT_ACCEPTABLE)
            }
        } else {
            ErrorResponse("Db oluşturulurken bir hata oluştur", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getAllPackageCategories():BaseResponse{
        val sql = "Select * From packageCategoriesType order by id asc"
        val categoryModel = sqlRepo.getDataForList(sql,PackageCategoriesTypeModel::class.java)
        if(categoryModel is BaseSealed.Error){
            return ErrorResponse("Paket kategorileri çekilirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
        return SuccesResponse(data = (categoryModel as BaseSealed.Succes).data, status = HttpStatus.OK)
    }

    fun addPackageCategory(type:String):BaseResponse{
        val sql = "INSERT INTO packageCategoriesType(type) VALUES (\"$type\")"
        val result = sqlRepo.setData(sql)
        if(result is BaseSealed.Error){
            return ErrorResponse("Kategori eklenirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
        return SuccesResponse("Ekleme başarılı", HttpStatus.OK)
    }

    fun updatePackageCategory(packageCategoryModel:PackageCategoriesTypeModel):BaseResponse{
        val sql = "UPDATE packageCategoriesType SET type = \"${packageCategoryModel.type}\" WHERE id = ${packageCategoryModel.id} "
        val result = sqlRepo.setData(sql)
        if(result is BaseSealed.Error){
            return ErrorResponse("Kategori düzenlenirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
        return SuccesResponse("Paket kategorisi düzenlendi.", HttpStatus.OK)
    }
}