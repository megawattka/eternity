package org.mgwt.eternity.config

import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor
import io.github.notenoughupdates.moulconfig.processor.BuiltinMoulConfigGuis
import io.github.notenoughupdates.moulconfig.processor.ConfigProcessorDriver
import org.mgwt.eternity.Eternity
import java.lang.Exception
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.staticFunctions
import kotlin.reflect.jvm.isAccessible

object ConfigGuiManager {

    private var editor: MoulConfigEditor<Features>? = null

    fun getEditorInstance(): MoulConfigEditor<Features>? {
        if (this.editor == null) {
            println("1")
            val processor = BlockingMoulConfigProcessor()
            BuiltinMoulConfigGuis.addProcessors(processor)
            println("2")
            val driver = ConfigProcessorDriver(processor)
            println("3")
            driver.warnForPrivateFields = false

            val func1 = ConfigProcessorDriver::class.staticFunctions
                .first { it.name == "getAllFields" }
                .apply { isAccessible = true }

            val func2 = ConfigProcessorDriver::class.memberFunctions
                .first { it.name == "processCategoryMeta" }
                .apply { isAccessible = true }

            var x = 0
            for (categoryField in func1.call(Eternity.features.javaClass) as List<*>) {
                println(categoryField)
                try {
                    func2.call(driver, Eternity.features, categoryField, null)
                }
                catch (e: Exception) {
                    println(e)
                }
                println(x)
                x += 1
            }
            driver.processConfig(Eternity.features)
            println("4")
            val editor = MoulConfigEditor(processor)
            println("5")
            throw RuntimeException("LOX")
            this.editor = editor
        }
        return this.editor
    }
}