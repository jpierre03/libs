package com.cor.cep.subscriber;

import com.cor.cep.event.TemperatureEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class WarningEventSubscriber implements StatementSubscriber<TemperatureEvent> {

    /**
     * If 2 consecutive temperature events are greater than this - issue a warning
     */
    private static final String WARNING_EVENT_THRESHOLD = "400";
    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(WarningEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String getStatement() {

        // Example using 'Match Recognise' syntax.
        @NotNull final String warningEventExpression = "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2 "
                + "       pattern (A B) "
                + "       define "
                + "               A as A.temperature > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.temperature > " + WARNING_EVENT_THRESHOLD + ")";

        return warningEventExpression;
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    @Override
    public void update(@NotNull final Map<String, TemperatureEvent> eventMap) {

        // 1st Temperature in the Warning Sequence
        TemperatureEvent temp1 = eventMap.get("temp1");
        // 2nd Temperature in the Warning Sequence
        TemperatureEvent temp2 = eventMap.get("temp2");

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- [WARNING] : TEMPERATURE SPIKE DETECTED = " + temp1 + "," + temp2);
        sb.append("\n--------------------------------------------------");

        LOG.debug(sb.toString());
    }
}
