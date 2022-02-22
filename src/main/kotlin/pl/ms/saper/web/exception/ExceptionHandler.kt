package pl.ms.saper.web.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.ms.saper.app.exceptions.*
import pl.ms.saper.business.exceptions.*
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

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

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationExceptionHandler(ex: ConstraintViolationException): ResponseEntity<ApiError> {
        val subApiErrors: List<ApiSubError> = ex.constraintViolations.asSequence().map {
            ApiSubError(
                error = "validation failed for ${it.invalidValue} - ${it.message}",
                suggestedAction = "Check rejected value"
            )
        }.toList()

        return error(
            suggestedAction = "Check error sublist for more information",
            errorMessage = "Error occurred during validation",
            apiSubErrors = subApiErrors,
            httpStatus = HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(InvalidUserException::class)
    fun invalidUserExceptionHandler(ex: InvalidUserException): ResponseEntity<ApiError> = error(
        suggestedAction = "Selected user does not exist, register before login",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(InvalidUserEmailException::class)
    fun invalidUserEmailExceptionHandler(ex: InvalidUserEmailException): ResponseEntity<ApiError> = error(
        suggestedAction = "Type correct email",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordExceptionHandler(ex: InvalidPasswordException): ResponseEntity<ApiError> = error(
        suggestedAction = "Typed password is not correct, check error message",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(SendingEmailException::class)
    fun sendingEmailExceptionHandler(ex: SendingEmailException): ResponseEntity<ApiError> = error(
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    )

    @ExceptionHandler(InvalidTokenException::class)
    fun invalidTokenExceptionHandler(ex: InvalidTokenException): ResponseEntity<ApiError> = error(
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(InvalidPositionException::class)
    fun invalidPositionExceptionHandler(ex: InvalidPositionException): ResponseEntity<ApiError> = error(
        suggestedAction = "Select valid position",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(InvalidSpotException::class)
    fun invalidSpotExceptionHandler(ex: InvalidSpotException): ResponseEntity<ApiError> = error(
        suggestedAction = "Select valid spot",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(SpotCheckedException::class)
    fun spotCheckedExceptionHandler(ex: SpotCheckedException): ResponseEntity<ApiError> = error(
        suggestedAction = "Select not checked spot",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(SpotFlaggedException::class)
    fun spotFlaggedExceptionHandler(ex: SpotFlaggedException): ResponseEntity<ApiError> = error(
        suggestedAction = "Select not flagged spot for check",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(SpotMinedException::class)
    fun spotFlaggedExceptionHandler(ex: SpotMinedException): ResponseEntity<ApiError> = error(
        suggestedAction = "Start new game",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(SpotRevealException::class)
    fun spotRevealExceptionHandler(ex: SpotRevealException): ResponseEntity<ApiError> = error(
        suggestedAction = "Select spot which has equals flagged spot around to number on spot",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(InvalidValueException::class)
    fun invalidValueExceptionHandler(ex: InvalidValueException): ResponseEntity<ApiError> = error(
        suggestedAction = "Type valid value",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(ConfigNotFoundException::class)
    fun configNotFoundExceptionHandler(ex: ConfigNotFoundException): ResponseEntity<ApiError> = error(
        suggestedAction = "Create new game",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(BoardNotFoundException::class)
    fun boardNotFoundExceptionHandler(ex: BoardNotFoundException): ResponseEntity<ApiError> = error(
        suggestedAction = "Create new user",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    )
}