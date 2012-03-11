package com.radioreference.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

public class XmlHelper {
    private static final Serializer SERIALIZER = new Persister();

    public static <T> T fromXml(Class<? extends T> clazz, InputStream xml) {
        try {
            Object result = SERIALIZER.read(clazz, xml, false);
            return (T) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}