# ${project.name} Documentation

## Introduction
This document provides an overview of the ${project.name} system.

## Project Information
- Version: ${project.version}
- Group: ${project.groupId}
- Artifact: ${project.artifactId}

## Dependencies
The project uses the following main dependencies:
- JUnit 5 for testing
- Gson for JSON processing
- Guava for utilities

## Design Patterns
The system implements several design patterns:
- Bridge Pattern for rendering
- Command Pattern for undo/redo
- Observer Pattern for notifications
- Visitor Pattern for exporting

## Usage
To build the project:
```bash
mvn clean install
```

To generate documentation:
```bash
mvn site
```
