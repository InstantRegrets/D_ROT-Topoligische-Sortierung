@file:Suppress("PackageName")

package de.thkoeln.inf.D_Rot.main

fun main(){
    val g = BastiGraph()
    g.addNode(arrayListOf())
    g.addNode(arrayListOf(0))
    g.addNode(arrayListOf(0))
    g.addNode(arrayListOf(1,2))
    println(g.nodes)
}

/**
 * Graph class handles all nodes
 */
class BastiGraph{
    val nodes: ArrayList<ArrayList<Int>> = arrayListOf()
    /**
     * adds a Node to the Graph
     */
    fun addNode(dependencies: Collection<Int> ){
        val size = nodes.size+1
        nodes.fillToSize(size, arrayListOf())
        nodes.forEach { it.fillToSize(size,0) }
        println(nodes)
        dependencies.forEach {
            // adds a -1 for all the dependencies
            nodes.last().add(it,-1)
            //adds the 1 for all the stuff
            nodes[it].add(nodes.lastIndex+1,1)
        }
    }
}

fun<E> ArrayList<E>.fillToSize(index: Int, value: E){
    repeat(index){
        this.getOrElse(it){ this.add(value)}
    }
}


