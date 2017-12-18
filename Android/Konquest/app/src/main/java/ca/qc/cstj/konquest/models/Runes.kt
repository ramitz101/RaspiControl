package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Created by trist on 18/12/2017.
 */
class Runes(jsonObject: Json) {
    var air : String = jsonObject.obj().getString("air")
    var darkness : String = jsonObject.obj().getString("darkness")
    var earth : String = jsonObject.obj().getString("earth")
    var energy : String = jsonObject.obj().getString("energy")
    var fire : String = jsonObject.obj().getString("fire")
    var life : String = jsonObject.obj().getString("life")
    var light : String = jsonObject.obj().getString("light")
    var logic : String = jsonObject.obj().getString("logic")
    var music : String = jsonObject.obj().getString("music")
    var space : String = jsonObject.obj().getString("space")
    var toxic : String = jsonObject.obj().getString("toxic")
    var water : String = jsonObject.obj().getString("water")
}