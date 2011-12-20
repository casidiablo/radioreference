package com.radioreference.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.TransformException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

class XmlHelper {
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
    private static final Serializer SERIALIZER = new Persister();

    public static String toXml(Object object) {
        OutputStream out = new ByteArrayOutputStream();
        try {
            SERIALIZER.write(object, out);
            String result = out.toString();
            out.close();
            return XML_HEADER + result;
        } catch (Exception e) {
            if (e instanceof TransformException) {
                throw new RuntimeException(
                        "The specified object cannot be transformed to XML. "
                                + "Did you forget to add SimpleXML annotations to it?");
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T fromXml(Class<? extends T> clazz, InputStream xml) {
        try {
            Object result = SERIALIZER.read(clazz, xml, false);
            return (T) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromXml(Class<? extends T> clazz, String xml) {
        try {
            Object result = SERIALIZER.read(clazz, xml, false);
            return (T) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}