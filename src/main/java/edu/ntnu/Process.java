package edu.ntnu;

public class Process {
  int id;
  int at;
  int bt;
  int priority;

  public Process(int id, int at, int bt, int priority) {
    this.id = id;
    this.at = at;
    this.bt = bt;
    this.priority = priority;
  }

  public Process(int id, int at, int bt) {
    this.id = id;
    this.at = at;
    this.bt = bt;
  }
}