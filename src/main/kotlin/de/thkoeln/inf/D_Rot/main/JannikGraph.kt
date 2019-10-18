package de.thkoeln.inf.D_Rot.main

import java.util.*
import kotlin.collections.ArrayList

fun main(){
    val g = JannikGraph()
    g.addNode(1)
    g.addNode(2)
    g.addNode(3)
    g.addEdge(g.nodes[0],g.nodes[1])
    g.addEdge(g.nodes[0],g.nodes[2])
    println("${g.edges[1].from.label} to ${g.edges[1].to.label}")
    println(g.nodes[2].indegree)

}

class JannikGraph {
    val edges: ArrayList<Edge> = arrayListOf()
    val nodes: ArrayList<Node> = arrayListOf()
    inner class Node(var label: Int, var indegree: Int = 0, val adjacentEdges : ArrayList<Edge> = arrayListOf()) {
        init {
            adjacentEdges.forEach{
                it.to.indegree++
                edges.add(it)
            }
        }
    }
    class Edge(val from: Node, val to: Node)
    fun addNode(label: Int) = nodes.add(Node(label))

    fun addEdge(from: Node, to: Node){
        edges.add(Edge(from, to))
        to.indegree++
    }
    fun findRoots(input: Node): ArrayList<Node>{
        val nodesTemp = arrayListOf<Node>()
        nodes.forEach{
            if (it.indegree==0)
                nodesTemp.push(it)
                inductiveStep(it)
        }
        return nodesTemp
    }

    private fun inductiveStep(input: Node){
        input.adjacentEdges.forEach {
            if (it.from == input){
                it.to.indegree--
                edges.remove(it)
            }
        }
    }
}
fun<E> ArrayList<E>.pop(): E{
    return this.first().also { this.remove(it) }
}
fun <E> ArrayList<E>.push(input: E){
    this.add(input)
}