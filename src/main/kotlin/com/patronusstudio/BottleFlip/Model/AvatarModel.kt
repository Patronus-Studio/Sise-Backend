package com.patronusstudio.BottleFlip.Model

import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.enums.BuyedStatu

data class AvatarModel(
    val id: Int? = 0,
    val imageUrl: String,
    val isSelectable: Int,
    val starCount: Int,
    var buyedStatu: BuyedStatu = BuyedStatu.NON_BUYED
) : BaseModel()
