# Spell Checker

## Overview

The Spell Checker is a Java-based application that helps users identify and correct misspelled words in a given text file. It provides an intuitive user interface for loading dictionaries, text files, and custom words. The application utilizes a spell-checking algorithm to suggest corrections for misspelled words and allows users to save the corrected text.

## Core Working

The spell checker works by comparing the words in the input text file against a loaded dictionary. It identifies misspelled words and suggests corrections based on a predefined algorithm (Levenshtein distance). Users can customize the dictionary by adding or removing words, enabling case sensitivity, and providing a list of custom words.

## Features

- **Spell Checking:** Identifies and suggests corrections for misspelled words in the input text file.
- **Custom Dictionary:** Allows users to add or remove words from the dictionary, providing flexibility and customization.
- **Case Sensitivity:** Users can enable or disable case sensitivity for improved accuracy in spell checking.
- **User Interface:** A user-friendly interface with options to load dictionary and text files, customize settings, and view corrected text.
- **Save Corrected Text:** Enables users to save the corrected text to a new file for future use.

## User Interface

The graphical user interface (GUI) is designed to be simple and intuitive:

1. **Dictionary File Path:** Input field to specify the path of the dictionary file.
2. **Load Dictionary Button:** Button to load the dictionary file.
3. **Text File Path:** Input field to specify the path of the text file for spell checking.
4. **Load Text Button:** Button to load the text file.
5. **Case Sensitivity Checkbox:** Checkbox to enable or disable case sensitivity.
6. **Custom Words:** Input field to add custom words to the dictionary.
7. **Add Words Button:** Button to add custom words to the dictionary.
8. **Check Text Button:** Button to initiate the spell-checking process.
9. **Result TextArea:** Area to display the corrected text and suggestions.
10. **Time Label:** Displays the time taken for the spell-checking process.

## How to Use

1. Launch the application.
2. Specify the dictionary file path and load the dictionary.
3. Specify the text file path for spell checking and load the text file.
4. Customize settings such as case sensitivity and custom words.
5. Click the "Check Text" button to initiate spell checking.
6. View the corrected text and suggestions in the result area.
7. Save the corrected text to a new file if desired.

## Dependencies

The application is built using Java and utilizes the Swing library for the graphical user interface.
