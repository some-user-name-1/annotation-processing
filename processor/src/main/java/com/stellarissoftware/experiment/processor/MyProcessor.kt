package com.stellarissoftware.experiment.processor

import java.io.PrintWriter
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@SupportedSourceVersion(SourceVersion.RELEASE_8)
class MyProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = mutableSetOf(MyAnnotation::class.java.canonicalName)

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment): Boolean {

        roundEnv.getElementsAnnotatedWith(MyAnnotation::class.java)
            .forEach { element ->
                val className = element.simpleName
                val packageName = (element.enclosingElement as PackageElement).qualifiedName
                val builderFile = processingEnv.filer.createSourceFile("${className}Factory")

                with (PrintWriter(builderFile.openWriter())) {
                    println("""
                        import $packageName.$className;

                        public class ${className}Factory {
                            public static $className create() {
                                return new $className();
                            }
                        }
                        """.trimIndent())

                    close()
                }
            }

        return true
    }
}
