package com.prgrms.boardapp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class PostRequest {
    private String title;
    private String content;

    private PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static PostRequestBuilder builder() {
        return new PostRequestBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostRequest that = (PostRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(content, that.content);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public static class PostRequestBuilder {
        private String title;
        private String content;

        PostRequestBuilder() {
        }

        public PostRequestBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public PostRequestBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public PostRequest build() {
            return new PostRequest(this.title, this.content);
        }
    }
}
