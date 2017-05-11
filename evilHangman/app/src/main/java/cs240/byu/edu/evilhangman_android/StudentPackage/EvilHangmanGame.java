package cs240.byu.edu.evilhangman_android.StudentPackage;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bergeson on 6/30/2016.
 */
public class EvilHangmanGame implements StudentEvilHangmanGameController {
//DOMAIN
    private GAME_STATUS gameStatus;
    private int numGuessesLeft;
    private String currWord;
    private Set<Character> usedLetters;
    private int totalGuesses;
    private WordBank dictionary;

//CONSTRUCTORS
    public EvilHangmanGame(){
        gameStatus = null;
        numGuessesLeft = -1;
        currWord = "";
        usedLetters = new HashSet<>();
        totalGuesses = -1;
        dictionary = null;
    }

//QUERIES
    /**
     * This will get called after every valid guess. You should check to see if the user has won,
     *  lost, or is still in the heat of battle! (aka they are still playing)
     * @return {@link GAME_STATUS}
     */
    public GAME_STATUS getGameStatus(){
        return gameStatus;
    }

    /**
     * Simply return the number of guesses the user has left before the game ends
     * @return number of guesses left
     */
    public int getNumberOfGuessesLeft(){
        return numGuessesLeft;
    }

    /**
     * Return what the current word looks like. For example, lets say the game has started and the
     *  word length is 5. Then you would return this string "-----" (that's 5 dashes). Now after the
     *  user has guess a letter (say the letter a), you would return this string "-a---". When the
     *  user guesses the letter z you return the string "-azz-".
     * @return the current word to show on the screen (dashes and letters)
     */
    public String getCurrentWord(){
        return currWord;
    }

    /**
     * Return a set of characters of all the guesses that have been made already. This is simply to
     *  show on the screen what guesses have been made. YOU still need to check on every guess if that
     *  character has already been guessed or not
     * @return Set of all characters already guessed
     */
    public Set<Character> getUsedLetters(){
        return usedLetters;
    }

//COMMANDS
    /**
     * This is called at the beginning of the game to let your controller know how many guesses to start
     *  the game with.
     * @param numberOfGuessesToStart
     */
    public void setNumberOfGuesses(int numberOfGuessesToStart){
        this.numGuessesLeft = numberOfGuessesToStart;
    }

    /**
     * Starts a new game of evil hangman using words from <code>dictionary</code>
     * with length <code>wordLength</code>.
     *	<p>
     *	This method should set up everything required to play the game,
     *	but should not actually play the game. (ie. There should not be
     *	a loop to prompt for input from the user.)
     *
     * @param dictionary Dictionary of words to use for the game
     * @param wordLength Number of characters in the word to guess
     */
    public void startGame(InputStreamReader dictionary, int wordLength){
        this.dictionary = new WordBank(dictionary, wordLength);
        gameStatus = GAME_STATUS.NORMAL;
    }


    /**
     * Make a guess in the current game.
     *
     * @param guess The character being guessed
     *
     * @return The set of strings that satisfy all the guesses made so far
     * in the game, including the guess made in this call. The game could claim
     * that any of these words had been the secret word for the whole game.
     *
     * @throws GuessAlreadyMadeException If the character <code>guess</code>
     * has already been guessed in this game.
     */
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException{
        if(usedLetters.contains(guess)){
            throw new GuessAlreadyMadeException();
        }

        if(!Character.isAlphabetic(guess)){
            //TODO: log message or toast
            return null;
        }
        --numGuessesLeft;

        usedLetters.add(Character.toLowerCase(guess));
        Set<String> retval = this.dictionary.makeGuess(Character.toLowerCase(guess));
        currWord = this.dictionary.getWord();
        if(!currWord.contains("-")){
            gameStatus = GAME_STATUS.PLAYER_WON;
        }
        else if(numGuessesLeft == 0){
            gameStatus = GAME_STATUS.PLAYER_LOST;
        }
        else{
            gameStatus = GAME_STATUS.NORMAL;
        }
        return retval;

    }
}
