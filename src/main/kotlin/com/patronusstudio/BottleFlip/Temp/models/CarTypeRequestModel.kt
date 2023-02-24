package com.patronusstudio.BottleFlip.Temp.models

import com.fasterxml.jackson.annotation.JsonProperty

class CarTypeRequestModel {
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