package com.mentor4you.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name="Contacts_to_accounts")
public class ContactsToAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "type_contacts_id")
    private TypeContacts typeContacts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "accounts_id")
    private Accounts accounts;

    private String contactData;

    public ContactsToAccounts() {
    }

    public ContactsToAccounts(TypeContacts typeContacts,
                              Accounts accounts,
                              String contactData) {
        this.typeContacts = typeContacts;
        this.accounts = accounts;
        this.contactData = contactData;
    }

    public int getId() {
        return id;
    }

    public TypeContacts getTypeContacts() {
        return typeContacts;
    }

    public void setTypeContacts(TypeContacts typeContacts) {
        this.typeContacts = typeContacts;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        this.contactData = contactData;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactsToAccounts that = (ContactsToAccounts) o;

        return id == that.id && Objects.equals(typeContacts, that.typeContacts) && Objects.equals(accounts, that.accounts) && Objects.equals(contactData, that.contactData);
    }*/

    /*@Override
    public int hashCode() {
        return Objects.hash(id, typeContacts, accounts, contactData);
    }

    @Override
    public String toString() {
        return "ContactsToAccounts{" +
                "id=" + id +
                ", typeContacts=" + typeContacts +
                ", accounts=" + accounts +
                ", contactData='" + contactData + '\'' +
                '}';
    }*/
}
