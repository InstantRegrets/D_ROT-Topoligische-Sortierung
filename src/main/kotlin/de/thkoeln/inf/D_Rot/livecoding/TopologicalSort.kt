package de.thkoeln.inf.D_Rot.main.jannikGraph

class TopologicalSort() {
    /**
     * topological sorting of the graph
     * @return sorted ArrayList of Integers (the labels of the nodes)
     */
    companion object {
        fun topologicalSort(graph: JannikGraph): List<Int> {
            val nodesToProcess = graph.findRoots()
            val out = arrayListOf<JannikGraph.Node>()
            while (nodesToProcess.isNotEmpty()) {
                inductiveStep(nodesToProcess.first(), nodesToProcess as ArrayList<JannikGraph.Node>, out)
            }
            if (out.size < graph.getNodes().size) {
                println("is cyclic - check graph please")
                return listOf()
            }
            return out.map { it.label }
        }


        /**
         * inductiveStep takes the node to process, decreases the indegree of all successor nodes, checks if their indegree
         * is now 0 and if so, adds them to the list of nodes to process (zeroNodes).
         * it then proceeds to mark the current node as processed (removing it from the array and adding it to the output array)
         * @param nodeToProcess a node with an indegree of 0
         * @param processedNodes ArrayList of type node which will be the output list
         * @return changed output arrayList
         */
        private fun inductiveStep(
            nodeToProcess: JannikGraph.Node,
            remainingNodes: ArrayList<JannikGraph.Node>,
            processedNodes: ArrayList<JannikGraph.Node>
        ): ArrayList<JannikGraph.Node> {
            remainingNodes.remove(nodeToProcess)
            processedNodes.add(nodeToProcess)
            nodeToProcess.successors.forEach {
                it.indegree--
                if (it.indegree == 0) {
                    remainingNodes.add(it)
                }
                it.predecessors.remove(nodeToProcess)
            }
            return processedNodes
        }
    }
}