@file:Suppress("PackageName")

package de.thkoeln.inf.D_Rot.livecoding

import de.thkoeln.inf.D_Rot.main.Graph

fun main(){
    val graph = createBaseGraph()
    graph.toPic("GraphPicture")
    topologicalSort(graph).forEach { println(it) }
}

fun createBaseGraph(): Graph {
    val graph = Graph()
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
