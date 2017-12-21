package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

data class Exploration (@Expose var dateExploration : String?,
                              @Expose var destination : String?,
                              @Expose var depart : String?,
                              @Expose var runes : String?,
                              @Expose var unit : String?,
                            @Expose var href : String): Item()


{

    override fun getUrl():String {
        return href
    }

    constructor(json: Json) : this(json.obj().getString("dateExploration"),
                                        json.obj().getString("destination"),
                                        json.obj().getString("depart"),
                                       json.obj().getString("runes"),
                                        json.obj().getString("unit"),
            json.obj().getString("href")
    )

    fun toJson() : String =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this)
}