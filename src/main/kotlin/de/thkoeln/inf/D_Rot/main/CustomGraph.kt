package de.thkoeln.inf.D_Rot.main

import guru.nidi.graphviz.attribute.Rank
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory
import guru.nidi.graphviz.model.Node
import java.io.File
import java.nio.file.Paths

interface CustomGraph {
    fun addNode(input: Int)

    fun addEdge(start: Int, end: Int)

    fun removeNode(input: Int)

    fun removeEdge(start: Int, end: Int)

    fun findRoots(): Collection<Any>

    fun isAcyclic(input: JannikGraph.Node): Boolean

    fun toVizNodes():List<Node>

    fun toPic(sorted:Boolean = false){
        val g = Factory.graph("example1").directed()
            .graphAttr().with(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT))
            .with(toVizNodes())
        val d = Paths.get("out","graphs").toFile()
        val f = if(sorted)
            File(d,"sortedGraph.png")
        else
            File(d,"unsortedGraph.png")
        Graphviz.fromGraph(g).height(500).render(Format.PNG).toFile(f)
    }
}