package com.backend.project.domain.utils;

public class Result<T> {

    public enum Status{
        SUCCESS,
        FAILURE
    }
    private final T value;
    private final String message;
    private final Status status;

    private Result(T value, String message, Status status) {
        this.value = value;
        this.message = message;
        this.status = status;
    }

    public static <T> Result<T> ok(T value){
        return new Result<>(value, null, Status.SUCCESS);
    }

    public static <T> Result<T> fail(String message){
        return new Result<>(null, message, Status.FAILURE);
    }

    public boolean isFailure() { return status == Status.FAILURE; }
    public boolean isSuccess() { return status == Status.SUCCESS; }
    public boolean isOk() { return isSuccess(); }
    public T getValue() { return value; }

    public String getMessage() { return message; }
    public Status getStatus() { return status; }

}
