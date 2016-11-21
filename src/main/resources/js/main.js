/* global options, d3pie */

var pie = new d3pie('results', { // eslint-disable-line new-cap
	data: {
		content: options
	}
});
