[![Build Status](https://snap-ci.com/energizedwork/asl2-gradle-plugin/branch/master/build_image)](https://snap-ci.com/energizedwork/asl2-gradle-plugin/branch/master)
[![License](https://img.shields.io/badge/license-ASL2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# asl2-gradle-plugin

This project contains a Gradle plugin which simplifies setting up projects released under [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Installation

For installation instructions please see [this plugin's page on Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.energizedwork.asl2).

## Usage

### Tasks

This plugin adds the following task to the project:
 * `writeLicenseFile` - writes contents of the Apache License, Version 2.0 into a file called `LICENSE` located in the root of the project

### Applied plugins

This plugin does not add any plugins by default. 
It does hover apply [Idea Project Components plugin](https://github.com/energizedwork/idea-gradle-plugins#idea-project-components-plugin) if [Gradle's built-in IDEA plugin](https://docs.gradle.org/current/userguide/idea_plugin.html) is applied to the project.
It then uses that plugin to configure [InteliiJ's Copyright plugin](https://www.jetbrains.com/help/idea/2016.3/generating-and-updating-copyright-notice.html) to add Apache License, Version 2.0 headers to source files.

### Extensions

This plugin does not add any extensions.

## Building

### Importing into IDE

The project is setup to generate IntelliJ configuration files.
Simply run `./gradlew idea` and open the generated `*.ipr` file in IntelliJ.

### Tests

If you import the project into IntelliJ as described above then you can run integration tests even after changing the code without having to perform any manual steps.
They are configured to run in an environment matching the one used when running them using Gradle on the command line.

### Checking the build

The project contains some code verification tasks aside from tests so if you wish to run a build matching the one on CI then execute `./gradlew check`. 
