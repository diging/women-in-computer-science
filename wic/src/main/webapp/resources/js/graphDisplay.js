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

function filterNodes(cy, hideTypes, searchTerm) {
	//Removes any pre-existing filters
	cy.$('node').removeStyle('opacity');
	cy.$('edge').removeStyle('line-color');
	
	let isHideTypesEmpty = hideTypes == null || hideTypes.length == 0;
	let hideNodes = new Set();
	let partialHideNodes = new Set();
	
	if (!isHideTypesEmpty || searchTerm) {
		searchTerm = searchTerm.toLowerCase();
		
		cy.nodes().forEach(function(node) {
			//Selects nodes which do not meet one of the following selection criteria:
			//(1) No hide types criteria provided and does not contain search term
			//(2) No search term provided and belongs to a type which is to be hidden
			//(3) Belongs to a type to be hidden and does not contain the search term
			if ((isHideTypesEmpty && !node.data('label').toLowerCase().includes(searchTerm))
				|| (!searchTerm && hideTypes.includes(node.data('type')))
				|| (hideTypes.includes(node.data('type')) || !node.data('label').toLowerCase().includes(searchTerm))) {
					hideNodes.add(node);
			} else if (searchTerm && node.data('label').toLowerCase().includes(searchTerm)) {
			//As the current node meets the crtieria, select its neighbors to hide partially
				node.neighborhood().forEach(function(ele) {
					if (ele.isNode() && (!ele.data('label').toLowerCase().includes(searchTerm) 
							|| (!isHideTypesEmpty && hideTypes.includes(ele.data('type'))))) {
						partialHideNodes.add(ele);
					}
				});
			}
		});
		
		//Hide elements
		hideNodes.forEach(function(node) {
			node.style('opacity', 0.4);
			node.connectedEdges().forEach(ele => ele.style('line-color', '#e1e6e5'));
		});
		
		//Partially highlight the hidden nodes which are immediate neighbors of selected nodes(Selected only if there is a search term involved)
		partialHideNodes.forEach(function(node) {
			node.style('opacity', 0.7);
			node.connectedEdges().forEach(ele => ele.style('line-color', '#bccfcb'));
		});
	}
	
	return cy;
}