package GraphAlgorithms;


public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public void insert(int element) {
        if (pos >= nodes.length) {
            resize();
        }

        nodes[pos] = element;
        int current = pos;
        pos++;

        while (current > 0) {
            int parent = (current - 1) / 2;
            if (nodes[current] < nodes[parent]) {
                swap(current, parent);
                current = parent;
            } else {
                break;
            }
        }
    }

    public int remove() {
        if (isEmpty()) {
            return Integer.MAX_VALUE;
        }
        int removed = nodes[0];
        pos--;
        nodes[0] = nodes[pos];
        nodes[pos] = Integer.MAX_VALUE;
        int current = 0;
        while (!isLeaf(current)) {
            int best = getBestChildPos(current);
            if (best != Integer.MAX_VALUE && nodes[current] > nodes[best]) {
                swap(current, best);
                current = best;
            } else {
                break;
            }
        }

        return removed;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) {
            return Integer.MAX_VALUE;
        } else {
            int left = 2 * src + 1;
            int right = 2 * src + 2;
            if (right >= pos) {
                return left;
            }
            return (nodes[left] <= nodes[right]) ? left : right;
        }
    }

    
    /**
	 * Test if the node is a leaf in the binary heap
	 * 
	 * @returns true if it's a leaf or false else
	 * 
	 */	
    private boolean isLeaf(int src) {
        return (2 * src + 1) >= pos;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @returns a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println("Tas vide: " + jarjarBin.isEmpty() + "\n");
        System.out.println("=== Test des insertions: 4, 10, 8, 6, 3 ===");
        int[] testValues = {4, 10, 8, 6, 3};
        for (int val : testValues) {
            System.out.println("Insert " + val);
            jarjarBin.insert(val);
            System.out.println("Tas: " + jarjarBin);
        }

        System.out.println("Tas final après insertions: " + jarjarBin);
        System.out.println("Tas valide: " + jarjarBin.test());

        System.out.println("\n=== Test de deux suppressions consécutives ===");
        System.out.println("Première suppression: " + jarjarBin.remove());
        System.out.println("Tas après première suppression: " + jarjarBin);
        System.out.println("Tas valide: " + jarjarBin.test());

        System.out.println("Deuxième suppression: " + jarjarBin.remove());
        System.out.println("Tas après deuxième suppression: " + jarjarBin);
        System.out.println("Tas valide: " + jarjarBin.test());

        System.out.println("\n=== Test avec valeurs aléatoires ===");
        BinaryHeap randomHeap = new BinaryHeap();
        int k = 10;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand + " ");
            randomHeap.insert(rand);
            k--;
        }
        System.out.println("\nTas généré: " + randomHeap);
        System.out.println("Tas valide: " + randomHeap.test());
    }

}
