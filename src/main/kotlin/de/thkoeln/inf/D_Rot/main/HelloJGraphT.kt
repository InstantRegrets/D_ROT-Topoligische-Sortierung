package de.thkoeln.inf.D_Rot.main

import guru.nidi.graphviz.attribute.Color
import java.io.File
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.attribute.Rank
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.model.Factory.graph
import guru.nidi.graphviz.model.Factory.node
import guru.nidi.graphviz.model.Link.to







fun main() {
    // creates an example graph

    //doku: https://github.com/nidi3/graphviz-java

    val g = graph("example1").directed()
        .graphAttr().with(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT))
        .with(
            node("a").with(Color.RED).link(node("b")),
            node("b").link(to(node("c")).with(Style.DASHED))
        )
    Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(File("out/graphs/graph.png"))

}

