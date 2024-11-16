package no.realitylab.arface

import android.content.Context
import android.widget.ImageView
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.AugmentedFaceNode

class CustomFaceNode(augmentedFace: AugmentedFace?,
                 val context: Context
): AugmentedFaceNode(augmentedFace) {

    private var monocNode: Node? = null
    private var mustacheNode: Node? = null

    companion object {
        enum class FaceRegion {
            MONOC,
            MUSTACHE
        }
    }

    override fun onActivate() {
        super.onActivate()
        monocNode = Node()
        monocNode?.setParent(this)



        mustacheNode = Node()
        mustacheNode?.setParent(this)

        ViewRenderable.builder()
            .setView(context, R.layout.element_layout)
            .build()
            .thenAccept { uiRenderable: ViewRenderable ->
                uiRenderable.isShadowCaster = false
                uiRenderable.isShadowReceiver = false
                monocNode?.renderable = uiRenderable
                uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.monoc)
            }
            .exceptionally { throwable: Throwable? ->
                throw AssertionError(
                    "Could not create ui element",
                    throwable
                )
            }

        ViewRenderable.builder()
            .setView(context, R.layout.element_layout)
            .build()
            .thenAccept { uiRenderable: ViewRenderable ->
                uiRenderable.isShadowCaster = false
                uiRenderable.isShadowReceiver = false
                mustacheNode?.renderable = uiRenderable
                uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(Mustache)
            }
            .exceptionally { throwable: Throwable? ->
                throw AssertionError(
                    "Could not create ui element",
                    throwable
                )
            }
    }

    private fun getRegionPose(region: FaceRegion) : Vector3? {
        val buffer = augmentedFace?.meshVertices
        if (buffer != null) {
            return when (region) {
                FaceRegion.MONOC ->
                    Vector3(buffer.get(374 * 3),buffer.get(374 * 3 + 1),  buffer.get(374 * 3 + 2))

                FaceRegion.MUSTACHE ->
                    Vector3(buffer.get(11 * 3),
                        buffer.get(11 * 3 + 1),
                        buffer.get(11 * 3 + 2))
            }
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace?.let {face ->
            getRegionPose(FaceRegion.MONOC)?.let {
                monocNode?.localPosition = Vector3(it.x - 0.01f, it.y - 0.15f, it.z + 0.015f)
                monocNode?.localScale = Vector3(0.077f, 0.077f, 0.077f)
                monocNode?.localRotation = Quaternion.axisAngle(Vector3(0.0f, 0.0f, 1.0f), -10f)
            }



            getRegionPose(FaceRegion.MUSTACHE)?.let {
                mustacheNode?.localPosition = Vector3(it.x, it.y - 0.04f, it.z + 0.015f)
                mustacheNode?.localScale = Vector3(0.07f, 0.07f, 0.07f)
            }
        }
    }
}