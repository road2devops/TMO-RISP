
package com.tmobile.retailinventoryserialization.query.device.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tmobile.retailinventoryserialization.query.device.exception.CustomHTTPResponse;
import com.tmobile.retailinventoryserialization.query.device.exception.HTTPErrorResponse;

/**
 * The Class EcommerceErrorController. This class handles single point exception handler for EccommerceService Module. .
 */
@RestController( "${error.mapping}")
public class ServiceErrorController implements ErrorController {

	/** The log. */
    private static Logger      log                   = LoggerFactory.getLogger(ServiceErrorController.class);

	/** The error attributes. */
    @Autowired
    private ErrorAttributes    errorAttributes;

    /** The Constant PATH. */
    public static final String PATH      = "/error";

    /** The Constant ERROR. */
    public static final String STR_ERROR = "error";

    /**
     * Error.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @param ex
     *            the ex
     * @return the error json
     */
    @ExceptionHandler
    @RequestMapping( value = "${error.mapping}")
    public CustomHTTPResponse error( HttpServletRequest request, HttpServletResponse response, Exception ex) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request, response);
        Map<String, Object> error = errorAttributes.getErrorAttributes(requestAttributes, true);

        String message = (String) error.get("error");
        int statusCode = (Integer) error.get("status");
        String exception = (String) error.get("exception");

        if (ex instanceof HttpClientErrorException) {
            statusCode = ((HttpClientErrorException) ex).getStatusCode().value();
            exception = ((HttpClientErrorException) ex).getMessage();
        }

        HTTPErrorResponse errorResp = new HTTPErrorResponse(exception, message);
        return new CustomHTTPResponse(STR_ERROR, statusCode, errorResp, request.getRequestURI());

    }

    /*
     * (non-Javadoc)
     * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
     */

    @Override
    public String getErrorPath() {

        return PATH;

    }

}
