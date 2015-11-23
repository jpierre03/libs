package fr.prunetwork;

import fr.prunetwork.random.RandomEnum;
import fr.prunetwork.status.Anomalie;
import fr.prunetwork.status.Etat;
import fr.prunetwork.status.Ville;
import org.json.JSONStringer;

import static fr.prunetwork.random.RandomToolBox.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class FakeLaveuseDataGenerator {

    private final RandomEnum<Ville> ville = new RandomEnum<>(Ville.class);
    private final RandomEnum<Etat> etat = new RandomEnum<>(Etat.class);
    private final RandomEnum<Anomalie> anomalie = new RandomEnum<>(Anomalie.class);

    public FakeLaveuseDataGenerator() {
    }

    public String getHeader() {
        return String.format("{\"create\":{\"_index\":\"sandbox\",\"_type\":\"json\",\"_id\":\"%s\"}}", getId(25));
    }

    public String getBody() {
        return new JSONStringer()
                .object()
                .key("dataset").value("no7")
                .key("name").value("laveuse" + getId(2))
                .key("site").value("site" + getId(2))
                .key("mesure.moteur.tension").value(nextDouble(0, 24))
                .key("mesure.brosse.tension").value(nextDouble(0, 24))
                .key("mesure.batterie.tension").value(nextDouble(22, 24))
                .key("mesure.batterie.courant").value(nextDouble(0, 40))
                .key("gps.longitude").value(nextDouble(0, 90))
                .key("gps.latitude").value(nextDouble(0, 90))
                .key("gps.altitude").value(nextDouble(100, 120))
                .key("is_ok").value(nextBoolean())
                .key("id").value(getId(25))
                .key("ville").value(ville.random())
                .key("etat").value(etat.random())
                .key("anomalie").value(getSuccessiveAndRandomBoolean(4) ? anomalie.random() : "Ok")
                .endObject()
                .toString();
    }
}
