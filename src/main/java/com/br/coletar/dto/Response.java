package com.br.coletar.dto;

import java.util.List;

public class Response<T> {

    //pros casos de sucesso
    private T data;
    //pros casos de erros
    private List<String> errors;

    public Response(T data) {
        this.data = data;
    }

    public Response(List<String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
