package com.alura.forum.exception;

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String s) {
            super(s);
            }
    }
