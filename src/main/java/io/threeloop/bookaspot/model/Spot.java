package io.threeloop.bookaspot.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Spot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  private int spotNumber;
  
  @ManyToOne
  private User owner;
  
  @ManyToOne
  private User visitor;
  
  private LocalDateTime startTime;
  
  private LocalDateTime endTime;
  
  private State state;
  
  Spot() {}
  
  public Spot(User owner, int spotNumber) {
    this.owner = owner;
    this.spotNumber = spotNumber;
    state = State.FREE;
  }

  public void take(User visitor, LocalDateTime startTime, LocalDateTime endTime) {
    this.visitor = visitor;
    this.startTime = startTime;
    this.endTime = endTime;
    state = State.TAKEN;
  }

  public void release() {
    visitor = null;
    state = State.FREE;
  }

  public long getId() {
    return id;
  }

  public int getSpotNumber() {
    return spotNumber;
  }

  public User getOwner() {
    return owner;
  }

  public User getVisitor() {
    return visitor;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public State getState() {
    return state;
  }
}
