package tk.parking.app.http.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import tk.parking.app.exception.SegmentNotFoundException;
import tk.parking.app.http.response.NotFoundErrResponse;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SegmentNotFoundException.class)
    public NotFoundErrResponse handleSegmentNotFound(final SegmentNotFoundException e) {
        return NotFoundErrResponse.builder().notFound(e.segment).build();
    }
}
