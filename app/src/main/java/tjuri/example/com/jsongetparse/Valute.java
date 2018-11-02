package tjuri.example.com.jsongetparse;

import java.util.HashMap;
import java.util.Map;

public class Valute {


    public String currencyCode;
    public String medianRate;

    public Valute(String currencyCode, String medianRate, String sellingRate, String buyingRate, Integer unitValue) {
        this.currencyCode = currencyCode;
        this.medianRate = medianRate;
        this.sellingRate = sellingRate;
        this.buyingRate = buyingRate;
        this.unitValue = unitValue;
    }

    public String sellingRate;
    public String buyingRate;
    public Integer unitValue;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
