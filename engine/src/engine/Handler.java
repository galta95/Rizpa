package engine;

import dataManager.jaxb.SchemaBasedJAXB;

public class Handler {
    public static RSE startApp(String xmlPath) {
        return SchemaBasedJAXB.loadXml(xmlPath);
    }
}
