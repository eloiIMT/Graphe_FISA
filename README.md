# Graphe_FISA - Mathéo Vallée et Eloi Garnier


## Structure du projet

### TP1 - Représentations de graphes
- **AdjacencyMatrix/** : Représentation par matrice d'adjacence
    - `AdjacencyMatrixDirectedGraph.java`
    - `AdjacencyMatrixUndirectedGraph.java`
    - `AdjacencyMatrixDirectedValuedGraph.java`
    - `AdjacencyMatrixUndirectedValuedGraph.java`

- **AdjacencyList/** : Représentation par liste d'adjacence
    - `AdjacencyListDirectedGraph.java`
    - `AdjacencyListUndirectedGraph.java`
    - `AdjacencyListDirectedValuedGraph.java`
    - `AdjacencyListUndirectedValuedGraph.java`

### TP2 - Parcours de graphes
- **GraphAlgorithms/GraphToolsList.java** :
    - Parcours en largeur (BFS)
    - Parcours en profondeur (DFS)
    - Composantes fortement connexes

### TP3 - Dijkstra, tas binaire, algorithme de Prim
- **GraphAlgorithms/GraphToolsList.java** :
    - Algorithme de Dijkstra

- **GraphAlgorithms/BinaryHeap.java** :
    - Tas binaire pour entiers
    - Méthodes `insert()`, `remove()`, `getBestChildPos()`

- **GraphAlgorithms/BinaryHeapEdge.java** :
    - Tas binaire pour arêtes
    - Algorithme de Prim pour arbre couvrant minimal