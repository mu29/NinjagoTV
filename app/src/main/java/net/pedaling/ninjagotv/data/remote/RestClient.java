package net.pedaling.ninjagotv.data.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.pedaling.ninjagotv.data.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class RestClient {

    public static String BASE_URL = "http://10.0.1.93:9293";
    private static NinjaGoService ninjagoService;

    public static void createService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ninjagoService = retrofit.create(NinjaGoService.class);
    }

    public static NinjaGoService getService() {
        if (ninjagoService == null)
            createService();

        return ninjagoService;
    }

    public interface NinjaGoService {

        // 동영상 목록 받기
        @GET("/videos")
        Call<List<Video>> getVideos();

    }

}
