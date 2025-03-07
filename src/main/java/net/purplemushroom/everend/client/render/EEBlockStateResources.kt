package net.purplemushroom.everend.client.render;

import ru.timeconqueror.timecore.api.client.resource.BlockStateResource
import ru.timeconqueror.timecore.api.client.resource.location.BlockModelLocation
import ru.timeconqueror.timecore.api.util.json.json
import ru.timeconqueror.timecore.api.util.json.x
import ru.timeconqueror.timecore.api.util.json.y
import ru.timeconqueror.timecore.api.util.json.z

object EEBlockStateResources {
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
    fun oneVarAxisXYZState(location: BlockModelLocation): BlockStateResource {
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

    @JvmStatic
    fun oneVarFacingNESWState(location: BlockModelLocation): BlockStateResource {
        return BlockStateResource.fromJson(json {
            "variants" {
                "facing=east" {
                    "model" set location
                    y = 90
                }
                "facing=north" {
                    "model" set location
                }
                "facing=south" {
                    "model" set location
                    y = 180
                }
                "facing=west" {
                    "model" set location
                    z = 270
                }
            }
        })
    }

    @JvmStatic
    fun endAltarState(stage0Location: BlockModelLocation, stage1Location: BlockModelLocation, stage2Location: BlockModelLocation, stage3Location: BlockModelLocation, stage4Location: BlockModelLocation, stage5Location: BlockModelLocation): BlockStateResource {
        return BlockStateResource.fromJson(json {
            "variants" {
                "facing=east,stage=0" {
                    "model" set stage0Location
                    y = 90
                }
                "facing=east,stage=1" {
                    "model" set stage1Location
                    y = 90
                }
                "facing=east,stage=2" {
                    "model" set stage2Location
                    y = 90
                }
                "facing=east,stage=3" {
                    "model" set stage3Location
                    y = 90
                }
                "facing=east,stage=4" {
                    "model" set stage4Location
                    y = 90
                }
                "facing=east,stage=5" {
                    "model" set stage5Location
                    y = 90
                }

                "facing=north,stage=0" {
                    "model" set stage0Location
                }
                "facing=north,stage=1" {
                    "model" set stage1Location
                }
                "facing=north,stage=2" {
                    "model" set stage2Location
                }
                "facing=north,stage=3" {
                    "model" set stage3Location
                }
                "facing=north,stage=4" {
                    "model" set stage4Location
                }
                "facing=north,stage=5" {
                    "model" set stage5Location
                }

                "facing=south,stage=0" {
                    "model" set stage0Location
                    y = 180
                }
                "facing=south,stage=1" {
                    "model" set stage1Location
                    y = 180
                }
                "facing=south,stage=2" {
                    "model" set stage2Location
                    y = 180
                }
                "facing=south,stage=3" {
                    "model" set stage3Location
                    y = 180
                }
                "facing=south,stage=4" {
                    "model" set stage4Location
                    y = 180
                }
                "facing=south,stage=5" {
                    "model" set stage5Location
                    y = 180
                }

                "facing=west,stage=0" {
                    "model" set stage0Location
                    z = 270
                }
                "facing=west,stage=1" {
                    "model" set stage1Location
                    z = 270
                }
                "facing=west,stage=2" {
                    "model" set stage2Location
                    z = 270
                }
                "facing=west,stage=3" {
                    "model" set stage3Location
                    z = 270
                }
                "facing=west,stage=4" {
                    "model" set stage4Location
                    z = 270
                }
                "facing=west,stage=5" {
                    "model" set stage5Location
                    z = 270
                }
            }
        })
    }

    @JvmStatic
    fun anchorState(
        charge0: BlockModelLocation,
        charge1: BlockModelLocation,
        charge2: BlockModelLocation,
        charge3: BlockModelLocation,
        charge4: BlockModelLocation
    ): BlockStateResource {
        return BlockStateResource.fromJson(json {
            "variants" {
                "charges=0" {
                    "model" set charge0
                }
                "charges=1" {
                    "model" set charge1
                }
                "charges=2" {
                    "model" set charge2
                }
                "charges=3" {
                    "model" set charge3
                }
                "charges=4" {
                    "model" set charge4
                }
            }
        })
    }
}
