package models

import org.json.simple.JSONObject

//primary constructor represent nmos device
//overridden constructor from device class
class Nmos(
    type: String,
    id: String,
    defaultValue: Double,
    min: Double,
    max: Double,
    Drain: String,
    Gate: String,
    Source: String,
) : Device(type, id, defaultValue, min, max) {

    // empty constructor
    constructor() : this("", "", 0.0, 0.0, 0.0, "", "", "")

    var drain = Drain
    var gate = Gate
    var source = Source

    /*
    converting json to json object then to class representing nmos class
    building device from json object
    overriding from device class
    @param: json file (topologies json)
     */
    override fun readFromJson(jsonObject: JSONObject?) {
        val id = jsonObject?.get("id") as? String
        val type = jsonObject?.get("type") as? String

        //getting ml object values
        val mlObj = jsonObject?.get("m(l)") as? JSONObject
        val defaultValue = mlObj?.get("default") as? Double
        val min = mlObj?.get("min") as? Double
        val max = mlObj?.get("max") as? Double

        //getting netlistObj
        val netListObj = jsonObject?.get("netlist") as? JSONObject

        val drainValue = netListObj?.get("drain") as? String
        val gateValue = netListObj?.get("gate") as? String
        val sourceValue = netListObj?.get("source") as? String

        val deviceSpecs = DeviceSpecs(defaultValue, max, min)
        // adding members to device class
        setSpecifications(deviceSpecs)
        setType(type!!)
        setId(id!!)
        //getting values from json obj
        //to string to prevent getting null values
        drain = drainValue.toString()
        gate = gateValue.toString()
        source = sourceValue.toString()
    }

    /*
        generate json object ( converting class to json object )
        @return jsonObject
         */
    override fun generateJsonObject(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject["type"] = getType()
        jsonObject["id"] = getId()
        val mlObj = JSONObject()
        val deviceSpecs: DeviceSpecs = getDeviceSpecs()
        //ml json object
        mlObj["default"] = deviceSpecs.default
        mlObj["min"] = deviceSpecs.min
        mlObj["max"] = deviceSpecs.max
        jsonObject["m(l)"] = mlObj
        //netlist json object
        val netlistObj = JSONObject()
        netlistObj["drain"] = drain
        netlistObj["gate"] = gate
        netlistObj["source"] = source

        jsonObject["netlist"] = netlistObj

        return jsonObject
    }
}