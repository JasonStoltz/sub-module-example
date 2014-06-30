sub-module-example
==================

Demonstrate including assets from sub-projects with sbt-rjs


Run this project with `activator run`. Navigate to "localhost:9000" and notice that require successfully includes "core" and "common" modules, which live in sbt sub projects.

Try, then, running `activator web-stage`. Note that rjs fails to find "common" and "core" modules which live in sbt-subprojects.
