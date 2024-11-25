package com.shoppingplatform.product.controller;

record ApiResponse<T>(int status, String message, T data) {

    static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}