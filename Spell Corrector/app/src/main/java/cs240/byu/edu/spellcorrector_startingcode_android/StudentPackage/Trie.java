package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by User on 6/27/2016.
 */
public class Trie implements ITrie{
//DOMAIN
    private Node root;
    private int nodeCount;
    private int wordCount;
    private Set<String> oneEditAway;
    private boolean secondPass;

//CONSTRUCTORS
    public Trie(){
        root = new Node('@',null);
        nodeCount = 1;
        wordCount = 0;
        oneEditAway = new HashSet<String>();
        secondPass = false;
    }

//QUERIES

    /**
     * Returns the number of unique words in the trie
     *
     * @return The number of unique words in the trie
     */
    public int getWordCount(){
        return wordCount;
    }

    /**
     * Returns the number of nodes in the trie
     *
     * @return The number of nodes in the trie
     */
    public int getNodeCount(){
        return nodeCount;
    }


//COMMANDS

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count
     *
     * @param word The word being added to the trie
     */
    public void add(String word){
        addRecursive(word.toLowerCase(), root);
    }

    /**
     * Adds the first character of <code>word</code> to the <code>nodes</code> array in <code>currentNode</code>.
     * @param word The string to be added to the trie.
     * @param currentNode The node to start adding <code>word</code> from.
     */
    private void addRecursive(String word, Node currentNode){
        if(word.length() == 0){
            if(currentNode.count == 0){
               ++wordCount;
            }
            ++currentNode.count;
            return;
        }
        else {
            char c = word.charAt(0);
            int index = (int) c - (int) 'a';
            if (currentNode.nodes[index] == null) {
                currentNode.nodes[index] = new Node(c, currentNode);
                ++nodeCount;
            }
            addRecursive(word.substring(1), currentNode.nodes[index]);
        }
    }

    /**
     * Returns the Node that represents the best word that is one delete distance away from the <code>inputWord</code>.
     * @param inputWord The starting word to start the delete from.
     * @return Node representing the best word that is one delete distance away.
     */
    private Node oneDeleteAway(String inputWord){
        Node bestNode = root;

        for(int i = 0; i < inputWord.length(); ++i){
            StringBuilder sb = new StringBuilder(inputWord);
            sb.deleteCharAt(i);
            if(!secondPass) {
                oneEditAway.add(sb.toString());
            }
            Node result = (Node)find(sb.toString());
            if(bestNode.compareTo(result) > 0){
                bestNode = result;
            }
        }
        return bestNode;
    }

    /**
     * Returns the Node that represents the best word that is one transpose distance away fromt the <code>inputWord</code>.
     * @param inputWord The starting word to start the transpose from.
     * @return Node representing the best word that is one transpose distance away.
     */
    private Node oneTransposeAway(String inputWord){
        Node bestNode = root;

        for(int i = 0; i < inputWord.length() - 1; ++i){
            StringBuilder sb = new StringBuilder(inputWord);
            char c = sb.charAt(i);
            sb.deleteCharAt(i);
            sb.insert(i+1,c);
            if(!secondPass) {
                oneEditAway.add(sb.toString());
            }
            Node result = (Node)find(sb.toString());
            if(bestNode.compareTo(result) > 0){
                bestNode = result;
            }
        }

        return bestNode;
    }

    /**
     * Returns the Node that represents the best word that is one alter distance away from the <code>inputWord</code>.
     * @param inputWord The starting word to start the alter from.
     * @return Node representing the best word that is one alter distance away.
     */
    private Node oneAlterAway(String inputWord){
        Node bestNode = root;

        for(int i = 0; i < inputWord.length(); ++i){
            for(int j = 0; j < 26; ++j){
                StringBuilder sb = new StringBuilder(inputWord);
                sb.deleteCharAt(i);
                sb.insert(i, (char)(j + (int)'a'));
                if(sb.toString().compareTo(inputWord) != 0){
                    if(!secondPass) {
                        oneEditAway.add(sb.toString());
                    }
                    Node result = (Node)find(sb.toString());
                    if(bestNode.compareTo(result) > 0){
                        bestNode = result;
                    }
                }
            }
        }

        return bestNode;
    }

    /**
     * Returns the Node that represents the best word that is one insert away from the <code>inputWord</code>.
     * @param inputWord The starting word to start the insert from.
     * @return Node representing the best word that is one insert distance away.
     */
    private Node oneInsertAway(String inputWord){
        Node bestNode = root;

        for(int i = 0; i < inputWord.length() + 1; ++i){
            for(int j = 0; j < 26; ++j){
                StringBuilder sb = new StringBuilder(inputWord);
                sb.insert(i, (char)(j + (int)'a'));
                if(!secondPass) {
                    oneEditAway.add(sb.toString());
                }
                Node result = (Node)find(sb.toString());
                if(bestNode.compareTo(result) > 0){
                    bestNode = result;
                }
            }
        }

        return bestNode;
    }

    /**
     * Searches the trie for the specified word     *
     * @param word The word being searched for     *
     * @return A reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public INode find(String word){
        return findRecursive(word.toLowerCase(), root);
    }

    /**
     * Searches recursively in the trie for the specified word.
     * @param word The word being searched for.
     * @param currentNode The Node to start the search from.
     * @return A reference to the trie node that represents the word,
     *          or null if the word is not in the trie.
     */
    private Node findRecursive(String word, Node currentNode){
        if(word.length() == 0){
            if(currentNode.count > 0){
                return currentNode;
            }
            return null;
        }
        else{
            char c = word.charAt(0);
            int index = (int)c - (int)'a';
            if (currentNode.nodes[index] == null){
                return null;
            }
            else{
                return findRecursive(word.substring(1), currentNode.nodes[index]);
            }
        }
    }

    /**
     * Returns the best word for <code>inputWord</code>. It returns the same word
     *      if <code>inputWord</code> is found. It returns "" if no good word is
     *      found.
     * @param inputWord The word to be checked.
     * @return String representing the best word.
     */
    public String suggestSimilarWord(String inputWord){
        oneEditAway.clear();
        secondPass = false;
        if(find(inputWord) != null){
            return inputWord;
        }
        else{
            Node bestNode = root;
            bestNode = oneDeleteAway(inputWord);
            Node tempNode = oneTransposeAway(inputWord);
            if(bestNode.compareTo(tempNode) > 0){
                bestNode = tempNode;
            }
            tempNode = oneAlterAway(inputWord);
            if(bestNode.compareTo(tempNode) > 0){
                bestNode = tempNode;
            }
            tempNode = oneInsertAway(inputWord);
            if(bestNode.compareTo(tempNode) > 0){
                bestNode = tempNode;
            }

            if(bestNode.equals(root)){
                secondPass = true;
                for(String word : oneEditAway){
                    tempNode = oneDeleteAway(word);
                    if(bestNode.compareTo(tempNode) > 0){
                        bestNode = tempNode;
                    }
                    tempNode = oneTransposeAway(word);
                    if(bestNode.compareTo(tempNode) > 0){
                        bestNode = tempNode;
                    }
                    tempNode = oneAlterAway(word);
                    if(bestNode.compareTo(tempNode) > 0){
                        bestNode = tempNode;
                    }
                    tempNode = oneInsertAway(word);
                    if(bestNode.compareTo(tempNode) > 0){
                        bestNode = tempNode;
                    }
                }
            }

            return bestNode.toString();
        }
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     */
    @Override
    public String toString(){
        return print(root);
    }

    /**
     * Returns a String that represents all words under the <code>currNode</code> alphabetically
     *      with a "\n" between words.
     * @param currNode  Node representing the starting point.
     * @return String that represents all words under <code>currNode</code>.
     */
    private String print(Node currNode){
        StringBuilder sb = new StringBuilder();
        if(currNode.count > 0){
            sb.append(currNode.toString());
            sb.append("\n");
        }
        for(int i = 0; i < 26; ++i){
            if(currNode.nodes[i] != null){
                sb.append(print(currNode.nodes[i]));
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trie trie = (Trie) o;

        if (nodeCount != trie.nodeCount) return false;
        if (wordCount != trie.wordCount) return false;
        return root.equals(trie.root);
    }

    @Override
    public int hashCode() {
        return 31 * nodeCount * wordCount;
    }

    
    class Node implements ITrie.INode {
        public int count;
        public Node[] nodes;
        public char character;
        public Node parent;

        public Node(char c, Node p){
            character = c;
            count = 0;
            nodes = new Node[26];
            parent = p;
        }
        /**
         * Returns the frequency count for the word represented by the node
         *
         * @return The frequency count for the word represented by the node
         */
        public int getValue(){
            return count;
        }

        public String toString(){
            if(character == '@'){
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(parent.toString());
            sb.append(character);
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (count != node.count) return false;
            if (character != node.character) return false;
            for(int i = 0; i < 26; ++i){
                if(nodes[i] == null){
                    if(node.nodes[i] != null){
                        return false;
                    }
                }
                else{
                    if(node.nodes[i] == null){
                        return false;
                    }
                    else{
                        if(!nodes[i].equals(node.nodes[i])){
                            return false;
                        }
                    }
                }
            }

            return true;
        }

        private int compareTo(Node n){
            if(n == null){
                return -1;
            }
            if(count > n.count){
                return -1;
            }
            else if(count == n.count){
                return toString().compareTo(n.toString());
            }
            else{
                return 1;
            }
        }
    }
}
