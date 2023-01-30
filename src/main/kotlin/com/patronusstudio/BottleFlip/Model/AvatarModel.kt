package com.patronusstudio.BottleFlip.Model

import com.patronusstudio.BottleFlip.Base.BaseModel

data class AvatarModel(
    val id: Int? = 0,
    val imageUrl: String,
    val isSelectable: Int
) : BaseModel()
