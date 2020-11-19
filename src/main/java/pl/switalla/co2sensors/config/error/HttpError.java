package pl.switalla.co2sensors.config.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Class used for signaling API errors
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpError {
    HttpStatus status;
    String message;
}
