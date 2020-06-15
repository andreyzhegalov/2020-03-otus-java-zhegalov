package hw10.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = AdressDataSet.class, cascade = CascadeType.ALL)
    private AdressDataSet adress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneDataSet> phones = new ArrayList<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(long id, String name) {
        this.id = -1L;
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
        phones.remove(phone);
        phone.setUser(null);
    }

    @Override
    public String toString() {
        return "User{" + "id = " + getId() + ", name = " + getName() + ", adress = " + getAdress() + ", phones = "
                + getPhones() + "}";
    }
}
