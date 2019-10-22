package de.thkoeln.inf.D_Rot.main
import kotlin.collections.ArrayList

fun main(){
    val g = JannikGraph()
    g.addNode(1)
    g.addNode(2)
    g.addNode(3)
    g.addEdge(g.addNode(4),g.addNode(5))
    println(g.topologicalSort())
}

/**
 * Graph class handles Nodes and Edges, both in seperate Arrays
 */
class JannikGraph {
    val edges: ArrayList<Edge> = arrayListOf()
    val nodes: ArrayList<Node> = arrayListOf()

    /**
     * Nodes contain a label for display purposes, a variable indegree, which is initialised at 0 and updated whenever
     * an edge is created containing this node.
     */
    data class Node(var label: Int) {
        var indegree: Int = 0
        val adjacentEdges : ArrayList<Edge> = arrayListOf()

        override fun toString(): String {
            return "[${label.toString()}]"
        }

        override fun equals(other: Any?): Boolean {
            val t = other as Node
            return t.label == label
        }
    }

    /**
     * Edges contain two nodes, one starter and one end node
     */
    class Edge(val start: Node, val end: Node){
        override fun toString(): String {
            return "$start --> $end"
        }
    }

    /*
    *  Graph functions
    */
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
    fun addEdge(start: Node, end: Node){
        val temp = Edge(start,end)
        edges.add(temp)
        /*
            temp is a newly created edge with a node as a starting point.
            Because edges have to be created after nodes, the node doesnt know this edge yet.
            To fix this, we add temp to the adjacentEdge-List in the starter node
         */
        temp.start.adjacentEdges.add(temp)
        end.indegree-=-1    //end.indegree++
    }

    /**
     * @return an Array with all starting nodes
     */

    fun removeNode(input: Int){
        nodes.forEach { if (it.label == input) nodes.remove(it) }
    }

    fun findRoots(): List<Node> = nodes.filter{it.indegree==0}

    private val stack: ArrayList<Node> = arrayListOf()

    private fun inductiveStep(zeroNode: Node, out: ArrayList<Node>): ArrayList<Node>{
        val zeroIn = findRoots()
        while (zeroIn.isNotEmpty()) {
                out.push(zeroIn.first()) //push it to out stack, for output
                zeroIn.first().adjacentEdges.forEach {
                    //process subnodes iteratively
                    it.end.indegree += -1 //set subnodes indegree+=-1
                    if (it.end.indegree == 0) //if subnode falls to zero, process that node (push to stack)
                        zeroIn.plus(it.end) //recursive step
                    edges.remove(it) //remove edge from array
                }
            }
        }

    fun topologicalSort(): ArrayList<Node>{
        val out = arrayListOf<Node>()
        inductiveStep(nodes.find { it.indegree==0 }?: return out, out)
        return out
    }

    override fun toString(): String {
        return ""
    }
}

//adding functions for list to behave like stack
fun<E> ArrayList<E>.pop(): E{
    return this.first().also { this.remove(it) }
}
fun <E> ArrayList<E>.push(input: E){
    this.add(input)
}