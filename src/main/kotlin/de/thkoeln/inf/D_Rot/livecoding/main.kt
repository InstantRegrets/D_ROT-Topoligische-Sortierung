@file:Suppress("PackageName")

package de.thkoeln.inf.D_Rot.livecoding

import de.thkoeln.inf.D_Rot.main.jannikGraph.*

fun main(){
    val graph = createBaseGraph()
    graph.toPic("Output_Graph")
    TopologicalSort.topologicalSort(graph).forEach { println(it) }
}

fun createBaseGraph(): JannikGraph{
    val graph = JannikGraph()
    graph.addNode(1)
    graph.addNode(2)
    graph.addNode(3)
    graph.addNode(4)
    graph.addNode(5)
    graph.addNode(6)
    graph.addEdge(1,2)
    graph.addEdge(2,6)
    graph.addEdge(3,6)
    graph.addEdge(2,3)
    graph.addEdge(5,1)
    graph.addEdge(4,1)
    return graph
}
