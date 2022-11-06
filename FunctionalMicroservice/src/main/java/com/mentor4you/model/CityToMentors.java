package com.mentor4you.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="cityToMentors")
public class CityToMentors {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "cityId")
        private City city;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "mentorId")
        private Mentors mentors;

        public CityToMentors() {
        }

        public CityToMentors(City city, Mentors mentors) {
                this.city = city;
                this.mentors = mentors;
        }

        public int getId() {
                return id;
        }

        public City getCity() {
                return city;
        }

        public void setCity(City city) {
                this.city = city;
        }

        public Mentors getMentors() {
                return mentors;
        }

        public void setMentors(Mentors mentors) {
                this.mentors = mentors;
        }
}
