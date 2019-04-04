package com.options.json.responses;

import com.options.recommendation.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuickAnalyzeResponse {

    private Recommendation lastRecommendation;

}
