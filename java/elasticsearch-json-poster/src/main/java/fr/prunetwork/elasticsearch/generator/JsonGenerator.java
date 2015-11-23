package fr.prunetwork.elasticsearch.generator;

import fr.prunetwork.elasticsearch.generator.status.City;
import fr.prunetwork.elasticsearch.generator.status.Status;
import fr.prunetwork.elasticsearch.generator.status.Warning;
import fr.prunetwork.random.RandomEnum;
import org.jetbrains.annotations.NotNull;
import org.json.JSONStringer;

import static fr.prunetwork.random.RandomToolBox.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class JsonGenerator {

    private final RandomEnum<City> city = new RandomEnum<>(City.class);
    private final RandomEnum<Status> status = new RandomEnum<>(Status.class);
    private final RandomEnum<Warning> warning = new RandomEnum<>(Warning.class);
    @NotNull
    private final String indexName;

    public JsonGenerator(@NotNull final String indexName) {
        this.indexName = indexName;
    }

    public String getHeader() {
        return String.format(
                "{\"create\":{\"_index\":\"%s\",\"_type\":\"json\",\"_id\":\"%s\"}}",
                indexName,
                getId(25)
        );
    }

    public String getBody() {
        return new JSONStringer()
                .object()
                .key("dataset").value("no7")
                .key("name").value("device" + getId(2))
                .key("site").value("site" + getId(2))
                .key("value.engine.voltage").value(nextDouble(0, 24))
                .key("value.battery.tension").value(nextDouble(22, 24))
                .key("gps.longitude").value(nextDouble(0, 90))
                .key("gps.latitude").value(nextDouble(0, 90))
                .key("gps.altitude").value(nextDouble(100, 120))
                .key("is_ok").value(nextBoolean())
                .key("id").value(getId(25))
                .key("city").value(city.random())
                .key("status").value(status.random())
                .key("warning").value(getSuccessiveAndRandomBoolean(4) ? warning.random() : "Ok")
                .endObject()
                .toString();
    }
}
