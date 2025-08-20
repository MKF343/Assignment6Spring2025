FYE Music Player

Description

This Java project is a simple command-line music player that can load and play music from a custom file format. It's designed to handle specific exceptions related to music file reading and playback.

How It Works

The program presents a menu with three options:

    Load music: Prompts for a music file name. It then reads the file, parses the notes, and loads them into the player.

    Play music: Plays the loaded music.

    Quit: Exits the program.

The music file format is a simple binary format:

    The first byte represents the number of notes in the file.

    The following bytes represent the notes and their timings, alternating between note value and timing value.

Class Overview

    Assignment6.java: The main class that contains the user interface and the logic for loading music files.

    FYENote.java: An interface that defines a musical note with methods to get the note value and its timing. This file also includes the custom exception classes FYEMusicException and FYEMusicPlayerException, as well as the FYEMusicPlayer class, which handles loading and playing the music.

    Note.java: A class that implements the FYENote interface.

    FYEMusicReaderException.java: A custom exception class for errors that occur while reading a music file.
