package com.radioreference.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Relay {
    @Attribute(name = "host")
    private String host;
    @Attribute(name = "port")
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relay)) return false;

        Relay relay = (Relay) o;

        if (port != relay.port) return false;
        if (host != null ? !host.equals(relay.host) : relay.host != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "Relay{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
