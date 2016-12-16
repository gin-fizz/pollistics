import * as d3 from 'd3';

function isEmpty(data) {
	return data.every((item) => item.value === 0);
}

function rand(min, max) {
	return min + Math.random() * (max - min);
}

function get_random_color() {
	const h = rand(1, 360);
	const s = rand(0, 100);
	const l = rand(0, 100);
	return `hsl(${h},${s}%,${l}%)`;
}

/**
 * When the charts are lined horizontally
 * @returns {boolean}
 */
function chartsHorizontal() {
	return window.innerWidth > 671
}

export function initVisualisation(container = '#results') {
	window.pieSvg = d3.select(container).append('svg');
	window.pieTooltip = d3.select(container).append('div').attr('class', 'toolTip');
	window.barSvg = d3.select(container).append('svg');
	window.barTooltip = d3.select(container).append('div').attr('class', 'toolTip');

}

/**
 * Visualise pollistics data
 * @param  {Array} data      Data which has object with value and label
 * @param  {String} container a d3 selector
 */
export function visualise(data, container = '#results') {
	if (isEmpty(data)) {
		const containerEl = document.querySelector(container);
		const item = document.createElement('h2');
		item.style.margin = '0 auto';
		item.innerText = 'No data available';
		containerEl.appendChild(item);
		return undefined;
	}

	function drawPie(data, svg, tooltip) {
		const containerEl = document.querySelector(container);
		let width = containerEl.getBoundingClientRect().width;
		let height = (data.length + 1) * 100;
		if (chartsHorizontal()) {
			width = containerEl.getBoundingClientRect().width / 2;
		}
		const radius = Math.min(width, height) / 2;

		const arc = d3.arc()
			.outerRadius(radius - 10)
			.innerRadius(radius - 70);

		const pie = d3.pie()
			.sort(null)
			.value(d => d.value);

		svg.attr('width', width)
			.attr('height', height);

		// todo: without removing xdd
		svg.selectAll("*").remove();

		svg.append('g').attr('transform', `translate(${width / 2},${height / 2})`);

		const g = svg.selectAll('.arc')
			.data(pie(data))
			.enter().append('g')
			.attr('class', 'arc');

		g.append('path')
			.attr('d', arc)
			.style('fill', d => get_random_color(d.data.label));

		g.on('mousemove', d => {
			tooltip.style('left', `${d3.event.pageX+10}px`);
			tooltip.style('top', `${d3.event.pageY-25}px`);
			tooltip.style('display', 'inline-block');
			tooltip.html(`${d.data.label}<br>${d.value} vote${d.data.value > 1 ? 's' : ''}`);
		});

		g.on('mouseout', () => {
			tooltip.style('display', 'none');
		});

		g.append('text')
			.attr('transform', (d) => {
				if (d.value == 0) {
					return null;
				} else {
					return `translate(${arc.centroid(d)})`;
				}
			})
			.attr('dy', '.35em')
			.text((d) => d.data.label)
			.attr('class', 'value');
	}

	function drawBar(data, svg, tooltip) {
		const axisMargin = 20;
		const margin = 40;
		const valueMargin = 4;

		const containerEl = document.querySelector(container);
		let width = containerEl.getBoundingClientRect().width;
		let height = (data.length + 1) * 100;

		if (chartsHorizontal()) {
			width = containerEl.getBoundingClientRect().width / 2;
		}

		const barHeight = (height - axisMargin - margin * 2) * 0.4 / data.length;
		const barPadding = (height - axisMargin - margin * 2) * 0.4 / data.length;
		let labelWidth = 0;

		const max = d3.max(data, d => d.value);

		svg.attr('width', width)
			.attr('height', height);

		// todo: without removing xdd
		svg.selectAll("*").remove();

		const bar = svg.selectAll('g')
			.data(data)
			.enter()
			.append('g');

		bar.attr('class', 'bar')
			.attr('cx', 0)
			.attr('transform', (d, i) => `translate(${margin},${i * (barHeight + barPadding) + barPadding})`);

		bar.append('text')
			.attr('class', 'value2')
			.attr('y', barHeight / 2)
			.attr('dy', '.35em') //vertical align middle
			.text(d => d.label).each(function() {
			if (data.value == 0) {
				return null;
			} else {
				labelWidth = Math.ceil(Math.max(labelWidth, this.getBBox().width));
			}
		});

		const scale = d3.scaleLinear()
			.domain([0, max * 1.2])
			.range([0, width - margin * 2 - labelWidth]);

		const xAxis = d3.axisBottom()
			.scale(scale)
			.tickSize(-height + 2 * margin + axisMargin);

		bar.append('rect')
			.attr('transform', `translate(${labelWidth}, 0)`)
			.attr('height', barHeight)
			.attr('width', d => scale(d.value));

		bar.append('text')
			.attr('class', 'value')
			.attr('y', barHeight / 2)
			.attr('dx', -valueMargin + labelWidth) //margin right
			.attr('dy', '.35em') //vertical align middle
			.attr('text-anchor', 'end')
			.text(d => `${d.value} vote${d.value > 1 ? 's' : ''}`)
			.attr('x', function(d) {
				const width = this.getBBox().width;
				return Math.max(width + valueMargin, scale(d.value));
			});

		bar.on('mousemove', d => {
				if (d.value == 0) {
					return null;
				} else {
					tooltip.style('left', `${d3.event.pageX + 10}px`);
					tooltip.style('top', `${d3.event.pageY - 25}px`);
					tooltip.style('display', 'inline-block');
					tooltip.html(`${d.label}<br>${d.value} vote${d.value > 1 ? 's' : ''}`);
				}
			});

		bar.on('mouseout', () => {
				tooltip.style('display', 'none');
			});

		svg.insert('g', ':first-child')
			.attr('class', 'axisHorizontal')
			.attr('transform', `translate(${margin + labelWidth},${height - axisMargin - margin})`)
			.call(xAxis);
	}

	drawPie(data, window.pieSvg, window.pieTooltip);
	drawBar(data, window.barSvg, window.barTooltip);
}
