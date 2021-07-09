package com.neighbwang.gradle.transform.retrace

import com.didiglobal.booster.kotlinx.asIterable
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import java.io.PrintWriter
import java.util.concurrent.ConcurrentHashMap

/**
 * @author neighbWang
 */
@AutoService(ClassTransformer::class)
class RetraceTransformer : ClassTransformer {

    private lateinit var logger: PrintWriter

    private lateinit var searches: Set<String>
    private val result = ConcurrentHashMap<String, MutableSet<String>>()

    override fun onPreTransform(context: TransformContext) {
        logger = getReport(context, "report.txt").touch().printWriter()
        searches = context.getProperty("booster.transform.retraces", "")
            .split(',')
            .filter { it.isNotEmpty() }
            .map { it.trim() }
            .toSet()
    }

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        klass.methods.forEach { method ->
            method.instructions.iterator().asIterable().filterIsInstance<MethodInsnNode>().map { methodIns ->
                "${methodIns.owner}.${methodIns.name}${methodIns.desc}"
            }.filter {
                searches.contains(it)
            }.forEach {
                val result = result.getOrPut(it, {
                    mutableSetOf()
                })
                result.add("class : ${klass.name}, method : ${method.name}${method.desc}")
            }
        }
        return klass
    }

    override fun onPostTransform(context: TransformContext) {
        super.onPostTransform(context)
        result.forEach {
            logger.println()
            logger.println(" **** search info => ${it.key}, total size:${it.value.size}")
            it.value.forEach { location ->
                logger.println(location)
            }
        }
        this.logger.close()
    }
}
