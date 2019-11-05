package de.thkoeln.inf.D_Rot.main

interface CustomGraph {
    fun addNode(input: Int)

    fun addEdge(start: Int, end: Int)

    fun removeNode(input: Int)

    fun removeEdge(start: Int, end: Int)

    fun findRoots(): Collection<Any>

    fun isAcyclic(): Boolean

    fun getNodes():Collection<Any>

}