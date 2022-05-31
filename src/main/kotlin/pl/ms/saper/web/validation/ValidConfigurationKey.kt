package pl.ms.saper.web.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.TYPE,
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidConfigurationKeyImpl::class])
annotation class ValidConfigurationKey(
    val message: String = "Invalid name of configuration key",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)