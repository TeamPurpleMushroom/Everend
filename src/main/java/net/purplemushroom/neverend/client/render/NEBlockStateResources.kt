package net.purplemushroom.neverend.client.render;

import ru.timeconqueror.timecore.api.client.resource.BlockStateResource
import ru.timeconqueror.timecore.api.client.resource.location.BlockModelLocation
import ru.timeconqueror.timecore.api.util.json.json

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
}
