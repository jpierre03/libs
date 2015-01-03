package fr.prunetwork.sandbox.common.json;

import org.json.simple.parser.ContainerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class BasicContainerFactory implements ContainerFactory {

    @Override
    public List creatArrayContainer() {
        return new LinkedList();
    }

    @Override
    public Map createObjectContainer() {
        return new HashMap();
    }

}
