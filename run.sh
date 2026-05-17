#!/bin/bash
mvn clean install &&
  mkdir -p libs &&
  cp ~/.m2/repository/org/openjfx/javafx-graphics/21/javafx-graphics-21-linux.jar libs/ &&
  cp ~/.m2/repository/org/openjfx/javafx-controls/21/javafx-controls-21-linux.jar libs/ &&
  cp ~/.m2/repository/org/openjfx/javafx-base/21/javafx-base-21-linux.jar libs/ &&
  find ~/.m2/repository/org/springframework -name "*.jar" \
    ! -name "*sources*" \
    ! -name "*javadoc*" \
    -exec cp {} libs/ \; &&
  java --module-path mods-mvn:libs --module Core/dk.sdu.mmmi.cbse.main.Main
