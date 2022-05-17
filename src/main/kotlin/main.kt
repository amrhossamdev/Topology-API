import helpers.TopologyApi
import utils.ApiUtils

fun main() {
    //See Topology Api Test for better understanding @tests
    val api = TopologyApi()
    api.readJson(ApiUtils.filePath)
    api.writeJson("top1")
}