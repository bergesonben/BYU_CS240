package cs240.byu.edu.evilhangman_android.StudentPackage;

import android.renderscript.ScriptGroup;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by bergeson on 6/30/2016.
 */
public class WordBank {
//DOMAIN
    private Set<String> bestWords;
    private String currWord;


//CONSTRUCTORS
    public WordBank(InputStreamReader dictionary, int length){
        bestWords = new HashSet<>();
        currWord = "";
        addWords(dictionary, length);
    }

//QUERIES
    public String getWord(){
        return currWord;
    }

//COMMANDS
    private void addWords(InputStreamReader dictionary, int length){
        StringBuilder blankWord = new StringBuilder();
        for(int i = 0; i < length; ++i){
            blankWord.append("-");
        }
        currWord = blankWord.toString();

        try{
            char c = (char)dictionary.read();
            while(c != (char)-1){
                StringBuilder sb = new StringBuilder();
                while(!Character.isWhitespace(c))
                {
                    sb.append(c);
                    c = (char)dictionary.read();
                }
                if(sb.length() == length){
                    bestWords.add(sb.toString().toLowerCase());
                }
                c = (char)dictionary.read();
            }
        }
        catch(IOException io){

        }
    }

    private int getNumLetters(String word){
        int retval = 0;
        for(int i = 0; i < word.length(); ++i){
            if(word.charAt(i) != '-'){
                ++retval;
            }
        }
        return retval;
    }

    private String getWordWithRightmostLetter(String wordA, String wordB){
        for(int i = wordA.length()-1; i >= 0; --i) {
            if(wordA.charAt(i) != '-' && wordB.charAt(i) == '-'){
                return wordA;
            }
            else if(wordA.charAt(i) == '-' && wordB.charAt(i) != '-'){
                return wordB;
            }
        }
        return wordA;
    }

    private String getWordWithLeastLetters(String wordA, String wordB){
        int lettersCountA = getNumLetters(wordA);
        int lettersCountB = getNumLetters(wordB);

        if(lettersCountA < lettersCountB){
            return wordA;
        }
        else if(lettersCountA == lettersCountB){
            return getWordWithRightmostLetter(wordA, wordB);
        }
        else{
            return wordB;
        }

    }

    private String getBestGroup(Map<String,Set<String>> map){
        int largestSetSize = -1;
        String largestSetKey = "";

        for(String key : map.keySet()){
            int currSetSize = map.get(key).size();
            if(currSetSize > largestSetSize){
                largestSetSize = currSetSize;
                largestSetKey = key;
            }
            else if(currSetSize == largestSetSize){
                largestSetKey = getWordWithLeastLetters(key,largestSetKey);
                largestSetSize = currSetSize;
            }
        }

        return largestSetKey;
    }

    public Set<String> makeGuess(char guess){
        Map<String,Set<String>> subsets = new HashMap<>();
        for(String word : bestWords){
            String wordMask = getWordMask(word,guess);
            if (!subsets.containsKey(wordMask)) {
                subsets.put(wordMask,new HashSet<String>());
            }
            subsets.get(wordMask).add(word);
        }

        currWord = getBestGroup(subsets);
        bestWords = subsets.get(currWord);
        return bestWords;
    }

    private String getWordMask(String word, char guess){
        StringBuilder retval = new StringBuilder(currWord);
        for(int i = 0; i < word.length(); ++i){
            if(word.charAt(i) == guess){
                retval.deleteCharAt(i);
                retval.insert(i,guess);
            }
        }
        return retval.toString();
    }
}
