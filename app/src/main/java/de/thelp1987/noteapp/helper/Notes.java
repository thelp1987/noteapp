package de.thelp1987.noteapp.helper;

import java.util.ArrayList;

/**
 * Created by the-l on 06.03.2017.
 */
public class Notes {
        private String nTitle;

        public Notes(String title) {
            nTitle = title;
        }

        public String getTitle() {
            return nTitle;
        }

        private static int lastNoteId = 0;

        public static ArrayList<Notes> createTestList(int numNotes) {
            ArrayList<Notes> notes = new ArrayList<>();

            for (int i = 1; i <= numNotes; i++) {
                notes.add(new Notes("Title " + ++lastNoteId));
            }

            return notes;
        }
}
