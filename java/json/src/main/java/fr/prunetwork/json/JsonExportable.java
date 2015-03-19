package fr.prunetwork.json;

import org.json.JSONString;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2015-03-19
 */
@FunctionalInterface
public interface JsonExportable extends JSONString {

    @Override
    String toJSONString();
}
