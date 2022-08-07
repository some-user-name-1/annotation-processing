package com.stellarissoftware.experiment.processor

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class MyProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = mutableSetOf(MyAnnotation::class.java.canonicalName)

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?): Boolean {

        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Hello from the annotation processor!")

        return true
    }
}
