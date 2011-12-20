package com.radioreference.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class State {
    @Attribute(name = "id")
    private long id;
    @Attribute(name = "name")
    private long name;
    @Attribute(name = "code")
    private long code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (code != state.code) return false;
        if (id != state.id) return false;
        if (name != state.name) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (name ^ (name >>> 32));
        result = 31 * result + (int) (code ^ (code >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name=" + name +
                ", code=" + code +
                '}';
    }
}
