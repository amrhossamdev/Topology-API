# Topology-Api
Topology API is an a API to provide the functionality to access, manage and store device topologies.
# Langugage 
Kotlin is a modern, statically-typed programming language that features both object-oriented and functional programming constructs
#Functionality
1. Read a topology from a given JSON file and store it in the memory. 
2. Write a given topology from the memory to a JSON file. 
3. Query about which topologies are currently in the memory. 
4. Delete a given topology from memory.
5. Query about which devices are in a given topology.
6. Query about which devices are connected to a given netlist node in a given topology

# Technologies 
- Kotlin
- Intelj IDE
- Maven 
- Junit for unit testing
- Json simple to parse json files to json objects

# Design 
Design is done using object orinted programming all tests passed and achived  

- `Topology` class represent topology in the memory
-  We have two type of the devices resistor and nmos
- `Device` is the parentclass and resistor & nmos child classes

# Run project 
First, clone the repo:

`git clone git@github.com:amrhossamdev/Topology-Api.git`

# Run project in intellj
open -> and import the project then select maven 
#Testing 
Done using junit testing 
You can test all functionalities in test -> then open `TopologyApiTest`


