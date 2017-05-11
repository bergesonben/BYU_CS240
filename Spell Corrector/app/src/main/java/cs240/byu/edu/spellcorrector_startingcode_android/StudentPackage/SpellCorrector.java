package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by User on 6/27/2016.
 */
public class SpellCorrector implements ISpellCorrector {

//DOMAIN
    private Trie myTrie;

//CONSTRUCTORS
    public SpellCorrector(){
        myTrie = new Trie();
    }

//QUERIES

//COMMANDS
    /**
     * Tells this <code>ISpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @param dictionaryFile File containing the words to be used
     * @throws IOException If the file cannot be read
     */
    public void useDictionary(InputStreamReader dictionaryFile) throws IOException{
        char c = (char) dictionaryFile.read();
        while(c != (char)-1){
            StringBuilder sb = new StringBuilder();
            while(!Character.isWhitespace(c) && c != (char)-1){
                sb.append(c);
                c = (char) dictionaryFile.read();
            }
            if( sb.length() > 0){
                myTrie.add(sb.toString());
            }
            c = (char) dictionaryFile.read();
        }
    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     * @param inputWord
     * @return The suggestion
     * @throws NoSimilarWordFoundException If no similar word is in the dictionary
     */
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException{
        String result = myTrie.suggestSimilarWord(inputWord);
        if(result.equals("")){
            throw new NoSimilarWordFoundException();
        }
        return result;
    }
}
