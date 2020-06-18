package hw10.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hw10.core.dao.UserDaoException;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = "graph.userEntity.addresesAndPhones", attributeNodes = {
        @NamedAttributeNode(value = "phones") })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, orphanRemoval = true)
    private AdressDataSet adress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneDataSet> phones = new ArrayList<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public void setAdress(AdressDataSet adress) {
        if (adress == null) {
            if (this.adress != null) {
                this.adress.setUser(null);
            }
        } else {
            adress.setUser(this);
        }
        this.adress = adress;
    }

    public AdressDataSet getAdress() {
        return adress;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void addPhone(PhoneDataSet phone) {
        phones.add(phone);
        phone.setUser(this);
    }

    public void removePhone(PhoneDataSet phone) {
        if (!phones.remove(phone)) {
            throw new UserDaoException(new NoSuchElementException());
        }
        phone.setUser(null);
    }

    @Override
    public String toString() {
        return "User{" + "id = " + getId() + ", name = " + getName() + ", adress = " + getAdress() + ", phones = "
                + getPhones() + "}";
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User object = (User) o;

        if (id != object.id)
            return false;
        if (name != null ? !name.equals(object.name) : object.name != null)
            return false;
        if (adress != null ? !adress.equals(object.adress) : object.adress != null)
            return false;

        return Objects.deepEquals(this.getPhones() != null ? this.getPhones().toArray() : this.getPhones(),
                object.getPhones() != null ? object.getPhones().toArray() : object.getPhones());
    }

}
