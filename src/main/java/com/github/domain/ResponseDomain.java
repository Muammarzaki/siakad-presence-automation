package com.github.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDomain<T> {
    private int statusCode;
    private HttpStatus status;
    private Map<String, String> message;
    private T data;

    public static class Builder {
        private int statusCode;
        private HttpStatus status;
        private Map<String, String> message = new HashMap<>();

        /**
         * @return the statusCode
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * @param statusCode the statusCode to set
         * @return
         */
        public ResponseDomain.Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * @return the status
         */
        public HttpStatus getStatus() {
            return status;
        }

        /**
         * @param accepted the status to set
         * @return
         */
        public ResponseDomain.Builder setStatus(HttpStatus accepted) {
            this.status = accepted;
            return this;
        }

        /**
         * @return the message
         */
        public Map<String, String> getMessage() {
            return message;
        }

        /**
         * @param message the message to set
         * @return
         */
        public ResponseDomain.Builder setMessage(Map<String, String> message) {
            this.message = message;
            return this;
        }

        public ResponseDomain<Object> build() {
            return new ResponseDomain<>(
                    this.statusCode,
                    this.status,
                    this.message,
                    null);
        }

        public ResponseDomain<Object> build(Object data) {
            return new ResponseDomain<>(
                    this.statusCode,
                    this.status,
                    this.message,
                    data);
        }

    }
}
