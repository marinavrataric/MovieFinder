package com.example.moviefinder.favorites

class Movie_Fav {
    var id: Int = 0
    var titleFav : String = ""
    var releaseFav : String = ""
    var posterPathFav : String = ""

    constructor(movieId: Int,titleFav:String, releaseFav:String , posterPath:String){
        this.id = movieId
        this.titleFav = titleFav
        this.releaseFav = releaseFav
        this.posterPathFav = posterPath
    }
    constructor(){
        this.titleFav = titleFav
        this.releaseFav = releaseFav
        this.posterPathFav = posterPathFav
    }
}

