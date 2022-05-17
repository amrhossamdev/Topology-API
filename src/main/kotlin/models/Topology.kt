package models

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import kotlin.collections.ArrayList

/*
class topology represent topology in the memory
having topology id and device list and also netlist
 */

//Primary constructor
class Topology(ID: String) {
    internal var id = ID
    private var devicesList = ArrayList<Device>()
    private var netList: HashMap<String, ArrayList<String>>? = null

    //initiating values
    init {
        devicesList = ArrayList()
        netList = HashMap<String, ArrayList<String>>()
        id = ID
    }

    /*writing topology to json by getting topology from memory
     and converting it to json object
     */
    fun writeToJson() {
        val topologyObj = JSONObject()
        topologyObj["id"] = id
        val componentsArray = JSONArray()
        for (device in devicesList) {
            componentsArray.add(device.generateJsonObject())
        }
        topologyObj["components"] = componentsArray
        //writing json to file
        writeFile(topologyObj)
    }

    /*
    writing file to json
    @param: json object */
    private fun writeFile(json: JSONObject) {
        val file = FileWriter("topology-${id}")
        file.write(json.toJSONString())
        file.flush()
        file.close()
    }

    /*Reading topologies from json file
    * Function takes json filePath
    * Start reading every object in json object and convert it to class ( represented in memory )
    * We have two type of devices resistor and nmos creating classes for two devices then add them to memory
    * We have device class that ( super class ) for resistor and nmos class
    * @param: String file path
    *  */
    fun readFromJson(filePath: String) {
        val jsonParser = JSONParser()
        val obj: Any = jsonParser.parse(FileReader(filePath))
        val jsonObject = obj as JSONObject
        val deviceId = jsonObject["id"] as String
        id = deviceId
        //println("Topology ID $deviceId")
        val components = jsonObject["components"] as JSONArray
        for (i in components.indices) {
            val componentObj = components[i] as JSONObject
            // We have two type of devices resistor and nmos
            if (componentObj["type"] == "resistor") {
                val resistor = Resistor()
                resistor.readFromJson(componentObj)
                addDevice(resistor)
            } else if (componentObj["type"] == "nmos") {
                val nmos = Nmos()
                nmos.readFromJson(componentObj)
                addDevice(nmos)
            }
        }
    }

    /*
    adding device to the memory
    @param:Device object
     */
    private fun addDevice(device: Device) {
        devicesList.add(device)
        if (device.getType() == "resistor") {
            val resistorDevice = device as Resistor
            //connecting nodes using hashmaps
            addDeviceToNetList(resistorDevice.t1, resistorDevice.getId())
            addDeviceToNetList(resistorDevice.t2, resistorDevice.getId())
        } else if (device.getType() == "nmos") {
            val nmosDevice = device as Nmos
            addDeviceToNetList(nmosDevice.drain, nmosDevice.getId())
            addDeviceToNetList(nmosDevice.gate, nmosDevice.getId())
            addDeviceToNetList(nmosDevice.source, nmosDevice.getId())
        }
        //println("Added device successfully")
    }

    //return the devices list
    fun getDevices(): ArrayList<Device> {
        return devicesList
    }

    /*return device if found else return null
    * @Param deviceId
    * @return device object
    * */
    fun getDevice(id: String): Device? {
        for (i in devicesList) {
            if (i.getId() == id) {
                return i
            }
        }
        return null
    }

    /*
    Function helps adding connected device to hashmaps
    *connecting device in the same gates or inputs(t1,t2)
    @param: key drain,gate,source,t1,t2
    @param: deviceId
     */
    private fun addDeviceToNetList(key: String, deviceId: String) {
        var deviceList: ArrayList<String>? = netList?.get(key)
        // if not device list we create one
        if (deviceList == null) {
            deviceList = ArrayList()
            deviceList.add(deviceId)
            netList?.set(key, deviceList)
        } else {
            // if device is not in the list add it
            if (!deviceList.contains(deviceId)) deviceList.add(deviceId)
        }
    }

    /*
    return the list of connected devices in netlist node
    @param netListId
    @return devices array list
     */
    fun getNetListConnectedDevices(netlistId: String): ArrayList<String>? {
        return netList?.get(netlistId)
    }
}