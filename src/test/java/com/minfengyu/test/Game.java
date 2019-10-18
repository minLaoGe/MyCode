package com.minfengyu.test;

import java.util.Objects;

public class Game {
  private  String name;
  private Integer age;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
  Game game = (Game) o;
    return Objects.equals(name, game.name) &&
    Objects.equals(age, game.age);
}

/*  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }*/

  public Game() {
  }

  public Game(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
