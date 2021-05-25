package ba.etf.rma21.projekat

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("/student/{id}/kviz/{kid}")
    fun beginKviz(@Path("acHash") studentId: String, @Path("kvizID") kvizId: Int){(

    ): Response<GetMoviesResponse>
}