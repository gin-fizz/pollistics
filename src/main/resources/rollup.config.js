import babel from 'rollup-plugin-babel';
import nodeResolve from 'rollup-plugin-node-resolve';
import commonjs from 'rollup-plugin-commonjs';
import uglify from 'rollup-plugin-uglify';

export default {
	entry: 'js/main.js',
	dest: 'js/bundle.js',
	format: 'umd',
	moduleName: 'pollistics',
	plugins: [
		babel(),

		nodeResolve({
			jsnext: true,
			main: true
		}),

		commonjs({
			// non-CommonJS modules will be ignored, but you can also
			// specifically include/exclude files
			include: 'node_modules/**',  // Default: undefined
			exclude: [ 'node_modules/foo/**', 'node_modules/bar/**' ],  // Default: undefined

			// search for files other than .js files (must already
			// be transpiled by a previous plugin!)
			extensions: [ '.js', '.coffee' ],  // Default: [ '.js' ]

			// if true then uses of `global` won't be dealt with by this plugin
			ignoreGlobal: false,  // Default: false

			// if false then skip sourceMap generation for CommonJS modules
			sourceMap: false,  // Default: true

			// explicitly specify unresolvable named exports
			// (see below for more details)
			namedExports: { './module.js': ['foo', 'bar' ] }  // Default: undefined
		}),

		uglify()
	]
};
