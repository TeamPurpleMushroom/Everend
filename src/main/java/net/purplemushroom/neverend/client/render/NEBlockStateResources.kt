package net.purplemushroom.neverend.client.render;

import ru.timeconqueror.timecore.api.client.resource.BlockStateResource
import ru.timeconqueror.timecore.api.client.resource.location.BlockModelLocation
import ru.timeconqueror.timecore.api.util.json.json
import ru.timeconqueror.timecore.api.util.json.x
import ru.timeconqueror.timecore.api.util.json.y

object NEBlockStateResources {
    @JvmStatic
    fun halfState(modelLocationTop: BlockModelLocation, modelLocationBottom: BlockModelLocation): BlockStateResource {
        return BlockStateResource.fromJson(json {
            "variants" {
                "half=lower" {
                    "model" set modelLocationTop.toString()
                }
                "half=upper" {
                    "model" set modelLocationBottom.toString()
                }
            }
        })
    }

    @JvmStatic
    fun rotatablePillarState(location: BlockModelLocation): BlockStateResource {
        return BlockStateResource.fromJson(json {
            "variants" {
                "axis=y" {
                    "model" set location
                }
                "axis=z" {
                    "model" set location
                    x = 90
                }
                "axis=x" {
                    "model" set location
                    x = 90
                    y = 90
                }
            }
        })
    }
}
