package com.alura.forum.exception;

    public class ResourceNotFoundException extends Exception {
        public ResourceNotFoundException(String s) {
            super(s);
            }

        public ResourceNotFoundException() {
                super();
            }
    }
