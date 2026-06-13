package com.finsight.backend.dto;

import java.util.List;

public class InsightResponse {

    private List<String> insights;

    public InsightResponse() {
    }

    public InsightResponse(List<String> insights) {
        this.insights = insights;
    }

    public List<String> getInsights() {
        return insights;
    }

    public void setInsights(List<String> insights) {
        this.insights = insights;
    }
}

