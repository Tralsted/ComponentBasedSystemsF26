#!/bin/bash
mvn clean install &&
  mkdir -p libs &&
  cp ~/.m2/repository/org/openjfx/javafx-graphics/21/javafx-graphics-21-linux.jar libs/ &&
  cp ~/.m2/repository/org/openjfx/javafx-controls/21/javafx-controls-21-linux.jar libs/ &&
  cp ~/.m2/repository/org/openjfx/javafx-base/21/javafx-base-21-linux.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-context/6.2.6/spring-context-6.2.6.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-core/6.2.6/spring-core-6.2.6.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-beans/6.2.6/spring-beans-6.2.6.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-aop/6.2.6/spring-aop-6.2.6.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-expression/6.2.6/spring-expression-6.2.6.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-jcl/6.2.6/spring-jcl-6.2.6.jar libs/ &&
  cp ~/.m2/repository/org/springframework/spring-web/6.2.6/spring-web-6.2.6.jar libs/ &&
  java --module-path mods-mvn:libs --module Core/dk.sdu.mmmi.cbse.main.Main
