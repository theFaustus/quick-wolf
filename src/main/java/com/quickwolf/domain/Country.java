package com.quickwolf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Faust on 4/16/2018.
 */
@Entity
@Table(name = "country", schema = "wolf")
public class Country {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (getId() != null ? !getId().equals(country.getId()) : country.getId() != null) return false;
        return getValue() != null ? getValue().equals(country.getValue()) : country.getValue() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
