package com.cor.cep.util;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public interface Event {

    @NotNull
    String getDescription();

    @NotNull
    Date getCreationDate();
}
