package de.thkoeln.inf.D_Rot.main

import kotlin.math.max


/**
 * Graph class handles all nodes
 */
class BastiGraph:CustomGraph {
    override fun addEdge(start: Int, end: Int) {
        nodes[start][end] = 1
        nodes[end][start] = -1
    }

    override fun removeEdge(start: Int, end: Int) {
        nodes[start][end] = 0
        nodes[end][start] = 0
    }

    override fun findRoots(): Collection<Any> {
        val t =  nodes.filterNot { it.contains(-1) }
        return t.map {
            nodes.indexOf(it)
        }

    }
    //    ____             __  _         ______                 __       ________
    //   / __ )____ ______/ /_(_)____   / ____/________ _____  / /_     / ____/ /___ ___________
    //  / __  / __ `/ ___/ __/ / ___/  / / __/ ___/ __ `/ __ \/ __ \   / /   / / __ `/ ___/ ___/
    // / /_/ / /_/ (__  ) /_/ (__  )  / /_/ / /  / /_/ / /_/ / / / /  / /___/ / /_/ (__  |__  )
    ///_____/\__,_/____/\__/_/____/   \____/_/   \__,_/ .___/_/ /_/   \____/_/\__,_/____/____/
    //                                               /_/

    private val nodes: ArrayList<ArrayList<Int>> = arrayListOf()

    /**
     * adds a Node to the Graph
     */
    override fun addNode(input: Int){
        val size = max(nodes.size, input+1)
        nodes.fillToSize(size)
        nodes.forEach { it.fillToSize(size) }
    }

    /**
     * removes the given node from the Graph
     */
    override fun removeNode(input: Int){
        nodes.forEach { it.removeAt(input) }
        nodes.removeAt(input)
    }

    /**
     * returns the degree of the given note
     */
    fun inDegree(note: Int) =
        nodes[note]
            .filter { it == -1 }
            .size


    //                               __   _____  __          ____ ____
    //     ____ _ ____   ____   ____/ /  / ___/ / /_ __  __ / __// __/
    //    / __ `// __ \ / __ \ / __  /   \__ \ / __// / / // /_ / /_
    //   / /_/ // /_/ // /_/ // /_/ /   ___/ // /_ / /_/ // __// __/
    //   \__, / \____/ \____/ \__,_/   /____/ \__/ \__,_//_/  /_/
    //  /____/

    /**
     * topologische sortierung. Findet Die Torpologische Sortierung Vorgang:
     *      - irgend ein random node nehmen (einfach den ersten in unseren fall)
     *      - schauen ob er visited ist, wenn nicht -> visiten (depth first search)
     *          dadurch wird dieser automatisch auf visited gezogen und der output value vom mit gezählten
     *          index wird gesetzt (vergleiche Graphik eta)
     *      - geordnete Liste zurückgeben
     */
    override fun topologicalSort(): List<Int> {
        val n = nodes.size
        val visited = Array(n) { false } //array of all the visited nodes
        val ordering = Array(n) { 0 } //ouput
        var i= n -1 //index pointing at the last element

        for( at in nodes.indices){
            if (!visited[at]){
                i = depthFirstSearch(i,at,visited, ordering)
            }
        }
        return ordering.toList()
    }

    /**
     * Geht für einen bestimmten node (at) ein depth first Search durch. Hierbei beachtetet er nur nicht-besuchte nodes
     * Vorgehen:
     *      - setze den aktuellen node auf visited
     *      - laufe alle parents durch
     *          - laufe für jeden successor erneut die depth first Search durch
     *      - passe das ordering output an. speichert an der letzten nicht gesetzten variable (index) den aktuellen node
     *      - und ändere den index, so das er jezt auf das die neue letzte nicht gesetzte variable im ordering zeigt
     */
    private fun depthFirstSearch(index: Int, at: Int, visited: Array<Boolean>, ordering: Array<Int>): Int {
        var i = index
        visited[at] = true
        val successors = successor(at)
        for( p in successors){
            if (!visited[p])
                i = depthFirstSearch(i, p,visited, ordering)
        }
        ordering[i] = at
        return i-1
    }

    /**
     * gibt die parents ( wo die nodes hinzeigen) für einen node zurück
     */
    private fun successor(node: Int): List<Int> {
        val l = mutableListOf<Int>()
        for ((index, it) in nodes[node].withIndex()) {
            if (it == 1)
                l.add(index)
        }
        return l
    }



    //    __               _
    //   / /_  ____  _____(_)___  ____ _
    //  / __ \/ __ \/ ___/ / __ \/ __ `/
    // / /_/ / /_/ / /  / / / / / /_/ /
    ///_.___/\____/_/  /_/_/ /_/\__, /
    //                         /____/
    /**
     * helpers function to fill the Array to the given size
     */
    private fun ArrayList<Int>.fillToSize(index: Int, value: Int=0){
        repeat(index){
            this.getOrElse(it){ this.add(value)}
        }
    }
    private fun<E> ArrayList<ArrayList<E>>.fillToSize(index: Int){
        repeat(index){
            this.getOrElse(it){ this.add(arrayListOf()) }
        }
    }

    /**
     * outputs the Graph
     */
    override fun toString(): String {
        var a = ""
        nodes.forEach { a="$a\n$it" }
        return a
    }

}

fun main(){
    val g = BastiGraph()
    g.addNode(5)
    g.addEdge(1,3)
    g.addEdge(1,4)
    println(g)
    println(g.topologicalSort())
}

