@file:Suppress("PackageName")

package de.thkoeln.inf.D_Rot.main

fun main(){
    println("helloWorld")
}

/**
 * Graph class handles all nodes
 */
class Graph{
    val nodes: ArrayList<ArrayList<Int?>> = arrayListOf()
    fun addNode(dependencies: ArrayList<Int> ){
        val l = arrayListOf<Int>()
        dependencies.forEach {
            l.add(it,-1)
            nodes[it][nodes.lastIndex+1]

        }

    }
}


