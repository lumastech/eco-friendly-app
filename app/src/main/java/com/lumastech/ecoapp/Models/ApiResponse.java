package com.lumastech.ecoapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ApiResponse<T> {
    private boolean status;
    private String message;
    private T data;
    private Pagination pagination;
    private Error error;

    // Getters and Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    // Pagination Inner Class
    public static class Pagination {
        @SerializedName("current_page")
        private int currentPage;

        @SerializedName("total_pages")
        private int totalPages;

        @SerializedName("per_page")
        private int perPage;

        @SerializedName("total_items")
        private int totalItems;

        // Getters and Setters
        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }
    }

    // Error Inner Class
    public static class Error {
        private int code;
        private String description;
        private Map<String, List<String>> details;

        // Getters and Setters
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, List<String>> getDetails() {
            return details;
        }

        public void setDetails(Map<String, List<String>> details) {
            this.details = details;
        }
    }

    // Helper Methods
    public boolean isSuccess() {
        return status;
    }

    public boolean hasPagination() {
        return pagination != null;
    }

    public boolean hasError() {
        return error != null;
    }
}
