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
public class CriticalEventSubscriber implements StatementSubscriber<TemperatureEvent> {

    /**
     * Used as the minimum starting threshold for a critical event.
     */
    private static final String CRITICAL_EVENT_THRESHOLD = "100";
    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */
    private static final String CRITICAL_EVENT_MULTIPLIER = "1.5";
    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(CriticalEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String getStatement() {

        // Example using 'Match Recognise' syntax.
        @NotNull final String crtiticalEventExpression = "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2, C as temp3, D as temp4 "
                + "       pattern (A B C D) "
                + "       define "
                + "               A as A.temperature > " + CRITICAL_EVENT_THRESHOLD + ", "
                + "               B as (A.temperature < B.temperature), "
                + "               C as (B.temperature < C.temperature), "
                + "               D as (C.temperature < D.temperature) and D.temperature > (A.temperature * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";

        return crtiticalEventExpression;
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    @Override
    public void update(Map<String, TemperatureEvent> eventMap) {

        // 1st Temperature in the Critical Sequence
        TemperatureEvent temp1 = eventMap.get("temp1");
        // 2nd Temperature in the Critical Sequence
        TemperatureEvent temp2 = eventMap.get("temp2");
        // 3rd Temperature in the Critical Sequence
        TemperatureEvent temp3 = eventMap.get("temp3");
        // 4th Temperature in the Critical Sequence
        TemperatureEvent temp4 = eventMap.get("temp4");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : CRITICAL EVENT DETECTED! ");
        sb.append("\n* " + temp1 + " > " + temp2 + " > " + temp3 + " > " + temp4);
        sb.append("\n***************************************");

        LOG.debug(sb.toString());
    }
}
