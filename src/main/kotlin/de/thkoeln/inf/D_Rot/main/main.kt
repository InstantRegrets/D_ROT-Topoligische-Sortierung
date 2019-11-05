@file:Suppress("PackageName")

package de.thkoeln.inf.D_Rot.main

/***
 * using jgrapht: https://jgrapht.org/
 */

fun main(){
    print("""
 ____   __   ____  ____  __  _ ____     ___  ____   __   ____  _  _ 
(  _ \ / _\ / ___)(_  _)(  )(// ___)   / __)(  _ \ / _\ (  _ \/ )( \
 ) _ (/    \\___ \  )(   )(   \___ \  ( (_ \ )   //    \ ) __/) __ (
(____/\_/\_/(____/ (__) (__)  (____/   \___/(__\_)\_/\_/(__)  \_)(_/
        
    """.trimIndent())

    var g: CustomGraph = BastiGraph()
    g.addNode(5)
    g.addEdge(1,3)
    g.addEdge(1,4)
    g.addEdge(2,1)
    g.addEdge(4,1)
    g.toPic("BastiGraph")
    println(g.topologicalSort())

    println("press enter for jannik's graph")
    readLine()

    print("""
   __   __   __ _  __ _  __  __ _  _ ____     ___  ____   __   ____  _  _ 
 _(  ) / _\ (  ( \(  ( \(  )(  / )(// ___)   / __)(  _ \ / _\ (  _ \/ )( \
/ \) \/    \/    //    / )(  )  (   \___ \  ( (_ \ )   //    \ ) __/) __ (
\____/\_/\_/\_)__)\_)__)(__)(__\_)  (____/   \___/(__\_)\_/\_/(__)  \_)(_/
    """.trimIndent())

    g = JannikGraph()
    g.addNode(1)
    g.addNode(2)
    g.addNode(3)
    g.addNode(4)
    g.addEdge(4,2)
    g.addEdge(4,3)
    g.addEdge(2,1)
    g.addEdge(3,1)
    g.toPic("JannikGraph")
    println(g.topologicalSort())
}


