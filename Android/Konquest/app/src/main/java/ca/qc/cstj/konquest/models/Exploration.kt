package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

/**
 * Created by Administrateur on 2017-12-20.
 */
class Exploration (@Expose var dateExploration : String?,
                              @Expose var destination : String?,
                              @Expose var runes : String?,
                              @Expose var unit : String?

) {



    constructor(json: Json) : this(json.obj().getString("dateExploration"),
            json.obj().getString("destination"),
           json.obj().getString("runes"),
            json.obj().getString("unit")


    )
    fun toJson() : String =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this)
}