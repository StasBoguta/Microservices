package com.mentor4you.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "city")
public class City {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;


        @Column(unique=true)
        private String name;

        @JsonIgnore
        @OneToMany(mappedBy = "city")
        private List<CityToMentors> cityToMentorsList;

        public City() {
        }

        public City(String name, List<CityToMentors> cityToMentorsList) {
                this.name = name;
                this.cityToMentorsList = cityToMentorsList;
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

        public List<CityToMentors> getCityToMentorsList() {
                return cityToMentorsList;
        }

        public void setCityToMentorsList(List<CityToMentors> cityToMentorsList) {
                this.cityToMentorsList = cityToMentorsList;
        }
}
