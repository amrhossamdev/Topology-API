package helpers

import models.Device
import models.Topology
import kotlin.collections.ArrayList

class TopologyApi {
    private var topologies: ArrayList<Topology> = ArrayList()

    /*
    reads topologies from the json file and store it in the memory in the topologies arraylist
    @param filepath
    @return 0 if it's successfully read
     */
    fun readJson(filePath: String): Int {
        val topology = Topology("")
        topology.readFromJson(filePath)
        topologies.add(topology)
        return 0
    }

    /*
    converting topologies in the memory into a json file
    @param: topologyId to write
    @return: 0 for success and -1 for unsuccessful write
     */
    fun writeJson(topologyId: String): Int {
        val pos = getTopologyPosition(topologyId)
        if (pos != -1) {
            topologies[pos].writeToJson()
            println("Written json successfully for topology id = $topologyId check output")
            return 0
        }
        println("Cannot write json for topology id = $topologyId check topology id again")
        return -1
    }

    /*return the topology position in topology list it will return position (i) if found
    *@param topologyId
    *@return topology position in the list if found
    else it will return -1 */
    private fun getTopologyPosition(topologyId: String): Int {
        for (i in topologies.indices) {
            if (topologies[i].id == topologyId) {
                return i
            }
        }
        return -1
    }

    /*
    function to delete topology from the memory ( array list )
    @param: topologyId
    @return: integer 0 for success deletion or -1 in no topology id found in the memory
     */
    fun deleteTopology(topologyId: String): Int {
        val pos = getTopologyPosition(topologyId)
        if (pos != -1) {
            topologies.removeAt(pos)
            println("Successfully removed topology from the memory")
            return 0
        }
        println("No topology with the id = $topologyId found")
        return -1
    }

    /*
    return the list of the same device that have the same netlist nodes (connected devices)
    @param: topologyId
    @param: netListNodeId
    @return: array list of connected devices
     */
    fun queryDeviceWithNetListNode(topologyId: String, netListNodeId: String): ArrayList<Device> {
        val deviceList = ArrayList<Device>()
        val pos = getTopologyPosition(topologyId)
        if (pos != -1) {
            for (i in topologies[pos].getNetListConnectedDevices(netListNodeId)!!) {
                deviceList.add(topologies[pos].getDevice(i)!!)
            }
        }
        return deviceList
    }

    /*
    query about available device in the memory
    @param topology id
    @return array list of all devices in the memory
     */
    fun queryDevices(topologyId: String): ArrayList<Device>? {
        val pos: Int = getTopologyPosition(topologyId)
        if (pos != -1) {
            return topologies[pos].getDevices()
        }
        return null
    }

    /*
   query about available device in the memory
   @return the topology array list in the memory
    */
    fun queryTopologies(): ArrayList<Topology> {
        return topologies
    }

}