package io.github.maritims.soundeks;

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

    final String convertToSoundex(final String s) {
        var chars = s.toCharArray();

        for (int i = 1; i < chars.length; i++) {
            chars[i] = (char)(getConsonantValue(chars[i]) + '0');
        }

        return String.format("%-4s", new String(chars)).replace(' ', '0');
    }

    public String transform(String original) {
        original = original.toUpperCase();
        var sb                       = new StringBuilder().append(original.charAt(0));

        for (var i = 1; i < original.length() && sb.length() < 4; i++) {
            var currentChar            = original.charAt(i);
            var previousChar           = original.charAt(i - 1);
            var currentConsonantValue  = getConsonantValue(currentChar);
            var previousConsonantValue = getConsonantValue(previousChar);

            if (currentConsonantValue != previousConsonantValue && currentConsonantValue != 0) {
                sb.append(currentChar);
                continue;
            }

            if ((currentChar == 'H' || currentChar == 'W' || currentChar == 'Y') && i != original.length() - 1) {
                var nextChar           = original.charAt(i + 1);
                var nextConsonantValue = getConsonantValue(nextChar);

                if(previousConsonantValue != nextConsonantValue) {
                    sb.append(nextChar);
                }

                i++;
            }
        }

        return convertToSoundex(sb.toString());
    }
}
