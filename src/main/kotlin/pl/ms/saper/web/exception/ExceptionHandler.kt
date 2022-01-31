package pl.ms.saper.web.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.ms.saper.app.exceptions.InvalidUserException
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

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

    @ExceptionHandler(InvalidUserException::class)
    fun invalidUserExceptionHandler(ex: InvalidUserException): ResponseEntity<ApiError> = error(
        suggestedAction = "Selected user does not exist, register before login",
        errorMessage = ex.message ?: DEFAULT_ERROR_MESSAGE,
        httpStatus = HttpStatus.BAD_REQUEST
    )
}