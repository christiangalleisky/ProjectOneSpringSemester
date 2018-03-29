package edu.gcccd.csis;

/**
 * @version 1.1
 * @author Christian Galleisky
 */
public class  SequenceSearchImpl extends SequenceSearch {

    private String[] toBeReturned = new String[0];

    /**
     * @param content The Original source String
     * @param start   The start tag delimiter
     * @param end     The end tag delimiter
     */
    public SequenceSearchImpl(final String content, final String start, final String end) {
        super(content, start, end);
    }

    /**
     * @return returns a String array of delimited chunks via the start and end tags given to the constructor
     */
    @Override
    public String[] getAllTaggedSequences() {
        String[] contentDelimitedByEndTags = content.split(endTag);
        if (startTag.equals(endTag)) {
            for (int i = 1; i < contentDelimitedByEndTags.length; i += 2) {
                toBeReturned = SequenceSearch.adds(toBeReturned, contentDelimitedByEndTags[i]);
            }
        } else if (!startTag.equals(endTag)) {
                for (String x : contentDelimitedByEndTags) {
                    String[] elementNearestEndTag = x.split(startTag);
                    toBeReturned = SequenceSearch.adds(toBeReturned, elementNearestEndTag[elementNearestEndTag.length - 1]);
                }
            }
        return toBeReturned;
        }


        /**
         * @return returns the longest String out of the getAllTaggedSequences method
         */
        @Override
        public String getLongestTaggedSequence (){
            String[] holding = getAllTaggedSequences();
            if(holding.length == 1){
                holding = new String [0];
            }
            if (holding.length > 1) {
                String r = holding[0];
                for (int i = 1; i < holding.length-1; i++) { //make this into an enhanced for statement
                    if (holding[i].length() >= r.length()) {
                        r = holding[i];
                    }
                }
                return r;
            } else {
                return null;
            }
        }

        /**
         * @return returns the string array produced by getAllTaggedSequences on new lines for each element displayed as "String : (Stringlength as an integer value)"
         */
        @Override
        public String displayStringArray () {
            StringBuilder r = new StringBuilder();
            String[] sh = this.getAllTaggedSequences();
            for (String x : sh) {
                r.append(x).append(" : ").append(x.length()).append("\n");
            }
            return r.toString();
        }

        /**
         * @return returns the original source string with all constructor end and start tags removed
         */
        @Override
        public String toString () {
            return this.content.replaceAll(this.startTag, "").replaceAll(this.endTag, "");
        }
    }


