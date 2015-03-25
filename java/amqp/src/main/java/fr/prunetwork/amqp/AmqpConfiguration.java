package fr.prunetwork.amqp;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2015-03-19
 */
public class AmqpConfiguration {

    @NotNull
    private final String uri;
    @NotNull
    private final String exchange;
    @NotNull
    private final Collection<String> bindingKeys;
    @NotNull
    private final ExchangeType exchangeType;
    private final boolean isDurable;

    public AmqpConfiguration(@NotNull final String uri,
                             @NotNull final String exchange,
                             @NotNull final Collection<String> bindingKeys,
                             @NotNull final ExchangeType exchangeType,
                             final boolean isDurable){

        this.uri = uri;
        this.exchange = exchange;
        this.bindingKeys = bindingKeys;
        this.exchangeType = exchangeType;
        this.isDurable = isDurable;
    }

    @NotNull
    public String getUri() {
        return uri;
    }

    @NotNull
    public String getExchange() {
        return exchange;
    }

    @NotNull
    public Collection<String> getBindingKeys() {
        return bindingKeys;
    }

    @NotNull
    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public boolean isDurable() {
        return isDurable;
    }
}
