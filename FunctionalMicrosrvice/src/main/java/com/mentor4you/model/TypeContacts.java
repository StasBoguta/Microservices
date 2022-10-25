package com.mentor4you.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="TypeContacts")
public class TypeContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "typeContacts")
    private Set<ContactsToAccounts> links_to_accounts;

    public TypeContacts() {}

    public TypeContacts(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeContacts that = (TypeContacts) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(links_to_accounts, that.links_to_accounts);
    }*/

    /*@Override
    public int hashCode() {
        return Objects.hash(id, name, links_to_accounts);
    }

    @Override
    public String toString() {
        return "TypeContacts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", links_to_accounts=" + links_to_accounts +
                '}';
    }*/
}
