package compsevice.ua.app.model;

import java.util.HashMap;
import java.util.List;
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
        "number",
        "owner",
        "sectorNumber",
        "counters",
        "credits"
})
public class ContractInfo {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("number")
    private String number;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("sectorNumber")
    private Integer sectorNumber;
    @JsonProperty("counters")
    private List<Counter> counters = null;
    @JsonProperty("credits")
    private List<Credit> credits = null;
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

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonProperty("sectorNumber")
    public Integer getSectorNumber() {
        return sectorNumber;
    }

    @JsonProperty("sectorNumber")
    public void setSectorNumber(Integer sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    @JsonProperty("counters")
    public List<Counter> getCounters() {
        return counters;
    }

    @JsonProperty("counters")
    public void setCounters(List<Counter> counters) {
        this.counters = counters;
    }

    @JsonProperty("credits")
    public List<Credit> getCredits() {
        return credits;
    }

    @JsonProperty("credits")
    public void setCredits(List<Credit> credits) {
        this.credits = credits;
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