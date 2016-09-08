# Predictive-Text-Java
Most mobile phones offer predictive text to make easier enter messages write for instance text with mobile phone keyboards, which usually only contain 12 keys. Several letters are then written pressing the same key.

Predictive text allows the user to press a key only once to reach any of the letters it represents. A dictionary is used by the system to “predict” which of the letters of the key the user actually wanted to use. The principle relies on the fact that in a language, a sequence of keystrokes only corresponds to few words.

This project will make use of a mock up of a mobile phone interface, written in Java. Pressing 1 results in the last keystroke being removed, pressing 2-9 results in a character being added to the word that is currently being entered, pressing 0 inserts a space (that acts as a separator between words), pressing # switches between predictive text mode and standard text mode (where pressing a key repeatedly in quick succession causes the current character to cycle through its associated options), and pressing * either adds the current word to the dictionary (int standard mode), or cycles through the set of possible word fragments based on the current set of keystrokes for a given word (in predictive mode).

As things stand, the interface, and the standard mode have currently been completely implemented and your task is to complete the implementation of the predictive text mode. Specifically, you must finish off the implementation of the Dictionary component, which is used to store and retrieve words that correspond to sequences of keystrokes.

In terms of the implementation, it is expected that you will use a tree data structure. In this data structure, nodes represent both a keystroke (e.g. 2-9) and a list of word fragments are associated with the set of keystrokes that correspond to the path between the root node and the given node.

An example tree is given above in figure 2. This tree encodes five words: “happy”, “hello”, “good”, “god”, and “fun”. Based on this dictionary, pressing the following set of keys: 3,8,6 would result in the word “fun” being selected. In standard mode, this sequence of keystrokes would result in “dtm”. To get “fun” in standard mode, you would have had to do the following: press 3 (3 times), press 8 (2 times), and press 6 (2 times).

Also, in predictive mode, pressing 4 would result in “h” being displayed, however, pressing “*”, would cycle through the predictive options (i.e. pressing * once would result in “g” being displayed, pressing it a second time would result in “h” being displayed – “i” would never be displayed because no word beginning with this character is stored in the dictionary).

If no word exists the corresponds to the current sequence of keystrokes, then the predictive text system reverts to the standard text system and generates a word based on the first character associated with the keystrokes (i.e. if “fun” was not in the dictionary, then 3,8,6 would correspond to “dtm”.
