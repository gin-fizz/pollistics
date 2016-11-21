import d3 from 'd3';
import d3pie from 'd3pie';

/* global options */

const pie = new d3pie('results', { // eslint-disable-line new-cap
	data: {
		content: options
	}
});
