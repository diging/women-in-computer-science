function hideNodes(cy) {
	var brands = $('#dropdown option:selected');
	var selected = [];
	$(brands).each(function(index, brand){
	    selected.push($(this).val());
	});
	data = selected;
	var searchTerm = $("#search").val()
	return filterNodes(cy, data, searchTerm);
}

function highlightPersonInGraph(cy, highlightSize) {
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

function removeHighlight(cy, nodeSize) {
    var id = $(this).data("concept-id");
    var node = cy.getElementById(id);
    node.animate({
        css: { 'width': nodeSize, 'height' : nodeSize}
    });
}

function loadCytoScape(data, result, highlightNodes, highlightSize, nodeSize, hrefLocation) {
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
        window.location.href = hrefLocation + this.data('id');
    })
    
    cy.ready(function() {
        $(".person-entry").hover(highlightPersonInGraph(cy, highlightSize), removeHighlight(cy, nodeSize));
    });
	
    filterNodes(cy, highlightNodes);
    return cy;
}

function filterNodes(cy, highlightTypes, searchTerm) {
	//Removes any pre-existing filters
	cy.$('node').removeStyle('opacity');
	cy.$('edge').removeStyle('line-color');
	
	let isHighlightTypesEmpty = highlightTypes == null || highlightTypes.length == 0;
	let hideNodes = new Set();
	let partialHideNodes = new Set();
	
	if (!isHighlightTypesEmpty || searchTerm) {
		searchTerm = searchTerm.toLowerCase();
		
		cy.nodes().forEach(function(node) {
			//Hide nodes which meet the following criteria:
			//(1) There is a non-emty search term and the node label does not contain the search term
			//(2) There is no search term and the node type is not part of the list of types to be highlighted
			//Partially hide nodes which meet the following criteria:
			//(1) Node contains the search term but does not belong to the types to be highlighted
			if ((searchTerm && !node.data('label').toLowerCase().includes(searchTerm))
				|| (!searchTerm && !isHighlightTypesEmpty && !highlightTypes.includes(node.data('type')))) {
				hideNodes.add(node);
			} else if (searchTerm && node.data('label').toLowerCase().includes(searchTerm) 
						&& !isHighlightTypesEmpty && !highlightTypes.includes(node.data('type'))) {
				partialHideNodes.add(node);
			}
		});
		
		hideNodes.forEach(function(node) {
			node.style('opacity', 0.4);
			node.connectedEdges().forEach(ele => ele.style('line-color', '#e1e6e5'));
		});
		
		partialHideNodes.forEach(function(node) {
			node.style('opacity', 0.7);
			node.connectedEdges().forEach(ele => ele.style('line-color', '#bccfcb'));
		});
	}
	
	return cy;
}