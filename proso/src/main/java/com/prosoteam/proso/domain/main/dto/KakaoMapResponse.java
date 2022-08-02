package com.prosoteam.proso.domain.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoMapResponse {


    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<Documents> documents;

    public Meta getMeta() {
        return meta;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public static class Meta {
        @JsonProperty("total_count")
        private int totalCount;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("is_end")
        private boolean isEnd;

        public int getTotalCount() {
            return totalCount;
        }

        public int getPageableCount() {
            return pageableCount;
        }

        public boolean getIsEnd() {
            return isEnd;
        }
    }
}

