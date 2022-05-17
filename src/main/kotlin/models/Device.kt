package models

import org.json.simple.JSONObject

//primary constructor
open class Device(Type: String, Id: String, defaultValue: Double, min: Double, max: Double) {
    private var type = Type
    private var id = Id
    private var deviceSpecs = DeviceSpecs(defaultValue, max, min)


    //It will be overridden in resistor and nmos classes to get json objects from it
    open fun generateJsonObject(): JSONObject? {
        return null
    }

    /*
   converting json to json object then to class representing device class
   building device from json object
   @param: json file (topologies json)
    */
    open fun readFromJson(jsonObject: JSONObject?) {}

    open fun setId(ID: String) {
        id = ID
    }

    open fun getId(): String {
        return id
    }

    open fun setType(Type: String) {
        type = Type
    }

    open fun getType(): String? {
        return type
    }

    open fun setSpecifications(deviceSpecs: DeviceSpecs) {
        this.deviceSpecs = deviceSpecs
    }

    open fun getDeviceSpecs(): DeviceSpecs {
        return deviceSpecs
    }

}