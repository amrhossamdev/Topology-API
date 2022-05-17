import helpers.TopologyApi
import org.junit.Test
import utils.ApiUtils
import kotlin.test.assertEquals

class TopologyApiTest {
    /*
    testing read topology from file json it should return 0 when successfully read
     */
    @Test
    fun testReadJson() {
        val api = TopologyApi()
        assertEquals(0, api.readJson(ApiUtils.filePath))
    }

    /*
    testing write json for a specific topology id
    * we start reading json file first to store topology in the memory
    * then we write a json file for this topology
    * for successful operation it will return 0 else -1
    */
    @Test
    fun testWriteJson() {
        val api = TopologyApi()
        //start reading json
        api.readJson(ApiUtils.filePath)
        //passing topology id
        assertEquals(0, api.writeJson("top1"))
    }

    /*
    testing delete topology
     * we start reading json file first to store topology in the memory
     * then we delete a topology from the memory
     * for successful operation it will return 0 else -1
    */
    @Test
    fun testDeleteTopology() {
        val api = TopologyApi()
        //start reading json
        api.readJson(ApiUtils.filePath)
        //passing topology id
        assertEquals(0, api.deleteTopology("top1"))
    }

    /*
    * right now we have one topology in the memory from the json file
    * test should validate the size of the topologies in the memory it should equal one
    */
    @Test
    fun testQueryTopologies() {
        val api = TopologyApi()
        //start reading json
        api.readJson(ApiUtils.filePath)
        assertEquals(1, api.queryTopologies().size)
    }
    /*
    * Testing connected device nodes
    * test should validate the size of the connected devices in the memory
    * it should be equal to one device connected to vss netlist
    */
    @Test
    fun testQueryDevicesWithNetListNode() {
        val api = TopologyApi()
        //start reading json
        api.readJson(ApiUtils.filePath)
        assertEquals(1, api.queryDeviceWithNetListNode("top1", "vss").size)
    }

}