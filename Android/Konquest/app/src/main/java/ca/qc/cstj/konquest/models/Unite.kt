package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json

class Unite(jsonObject: Json) {
    var name : String = jsonObject.obj().getString("name")
    var life : String = jsonObject.obj().getString("life")
    var speed : String = jsonObject.obj().getString("speed")
    var imageURL : String = jsonObject.obj().getString("imageURL")
    var affinity : String = jsonObject.obj().getString("affinity")
    // TODO: RUNES RESTE A DECIDER !
    var set : String = jsonObject.obj().getString("set")
    var number : String = jsonObject.obj().getString("number")
    // TODO: KERNEL
    var href : String = jsonObject.obj().getString("href")
}