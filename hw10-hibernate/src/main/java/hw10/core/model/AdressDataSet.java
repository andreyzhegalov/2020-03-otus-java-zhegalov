package hw10.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adress")
public class AdressDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "adress")
    private String street;

    public long getId() {
        return id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int)(id ^ (id >>> 32));
        result = 31 * result + (street != null ? street.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdressDataSet object = (AdressDataSet) o;

        if (id != object.id) return false;
        return !(street != null ? !street.equals(object.street) : object.street != null);
    }

}

