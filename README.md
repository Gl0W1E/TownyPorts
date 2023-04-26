### **TownyPorts**
TownyPorts is a Minecraft plugin for Towny that allows the concept of instant travel between towns across oceans.\

### **System Requirements**
- Minimum Java version: 17
- Minimum MC version: 1.18.2
- Server type: Paper / Fork of Paper
- Minimum Towny version: 0.98.4.0
- Minimum Vault version: v1.17

### **Installation Guide**
TownyPorts requires and relies a single thing from the Towny configuration file.\
Go to the */serverdirectory/plugins/Towny/settings/config.yml* file and add the following lines:\
Use CTRL+F or any search tool to find the "townblocktypes:" option and create a new TownBlockType in there called "port" like this:\
`  - name: port`\
`    cost: 0.0`\
`    tax: 0.0`\
`    mapKey: P`\
`    itemUseIds: ''`\
`    switchIds: ''`\
`    allowedBlocks: ''`\
You can set "cost" or "tax" to a high value (optional) if you are going to enable economy for Towny and TownyPorts.

There are two .YML files for TownyPorts that you must know about.\
The config.yml file (located in */serverdirectory/plugins/TownyPorts/*) is used for storing Port travel fee values for travelling, and you must NOT deleted it even with economy turned off.\
The settings.yml file (located in */serverdirectory/plugins/TownyPorts/*) is used for actual configuration and values that an administrator might want to change, based on their liking.

### **Player Guide**

When a player wants to create a port as a town assistant or mayor, they must stand on a plot and type */plot set port*.\
They must make sure that the 4 middle blocks of the plot (at Y Level 61) are in an Ocean biome. Otherwise, the action will fail.

A town must not have more than two port plots. It's simply impossible with the TownyPorts plugin installed.

If a player wants to teleport to another port, they need to type the command */port* followed by the name of the town that owns the port, while standing on a port plot.\
They need to make sure the port is not too close and not too far away from their current location, according to the administrators' *settings.yml* configuration.
They also must make sure both ports are not part of nationless towns. If any of them are, the action will fail.

A town mayor can set their town's port travel fee to any value from X (configurable by administrators) to Z (configurable by administrators) using the command */setportprice (integer)*.\
That way, travelling from a port to another is not so easy, and players need to pay.\
The money goes from the player's economy account to the town's economy account.

Before a player can travel to a port, they can use the command */portprice* followed by the port's town name to see the cost of travelling.

### **Admin Commands**
 /townuuid (town-name)
  - Permission: **townyports.townuuid** || **CONSOLE**
  - Use this to find the UUID of a town, which is used to store the port travel fee values in the config.yml for each town. 
