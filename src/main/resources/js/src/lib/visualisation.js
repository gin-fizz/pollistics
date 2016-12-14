import d3 from 'd3';

const pieDiv = d3.select('body').append('div').attr('class', 'toolTip');

const pieWidth = 500;
const pieHeight = 500;
const pieRadius = Math.min(pieWidth, pieHeight) / 2;

function getRandomColor() {
	const letters = '0123456789ABCDEF';
	let color = '#';
	for (let i = 0; i < 6; i++ ) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}

const arc = d3.arc()
	.outerRadius(pieRadius - 10)
	.innerRadius(pieRadius - 70);

const pie = d3.pie()
	.sort(null)
	.value(d => d.value);

const svgPie = d3.select('body').append('svg')
	.attr('width', pieWidth)
	.attr('height', pieHeight)
	.append('g')
	.attr('transform', `translate(${pieWidth / 2},${pieHeight / 2})`);

const g = svgPie.selectAll('.arc')
	.data(pie(data))
	.enter().append('g')
	.attr('class', 'arc');

g.append('path')
	.attr('d', arc)
	.style('fill', d => getRandomColor(d.data.label));

g
	.on('mousemove', d => {
		pieDiv.style('left', `${d3.event.pageX+10}px`);
		pieDiv.style('top', `${d3.event.pageY-25}px`);
		pieDiv.style('display', 'inline-block');
		pieDiv.html(`${d.data.label}<br>${d.value}Vote(s)`);
	});

g
	.on('mouseout', d => {
		pieDiv.style('display', 'none');
	});
g.append('text')
	.attr('transform', (d) => {
		console.log(d);
		return `translate(${arc.centroid(d)})`;
	})
	.attr('dy', '.35em')
	.text((d) => d.data.label)
	.attr('class', 'value');

const div = d3.select('body').append('div').attr('class', 'toolTip');

const axisMargin = 20;
const margin = 40;
const valueMargin = 4;
let width = parseInt(d3.select('body').style('width'), 10);
let height = parseInt(d3.select('body').style('height'), 10);
const barHeight = (height-axisMargin-margin*2)* 0.4/data.length;
const barPadding = (height-axisMargin-margin*2)*0.4/data.length;
var data;
let bar;
let svg;
let scale;
let xAxis;
let labelWidth = 0;
width *= .5;
height *= .8;
console.log(width);
max = d3.max(data, d => d.value);

svg = d3.select('body')
	.append('svg')
	.attr('width', width)
	.attr('height', height);


bar = svg.selectAll('g')
	.data(data)
	.enter()
	.append('g');

bar.attr('class', 'bar')
	.attr('cx',0)
	.attr('transform', (d, i) => `translate(${margin},${i * (barHeight + barPadding) + barPadding})`);

bar.append('text')
	.attr('class', 'value2')
	.attr('y', barHeight / 2)
	.attr('dy', '.35em') //vertical align middle
	.text(d => d.label).each(function() {
	labelWidth = Math.ceil(Math.max(labelWidth, this.getBBox().width));
});

scale = d3.scaleLinear()
	.domain([0, max + max *0.20])
	.range([0, width - margin*2 - labelWidth]);

xAxis = d3.axisBottom()
	.scale(scale)
	.tickSize(-height + 2*margin + axisMargin)

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
	.text(d => `${d.value} Vote(s)`)
	.attr('x', function(d){
		const width = this.getBBox().width;
		return Math.max(width + valueMargin, scale(d.value));
	});

bar
	.on('mousemove', d => {
		div.style('left', `${d3.event.pageX+10}px`);
		div.style('top', `${d3.event.pageY-25}px`);
		div.style('display', 'inline-block');
		div.html(`${d.label}<br>${d.value}Vote(s)`);
	});
bar
	.on('mouseout', d => {
		div.style('display', 'none');
	});

svg.insert('g',':first-child')
	.attr('class', 'axisHorizontal')
	.attr('transform', `translate(${margin + labelWidth},${height - axisMargin - margin})`)
	.call(xAxis);

