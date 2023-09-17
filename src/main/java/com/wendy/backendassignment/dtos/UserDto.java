package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Authority;


import java.time.LocalDate;
import java.util.Set;

public class UserDto {


    public String username;
    public String password;
    public String firstname;
    public String lastname;
    private LocalDate dob;
    public Boolean enabled;
    public String apikey;
    public String email;
    public Set<Authority> authorities;
//    public List<Treatment> order;

    //public Long getId() {return id;}

   // public void setId(Long id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getFirstname() {return firstname;}

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public LocalDate getDob() {return dob;}

    public void setDob(LocalDate dob) {this.dob = dob;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Boolean getEnabled() {return enabled;}

    public void setEnabled(Boolean enabled) {this.enabled = enabled;}

    public String getApikey() {return apikey;}

    public void setApikey(String apikey) {this.apikey = apikey;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public void setAuthorities(Set<Authority> authorities) {this.authorities = authorities;}
    public Set<Authority> getAuthorities() {
        return authorities;
    }



  /*  public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }*/
}
