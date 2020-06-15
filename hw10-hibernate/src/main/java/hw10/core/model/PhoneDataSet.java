package hw10.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "phone")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
            "id = " + id +
            ", number = " + getNumber() +
            "}";
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int)(id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneDataSet object = (PhoneDataSet) o;

        if (id != object.id) return false;
        return !(number != null ? !number.equals(object.number) : object.number != null);
    }

}
