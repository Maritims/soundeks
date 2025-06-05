package io.github.maritims.soundeks;

/**
 * A Soundex implementation according to the rules described on <a href="https://en.wikipedia.org/wiki/Soundex">https://en.wikipedia.org/wiki/Soundex</a>.
 */
public class Soundeks {
    final int getConsonantValue(final char c) {
        return switch (c) {
            case 'B', 'F', 'P', 'V' -> 1;
            case 'C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z' -> 2;
            case 'D', 'T' -> 3;
            case 'L' -> 4;
            case 'M', 'N' -> 5;
            case 'R' -> 6;
            default -> 0;
        };
    }

    /**
     * From Wikipedia: "Replace consonants with digits as follows (after the first letter)"
     *
     * @param s The consonants from the string passed to {@link #transform(String)} with and without duplicates as described by the rules of the Soundex algorithm.
     * @return A string retaining the first character of the given argument in which all other characters are converted to their consonant values with {@link #getConsonantValue(char)}.
     */
    final String convertToSoundex(final String s) {
        var chars = s.toCharArray();

        for (int i = 1; i < chars.length; i++) {
            chars[i] = (char) (getConsonantValue(chars[i]) + '0');
        }

        return String.format("%-4s", new String(chars)).replace(' ', '0');
    }

    /**
     * Transform a string to its Soundex representation.
     * @param original The string to transform.
     * @return A Soundex representation of the string.
     */
    public String transform(String original) {
        original = original.toUpperCase();

        var sb = new StringBuilder().append(original.charAt(0));

        for (var i = 1; i < original.length() && sb.length() < 4; i++) {
            var currentChar            = original.charAt(i);
            var previousChar           = original.charAt(i - 1);
            var currentConsonantValue  = getConsonantValue(currentChar);
            var previousConsonantValue = getConsonantValue(previousChar);

            // Two letters with the same number separated by a vowel are coded as two numbers. Therefore, append the current character if it differs from the previous character.
            // The current character will always differ from the previous character when we're looking at a vowel, so the current character will always be added.
            // Once we move past the vowel, we'll again experience that the current consonant value is different from the previous consonant value which is zero because it represents the vowel we just passed.
            // We avoid interrupting a potential upcoming H/W/Y check by stating the current consonant value cannot be zero which it would have been for the character Y.
            // Example: D A D (5 0 5)
            // Round 1: currentChar = D, currentConsonantValue = 5, assumed to be different from previousConsonantValue for the sake of this example. D will be appended to the StringBuilder.
            // Round 2: currentChar = A, currentConsonantValue = 0, nothing is appended to the StringBuilder, and we move to the H/W/Y check.
            // Round 3: currentChar = D, currentConsonantValue = 5, different from the previousConsonantValue (0). D will be appended to the StringBuilder.
            // The fact that currentChar is only appended if the consonant values are different ensures compliance with the requirement of no adjacent letters with equal numbers in the original string.
            if (currentConsonantValue != previousConsonantValue && currentConsonantValue != 0) {
                sb.append(currentChar);
                continue;
            }

            // Two letters with the same number separated by 'h', 'w' or 'y' are coded as a single number.
            // Therefore, append the next character if it differs from the previous character and move the index forward.
            if ((currentChar == 'H' || currentChar == 'W' || currentChar == 'Y') && i != original.length() - 1) {
                var nextChar           = original.charAt(i + 1);
                var nextConsonantValue = getConsonantValue(nextChar);

                if (previousConsonantValue != nextConsonantValue) {
                    sb.append(nextChar);
                }

                i++;
            }
        }

        return convertToSoundex(sb.toString());
    }
}
