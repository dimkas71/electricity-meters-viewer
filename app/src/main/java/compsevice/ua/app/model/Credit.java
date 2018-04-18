
package compsevice.ua.app.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "service",
        "counter",
        "credit"
})
public class Credit {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("service")
    private String service;
    @JsonProperty("counter")
    private String counter;
    @JsonProperty("credit")
    private Double credit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("service")
    public String getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    @JsonProperty("counter")
    public String getCounter() {
        return counter;
    }

    @JsonProperty("counter")
    public void setCounter(String counter) {
        this.counter = counter;
    }

    @JsonProperty("credit")
    public Double getCredit() {
        return credit;
    }

    @JsonProperty("credit")
    public void setCredit(Double credit) {
        this.credit = credit;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}