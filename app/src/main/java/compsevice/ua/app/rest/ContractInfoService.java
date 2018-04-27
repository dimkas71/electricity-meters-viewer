package compsevice.ua.app.rest;

import java.util.List;

import compsevice.ua.app.model.ContractInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContractInfoService {

    @GET("/contractinfo/{number}")
    Call<List<ContractInfo>> contractInfos(@Path("number") String number);

}
