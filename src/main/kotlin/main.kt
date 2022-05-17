import helpers.TopologyApi

fun main() {
    val api = TopologyApi()
    api.readJson("src/main/assets/topology.json")
    //api.deleteTopology("top1")
    api.queryDevices("top1")
    api.writeJson("top1")

}