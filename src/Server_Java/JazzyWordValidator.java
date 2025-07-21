package Server_Java;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple Jazzy-inspired Word Validation Service
 * Lightweight word validation focusing on English dictionary checking
 */
public class JazzyWordValidator {

    // Dictionary storage
    private static final Set<String> dictionary = ConcurrentHashMap.newKeySet();
    private static final Map<String, Boolean> validationCache = new ConcurrentHashMap<>();
    private static boolean isLoaded = false;

    // Statistics
    private static int totalChecks = 0;
    private static int cacheHits = 0;

    static {
        loadDictionary();
    }

    /**
     * Load dictionary from words.txt file
     */
    private static void loadDictionary() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/Server_Java/words.txt"));
            String line;
            int wordCount = 0;

            while ((line = reader.readLine()) != null) {
                String word = line.trim().toUpperCase();
                if (isValidFormat(word)) {
                    dictionary.add(word);
                    wordCount++;
                }
            }
            reader.close();
            isLoaded = true;
            System.out.println("✓ Jazzy Dictionary loaded: " + wordCount + " words");

        } catch (IOException e) {
            System.err.println("⚠ Warning: Could not load dictionary from words.txt");
            loadFallbackDictionary();
        }
    }

    /**
     * Load essential words as fallback
     */
    private static void loadFallbackDictionary() {
        String[] essentialWords = {
            "CAT", "DOG", "BAT", "HAT", "RAT", "CAR", "BUS", "SUN", "MAN", "BOY", "DAY", "WAY",
            "WORD", "WORK", "PLAY", "GAME", "TIME", "YEAR", "COME", "MAKE", "GOOD", "BEST",
            "HOUSE", "WATER", "FOUND", "STILL", "LEARN", "WORLD", "PLACE", "WHERE", "AFTER",
            "PEOPLE", "SHOULD", "SCHOOL", "BETTER", "LITTLE", "SYSTEM", "IMPORTANT", "BECAUSE"
        };

        dictionary.addAll(Arrays.asList(essentialWords));
        isLoaded = true;
        System.out.println("✓ Jazzy Fallback dictionary loaded: " + essentialWords.length + " words");
    }

    /**
     * Main word validation method - Jazzy-style simple check
     * @param word the word to validate
     * @return true if the word is valid English
     */
    public static boolean isWordValid(String word) {
        totalChecks++;

        if (word == null || word.isEmpty()) {
            return false;
        }

        String upperWord = word.toUpperCase().trim();

        // Check cache first for performance
        if (validationCache.containsKey(upperWord)) {
            cacheHits++;
            return validationCache.get(upperWord);
        }

        // Basic format validation
        if (!isValidFormat(upperWord)) {
            validationCache.put(upperWord, false);
            return false;
        }

        // Dictionary lookup - main validation
        boolean isValid = dictionary.contains(upperWord);

        // Cache the result
        validationCache.put(upperWord, isValid);

        return isValid;
    }

    /**
     * Check if word format is valid (3-15 letters only)
     */
    private static boolean isValidFormat(String word) {
        return word != null &&
               word.length() >= 3 &&
               word.length() <= 15 &&
               word.matches("[A-Z]+");
    }

    /**
     * Get word suggestions for misspelled words (simplified)
     * @param word the misspelled word
     * @return array of suggested corrections
     */
    public static String[] getSuggestions(String word) {
        if (word == null || word.isEmpty()) {
            return new String[0];
        }

        String upperWord = word.toUpperCase();
        List<String> suggestions = new ArrayList<>();

        // Simple suggestions: check for single character changes
        for (String dictWord : dictionary) {
            if (Math.abs(dictWord.length() - upperWord.length()) <= 1) {
                if (isSimilar(upperWord, dictWord)) {
                    suggestions.add(dictWord);
                    if (suggestions.size() >= 5) break; // Limit suggestions
                }
            }
        }

        return suggestions.toArray(new String[0]);
    }

    /**
     * Check if two words are similar (for suggestions)
     */
    private static boolean isSimilar(String word1, String word2) {
        if (Math.abs(word1.length() - word2.length()) > 1) {
            return false;
        }

        int differences = 0;
        int minLength = Math.min(word1.length(), word2.length());

        for (int i = 0; i < minLength; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                differences++;
                if (differences > 2) return false;
            }
        }

        differences += Math.abs(word1.length() - word2.length());
        return differences <= 2;
    }

    /**
     * Add a word to the dictionary
     */
    public static void addWord(String word) {
        if (isValidFormat(word.toUpperCase())) {
            dictionary.add(word.toUpperCase());
            validationCache.remove(word.toUpperCase()); // Remove from cache to revalidate
        }
    }

    /**
     * Get validation statistics
     */
    public static String getStats() {
        double hitRate = totalChecks > 0 ? (double) cacheHits / totalChecks * 100 : 0;
        return String.format("Jazzy Stats: %d checks, %d cached (%.1f%% hit rate), %d words",
                           totalChecks, cacheHits, hitRate, dictionary.size());
    }

    /**
     * Check if dictionary is loaded
     */
    public static boolean isDictionaryLoaded() {
        return isLoaded;
    }

    /**
     * Get dictionary size
     */
    public static int getDictionarySize() {
        return dictionary.size();
    }

    /**
     * Clear validation cache
     */
    public static void clearCache() {
        validationCache.clear();
        cacheHits = 0;
        totalChecks = 0;
    }

    /**
     * Detailed validation result for debugging
     */
    public static ValidationResult validateWordDetailed(String word) {
        boolean isValid = isWordValid(word);
        String reason;

        if (!isValid) {
            if (word == null || word.isEmpty()) {
                reason = "Word is empty";
            } else if (!isValidFormat(word.toUpperCase())) {
                reason = "Invalid format (must be 3-15 letters)";
            } else {
                reason = "Not found in dictionary";
                String[] suggestions = getSuggestions(word);
                if (suggestions.length > 0) {
                    reason += ". Suggestions: " + String.join(", ", suggestions);
                }
            }
        } else {
            reason = "Valid English word";
        }

        return new ValidationResult(isValid, reason);
    }

    /**
     * Simple validation result class
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String reason;

        public ValidationResult(boolean valid, String reason) {
            this.valid = valid;
            this.reason = reason;
        }

        public boolean isValid() { return valid; }
        public String getReason() { return reason; }

        @Override
        public String toString() {
            return (valid ? "VALID" : "INVALID") + ": " + reason;
        }
    }
}
