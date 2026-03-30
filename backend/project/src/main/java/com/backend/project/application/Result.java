package com.backend.project.application;

public class Result<T> {

    public enum Status{
        LOADING,
        SUCCESS,
        FAILURE
    }
    private final T value;
    private final String message;
    private final Status status;
    private final boolean success;

    public Result(T value, String message, Status status, boolean success) {
        this.value = value;
        this.message = message;
        this.status = status;
        this.success = success;
    }

    public static <T> Result<T> loading(String message){
        return new Result<>(null, message, Status.LOADING, true);
    }

    public static <T> Result<T> ok(T value){
        return new Result<>(value, null, Status.SUCCESS, true);
    }

    public static <T> Result<T> fail(String message){
        return new Result<>(null, message,Status.FAILURE, false);
    }

    public boolean isFailure() { return status == Status.FAILURE; }
    public boolean isSuccess() { return status == Status.SUCCESS; }
    public boolean isOk() { return isSuccess(); }
    public boolean isLoading() { return status == Status.LOADING; }
    public T getValue() { return value; }

    public String getMessage() { return message; }
    public Status getStatus() { return status; }

}
