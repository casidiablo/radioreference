package com.radioreference.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class State {
    @Attribute(name = "id")
    private long id;
    @Attribute(name = "name")
    private String name;
    @Attribute(name = "code")
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (id != state.id) return false;
        if (code != null ? !code.equals(state.code) : state.code != null) return false;
        if (name != null ? !name.equals(state.name) : state.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
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
