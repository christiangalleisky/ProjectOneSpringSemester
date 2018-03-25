package edu.gcccd.csis;

/**
 * @version 1.1
 * @author Christian Galleisky
 */
public class SequenceSearchImpl extends SequenceSearch {

    private String [] toBeReturned;
    private int [] intHolder;
    private String [] tagHolder;

    /**
     * @param content The Original source String
     * @param start The start tag delimiter
     * @param end The end tag delimiter
     */
    public SequenceSearchImpl(final String content, final String start, final String end) {
        super(content, start, end);
    }

    /**
     * @return returns a String array of delimited chunks via the start and end tags given to the constructor
     */
    @Override
    public String[] getAllTaggedSequences() {
        if (this.content.indexOf(this.endTag, 0) >= 0) {  //checks to see there is at least one end tag
                if (this.endTag.length() > this.startTag.length()) {  //selects proper for loop (in next line) for each case dependent on length of final
                   for (int i = 0; i < content.length() - this.endTag.length() + 1; i++)//what happens if start tag is longer or equal in length to the end tag!!!!
                       {
                        this.computeTagHolderArray(i);  //computes intHolder Array and tagHolder array
                   }
                }
                if (this.startTag.length() > this.endTag.length()) {
                    for (int i = 0; i < content.length() - this.startTag.length() + 1; i++)
                    {
                        this.computeTagHolderArray(i);  //computes intHolder Array and tagHolder array
                    }
                }
                if (this.startTag.length() == this.endTag.length()) {
                    for (int i = 0; i < content.length()-this.startTag.length() + 1; i++)
                    {
                        this.computeTagHolderArray(i);  //computes intHolder Array and tagHolder array
                    }
                }
                    if (this.startTag.equals(this.endTag)) {  //case when tags are the same
                        for (int i = 0; i < intHolder.length - 1; i += 2) {  //increments in groups of two, as they are paired in twos
                            toBeReturned = adds(toBeReturned, this.content.substring(intHolder[i] + this.startTag.length(), intHolder[i + 1]));
                        }
                        return toBeReturned;
                    }
                    if(!this.startTag.equals(this.endTag)){  //case when tags are not the same
                    for (int i = 1; i < tagHolder.length; i++) {
                        if (tagHolder[i].equals(this.endTag) && (!tagHolder[i - 1].equals(this.endTag)))  //checks that the prior tag was a start tag
                        {
                            toBeReturned = adds(toBeReturned, this.content.substring(intHolder[i - 1] + this.startTag.length(), intHolder[i]));
                        }
                    }
                }
                } else {
                    toBeReturned = new String[0];
                }return toBeReturned;
        }

    /**
     * @return returns the longest String out of the getAllTaggedSequences method
     */
    @Override
            public String getLongestTaggedSequence ()
            {
                String[] holding = getAllTaggedSequences();
                int k = 0;
                if (holding.length > 0) {
                    String r = holding[0];
                    for (int i = 1; i < holding.length; i++) {
                        if (holding[i].length() >= r.length()) {
                            r = holding[i];
                            k = i;
                        }

                    }
                    return holding[k];
                } else {
                    return null;
                }
            }

    /**
     * @return returns the string array produced by getAllTaggedSequences on new lines for each element displayed as "String : (Stringlength as an integer value)"
     */
    @Override
            public String displayStringArray ()
            {
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

    /**
     * @param i integer that is passed in from a internal for loop in the method getAllTaggedSequences. It guides the scanning of the source
     *          String for the placement of integers into an integer array and start and end tags into a string array
     */
    public void computeTagHolderArray ( int i)
            {
                if (this.content.substring(i, i + this.startTag.length()).equals(this.startTag) ||  //is Class String adding in extra blank units to keep this in bounds
                        this.content.substring(i, i + this.endTag.length()).equals(this.endTag) ||  //when it is being searched through by the method getAllTaggedSequences()???
                        this.content.substring(i, i + this.endTag.length()).equals(this.startTag) ||  //It looks like because of the plus 1 in the for loops that call this method in that
                        this.content.substring(i, i + this.startTag.length()).equals(this.endTag))  //method that if I call on the longer of the two that I will be out of bounds...???
                {
                    intHolder = addsInteger(intHolder, i);

                    if (this.content.substring(i, i + this.startTag.length()).equals(this.startTag) ||
                            this.content.substring(i, i + this.endTag.length()).equals(this.startTag))
                    {

                        tagHolder = adds(tagHolder, this.startTag);
                    } else {
                        tagHolder = adds(tagHolder, this.endTag);
                    }
                }
            }
        }

