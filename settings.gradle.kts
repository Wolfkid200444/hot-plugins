include(":clapclap")
include(":Zoo")
include(":PetPet")
include(":Osu")
rootProject.name = "plugins"

include(":DiscordStubs")
project(":DiscordStubs").projectDir = File("../repo/DiscordStubs")


include(":Aliucord")
project(":Aliucord").projectDir = File("../repo/Aliucord")
