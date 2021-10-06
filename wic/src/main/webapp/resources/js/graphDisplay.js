function highlightPersonInGraph() {
	var highlightSize = "50px";
    var id = $(this).data("concept-id");
    var node = cy.getElementById(id);
    cy.animate({
        fit: {
            eles: node,
            padding: 230,
        }
    });
    node.animate({
        css: { 'width': highlightSize, 'height' : highlightSize},
    });     
}

function removeHighlight() {
	var nodeSize = "15px";
    var id = $(this).data("concept-id");
    var node = cy.getElementById(id);
    node.animate({
        css: { 'width': nodeSize, 'height' : nodeSize}
    });
}

function loadCytoScape(data, result, removeNodes) {
	var nodeSize = "15px";
	var cy = cytoscape({
        container: $('#network'),
        zoom: 1,
        pan: { x: 0, y: 0 },
        elements: result,
        pixelRatio: 1,
        textureOnViewport: true,
        style: [ // the stylesheet for the graph
            {
                selector: 'node',
                style: {
                    'background-color': 'data(color)',
                    'width': nodeSize,
                    'height': nodeSize,
                    'label': 'data(label)',
                    'min-zoomed-font-size': 16
                }
            },
            {
//                	* selecting node with type person
//                	* selectors are overwritten so the last rule applicable to the
//                	  node will be applied
                selector: '[type = "http://www.digitalhps.org/types/TYPE_986a7cc9-c0c1-4720-b344-853f08c136ab" ]',
                style: {
                	'background-color': 'data(color)',
                    'width': nodeSize,
                    'height': nodeSize,
                    'label': 'data(label)',
                    'min-zoomed-font-size': 0
                }
            },
            {
                selector: 'edge',
                style: {
                    'width': 2,
                    'line-color': '#b0c7c3',
                    'curve-style':'haystack'
                }
            }
        ],
            
        layout: {
            name: 'cose-bilkent',
            nodeDimensionsIncludeLabels: true,
        }
    });
    
    cy.on('tap', 'node', function(){
        window.location.href = "concept/" + this.data('id');
    })
    
    cy.ready(function() {
        $(".person-entry").hover(highlightPersonInGraph, removeHighlight);
    });
	
	filterNodes(cy, removeNodes);
	return cy;
}

function filterNodes(cy, removeNodes) {
	cy.style().selector('node').style('opacity', 1).update();
	cy.$('edge').removeStyle('line-color');
	
	if(removeNodes !== null) {
	    for (i = 0; i < removeNodes.length; i++) {
    		var selectorData1 = 'node[type = "';
    		var selectorData2 = '"]';
    		var finalSelector = selectorData1.concat(removeNodes[i],selectorData2);
			cy.style().selector(finalSelector).style('opacity', 0.4)
				.update();
	    	cy.$(finalSelector).connectedEdges().style('line-color', '#e1e6e5');
	    }
    }
	
	return cy;
}