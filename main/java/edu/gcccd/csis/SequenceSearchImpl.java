package edu.gcccd.csis;

import java.util.regex.PatternSyntaxException;

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
        try{
            if(!startTag.equals(endTag)){
                int reverseFlag = 0;
                int lastKnownEndIndex = 0;
                int startIndex;
                int endIndex;
                do {
                    endIndex = content.indexOf(endTag , lastKnownEndIndex);
                    lastKnownEndIndex = endIndex + endTag.length();
                    int i = 0;
                    if(endIndex > 0) {
                        boolean control = true;
                        while (control){
                            startIndex = content.substring(endIndex - startTag.length() + i , endIndex + i).indexOf(startTag);
                            if(startTag.length() == endTag.length()) {
                                reverseFlag = content.substring(endIndex - endTag.length() + i , endIndex + i).indexOf(endTag);
                            }else if (startTag.length() < endTag.length()){
                                reverseFlag = content.substring(endIndex - endTag.length() + i + startTag.length() , endIndex + i).indexOf(endTag);
                            }
                            if(startIndex == 0 || reverseFlag == 0){
                                control = false;
                            }
                            i--;
                        }
                        i++;
                    }
                    if(endIndex == -1){
                        break;
                    }
                    if(reverseFlag == 0){
                        continue;
                    }else {
                        toBeReturned = SequenceSearch.adds(toBeReturned, content.substring(endIndex + i, endIndex));
                    }
                }while(true);
            }else{
                /**
                 * Demonstrate the use of regex delimiters. This is my third version of this project
                 * and I'm very aware of how sensitive the program is to regex string values
                 * from my experience building my second version of this project, which consisted of
                 * only regex values to make it work. No do while loop, no substring method, and no
                 * index of method. I left this here because for the tests it is the shortest possible
                 * implementation and also a little nifty and fun to look at. Enjoy!
                 */
                String[] contentDelimitedByEndTags = content.split(endTag);
                for (int i = 1; i < contentDelimitedByEndTags.length; i += 2) {
                    toBeReturned = SequenceSearch.adds(toBeReturned, contentDelimitedByEndTags[i]);
                }
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("Error processing content string, check that it has at least TWO tags!");
            System.exit(-1);
        }catch(PatternSyntaxException p){
            System.out.println("Error handling delimiters");
            System.exit(-1);
        }
            return toBeReturned;
    }



        /**
         * @return returns the longest String out of the getAllTaggedSequences method
         */
        @Override
        public String getLongestTaggedSequence (){
            String[] holding = getAllTaggedSequences();
            if (holding.length > 1) {
                String r = holding[0];
                for (String x : holding) {
                    if (x.length() >= r.length()) {
                        r = x;
                    }
                }
                return r;
            } else {
                return null;
            }
        }

        /**
         * @return returns the string array produced by getAllTaggedSequences on new lines for
         * each element displayed as "String : (Stringlength as an integer value)"
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


