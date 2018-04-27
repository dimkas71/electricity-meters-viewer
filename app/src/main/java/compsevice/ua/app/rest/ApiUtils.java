package compsevice.ua.app.rest;

public class ApiUtils {
    public static final String BASE_URL = "http://localhost:9090/";

    public static ContractInfoService service() {
        return RestClient.client(BASE_URL).create(ContractInfoService.class);
    }

}
