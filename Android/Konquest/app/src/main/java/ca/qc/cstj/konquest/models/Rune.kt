package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

data class Rune(@Expose var air : Json?,
                @Expose var darkness : Json?,
                @Expose var earth : Json?,
                @Expose var energy : Json?,
                @Expose var fire : Json?,
                @Expose var life : Json?,
                @Expose var light : Json?,
                @Expose var logic : Json?,
                @Expose var music : Json?,
                @Expose var space : Json?,
                @Expose var toxic : Json?,
                @Expose var water : Json?
                ) {
   /* constructor(json: Json) : this(
                                    )
*/
    fun toJson() : String =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this)
}