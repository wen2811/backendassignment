package com.wendy.backendassignment.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserDto {
    public Long id;
    @NotBlank(message = "Username should not be blank")
    public String username;
    public String firstname;
    public String lastname;
    @Past
    private LocalDate dateofbirth;
    @NotBlank(message = "Password should not be blank")
    @Size(min=6, max=20)
    public String password;
    @NotNull
    public Boolean enabled;
    public String apikey;
    @NotBlank
    @Email
    public String email;
 //   public Set<Authority> authorities;
//    public List<Treatment> order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    /*public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }*/
}
