package com.options.json.responses;


import com.options.recommendation.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullAnalyzeResponse {

    private List<Recommendation> recommendationList;

}
