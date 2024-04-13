package bPlusTree;

import java.util.*;
import java.io.*;

public class bplustree implements Serializable {
    int m;
    InternalNode root;
    LeafNode firstLeaf;

    /**
     * Constructor
     * 
     * @param m: the order (fanout) of the B+ tree
     */
    public bplustree(int m) {
        this.m = m;
        this.root = null;
    }

    /* ~~~~~~~~~~~~~~~~ HELPER FUNCTIONS ~~~~~~~~~~~~~~~~ */

    /**
     * This method performs a standard binary search on a sorted
     * DictionaryPair[] and returns the index of the dictionary pair
     * with target key t if found. Otherwise, this method returns a negative
     * value.
     * 
     * @param dps: list of dictionary pairs sorted by key within leaf node
     * @param t:   target key value of dictionary pair being searched for
     * @return index of the target value if found, else a negative value
     */
    private int binarySearch(DictionaryPair[] dps, int numPairs, Object t) {
        Comparator<DictionaryPair> c = new Comparator<DictionaryPair>() {
            @Override
            public int compare(DictionaryPair o1, DictionaryPair o2) {
                if (o1.key.getClass().getSimpleName().equals("Integer")) {
                    Integer a = (Integer) o1.key;
                    Integer b = (Integer) o2.key;
                    return a - b;
                } else if (o1.key.getClass().getSimpleName().equals("Double")) {
                    Double a = (Double) o1.key;
                    Double b = (Double) o2.key;
                    return a.compareTo(b);
                } else {
                    String a = (String) o1.key;
                    String b = (String) o2.key;
                    return a.compareTo(b);

                }
            }
        };
        return Arrays.binarySearch(dps, 0, numPairs, new DictionaryPair(t, null), c);
    }

    // #create a compare method to compare keys
    public static int compare(Object o1, Object o2) {
        if (o1.getClass().getSimpleName().equals("Integer")) {
            Integer a = (Integer) o1;
            Integer b = (Integer) o2;
            return a - b;
        } else if (o1.getClass().getSimpleName().equals("Double")) {
            Double a = (Double) o1;
            Double b = (Double) o2;
            return a.compareTo(b);
        } else {
            String a = (String) o1;
            String b = (String) o2;
            return a.compareTo(b);

        }
    }

    /**
     * This method starts at the root of the B+ tree and traverses down the
     * tree via key comparisons to the corresponding leaf node that holds 'key'
     * within its dictionary.
     * 
     * @param key: the unique key that lies within the dictionary of a LeafNode
     *             object
     * @return the LeafNode object that contains the key within its dictionary
     */
    private LeafNode findLeafNode(Object key) {

        // Initialize keys and index variable
        Object[] keys = this.root.keys;
        int i;

        // Find next node on path to appropriate leaf node
        for (i = 0; i < this.root.degree - 1; i++) {
            if (compare(key, keys[i]) < 0) {
                break;
            }
        }

        /*
         * Return node if it is a LeafNode object,
         * otherwise repeat the search function a level down
         */
        Node child = this.root.childPointers[i];
        if (child instanceof LeafNode) {
            return (LeafNode) child;
        } else {
            return findLeafNode((InternalNode) child, key);
        }
    }

    private LeafNode findLeafNode(InternalNode node, Object key) {

        // Initialize keys and index variable
        Object[] keys = node.keys;
        int i;

        // Find next node on path to appropriate leaf node
        for (i = 0; i < node.degree - 1; i++) {
            if (compare(key, keys[i]) < 0) {
                break;
            }
        }

        /*
         * Return node if it is a LeafNode object,
         * otherwise repeat the search function a level down
         */
        Node childNode = node.childPointers[i];
        if (childNode instanceof LeafNode) {
            return (LeafNode) childNode;
        } else {
            return findLeafNode((InternalNode) node.childPointers[i], key);
        }
    }

    /**
     * Given a list of pointers to Node objects, this method returns the index of
     * the pointer that points to the specified 'node' LeafNode object.
     * 
     * @param pointers: a list of pointers to Node objects
     * @param node:     a specific pointer to a LeafNode
     * @return (int) index of pointer in list of pointers
     */
    private int findIndexOfPointer(Node[] pointers, LeafNode node) {
        int i;
        for (i = 0; i < pointers.length; i++) {
            if (pointers[i] == node) {
                break;
            }
        }
        return i;
    }

    /**
     * This is a simple method that returns the midpoint (or lower bound
     * depending on the context of the method invocation) of the max degree m of
     * the B+ tree.
     * 
     * @return (int) midpoint/lower bound
     */
    private int getMidpoint() {
        return (int) Math.ceil((this.m + 1) / 2.0) - 1;
    }

    /**
     * Given a deficient InternalNode in, this method remedies the deficiency
     * through borrowing and merging.
     * 
     * @param in: a deficient InternalNode
     */
    private void handleDeficiency(InternalNode in) {

        InternalNode sibling;
        InternalNode parent = in.parent;

        // Remedy deficient root node
        if (this.root == in) {
            for (int i = 0; i < in.childPointers.length; i++) {
                if (in.childPointers[i] != null) {
                    if (in.childPointers[i] instanceof InternalNode) {
                        this.root = (InternalNode) in.childPointers[i];
                        this.root.parent = null;
                    } else if (in.childPointers[i] instanceof LeafNode) {
                        this.root = null;
                    }
                }
            }
        }

        // Borrow:
        else if (in.leftSibling != null && in.leftSibling.isLendable()) {
            sibling = in.leftSibling;
        } else if (in.rightSibling != null && in.rightSibling.isLendable()) {
            sibling = in.rightSibling;

            // Copy 1 key and pointer from sibling (atm just 1 key)
            Object borrowedKey = sibling.keys[0];
            Node pointer = sibling.childPointers[0];

            // Copy root key and pointer into parent
            in.keys[in.degree - 1] = parent.keys[0];
            in.childPointers[in.degree] = pointer;

            // Copy borrowedKey into root
            parent.keys[0] = borrowedKey;

            // Delete key and pointer from sibling
            sibling.removePointer(0);
            Arrays.sort(sibling.keys);
            sibling.removePointer(0);
            shiftDown(in.childPointers, 1);
        }

        // Merge:
        else if (in.leftSibling != null && in.leftSibling.isMergeable()) {

        } else if (in.rightSibling != null && in.rightSibling.isMergeable()) {
            sibling = in.rightSibling;

            // Copy rightmost key in parent to beginning of sibling's keys &
            // delete key from parent
            sibling.keys[sibling.degree - 1] = parent.keys[parent.degree - 2];
            Arrays.sort(sibling.keys, 0, sibling.degree);
            parent.keys[parent.degree - 2] = null;

            // Copy in's child pointer over to sibling's list of child pointers
            for (int i = 0; i < in.childPointers.length; i++) {
                if (in.childPointers[i] != null) {
                    sibling.prependChildPointer(in.childPointers[i]);
                    in.childPointers[i].parent = sibling;
                    in.removePointer(i);
                }
            }

            // Delete child pointer from grandparent to deficient node
            parent.removePointer(in);

            // Remove left sibling
            sibling.leftSibling = in.leftSibling;
        }

        // Handle deficiency a level up if it exists
        if (parent != null && parent.isDeficient()) {
            handleDeficiency(parent);
        }
    }

    /**
     * This is a simple method that determines if the B+ tree is empty or not.
     * 
     * @return a boolean indicating if the B+ tree is empty or not
     */
    private boolean isEmpty() {
        return firstLeaf == null;
    }

    /**
     * This method performs a standard linear search on a sorted
     * DictionaryPair[] and returns the index of the first null entry found.
     * Otherwise, this method returns a -1. This method is primarily used in
     * place of binarySearch() when the target t = null.
     * 
     * @param dps: list of dictionary pairs sorted by key within leaf node
     * @return index of the target value if found, else -1
     */
    private int linearNullSearch(DictionaryPair[] dps) {
        for (int i = 0; i < dps.length; i++) {
            if (dps[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method performs a standard linear search on a list of Node[] pointers
     * and returns the index of the first null entry found. Otherwise, this
     * method returns a -1. This method is primarily used in place of
     * binarySearch() when the target t = null.
     * 
     * @param pointers: list of Node[] pointers
     * @return index of the target value if found, else -1
     */
    private int linearNullSearch(Node[] pointers) {
        for (int i = 0; i < pointers.length; i++) {
            if (pointers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method is used to shift down a set of pointers that are prepended
     * by null values.
     * 
     * @param pointers: the list of pointers that are to be shifted
     * @param amount:   the amount by which the pointers are to be shifted
     */
    private void shiftDown(Node[] pointers, int amount) {
        Node[] newPointers = new Node[this.m + 1];
        for (int i = amount; i < pointers.length; i++) {
            newPointers[i - amount] = pointers[i];
        }
        pointers = newPointers;
    }

    /**
     * This is a specialized sorting method used upon lists of DictionaryPairs
     * that may contain interspersed null values.
     * 
     * @param dictionary: a list of DictionaryPair objects
     */
    private void sortDictionary(DictionaryPair[] dictionary) {
        Arrays.sort(dictionary, new Comparator<DictionaryPair>() {
            @Override
            public int compare(DictionaryPair o1, DictionaryPair o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * This method modifies the InternalNode 'in' by removing all pointers within
     * the childPointers after the specified split. The method returns the removed
     * pointers in a list of their own to be used when constructing a new
     * InternalNode sibling.
     * 
     * @param in:    an InternalNode whose childPointers will be split
     * @param split: the index at which the split in the childPointers begins
     * @return a Node[] of the removed pointers
     */
    private Node[] splitChildPointers(InternalNode in, int split) {

        Node[] pointers = in.childPointers;
        Node[] halfPointers = new Node[this.m + 1];

        // Copy half of the values into halfPointers while updating original keys
        for (int i = split + 1; i < pointers.length; i++) {
            halfPointers[i - split - 1] = pointers[i];
            in.removePointer(i);
        }

        return halfPointers;
    }

    /**
     * This method splits a single dictionary into two dictionaries where all
     * dictionaries are of equal length, but each of the resulting dictionaries
     * holds half of the original dictionary's non-null values. This method is
     * primarily used when splitting a node within the B+ tree. The dictionary of
     * the specified LeafNode is modified in place. The method returns the
     * remainder of the DictionaryPairs that are no longer within ln's dictionary.
     * 
     * @param ln:    list of DictionaryPairs to be split
     * @param split: the index at which the split occurs
     * @return DictionaryPair[] of the two split dictionaries
     */
    private DictionaryPair[] splitDictionary(LeafNode ln, int split) {

        DictionaryPair[] dictionary = ln.dictionary;

        /*
         * Initialize two dictionaries that each hold half of the original
         * dictionary values
         */
        DictionaryPair[] halfDict = new DictionaryPair[this.m];

        // Copy half of the values into halfDict
        for (int i = split; i < dictionary.length; i++) {
            halfDict[i - split] = dictionary[i];
            ln.delete(i);
        }

        return halfDict;
    }

    /**
     * When an insertion into the B+ tree causes an overfull node, this method
     * is called to remedy the issue, i.e. to split the overfull node. This method
     * calls the sub-methods of splitKeys() and splitChildPointers() in order to
     * split the overfull node.
     * 
     * @param in: an overfull InternalNode that is to be split
     */
    private void splitInternalNode(InternalNode in) {

        // Acquire parent
        InternalNode parent = in.parent;

        // Split keys and pointers in half
        int midpoint = getMidpoint();
        Object newParentKey = in.keys[midpoint];
        Object[] halfKeys = splitKeys(in.keys, midpoint);
        Node[] halfPointers = splitChildPointers(in, midpoint);

        // Change degree of original InternalNode in
        in.degree = linearNullSearch(in.childPointers);

        // Create new sibling internal node and add half of keys and pointers
        InternalNode sibling = new InternalNode(this.m, halfKeys, halfPointers);
        for (Node pointer : halfPointers) {
            if (pointer != null) {
                pointer.parent = sibling;
            }
        }

        // Make internal nodes siblings of one another
        sibling.rightSibling = in.rightSibling;
        if (sibling.rightSibling != null) {
            sibling.rightSibling.leftSibling = sibling;
        }
        in.rightSibling = sibling;
        sibling.leftSibling = in;

        if (parent == null) {

            // Create new root node and add midpoint key and pointers
            Object[] keys = new Object[this.m];
            keys[0] = newParentKey;
            InternalNode newRoot = new InternalNode(this.m, keys);
            newRoot.appendChildPointer(in);
            newRoot.appendChildPointer(sibling);
            this.root = newRoot;

            // Add pointers from children to parent
            in.parent = newRoot;
            sibling.parent = newRoot;

        } else {

            // Add key to parent
            parent.keys[parent.degree - 1] = newParentKey;
            Arrays.sort(parent.keys, 0, parent.degree);

            // Set up pointer to new sibling
            int pointerIndex = parent.findIndexOfPointer(in) + 1;
            parent.insertChildPointer(sibling, pointerIndex);
            sibling.parent = parent;
        }
    }

    /**
     * This method modifies a list of Integer-typed objects that represent keys
     * by removing half of the keys and returning them in a separate Integer[].
     * This method is used when splitting an InternalNode object.
     * 
     * @param keys:  a list of Integer objects
     * @param split: the index where the split is to occur
     * @return Integer[] of removed keys
     */
    private Object[] splitKeys(Object[] keys, int split) {

        Object[] halfKeys = new Object[this.m];

        // Remove split-indexed value from keys
        keys[split] = null;

        // Copy half of the values into halfKeys while updating original keys
        for (int i = split + 1; i < keys.length; i++) {
            halfKeys[i - split - 1] = keys[i];
            keys[i] = null;
        }

        return halfKeys;
    }

    /* ~~~~~~~~~~~~~~~~ API: DELETE, INSERT, SEARCH ~~~~~~~~~~~~~~~~ */

    /**
     * Given a key, this method will remove the dictionary pair with the
     * corresponding key from the B+ tree.
     * 
     * @param key: an integer key that corresponds with an existing dictionary
     *             pair
     */
    public void delete(Object key, String pageNum) {
        if (isEmpty()) {

            /* Flow of execution goes here when B+ tree has no dictionary pairs */

            System.err.println("Invalid Delete: The B+ tree is currently empty.");

        } else {

            // Get leaf node and attempt to find index of key to delete
            LeafNode ln = (this.root == null) ? this.firstLeaf : findLeafNode(key);
            int dpIndex = binarySearch(ln.dictionary, ln.numPairs, key);

            if (dpIndex < 0) {

                /* Flow of execution goes here when key is absent in B+ tree */

                System.err.println("Invalid Delete: Key unable to be found.");

            } else {

                // Successfully delete the dictionary pair
                ln.deleteValue(dpIndex, pageNum);

                // Check for deficiencies
                if (ln.isDeficient()) {

                    LeafNode sibling;
                    InternalNode parent = ln.parent;

                    // Borrow: First, check the left sibling, then the right sibling
                    if (ln.leftSibling != null &&
                            ln.leftSibling.parent == ln.parent &&
                            ln.leftSibling.isLendable()) {

                        sibling = ln.leftSibling;
                        DictionaryPair borrowedDP = sibling.dictionary[sibling.numPairs - 1];

                        /*
                         * Insert borrowed dictionary pair, sort dictionary,
                         * and delete dictionary pair from sibling
                         */
                        ln.insert(borrowedDP);
                        sortDictionary(ln.dictionary);
                        sibling.delete(sibling.numPairs - 1);

                        // Update key in parent if necessary
                        int pointerIndex = findIndexOfPointer(parent.childPointers, ln);
                        if (!(compare(borrowedDP.key, parent.keys[pointerIndex - 1]) >= 0)) {
                            parent.keys[pointerIndex - 1] = ln.dictionary[0].key;
                        }

                    } else if (ln.rightSibling != null &&
                            ln.rightSibling.parent == ln.parent &&
                            ln.rightSibling.isLendable()) {

                        sibling = ln.rightSibling;
                        DictionaryPair borrowedDP = sibling.dictionary[0];

                        /*
                         * Insert borrowed dictionary pair, sort dictionary,
                         * and delete dictionary pair from sibling
                         */
                        ln.insert(borrowedDP);
                        sibling.delete(0);
                        sortDictionary(sibling.dictionary);

                        // Update key in parent if necessary
                        int pointerIndex = findIndexOfPointer(parent.childPointers, ln);
                        if (!(compare(borrowedDP.key, parent.keys[pointerIndex]) < 0)) {
                            parent.keys[pointerIndex] = sibling.dictionary[0].key;
                        }

                    }

                    // Merge: First, check the left sibling, then the right sibling
                    else if (ln.leftSibling != null &&
                            ln.leftSibling.parent == ln.parent &&
                            ln.leftSibling.isMergeable()) {

                        sibling = ln.leftSibling;
                        int pointerIndex = findIndexOfPointer(parent.childPointers, ln);

                        // Remove key and child pointer from parent
                        parent.removeKey(pointerIndex - 1);
                        parent.removePointer(ln);

                        // Update sibling pointer
                        sibling.rightSibling = ln.rightSibling;

                        // Check for deficiencies in parent
                        if (parent.isDeficient()) {
                            handleDeficiency(parent);
                        }

                    } else if (ln.rightSibling != null &&
                            ln.rightSibling.parent == ln.parent &&
                            ln.rightSibling.isMergeable()) {

                        sibling = ln.rightSibling;
                        int pointerIndex = findIndexOfPointer(parent.childPointers, ln);

                        // Remove key and child pointer from parent
                        parent.removeKey(pointerIndex);
                        parent.removePointer(pointerIndex);

                        // Update sibling pointer
                        sibling.leftSibling = ln.leftSibling;
                        if (sibling.leftSibling == null) {
                            firstLeaf = sibling;
                        }

                        if (parent.isDeficient()) {
                            handleDeficiency(parent);
                        }
                    }

                } else if (this.root == null && this.firstLeaf.numPairs == 0) {

                    /*
                     * Flow of execution goes here when the deleted dictionary
                     * pair was the only pair within the tree
                     */

                    // Set first leaf as null to indicate B+ tree is empty
                    this.firstLeaf = null;

                } else {

                    /*
                     * The dictionary of the LeafNode object may need to be
                     * sorted after a successful delete
                     */
                    sortDictionary(ln.dictionary);

                }
            }
        }
    }

    /**
     * Given an integer key and floating point value, this method inserts a
     * dictionary pair accordingly into the B+ tree.
     * 
     * @param key:   an integer key to be used in the dictionary pair
     * @param value: a floating point number to be used in the dictionary pair
     */
    public void insert(Object key, String value) {
        if (isEmpty()) {

            /* Flow of execution goes here only when first insert takes place */

            // Create leaf node as first node in B plus tree (root is null)
            LeafNode ln = new LeafNode(this.m, new DictionaryPair(key, value));

            // Set as first leaf node (can be used later for in-order leaf traversal)
            this.firstLeaf = ln;

        } else {

            // Find leaf node to insert into
            LeafNode ln = (this.root == null) ? this.firstLeaf : findLeafNode(key);

            // Insert into leaf node fails if node becomes overfull
            if (!ln.insert(new DictionaryPair(key, value))) {

                // Sort all the dictionary pairs with the included pair to be inserted
                ln.dictionary[ln.numPairs] = new DictionaryPair(key, value);
                ln.numPairs++;
                sortDictionary(ln.dictionary);

                // Split the sorted pairs into two halves
                int midpoint = getMidpoint();
                DictionaryPair[] halfDict = splitDictionary(ln, midpoint);

                if (ln.parent == null) {

                    /* Flow of execution goes here when there is 1 node in tree */

                    // Create internal node to serve as parent, use dictionary midpoint key
                    Object[] parent_keys = new Object[this.m];
                    parent_keys[0] = halfDict[0].key;
                    InternalNode parent = new InternalNode(this.m, parent_keys);
                    ln.parent = parent;
                    parent.appendChildPointer(ln);

                } else {

                    /* Flow of execution goes here when parent exists */

                    // Add new key to parent for proper indexing
                    Object newParentKey = halfDict[0].key;
                    // DEBUGGING PURPOSES
                    // System.out.println("Inserting key of type: " + newParentKey.getClass());
                    // System.out.println("Array type: " +
                    // ln.parent.keys.getClass().getComponentType());
                    ln.parent.keys[ln.parent.degree - 1] = newParentKey;
                    Arrays.sort(ln.parent.keys, 0, ln.parent.degree);
                    // Comparator:
                    Arrays.sort(ln.parent.keys, 0, ln.parent.degree, new Comparator<Object>() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            // Your comparison logic here
                            if (o1.getClass().getSimpleName().equals("Integer")) {
                                Integer a = (Integer) o1;
                                Integer b = (Integer) o2;
                                return a - b;
                            } else if (o1.getClass().getSimpleName().equals("Double")) {
                                Double a = (Double) o1;
                                Double b = (Double) o2;
                                return a.compareTo(b);
                            } else {
                                String a = (String) o1;
                                String b = (String) o2;
                                return a.compareTo(b);
                            }
                        }

                    });
                }

                // Create new LeafNode that holds the other half
                LeafNode newLeafNode = new LeafNode(this.m, halfDict, ln.parent);

                // Update child pointers of parent node
                int pointerIndex = ln.parent.findIndexOfPointer(ln) + 1;
                ln.parent.insertChildPointer(newLeafNode, pointerIndex);

                // Make leaf nodes siblings of one another
                newLeafNode.rightSibling = ln.rightSibling;
                if (newLeafNode.rightSibling != null) {
                    newLeafNode.rightSibling.leftSibling = newLeafNode;
                }
                ln.rightSibling = newLeafNode;
                newLeafNode.leftSibling = ln;

                if (this.root == null) {

                    // Set the root of B+ tree to be the parent
                    this.root = ln.parent;

                } else {

                    /*
                     * If parent is overfull, repeat the process up the tree,
                     * until no deficiencies are found
                     */
                    InternalNode in = ln.parent;
                    // DEBUGGING
                    // System.out.println("degree: " + in.degree + " maxdegree:" + in.maxDegree);
                    // System.out.println(in);
                    while (in != null) {
                        if (in.isOverfull()) {
                            splitInternalNode(in);
                        } else {
                            break;
                        }
                        in = in.parent;
                    }
                }
            }
        }
    }

    /**
     * Given a key, this method returns the value associated with the key
     * within a dictionary pair that exists inside the B+ tree.
     * 
     * @param key: the key to be searched within the B+ tree
     * @return the vector of pages associated with the key within the B+ tree
     */
    public Vector<String> search(Object key) {

        // If B+ tree is completely empty, simply return null
        if (isEmpty()) {
            return null;
        }

        // Find leaf node that holds the dictionary key
        LeafNode ln = (this.root == null) ? this.firstLeaf : findLeafNode(key);

        // Perform binary search to find index of key within dictionary
        DictionaryPair[] dps = ln.dictionary;
        int index = binarySearch(dps, ln.numPairs, key);

        // If index negative, the key doesn't exist in B+ tree
        if (index < 0) {
            return null;
        } else {
            return dps[index].values;
        }
    }

    /*
     * This method is used for select query with lower bound inclusive.
     * 
     */
    public HashSet<String> rangeSearchWithLowerBoundInclusive(Object lowerBound) {
        HashSet<String> pages = new HashSet<>();
        // traverse tree levels to get first node
        LeafNode currNode = findLeafNodeShouldContainKey(lowerBound);
        while (currNode != null) {
            DictionaryPair[] dps = currNode.dictionary;
            for (DictionaryPair dp : dps) {
                // Special Case of deletion
                if (currNode.numPairs == 0 || dp == null) {
                    continue;
                }
                // Check if the key falls within the lower bound
                if (compare(lowerBound, dp.key) <= 0) {
                    for (String page : dp.values) {
                        pages.add(page);
                    }
                } else {
                    break;
                }
            }
            currNode = currNode.rightSibling;
        }

        return pages;
    }

    /*
     * This method is used for select query with lower bound exclusive.
     * Uses the inclusive logic and adds a remove condition.
     */
    public HashSet<String> rangeSearchWithLowerBoundExclusive(Object lowerBound) {
        HashSet<String> pages = new HashSet<>();
        // traverse tree levels to get first node
        LeafNode currNode = findLeafNodeShouldContainKey(lowerBound);
        while (currNode != null) {
            DictionaryPair[] dps = currNode.dictionary;
            for (DictionaryPair dp : dps) {
                // Special Case of deletion
                if (currNode.numPairs == 0 || dp == null) {
                    continue;
                }
                // Check if the key falls within the lower bound
                if (compare(lowerBound, dp.key) < 0) {
                    for (String page : dp.values) {
                        pages.add(page);
                    }
                } else {
                    break;
                }
            }
            currNode = currNode.rightSibling;
        }

        return pages;
    }

    /*
     * This method is used for select query with upper bound inclusive.
     */
    public HashSet<String> rangeSearchWithUpperBoundInclusive(Object upperBound) {
        HashSet<String> pages = new HashSet<>();
        // Traverse the B+ tree to find pages within the specified range
        LeafNode currNode = this.firstLeaf;
        while (currNode != null) {
            DictionaryPair[] dps = currNode.dictionary;
            for (DictionaryPair dp : dps) {
                // Special Case of deletion
                if (currNode.numPairs == 0 || dp == null) {
                    continue;
                }
                // Check if the key falls within the upper bound
                if (compare(upperBound, dp.key) >= 0) {
                    for (String page : dp.values) {
                        pages.add(page);
                    }
                } else {
                    break;
                }
            }
            currNode = currNode.rightSibling;
        }

        return pages;
    }

    /*
     * This method is used for select query with upper bound exclusive.
     * Uses the inclusive logic and adds a remove condition.
     */

    public HashSet<String> rangeSearchWithUpperBoundExclusive(Object upperBound) {
        HashSet<String> pages = new HashSet<>();
        // Traverse the B+ tree to find pages within the specified range
        LeafNode currNode = this.firstLeaf;
        while (currNode != null) {
            DictionaryPair[] dps = currNode.dictionary;
            for (DictionaryPair dp : dps) {
                // Special Case of deletion
                if (currNode.numPairs == 0 || dp == null) {
                    continue;
                }
                // Check if the key falls within the upper bound
                if (compare(upperBound, dp.key) > 0) {
                    for (String page : dp.values) {
                        pages.add(page);
                    }
                } else {
                    break;
                }
            }
            currNode = currNode.rightSibling;
        }

        return pages;
    }

    /**
     * This method traverses the doubly linked list of the B+ tree and records
     * all values whose associated keys are within the range specified by
     * lowerBound and upperBound.
     * 
     * @param lowerBound: (int) the lower bound of the range
     * @param upperBound: (int) the upper bound of the range
     * @return an ArrayList<Double> that holds all values of dictionary pairs
     *         whose keys are within the specified range
     */
    public ArrayList<String> search(Object lowerBound, Object upperBound) {

        // Instantiate array to hold values
        ArrayList<String> values = new ArrayList<String>();

        // Iterate through the doubly linked list of leaves
        LeafNode currNode = this.firstLeaf;
        while (currNode != null) {

            // Iterate through the dictionary of each node
            DictionaryPair dps[] = currNode.dictionary;
            for (DictionaryPair dp : dps) {

                /*
                 * Stop searching the dictionary once a null value is encountered
                 * as this the indicates the end of non-null values
                 */
                if (dp == null) {
                    break;
                }

                // Include value if its key fits within the provided range
                if ((compare(lowerBound, dp.key) <= 0) && (compare(dp.key, upperBound) <= 0)) {
                    for (int i = 0; i < dp.values.size(); i++)
                        values.add(dp.values.elementAt(i));
                }
            }

            /*
             * Update the current node to be the right sibling,
             * leaf traversal is from left to right
             */
            currNode = currNode.rightSibling;

        }

        return values;
    }

    /**
     * This class represents a general node within the B+ tree and serves as a
     * superclass of InternalNode and LeafNode.
     */
    public class Node implements Serializable {
        InternalNode parent;

        // // TEST
        // public String toString() {
        // return "parent: " + parent.toString();
        // }
    }

    /**
     * This class represents the internal nodes within the B+ tree that traffic
     * all search/insert/delete operations. An internal node only holds keys; it
     * does not hold dictionary pairs.
     */
    private class InternalNode extends Node {
        int maxDegree;
        int minDegree;
        int degree;
        InternalNode leftSibling;
        InternalNode rightSibling;
        Object[] keys;
        Node[] childPointers;

        // TEST
        public String toString() {
            String s = "";
            for (int i = 0; i < keys.length; i++) {
                s += keys[i] + ",";
            }
            return s;

        }

        /**
         * This method appends 'pointer' to the end of the childPointers
         * instance variable of the InternalNode object. The pointer can point to
         * an InternalNode object or a LeafNode object since the formal
         * parameter specifies a Node object.
         * 
         * @param pointer: Node pointer that is to be appended to the
         *                 childPointers list
         */
        private void appendChildPointer(Node pointer) {
            this.childPointers[degree] = pointer;
            this.degree++;
        }

        /**
         * Given a Node pointer, this method will return the index of where the
         * pointer lies within the childPointers instance variable. If the pointer
         * can't be found, the method returns -1.
         * 
         * @param pointer: a Node pointer that may lie within the childPointers
         *                 instance variable
         * @return the index of 'pointer' within childPointers, or -1 if
         *         'pointer' can't be found
         */
        private int findIndexOfPointer(Node pointer) {
            for (int i = 0; i < childPointers.length; i++) {
                if (childPointers[i] == pointer) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Given a pointer to a Node object and an integer index, this method
         * inserts the pointer at the specified index within the childPointers
         * instance variable. As a result of the insert, some pointers may be
         * shifted to the right of the index.
         * 
         * @param pointer: the Node pointer to be inserted
         * @param index:   the index at which the insert is to take place
         */
        private void insertChildPointer(Node pointer, int index) {
            for (int i = degree - 1; i >= index; i--) {
                childPointers[i + 1] = childPointers[i];
            }
            this.childPointers[index] = pointer;
            this.degree++;
        }

        /**
         * This simple method determines if the InternalNode is deficient or not.
         * An InternalNode is deficient when its current degree of children falls
         * below the allowed minimum.
         * 
         * @return a boolean indicating whether the InternalNode is deficient
         *         or not
         */
        private boolean isDeficient() {
            return this.degree < this.minDegree;
        }

        /**
         * This simple method determines if the InternalNode is capable of
         * lending one of its dictionary pairs to a deficient node. An InternalNode
         * can give away a dictionary pair if its current degree is above the
         * specified minimum.
         * 
         * @return a boolean indicating whether or not the InternalNode has
         *         enough dictionary pairs in order to give one away.
         */
        private boolean isLendable() {
            return this.degree > this.minDegree;
        }

        /**
         * This simple method determines if the InternalNode is capable of being
         * merged with. An InternalNode can be merged with if it has the minimum
         * degree of children.
         * 
         * @return a boolean indicating whether or not the InternalNode can be
         *         merged with
         */
        private boolean isMergeable() {
            return this.degree == this.minDegree;
        }

        /**
         * This simple method determines if the InternalNode is considered overfull,
         * i.e. the InternalNode object's current degree is one more than the
         * specified maximum.
         * 
         * @return a boolean indicating if the InternalNode is overfull
         */
        private boolean isOverfull() {
            return this.degree == maxDegree + 1;
        }

        /**
         * Given a pointer to a Node object, this method inserts the pointer to
         * the beginning of the childPointers instance variable.
         * 
         * @param pointer: the Node object to be prepended within childPointers
         */
        private void prependChildPointer(Node pointer) {
            for (int i = degree - 1; i >= 0; i--) {
                childPointers[i + 1] = childPointers[i];
            }
            this.childPointers[0] = pointer;
            this.degree++;
        }

        /**
         * This method sets keys[index] to null. This method is used within the
         * parent of a merging, deficient LeafNode.
         * 
         * @param index: the location within keys to be set to null
         */
        private void removeKey(int index) {
            this.keys[index] = null;
        }

        /**
         * This method sets childPointers[index] to null and additionally
         * decrements the current degree of the InternalNode.
         * 
         * @param index: the location within childPointers to be set to null
         */
        private void removePointer(int index) {
            this.childPointers[index] = null;
            this.degree--;
        }

        /**
         * This method removes 'pointer' from the childPointers instance
         * variable and decrements the current degree of the InternalNode. The
         * index where the pointer node was assigned is set to null.
         * 
         * @param pointer: the Node pointer to be removed from childPointers
         */

        private void removePointer(Node pointer) {
            for (int i = 0; i < childPointers.length; i++) {
                if (childPointers[i] == pointer) {
                    this.childPointers[i] = null;
                    break;
                }
            }
            this.degree--;
        }

        // remove leaf node->parent key is removed
        // private void removePointerSpecial(Node pointer) {
        // for (int i = 0; i < childPointers.length; i++) {
        // if (childPointers[i] == pointer) {
        // this.childPointers[i] = null;
        // // TEST: handle n n+1 violation
        // if (i == 0) {
        // this.removeKey(0);
        // } else {
        // this.removeKey(i - 1);
        // }
        // }
        // }
        // this.degree--;
        // if (this.isDeficient()) {
        // handleDeficiency(this);
        // }
        // }

        /**
         * Constructor
         * 
         * @param m:    the max degree of the InternalNode
         * @param keys: the list of keys that InternalNode is initialized with
         */
        private InternalNode(int m, Object[] keys) {
            this.maxDegree = m;
            this.minDegree = (int) Math.ceil(m / 2.0);
            this.degree = 0;
            this.keys = keys;
            this.childPointers = new Node[this.maxDegree + 1];
        }

        /**
         * Constructor
         * 
         * @param m:        the max degree of the InternalNode
         * @param keys:     the list of keys that InternalNode is initialized with
         * @param pointers: the list of pointers that InternalNode is initialized with
         */
        private InternalNode(int m, Object[] keys, Node[] pointers) {
            this.maxDegree = m;
            this.minDegree = (int) Math.ceil(m / 2.0);
            this.degree = linearNullSearch(pointers);
            this.keys = keys;
            this.childPointers = pointers;
        }
    }

    /**
     * This class represents the leaf nodes within the B+ tree that hold
     * dictionary pairs. The leaf node has no children. The leaf node has a
     * minimum and maximum number of dictionary pairs it can hold, as specified
     * by m, the max degree of the B+ tree. The leaf nodes form a doubly linked
     * list that, i.e. each leaf node has a left and right sibling
     */
    public class LeafNode extends Node {
        int maxNumPairs;
        int minNumPairs;
        int numPairs;
        LeafNode leftSibling;
        LeafNode rightSibling;
        DictionaryPair[] dictionary;

        // TEST
        public String toString() {
            String s = "<";
            for (int i = 0; i < numPairs; i++) {
                s += "(" + dictionary[i].key + "," + dictionary[i].values + ")" + ", ";
            }
            return s.substring(0, s.length() - 2) + ">";

        }

        // for insertione method
        public DictionaryPair[] getDictionary() {
            return dictionary;
        }

        /**
         * Given an index, this method sets the dictionary pair at that index
         * within the dictionary to null.
         * 
         * @param index: the location within the dictionary to be set to null
         */
        public void deleteValue(int index, String pageNum) {
            if (this.dictionary[index].values.size() == 1) {
                // Delete dictionary pair from leaf
                this.dictionary[index] = null;
                // Decrement numPairs
                numPairs--;
            } else {
                if (this.dictionary[index].values.contains(pageNum))
                    this.dictionary[index].values.removeElement(pageNum);
                else
                    System.out.println("Deletion Error: PageNum for given key doesn't exist");
            }
        }

        public void delete(int index) {

            // Delete dictionary pair from leaf
            this.dictionary[index] = null;

            // Decrement numPairs
            numPairs--;
        }

        /**
         * This method attempts to insert a dictionary pair within the dictionary
         * of the LeafNode object. If it succeeds, numPairs increments, the
         * dictionary is sorted, and the boolean true is returned. If the method
         * fails, the boolean false is returned.
         * 
         * @param dp: the dictionary pair to be inserted
         * @return a boolean indicating whether or not the insert was successful
         */
        public boolean insert(DictionaryPair dp) {
            // handle duplicate keys
            for (int i = 0; i < numPairs; i++) {
                if (dictionary[i].key.equals(dp.key)) {
                    dictionary[i].values.add(dp.values.firstElement()); // Add to existing vector
                    return true;
                }
            }
            if (this.isFull()) {

                /* Flow of execution goes here when numPairs == maxNumPairs */

                return false;
            } else {

                // Insert dictionary pair, increment numPairs, sort dictionary
                this.dictionary[numPairs] = dp;
                numPairs++;
                Arrays.sort(this.dictionary, 0, numPairs);

                return true;
            }
        }

        /**
         * This simple method determines if the LeafNode is deficient, i.e.
         * the numPairs within the LeafNode object is below minNumPairs.
         * 
         * @return a boolean indicating whether or not the LeafNode is deficient
         */
        public boolean isDeficient() {
            return numPairs < minNumPairs;
        }

        /**
         * This simple method determines if the LeafNode is full, i.e. the
         * numPairs within the LeafNode is equal to the maximum number of pairs.
         * 
         * @return a boolean indicating whether or not the LeafNode is full
         */
        public boolean isFull() {
            return numPairs == maxNumPairs;
        }

        /**
         * This simple method determines if the LeafNode object is capable of
         * lending a dictionary pair to a deficient leaf node. The LeafNode
         * object can lend a dictionary pair if its numPairs is greater than
         * the minimum number of pairs it can hold.
         * 
         * @return a boolean indicating whether or not the LeafNode object can
         *         give a dictionary pair to a deficient leaf node
         */
        public boolean isLendable() {
            return numPairs > minNumPairs;
        }

        /**
         * This simple method determines if the LeafNode object is capable of
         * being merged with, which occurs when the number of pairs within the
         * LeafNode object is equal to the minimum number of pairs it can hold.
         * 
         * @return a boolean indicating whether or not the LeafNode object can
         *         be merged with
         */
        public boolean isMergeable() {
            return numPairs == minNumPairs;
        }

        /**
         * Constructor
         * 
         * @param m:  order of B+ tree that is used to calculate maxNumPairs and
         *            minNumPairs
         * @param dp: first dictionary pair insert into new node
         */
        public LeafNode(int m, DictionaryPair dp) {
            this.maxNumPairs = m - 1;
            this.minNumPairs = (int) (Math.ceil(m / 2) - 1);
            this.dictionary = new DictionaryPair[m];
            this.numPairs = 0;
            this.insert(dp);
        }

        public int getNumPairs() {
            return numPairs;
        }

        /**
         * Constructor
         * 
         * @param dps:    list of DictionaryPair objects to be immediately inserted
         *                into new LeafNode object
         * @param m:      order of B+ tree that is used to calculate maxNumPairs and
         *                minNumPairs
         * @param parent: parent of newly created child LeafNode
         */
        public LeafNode(int m, DictionaryPair[] dps, InternalNode parent) {
            this.maxNumPairs = m - 1;
            this.minNumPairs = (int) (Math.ceil(m / 2) - 1);
            this.dictionary = dps;
            this.numPairs = linearNullSearch(dps);
            this.parent = parent;
        }
    }

    /**
     * This class represents a dictionary pair that is to be contained within the
     * leaf nodes of the B+ tree. The class implements the Comparable interface
     * so that the DictionaryPair objects can be sorted later on.
     */
    public class DictionaryPair implements Comparable<DictionaryPair>, Serializable {
        Object key;
        Vector<String> values;

        /**
         * Constructor
         * 
         * @param key:   the key of the key-value pair
         * @param value: the value of the key-value pair
         */
        public DictionaryPair(Object key, String values) {
            this.key = key;
            this.values = new Vector<>();
            this.values.add(values); // first value in vector
        }

        public Vector<String> getValues() {
            return values;
        }

        /**
         * This is a method that allows comparisons to take place between
         * DictionaryPair objects in order to sort them later on
         * 
         * @param o
         * @return
         */
        @Override
        public int compareTo(DictionaryPair o) {
            if (o.key.getClass().getSimpleName().equals("Integer")) {
                Integer a = (Integer) key;
                Integer b = (Integer) o.key;
                return a.compareTo(b);
            } else if (o.key.getClass().getSimpleName().equals("Double")) {
                Double a = (Double) key;
                Double b = (Double) o.key;
                return a.compareTo(b);
            } else {
                String a = (String) key;
                String b = (String) o.key;
                return a.compareTo(b);

            }

        }

        // added
        public int compare(Object o) {
            if (o.getClass().getSimpleName().equals("Integer")) {
                Integer a = (Integer) key;
                Integer b = (Integer) o;
                return a.compareTo(b);
            } else if (o.getClass().getSimpleName().equals("Double")) {
                Double a = (Double) key;
                Double b = (Double) o;
                return a.compareTo(b);
            } else {
                String a = (String) key;
                String b = (String) o;
                return a.compareTo(b);

            }

        }
    }

    public void printTree() {
        if (this.root == null) {
            // handle edge case of null root due to the presence of only one node
            if (this.firstLeaf != null) {
                System.out.print("<");
                for (int j = 0; j < firstLeaf.numPairs; j++) {
                    if (firstLeaf.dictionary[j] != null) {
                        System.out.print(
                                "(" + firstLeaf.dictionary[j].key + ", " + firstLeaf.dictionary[j].values + ")");
                        if (j < firstLeaf.numPairs - 1 && firstLeaf.dictionary[j + 1] != null) {
                            System.out.print(", ");
                        }
                    }
                }
                System.out.print("> ");
            } else {
                System.out.println("The B+ tree is empty.");
                return;
            }
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Node currentNode = queue.poll();
                if (currentNode instanceof InternalNode) {
                    InternalNode internalNode = (InternalNode) currentNode;
                    System.out.print("[");
                    for (int j = 0; j < internalNode.degree; j++) {
                        if (internalNode.keys[j] != null) {
                            System.out.print(internalNode.keys[j]);
                            if (j < internalNode.degree - 1 && internalNode.keys[j + 1] != null) {
                                System.out.print(", ");
                            }
                        }
                    }
                    System.out.print("] ");
                    for (Node child : internalNode.childPointers) {
                        if (child != null) {
                            queue.add(child);
                        }
                    }
                } else if (currentNode instanceof LeafNode) {
                    LeafNode leafNode = (LeafNode) currentNode;
                    System.out.print("<");
                    for (int j = 0; j < leafNode.numPairs; j++) {
                        if (leafNode.dictionary[j] != null) {
                            System.out.print(
                                    "(" + leafNode.dictionary[j].key + ", " + leafNode.dictionary[j].values + ")");
                            if (j < leafNode.numPairs - 1 && leafNode.dictionary[j + 1] != null) {
                                System.out.print(", ");
                            }
                        }
                    }
                    System.out.print("> ");
                }
            }
            System.out.println(); // Move to the next level
        }
    }

    public LeafNode findLeafNodeShouldContainKey(Object key) {
        if (root == null)
            return firstLeaf;
        Node node = this.root;
        while (node instanceof InternalNode) {
            InternalNode internalNode = (InternalNode) node;
            Object[] keys = internalNode.keys;
            int i;

            // Find the child node to traverse next
            for (i = 0; i < internalNode.degree - 1; i++) {
                if (compare(key, keys[i]) < 0) {
                    break;
                }
            }

            node = internalNode.childPointers[i];
        }
        LeafNode n = (LeafNode) node;
        // case of delete node can have no numPairs so try finding non-empty siblings
        while (n != null && n.numPairs == 0) {
            n = n.leftSibling;
        }
        // if still not found, try right siblings
        if (n.numPairs == 0) {
            n = ((LeafNode) node).rightSibling;
            while (n != null && n.numPairs == 0) {
                n = n.rightSibling;
            }
        }

        return n;

    }

    public String toString() {
        String s = "";
        if (this.root == null) {
            // handle edge case of null root due to the presence of only one node
            if (this.firstLeaf != null) {
                s += "<";
                for (int j = 0; j < firstLeaf.numPairs; j++) {
                    if (firstLeaf.dictionary[j] != null) {
                        s += firstLeaf.dictionary[j].key;
                        if (j < firstLeaf.numPairs - 1 && firstLeaf.dictionary[j + 1] != null) {
                            s += ", ";
                        }
                    }
                }
                s += "> ";
            } else {
                return "The B+ tree is empty.";
            }
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Node currentNode = queue.poll();
                if (currentNode instanceof InternalNode) {
                    InternalNode internalNode = (InternalNode) currentNode;
                    s += "[";
                    for (int j = 0; j < internalNode.degree; j++) {
                        if (internalNode.keys[j] != null) {
                            s += internalNode.keys[j];
                            if (j < internalNode.degree - 1 && internalNode.keys[j + 1] != null) {
                                s += ", ";
                            }
                        }
                    }
                    s += "] ";
                    for (Node child : internalNode.childPointers) {
                        if (child != null) {
                            queue.add(child);
                        }
                    }
                } else if (currentNode instanceof LeafNode) {
                    LeafNode leafNode = (LeafNode) currentNode;
                    s += "<";
                    for (int j = 0; j < leafNode.numPairs; j++) {
                        if (leafNode.dictionary[j] != null) {
                            s += leafNode.dictionary[j].key;
                            if (j < leafNode.numPairs - 1 && leafNode.dictionary[j + 1] != null) {
                                s += ", ";
                            }
                        }
                    }
                    s += "> ";
                }
            }
            s += "\n"; // Move to the next level
        }
        return s;
    }

    public static void main(String[] args) {
        // bplustree b = new bplustree(3);
        // b.insert(4, "1");
        // b.insert(2, "2");
        // b.insert(3, "1");
        // b.insert(6, "1");
        // b.insert(7, "2");
        // b.insert(8, "1");
        // b.insert(5, "2");
        // b.insert(1, "2");
        // b.insert(2, "2");
        // b.insert(4, "1");
        // b.insert(2, "2");
        // b.insert(3, "6");
        // b.insert(5, "2");
        // b.delete(3, "1");
        // b.delete(3, "6");
        // b.printTree();
        // Vector<String> n = b.search(1);
        // System.out.println(n);
        ///////////////////////////////////////////////////////
        // bplustree b2 = new bplustree(2);
        // b2.insert(4.2, null);
        // b2.insert(2.5, null);
        // b2.insert(3.1, null);
        // b2.insert(6.7, null);
        // b2.printTree();

        // test when deleted nodes are there
        // bplustree b3 = new bplustree(3);
        // b3.insert("seif", "5");
        // b3.insert("yasmine", "1");
        // b3.insert("youssef", "10");
        // b3.insert("ali", "11");
        // b3.insert("ahmed", "9");
        // b3.delete("ziad", "7");
        // b3.insert("hana", "4");
        // b3.insert("farah", "3");
        // b3.insert("jaydaa", "2");
        // b3.insert("alia", "1");
        // b3.delete("seif", "5");

        // b3.printTree();
        // System.out.println(b3.rangeSearchWithLowerBoundInclusive("ahmed0"));

        bplustree b4 = new bplustree(4);
        b4.insert(20, "3");
        b4.insert(21, "5");
        b4.insert(20, "5");
        b4.insert(20, "5");
        b4.insert(8, "5");
        b4.insert(2, "5");
        b4.insert(3, "5");
        b4.insert(2, "9");
        b4.insert(5, "9");
        b4.printTree();
        b4.delete(5, "5");
        b4.insert(5, "9");
        System.out.println(b4);
        // // assume index on name->find name=seif from hashtable in delete
        // Vector<String> finalPages = b3.search("seif");
        // System.out.println(finalPages);
        // // here loop on all other indices
        // // this age index->find age=20
        // Vector<String> temp = b4.search(20);
        bplustree t = new bplustree(2);
        t.insert(1, "1");
        t.insert(2, "1");
        System.out.println(t);
        t.delete(1, "1");
        t.printTree();

    }

}

// TEST: my delete
// public void delete(Object key, String pageNum) {
// if (isEmpty()) {

// /* Flow of execution goes here when B+ tree has no dictionary pairs */

// System.err.println("Invalid Delete: The B+ tree is currently empty.");

// } else {

// // Get leaf node and attempt to find index of key to delete
// LeafNode ln = (this.root == null) ? this.firstLeaf : findLeafNode(key);
// int dpIndex = binarySearch(ln.dictionary, ln.numPairs, key);

// if (dpIndex < 0) {

// /* Flow of execution goes here when key is absent in B+ tree */

// System.err.println("Invalid Delete: Key unable to be found.");

// } else {

// // Successfully delete the dictionary pair or reduce the values in bucket
// ln.deleteValue(dpIndex, pageNum);
// // TEST PROPER REMOVAL
// if (ln.numPairs == 0) {
// // Remove leaf node from doubly linked list
// if (ln.leftSibling != null) {
// ln.leftSibling.rightSibling = ln.rightSibling;
// } else {
// // If ln is the first leaf, update the firstLeaf pointer
// this.firstLeaf = ln.rightSibling;
// }
// if (ln.rightSibling != null) {
// ln.rightSibling.leftSibling = ln.leftSibling;
// }
// // handles deletion of pointer from parent
// ln.parent.removePointerSpecial(ln);

// }
// // Check for deficiencies
// if (ln.isDeficient()) {

// LeafNode sibling;
// InternalNode parent = ln.parent;

// // Borrow: First, check the left sibling, then the right sibling
// if (ln.leftSibling != null &&
// ln.leftSibling.parent == ln.parent &&
// ln.leftSibling.isLendable()) {

// sibling = ln.leftSibling;
// DictionaryPair borrowedDP = sibling.dictionary[sibling.numPairs - 1];

// /*
// * Insert borrowed dictionary pair, sort dictionary,
// * and delete dictionary pair from sibling
// */
// ln.insert(borrowedDP);
// sortDictionary(ln.dictionary);
// sibling.delete(sibling.numPairs - 1);

// // Update key in parent if necessary
// int pointerIndex = findIndexOfPointer(parent.childPointers, ln);
// if (!(compare(borrowedDP.key, parent.keys[pointerIndex - 1]) >= 0)) {
// parent.keys[pointerIndex - 1] = ln.dictionary[0].key;
// }

// } else if (ln.rightSibling != null &&
// ln.rightSibling.parent == ln.parent &&
// ln.rightSibling.isLendable()) {

// sibling = ln.rightSibling;
// DictionaryPair borrowedDP = sibling.dictionary[0];

// /*
// * Insert borrowed dictionary pair, sort dictionary,
// * and delete dictionary pair from sibling
// */
// ln.insert(borrowedDP);
// sibling.delete(0);
// sortDictionary(sibling.dictionary);

// // Update key in parent if necessary
// int pointerIndex = findIndexOfPointer(parent.childPointers, ln);
// if (!(compare(borrowedDP.key, parent.keys[pointerIndex]) < 0)) {
// parent.keys[pointerIndex] = sibling.dictionary[0].key;
// }

// }

// // Merge: First, check the left sibling, then the right sibling
// else if (ln.leftSibling != null &&
// ln.leftSibling.parent == ln.parent &&
// ln.leftSibling.isMergeable()) {

// sibling = ln.leftSibling;
// int pointerIndex = findIndexOfPointer(parent.childPointers, ln);

// // Remove key and child pointer from parent
// parent.removeKey(pointerIndex - 1);
// parent.removePointer(ln);

// // Update sibling pointer
// sibling.rightSibling = ln.rightSibling;

// // Check for deficiencies in parent
// if (parent.isDeficient()) {
// handleDeficiency(parent);
// }

// } else if (ln.rightSibling != null &&
// ln.rightSibling.parent == ln.parent &&
// ln.rightSibling.isMergeable()) {

// sibling = ln.rightSibling;
// int pointerIndex = findIndexOfPointer(parent.childPointers, ln);

// // Remove key and child pointer from parent
// parent.removeKey(pointerIndex);
// parent.removePointer(pointerIndex);

// // Update sibling pointer
// sibling.leftSibling = ln.leftSibling;
// if (sibling.leftSibling == null) {
// firstLeaf = sibling;
// }

// if (parent.isDeficient()) {
// handleDeficiency(parent);
// }
// }

// } else if (this.root == null && this.firstLeaf.numPairs == 0) {

// /*
// * Flow of execution goes here when the deleted dictionary
// * pair was the only pair within the tree
// */

// // Set first leaf as null to indicate B+ tree is empty
// this.firstLeaf = null;

// } else {

// /*
// * The dictionary of the LeafNode object may need to be
// * sorted after a successful delete
// */
// sortDictionary(ln.dictionary);

// }
// }
// }
// }
