package compsevice.ua.app.rest;

public class ApiUtils {
    //public static final String BASE_URL = "http://192.168.1.58:9090/";
    public static final String BASE_URL = "http://192.168.0.109:9090/";
    public static ContractInfoService service() {
        return RestClient.client(BASE_URL).create(ContractInfoService.class);
    }

}
