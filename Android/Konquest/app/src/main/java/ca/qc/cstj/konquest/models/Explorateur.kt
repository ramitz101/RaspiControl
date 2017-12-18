package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose


data class Explorateur (@Expose var courriel : String?,
                        @Expose var pseudonyme : String?,
                        @Expose var motDePasse : String?,
                    @Expose var username : String?,
                   @Expose var password : String?,
                   var inox : Double?,
                   @Expose var location : String?,
                   var runes : String?,
                   var hrefExplorateur : String?) {

    constructor(json: Json) : this(json.obj().getString("courriel"),
                                   json.obj().getString("pseudonyme"),
                                   "",//json.obj().getString("motDePasse"),
                                   "",//json.obj().getString("username"),
                                   "",//json.obj().getString("password"),
                                   json.obj().getDouble("inox"),
                                   json.obj().getString("location"),
                                   json.obj().getString("runes"),
                                   json.obj().getString("hrefExplorateur"))
    fun toJson() : String =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this)
}
