# Java Stopwatch

Simple Swing stopwatch app for the assignment requirements.

## Package

`ca.lastname.stopwatch`

## Features

- Uses exactly one Swing `Timer` (100ms tick).
- Tracks total elapsed time and lap time in seconds + tenths.
- `Start/Stop` button toggles between running and paused.
- `Lap/Reset` behavior:
  - While running: records the current lap in the bottom `JTextArea` and resets lap to `0.0`.
  - While stopped: resets all counters and clears previous laps.
- Time and lap displays are `JTextField`s.
- Previous laps display is a multi-line `JTextArea`.
- Stopwatch values are stored in private `int` fields.

## Run

Compile:

```bash
javac -d out src/main/java/ca/lastname/stopwatch/StopwatchApp.java
```

Run:

```bash
java -cp out ca.lastname.stopwatch.StopwatchApp
```
