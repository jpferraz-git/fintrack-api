package com.backend.project.application;

import jdk.jshell.Snippet;

public class Result<T> {

    public enum Status{
        LOADING,
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

    public static <T> Result<T> loading(String message){
        return new Result<>(null, message, Status.LOADING);
    }

    public static <T> Result<T> ok(T value){
        return new Result<>(value, null, Status.SUCCESS);
    }

    public static <T> Result<T> fail(String message){
        return new Result<>(null, message,Status.FAILURE);
    }

    public Status isFailure() { return Status.FAILURE; }
    public Status isSuccess() { return Status.SUCCESS; }
    public Status isLoading() { return Status.LOADING; }
    public T getValue() { return value; }

    public String getMessage() { return message; }
    public Status getStatus() { return status; }
}
