package edu.asu.diging.wic.core.graphs;

import edu.asu.diging.wic.core.model.impl.Edge;

public interface IPredicateProcessor {

    void setPredicateName(Edge edge, String transformationName);

    void setPredicateUri(Edge edge);

}