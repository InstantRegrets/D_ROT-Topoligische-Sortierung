//only for personal testing
package de.thkoeln.inf.D_Rot.main.jannikGraph

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
    //g.addEdge(1,4)
    TopologicalSort().topologicalSort(g).forEach { println(it) }
    println(g.isAcyclic())
}