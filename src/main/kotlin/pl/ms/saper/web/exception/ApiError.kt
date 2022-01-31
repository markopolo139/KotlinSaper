package pl.ms.saper.web.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ApiError(
    var suggestedAction: String,
    var error: String,
    var httpStatus: HttpStatus,
    var subErrorList: List<ApiSubError>
) {
    fun toResponseEntity() = ResponseEntity<ApiError>(this, httpStatus)
}