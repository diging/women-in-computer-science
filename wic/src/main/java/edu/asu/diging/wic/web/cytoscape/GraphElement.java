package edu.asu.diging.wic.web.cytoscape;

public class GraphElement {
    private Data data;
    
    public GraphElement(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
