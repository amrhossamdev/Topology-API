package models

import org.json.simple.JSONObject

//primary constructor
//overridden constructor from device class
class Resistor(T1: String, T2: String, type: String, id: String, defaultValue: Double, min: Double, max: Double) :
    Device(type, id, defaultValue, min, max) {

    //empty constructor
    constructor() : this("", "", "", "", 0.0, 0.0, 0.0)

    //Resistor netlist
    internal var t1 = T1
    internal var t2 = T2

    /*
    converting json to json object then to class representing resistor class
    building device from json object
    overriding from device class
    @param: json file (topologies json)
     */
    override fun readFromJson(jsonObject: JSONObject?) {
        //println("our json $jsonObject")
        val type = jsonObject?.get("type") as? String?
        val id = jsonObject?.get("id") as? String?
        //getting resistance values from resistance obj
        val resistanceObj = jsonObject?.get("resistance") as? JSONObject
        //println("our resistance $resistanceObj")

        /*parsing long values to double and access elements from resistance object*/
        val defaultValue = (resistanceObj?.get("default") as Long).toDouble()
        val min = (resistanceObj["min"] as Long).toDouble()
        val max = (resistanceObj["max"] as Long).toDouble()
        //println("our resistance values $min \n $max \n $defaultValue")
        //getting netlistObj
        val netListObj = jsonObject["netlist"] as? JSONObject
        val t1 = netListObj?.get("t1") as? String
        val t2 = netListObj?.get("t2") as? String

        val deviceSpecs = DeviceSpecs(defaultValue, max, min)

        // adding members to device class
        setSpecifications(deviceSpecs)
        setType(type!!)
        setId(id!!)

        this.t1 = t1.toString()
        this.t2 = t2.toString()
    }

    /*
    generate json object ( converting class to json object )
    @return jsonObject
     */
    override fun generateJsonObject(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject["type"] = getType()
        jsonObject["id"] = getId()

        val resistanceObj = JSONObject()
        val deviceSpecs: DeviceSpecs = getDeviceSpecs()

        resistanceObj["default"] = deviceSpecs.default
        resistanceObj["min"] = deviceSpecs.min
        resistanceObj["max"] = deviceSpecs.max
        jsonObject["resistance"] = resistanceObj

        val netlistObj = JSONObject()
        netlistObj["t1"] = t1
        netlistObj["t2"] = t2

        jsonObject["netlist"] = netlistObj
        return jsonObject
    }
}