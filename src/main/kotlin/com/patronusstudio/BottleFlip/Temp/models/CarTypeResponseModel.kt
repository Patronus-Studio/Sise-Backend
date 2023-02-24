package com.patronusstudio.BottleFlip.Temp.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

class CarTypeResponseModel : BaseModel() {
    @JsonProperty
    var id: Int? = null

    @JsonProperty
    var name: String? = null

    @JsonProperty
    var numberOfCapacity: Int? = null

    @JsonProperty
    var numberOfSuitcase: Int? = null

    @JsonProperty
    var imageUrl: String? = null
}