package io.threeloop.bookaspot.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(min = 3, max = 100, message = "Nazwa użytkownika musi zawierać minimum z 3 znaki.")
  private String username;

  @NotNull
  private String password;

  @Transient
  private String passwordConfirm;

  @NotNull
  @Email(message = "Email address is not valid.")
  private String email;

  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
  private Set<Spot> spotsOwned = new HashSet<>();

  @OneToMany(mappedBy = "visitor", fetch = FetchType.LAZY)
  private Set<Spot> spotsVisited = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private Set<Role> roles = new HashSet<>();

  public User() {}

  public User(String username, String email, Set<Spot> claimedSpots, Set<Role> roles) {
    this.username = username;
    this.email = email;
    this.roles = roles;
    spotsOwned = claimedSpots;
  }

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public Set<Spot> getSpotsOwned() {
    return spotsOwned;
  }

  public Set<Spot> getSpotsVisited() {
    return spotsVisited;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
