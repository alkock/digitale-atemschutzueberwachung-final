# Source-Code
Das Originalprojekt wurde in einem privaten Repository erstellt. 
Statistik des Repos:

![Commits](https://img.shields.io/badge/Originale%20Commits-98-blue)

![Failed_CI](https://img.shields.io/badge/Failed_CI%20Pipes-15-red)

Insbesondere die failed Pipes haben viele Fehler aufdecken können.

Die verwendete CI-Pipeline ist folgende:

```
name: Android CI

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    - name: set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
        cache: gradle

    - name: Grant execute permission for gradlew
      run: chmod +x gradle
    - name: Build with Gradle
      run: ./gradlew build
    - name: Run unit tests with Gradle
      run: ./gradlew test
```
