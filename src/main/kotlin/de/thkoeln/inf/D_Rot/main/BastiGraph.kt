package de.thkoeln.inf.D_Rot.main

/**
 * Graph class handles all nodes
 */
class BastiGraph{
    private val nodes: ArrayList<ArrayList<Int>> = arrayListOf()

    /**
     * adds a Node to the Graph
     */
    fun addNode(dependencies: Collection<Int> ){
        val size = nodes.size+1
        nodes.fillToSize(size, arrayListOf())
        nodes.forEach { it.fillToSize(size,0) }
        dependencies.forEach {
            // adds a -1 for all the dependencies
            nodes.last()[it]=-1
            //adds the 1 for all other stuff that depend on it
            nodes[it][nodes.lastIndex]=1
        }
    }

    /**
     * removes the given node from the Graph
     */
    fun removeNode(node: Int){
        nodes.forEach { it.removeAt(node) }
        nodes.removeAt(node)
    }

    /**
     * returns the degree of the given note
     */
    fun inDegree(note: Int) =
        nodes[note]
            .filter { it == -1 }
            .size
    /**
     * returns a list of all the roots
     */
    fun findRoots(): List<Int> =
        nodes.mapIndexedNotNull {
                index, value ->
            index.takeIf { !value.contains(-1) }
        }


    /**
     * helpers function to fill the Array to the given size
     */
    private fun<E> ArrayList<E>.fillToSize(index: Int, value: E){
        repeat(index){
            this.getOrElse(it){ this.add(value)}
        }
    }

    /**
     * outputs the Graph
     */
    override fun toString(): String {
        return nodes.toString()
    }
}

fun main(){
    val g = BastiGraph()
    g.addNode(arrayListOf())
    g.addNode(arrayListOf(0))
    g.addNode(arrayListOf(0))
    g.addNode(arrayListOf(1,2))
    g.addNode(arrayListOf())
    println("graph: $g")
    g.removeNode(0)
    println("removeing node 0: $g")
    println("all Roots: ${g.findRoots()}")
    println("indegree of 0: ${g.inDegree(0)}")
    
}

