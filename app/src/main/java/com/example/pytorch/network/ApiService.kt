import com.example.pytorch.network.PredictionResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("/result")
    fun getPrediction(): Call<PredictionResponse>

    @Multipart
    @POST("/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<JsonObject> // Return type changed to JsonObject
}
