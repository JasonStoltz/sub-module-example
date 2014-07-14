sub-module-example
==================

Demonstrate including assets from sub-projects with sbt-rjs


Run this project with `activator run`. Navigate to "localhost:9000" and notice that require successfully includes "core" and "common" modules, which live in sbt sub projects.

Try, then, running `activator web-stage`. Note that rjs fails to find "common" and "core" modules which live in sbt-subprojects.


Solution 1: Recursively copy all dependencies up from dependent submodules to parent module as the first step in the build pipeline: https://github.com/JasonStoltz/sub-module-example/tree/solution-1-rjs-update
