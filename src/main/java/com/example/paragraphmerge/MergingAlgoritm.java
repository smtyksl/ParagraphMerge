package com.example.paragraphmerge;

import java.util.PriorityQueue;

public class MergingAlgoritm {
    public static String mergeTexts(String... texts) {
        int n = texts.length;
        if (n == 0) {
            return "";
        } else if (n == 1) {
            return texts[0];
        } else {
            PriorityQueue<TextIterator> pq = new PriorityQueue<>(n);
            for (String text : texts) {
                pq.offer(new TextIterator(text));
            }
            StringBuilder mergedText = new StringBuilder();
            while (!pq.isEmpty()) {
                TextIterator ti = pq.poll();
                mergedText.append(ti.getCurrentChar());
                if (ti.hasNextChar()) {
                    pq.offer(ti);
                }
            }
            return mergedText.toString();
        }
    }

    private static class TextIterator implements Comparable<TextIterator> {
        private String text;
        private int index;

        public TextIterator(String text) {
            this.text = text;
            this.index = 0;
        }

        public char getCurrentChar() {
            return text.charAt(index);
        }

        public boolean hasNextChar() {
            return index < text.length() - 1;
        }

        public void nextChar() {
            index++;
        }

        public int compareTo(TextIterator other) {
            return Character.compare(getCurrentChar(), other.getCurrentChar());
        }
    }

}
