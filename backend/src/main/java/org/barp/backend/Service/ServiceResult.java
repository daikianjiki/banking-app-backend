package org.barp.backend.Service;

import java.util.HashMap;
import java.util.Optional;
/**
 * This class encapsulates the response from the service layer. It extends a HashMap which can
 * be converted to a JSON object to be sent to the end user.
 * <br><br>
 * Wrapping the response enables providing extra data to the consumer. Each response can contain the result, a detailed description
 * message, a status code, and a short status message.
 * The result is of the same type passed to this class as the Generic parameter T.
 *<br><br>
 * This class encapsulates: <br>
 * A result <br>
 * A descriptive message <br>
 * A status code <br>
 * A short status message <br>
 * @param <T>
 */
public class ServiceResult<T> extends HashMap<String, String> {
    private T result;
    public enum ServiceCode {
        // add new service codes here
        NONE ("", 0),
        INFORMATION ("information", 100),
        SUCCESS ("success", 200),
        REDIRECT ("redirect", 300),
        CLIENT_ERROR ("client error", 400),
        SERVER_ERROR ("server error", 500)
        ;

        public final String message;
        public final int statusCode;
        ServiceCode(String msg, int statusCode)
        {
            this.message = msg;
            this.statusCode = statusCode;
        }
    }

    ServiceResult(){
        addResult(null);
        addDescription("");
        addServiceCode(ServiceCode.NONE);
    }
    ServiceResult(T result){
        addResult(result);
        addDescription("");
        addServiceCode(ServiceCode.NONE);
    }
    ServiceResult(ServiceCode serviceCode){
        addResult(null);
        addDescription("");
        addServiceCode(serviceCode);
    }
    ServiceResult(ServiceCode serviceCode, String description){
        addResult(null);
        addDescription(description);
        addServiceCode(serviceCode);
    }
    ServiceResult(T result, String description){
        addResult(result);
        addDescription(description);
        addServiceCode(ServiceCode.NONE);
    }
    ServiceResult(T result, ServiceCode serviceCode){
        addResult(result);
        addDescription("");
        addServiceCode(serviceCode);
    }
    ServiceResult(T result, ServiceCode serviceCode,  String description){
        addResult(result);
        addDescription(description);
        addServiceCode(serviceCode);
    }

    private void addResult(T result)
    {
        this.result = result;
        this.put("result", Optional.ofNullable(result)
                .map(Object::toString)
                .orElse(""));
    }
    private void addDescription(String message)
    {
        this.put("description", message);
    }
    private void addServiceMsg(String message)
    {
        this.put("message", message);
    }
    private void addServiceCode(int serviceCode)
    {
        this.put("code", String.valueOf(serviceCode));
    }
    private void addServiceCode(ServiceCode serviceCode)
    {
        addServiceMsg(serviceCode.message);
        addServiceCode(serviceCode.statusCode);
    }

    public Optional<T> getResult()
    {
        return Optional.ofNullable( this.result );
    }
}
