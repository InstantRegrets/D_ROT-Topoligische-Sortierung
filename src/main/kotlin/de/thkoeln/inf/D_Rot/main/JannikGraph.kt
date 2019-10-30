package de.thkoeln.inf.D_Rot.main
import kotlin.collections.ArrayList

fun main(){
    val g = JannikGraph()
    g.addNode(1)
    g.addNode(2)
    g.addNode(3)
    g.addNode(4)
    g.addEdge(4,2)
    g.addEdge(4,3)
    g.addEdge(2,1)
    g.addEdge(3,1)
    g.topologicalSort().forEach { println(it.label) }


}

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
class JannikGraph {
    private val nodes: ArrayList<Node> = arrayListOf()

    /**
     * Nodes contain a label for display purposes, a variable indegree, which is initialised at 0 and updated whenever
     * an edge is created containing this node.
     */
    data class Node(var label: Int){
        //must override to compare nodes and int
        override fun equals(other: Any?): Boolean {
            if (other is Node){
                return this.label == other.label
            }
            else if (other is Int)
            {
                return this.label == other
            }
            return false
        }

        var indegree: Int = 0
        val successors : ArrayList<Node> = arrayListOf()
        val predecessors : ArrayList<Node> = arrayListOf()

        override fun hashCode(): Int {
            return label
        }
    }

    /*
    *  Graph functions
    */

    fun retrieveNodeOf(input: Int): Node?{
        return nodes.find { it.label == input }
    }

    /**
     *  adds a Node with a given label to the Node-Array
     */
    fun addNode(label: Int): Node {
        val temp = Node(label)
        nodes.add(temp)
        return temp
    }

    /**
     * adds an Edge with a given start and end point, also updates indegree of end node
     */
    fun addEdge(start: Int, end: Int){
        val temp = retrieveNodeOf(start)
        val temp2 = retrieveNodeOf(end)
        if (temp != null && temp2 != null) {
            println("found ${temp.label} and ${temp2.label}")
            temp.successors.add(temp2)
            temp2.predecessors.add(temp)
            temp2.indegree++
        }
        else{
            println("ungültiger Input")
        }
    }

    /**
     * function checks for the given start and end label if an edge with these two nodes exists and removes
     * it, if found.
     * @param start is the Label of the desired start node
     * @param end is the label of the desired end node
     */
    fun removeEdge(start: Int, end: Int){
        val startNode = retrieveNodeOf(start)
        val endNode = retrieveNodeOf(end)
        if(startNode != null && endNode != null){
            if (startNode.successors.contains(endNode) && endNode.predecessors.contains(startNode)){
                startNode.successors.remove(endNode)
                endNode.predecessors.remove(startNode)
            }
            TODO("Cleanup?")
        }
    }

    /**
     * removes node with given label from the node array
     * @param input label of node to remove as Int
     */
    fun removeNode(input: Int){
        nodes.remove(retrieveNodeOf(input))
    }

    /**
     *
     * @return an array with all starting nodes
     */
    private fun findRoots(): List<Node> = nodes.filter{it.indegree==0}

//             _                  _ _   _
//       /\   | |                (_) | | |
//      /  \  | | __ _  ___  _ __ _| |_| |__  _ __ ___
//     / /\ \ | |/ _` |/ _ \| '__| | __| '_ \| '_ ` _ \
//    / ____ \| | (_| | (_) | |  | | |_| | | | | | | | |
//   /_/    \_\_|\__, |\___/|_|  |_|\__|_| |_|_| |_| |_|
//                __/ |
//               |___/


    //global var to be invoked by topologicalSort() and to be changed by inductiveStep
    //contains all nodes with an indegree of 0, for the topological sort to use
    private var zeroNodes: MutableList<Node> = arrayListOf()
    /**
     * inductiveStep takes the node to process, decreases the indegree of all successor nodes, checks if their indegree
     * is now 0 and if so, adds them to the list of nodes to process (zeroNodes).
     * it then proceeds to mark the current node as processed (removing it from the array and adding it to the output array)
     * @param nodeToProcess a node with an indegree of 0
     * @param out ArrayList of type node which will be the output list
     * @return changed output arrayList
     */
    private fun inductiveStep(nodeToProcess: Node, out: ArrayList<Node>): ArrayList<Node>{
            nodeToProcess.successors.forEach {
                it.indegree--
                if (it.indegree==0){
                    zeroNodes.add(it)
                }
                it.predecessors.remove(nodeToProcess)
            }
        zeroNodes.remove(nodeToProcess)
        out.add(nodeToProcess)
        return out
    }

    /**
     * topological sorting of the graph
     * @return sorted ArrayList
     */
    fun topologicalSort(): ArrayList<Node>{
        zeroNodes = findRoots().toMutableList()
        val out = arrayListOf<Node>()
        while (zeroNodes.isNotEmpty()){
            inductiveStep(zeroNodes.first(),out)
        }
        return out
    }
}

//adding functions for list to behave like stack
fun<E> ArrayList<E>.pop(): E{
    return this.first().also { this.remove(it) }
}
fun <E> ArrayList<E>.push(input: E){
    this.add(input)
}