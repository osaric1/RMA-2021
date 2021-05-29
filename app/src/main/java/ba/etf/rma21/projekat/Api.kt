package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("/student/{id}/kviz/{kid}")
    suspend fun zapocniKviz(@Path("id") studentId: String, @Path("kid") kvizId: Int): Response<KvizTaken>

    @GET("/student/{id}/kviztaken")
    suspend fun getPocetiKvizovi(@Path("id") studentId: String): Response<List<KvizTaken>>

    @GET("/kviz/{id}/pitanja")
    suspend fun getPitanja(@Path("id") kvizId: Int): Response<List<Pitanje>>

    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun getOdgovoriKviz(@Path("id") studentId: String, @Path("ktid") kvizPokusajId: Int): Response<List<Odgovor>>

    @POST("/student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun postaviOdgovorKviz(@Path("id") studentId: String, @Path("ktid")kvizPokusajId: Int, @Body requestBody: OdgovorKviz): Response<OdgovorKviz>

    @GET("/kviz")
    suspend fun getAll(): Response<List<Kviz>>

    @GET("/kviz/{id}")
    suspend fun getById(@Path("id") kvizId: Int): Response<Kviz>

    @GET("/predmet")
    suspend fun getPredmeti(): Response<List<Predmet>>

    @GET("/grupa")
    suspend fun getGrupe(): Response<List<Grupa>>

    @GET("/predmet/{id}/grupa")
    suspend fun getGrupeZaPredmet(@Path("id") idPredmeta: Int): Response<List<Grupa>>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiUGrupu(@Path("gid") groupId: Int, @Path("id") studentId: String)

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") studentId: String): Response<List<Grupa>>

    @GET("/grupa/{id}/kvizovi")
    suspend fun getUpisani(@Path("id") grupaId: Int) : Response<List<Kviz>>

    @GET("/kviz/{id}/grupa")
    suspend fun getGrupeZaKviz(@Path("id") kvizId: Int): Response<List<Grupa>>

    @GET("/predmet/{id}")
    suspend fun getPredmetById(@Path("id") predmetId: Int): Response<Predmet>
}