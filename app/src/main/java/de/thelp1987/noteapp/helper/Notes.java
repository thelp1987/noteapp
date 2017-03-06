package de.thelp1987.noteapp.helper;

import java.util.ArrayList;

/**
 * Created by the-l on 06.03.2017.
 */
public class Notes {
        private String mName;
        private boolean mOnline;

        public Notes(String name, boolean online) {
            mName = name;
            mOnline = online;
        }

        public String getTitle() {
            return mName;
        }

        public boolean isOnline() {
            return mOnline;
        }

        private static int lastContactId = 0;

        public static ArrayList<Notes> createTestList(int numNotes) {
            ArrayList<Notes> notes = new ArrayList<Notes>();

            for (int i = 1; i <= numNotes; i++) {
                notes.add(new Notes("Title " + ++lastContactId, i <= numNotes / 2));
            }

            return notes;
        }
}
