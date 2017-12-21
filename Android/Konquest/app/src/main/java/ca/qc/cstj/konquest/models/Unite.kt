package ca.qc.cstj.konquest.models

import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.annotations.Expose

class Unite(@Expose var name:String?,
            @Expose var life:String?,
            @Expose var imageURL:String?,
            @Expose var href:String,
            @Expose var speed:String?
            ): Item() {

    override fun getUrl():String {
        return href
    }




    constructor(json: Json) : this(json.obj().getString("name"),
            json.obj().getString("life"),
            json.obj().getString("imageURL"),
            json.obj().getString("href"),
            json.obj().getString("speed")


    )

           /* json.obj().getString("destination"),
            json.obj().getString("depart"),
            json.obj().getString("runes"),
            json.obj().getString("unit"),
            json.obj().getString("href")*/



    /*
    //var name : String = jsonObject.obj().getString("name")

    var life  : String = jsonObject.obj().getString("life")
    //var speed : String = jsonObject.obj().getString("speed")
    var imageURL : String = jsonObject.obj().getString("imageURL")
    //var affinity : String = jsonObject.obj().getString("affinity")
    // TODO: RUNES RESTE A DECIDER !
    //var set : String = jsonObject.obj().getString("set")
    //var number : String = jsonObject.obj().getString("number")
    // TODO: KERNEL
    var href : String = jsonObject.obj().getString("href")*/
}