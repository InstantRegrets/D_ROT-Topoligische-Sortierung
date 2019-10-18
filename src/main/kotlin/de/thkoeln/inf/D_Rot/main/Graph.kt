package de.thkoeln.inf.D_Rot.main

import java.util.*
import kotlin.collections.ArrayList

class Graph {
    val edges: ArrayList<Edge> = arrayListOf()
    val nodes: ArrayList<Node> = arrayListOf()
    inner class Node(var label: Int, var indegree: Int = 0) {
        val adjacentEdges: ArrayList<Edge> = arrayListOf()
        init {
            edges.forEach{
                if (it.from==this) {
                    adjacentEdges.add(it)
                    it.to.indegree++
                }
                if (it.to == this)
                    indegree++
            }
        }
    }
    class Edge(val from: Node, val to: Node)

    fun addNode(input: Node) = nodes.add(input)
    fun addEdge(from: Node, to: Node) = edges.add(Edge(from, to))
    fun findRoots(input: Node): ArrayList<Node>{
        val nodesTemp = arrayListOf<Node>()
        nodes.forEach{
            if (it.indegree==0)
                nodesTemp.push(it)
                inductiveStep(it)
        }
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