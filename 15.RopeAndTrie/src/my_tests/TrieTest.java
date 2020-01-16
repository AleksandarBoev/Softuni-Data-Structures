package my_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import trie.Trie;

public class TrieTest {
    Trie<Integer> trie;

    @Before
    public void init() {
        trie = new Trie<>();
    }

    @Test
    public void remove_whenAddingOneElement_removeIt() {
        trie.insert("Pesho", 30);
        trie.remove("Pesho");

        Assert.assertFalse(trie.contains("Pesho")); //contains works correctly for sure
    }

    @Test
    public void remove_whenAddingElementsWithOverlappingCharactersAndRemovingLongerElement_removeOnlyTheCharactersAfterPrefix() {
        trie.insert("Pesho", 30);
        trie.insert("Pe", 30);

        trie.remove("Pesho");

        Assert.assertFalse(trie.contains("Pesho"));
        Assert.assertTrue(trie.contains("Pe"));
    }

    @Test
    public void remove_whenAddingElementsWithOverlappingCharactersAndRemovingSmallerElement_dontRemoveAnyCharacters() {
        trie.insert("Pesho", 30);
        trie.insert("Peshoslav", 30);

        trie.remove("Pesho");

        Assert.assertFalse(trie.contains("Pesho"));
        Assert.assertTrue(trie.contains("Peshoslav"));
    }

    @Test
    public void remove_whenAddingElementsWithSamePrefixesAndRemovingElement_dontRemovePrefixCharacters() {
        trie.insert("Pesho", 30);
        trie.insert("Petar", 30);

        trie.remove("Pesho");

        Assert.assertFalse(trie.contains("Pesho"));
        Assert.assertTrue(trie.contains("Petar"));
    }

    @Test
    public void remove_whenAddingTwoCompletelyDifferentKeys_removeWorksCorrectly() {
        trie.insert("Pesho", 30);
        trie.insert("Gosho", 30);

        trie.remove("Pesho");

        Assert.assertFalse(trie.contains("Pesho"));
        Assert.assertTrue(trie.contains("Gosho"));
    }

    @Test
    public void remove_whenRemovingNonExistingElement_nothingHappens() {
        trie.remove("Pesho");
    }
}