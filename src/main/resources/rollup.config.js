import babel from 'rollup-plugin-babel';

export default {
  entry: 'src/main.js',
  plugins: [ babel() ],
  format: 'umd'
};
