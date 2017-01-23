package nl.hu.tosad.businessruleservice.exceptions;

public class BusinessRuleServiceException extends RuntimeException {
    public BusinessRuleServiceException(String message) {
        super(message);
    }

    public BusinessRuleServiceException(String message, Throwable e) {
        super(message, e);
    }

    public BusinessRuleServiceException(Throwable e) {
        super(e.getMessage(), e);
    }
}
