package de.thkoeln.inf.D_Rot.main

interface CustomGraph {
    fun addNode(input: Int)

    fun addEdge(start: Any, end: Any)

    fun removeNode(input: Int)

    fun removeEdge(input: Any)
    fun removeEdge(start: Any, end: Any)

    fun findRoots(): Collection<Any>

    fun topologicalSort(): Collection<Any>
}