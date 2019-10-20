package de.thkoeln.inf.D_Rot.main
import kotlin.collections.ArrayList

fun main(){
    val g = JannikGraph()
    g.addNode(1)
    g.addNode(2)
    g.addNode(3)
    g.addNode(4)
    g.addNode(5)
    g.addEdge(g.nodes[0],g.nodes[1])
    g.addEdge(g.nodes[0],g.nodes[2])
    println("${g.edges[1].start.label} end ${g.edges[1].end.label}")
    println(g.nodes[2].indegree)
    g.findRoots().forEach{println(it.label)}

}

/**
 * Graph class handles Nodes and Edges, both in seperate Arrays
 */

class JannikGraph {
    val edges: ArrayList<Edge> = arrayListOf()
    val nodes: ArrayList<Node> = arrayListOf()

    /**
     * Nodes contain a label for display purposes, a variable indegree, which is inizialized at 0 and updated whenever
     * an edge is created containing this node. Also contains a List with Edges that start at this node, which is used
     * end update the indegree of following nodes
     */
    inner class Node(var label: Int, var indegree: Int = 0, val adjacentEdges : ArrayList<Edge> = arrayListOf()) {
        init { //only relevant when processing subtrees
            adjacentEdges.forEach{
                it.end.indegree++
                edges.add(it)
            }
        }
    }

    /**
     * Edges contain two nodes, one starter and one end node
     */
    class Edge(val start: Node, val end: Node)

    /*
    *  Graph functions
    */
    /**
     *  adds a Node with a given label to the Node-Array
     */
    fun addNode(label: Int) = nodes.add(Node(label))

    /**
     * adds an Edge with a given start and end point, also updates indegree of end node
     */
    fun addEdge(start: Node, end: Node){
        edges.add(Edge(start, end))
        end.indegree++
    }

    /**
     * @return an Array with all starting nodes
     */
    fun findRoots(): List<Node> = nodes.filter{it.indegree==0}
    /*{
        val nodesTemp = arrayListOf<Node>()
        nodes.forEach{
            if (it.indegree==0)
                nodesTemp.push(it)
                //inductiveStep(it)
        }
        return nodesTemp
    }
    */

    private fun inductiveStep(input: Node){
        input.adjacentEdges.forEach {
            if (it.start == input){
                it.end.indegree--
                edges.remove(it)
            }
        }
    }
}

//adding functions for list to behave like stack
fun<E> ArrayList<E>.pop(): E{
    return this.first().also { this.remove(it) }
}
fun <E> ArrayList<E>.push(input: E){
    this.add(input)
}