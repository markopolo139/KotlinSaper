package pl.ms.saper.web.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.ms.saper.app.exceptions.InvalidPasswordException
import pl.ms.saper.app.exceptions.InvalidUserException
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

@Suppress("UNCHECKED_CAST")
@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @Autowired
    private lateinit var httpRequest: HttpServletRequest

    companion object {
        const val DEFAULT_SUGGESTED_ACTION = "Please contact with admin"
        const val DEFAULT_ERROR_MESSAGE = "Error occurred"
    }

    fun error(
        suggestedAction: String = DEFAULT_SUGGESTED_ACTION,
        errorMessage: String = DEFAULT_ERROR_MESSAGE,
        httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        apiSubErrors: List<ApiSubError> = emptyList()
    ): ResponseEntity<ApiError> = ApiError(suggestedAction, errorMessage, httpStatus, apiSubErrors).toResponseEntity()

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        val subApiErrors: List<ApiSubError> = ex.bindingResult.fieldErrors.asSequence().map {
            ApiSubError(
                error = "validation failed for ${it.rejectedValue} - ${it.defaultMessage}",
                suggestedAction = "Check rejected value"
            )
        }.toList()

        return error(
            suggestedAction = "Check error sublist for more information",
            errorMessage = "Error occurred during validation",
            apiSubErrors = subApiErrors,
            httpStatus = HttpStatus.BAD_REQUEST
        ) as ResponseEntity<Any>
    }

    @ExceptionHandler(InvalidUserException::class)
    fun invalidUserExceptionHandler(ex: InvalidUserException): ResponseEntity<ApiError> = error(
        suggestedAction = "Selected user does not exist, register before login",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordExceptionHandler(ex: InvalidPasswordException): ResponseEntity<ApiError> = error(
        suggestedAction = "Typed password is not correct, check error message",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )
}