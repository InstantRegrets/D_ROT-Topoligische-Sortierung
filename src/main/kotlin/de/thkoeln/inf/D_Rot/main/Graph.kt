package de.thkoeln.inf.D_Rot.main

import guru.nidi.graphviz.attribute.Rank
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory
import guru.nidi.graphviz.model.Factory.node
import java.io.File
import java.nio.file.Paths


//     _____                 _
//    / ____|               | |
//   | |  __ _ __ __ _ _ __ | |__
//   | | |_ | '__/ _` | '_ \| '_ \
//   | |__| | | | (_| | |_) | | | |
//    \_____|_|  \__,_| .__/|_| |_|
//                    | |
//                    |_|

/**
 * Graph class handles all Nodes
 */
class Graph {
    private val nodes: ArrayList<Node> = arrayListOf()

    /**
     * Nodes contain a label for display purposes, a variable indegree, which is initialised at 0 and updated whenever
     * an edge is created containing this node.
     */
    data class Node(var label: Int,
                    val successors: ArrayList<Node> = arrayListOf(),
                    val predecessors: ArrayList<Node> = arrayListOf()
    ) {

        //must override to compare nodes and int
        override fun equals(other: Any?): Boolean {
            if (other is Node) {
                return this.label == other.label
            } else if (other is Int) {
                return this.label == other
            }
            return false
        }
        var indegree: Int = 0 //on node creation indegree is always 0

        //for equal check nodes should always return labels
        override fun hashCode(): Int {
            return label
        }
    }

    /*
    *  Graph functions
    */

    /**
     * @return ArrayList of all Nodes
     */
    fun getNodes() = nodes

    /**
     * helper function to resolve Integers to Node Objects
     * @return node with given label, if not found null
     */
    private fun retrieveNodeOf(input: Int): Node? {
        return nodes.find { it.label == input }
    }

    /**
     *  adds a Node with a given label to the Node-Array
     */
    fun addNode(input: Int) {
        nodes.add(Node(input))
    }

    /**
     * adds an Edge with a given start and end point, also updates indegree of end node
     */
    fun addEdge(start: Int, end: Int) {
        val temp = retrieveNodeOf(start)
        val temp2 = retrieveNodeOf(end)
        if (temp != null && temp2 != null) {
            println("found ${temp.label} and ${temp2.label}")
            temp.successors.add(temp2)
            temp2.predecessors.add(temp)
            temp2.indegree++
        } else {
            println("ungültiger Input")
        }
    }

    /**
     * function checks for the given start and end label if an edge with these two nodes exists and removes
     * it, if found.
     * @param start is the Label of the desired start node
     * @param end is the label of the desired end node
     */
    fun removeEdge(start: Int, end: Int) {
        val startNode = retrieveNodeOf(start)
        val endNode = retrieveNodeOf(end)
        if (startNode != null && endNode != null) {
            if (startNode.successors.contains(endNode) && endNode.predecessors.contains(startNode)) {
                startNode.successors.remove(endNode)
                endNode.predecessors.remove(startNode)
            }
        }
    }

    /**
     * removes node with given label from the node array
     * @param input label of node to remove as Int
     */
    fun removeNode(input: Int) {
        nodes.remove(retrieveNodeOf(input))
    }

    /**
     * @return a mutable list with all starting nodes
     */
    fun findRoots(): MutableList<Node> = nodes.filter { it.indegree == 0 }.toMutableList()

    /**
     * checks graph for cycles
     * @return true if graph does not contain cycles else false
     */
    fun isAcyclic(): Boolean {
        val nodesToProcess = findRoots()
        val processed = arrayListOf<Node>()
        while (nodesToProcess.isNotEmpty()) {
            cyclicStep(nodesToProcess.first(), nodesToProcess, processed)
        }
        if (processed.size < nodes.size){
            println("is cyclic - check graph please")
            return false
        }
        return true
    }

    /**
     *  helper function for isAcyclic()
     *  @param nodeToProcess current Node
     *  @param remainingNodes ArrayList of remaining Nodes with Indegree 0
     *  @param processed Array List of already processed nodes, for comparison with normal nodes
     */
    private fun cyclicStep(nodeToProcess: Node, remainingNodes: MutableList<Node>, processed: ArrayList<Node>) {
        nodeToProcess.successors.forEach {
            it.indegree--
            if (it.indegree == 0) {
                remainingNodes.add(it)
            }
        }
        remainingNodes.remove(nodeToProcess)
        processed.add(nodeToProcess)
    }

//    _____
//    |  __ \
//    | |  | |_ __ __ ___      __
//    | |  | | '__/ _` \ \ /\ / /
//    | |__| | | | (_| |\ V  V /
//    |_____/|_|  \__,_| \_/\_/

    // Stuff for graphical output
    private fun Node.toVizNode(): guru.nidi.graphviz.model.Node =
        node(this.label.toString())

    private fun toVizNodes(): List<guru.nidi.graphviz.model.Node> {
        val vizNodes = nodes.map { it to  it.toVizNode() }
        return vizNodes.map { pair ->
            pair.second.link(pair.first.successors.map { it.toVizNode() })
        }
    }
    fun toPic(name: String){
        val g = Factory.graph("example1").directed()
            .graphAttr().with(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT))
            .with(toVizNodes())
        val d = Paths.get("out","graphs").toFile()
        val f = File(d,"$name.png")
        Graphviz.fromGraph(g).height(500).render(Format.PNG).toFile(f)
    }
}

