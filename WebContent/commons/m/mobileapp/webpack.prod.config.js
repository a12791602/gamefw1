var webpack = require('webpack');
var path = require('path');
var websiteDir = path.resolve(__dirname);
var nodeModuleDir = path.resolve(__dirname, 'node_modules');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var Ignore = new webpack.IgnorePlugin(/\.svg$/);

var sassExtractor = new ExtractTextPlugin('css/main.min.css')

var config = {
  entry: {
    main: [path.resolve(websiteDir, 'scripts', 'main.js')],
    vendor: ['lodash', 'react', 'redux', 'react-slick', 'slick-carousel', 'isomorphic-fetch'],
  },
  output: {
    path: path.resolve(websiteDir, 'built'),
    filename: 'scripts/[name].min.js',
    publicPath: '/built/',
  },
  module: {
    loaders: [{
      test: /\.scss$/,
      loader: sassExtractor.extract(['css', 'resolve-url', 'sass'])
    }, {
      test: /\.js$/,
      loaders: ['react-hot', 'babel?' + JSON.stringify({presets: ['react', 'es2015', 'stage-0']})],
      exclude: [nodeModuleDir]
    }, {
            test: /\.(eot|svg|ttf|woff|woff2)$/,
            loader: 'file?name=fonts/[name].[ext]'
        }, {
          test: /\.(gif|png|jpg)$/,
          loader: 'file-loader?name=[name].[ext]&publicPath=../../images/&outputPath=/images/',
        }],
  },
  plugins: [
    sassExtractor,
    new webpack.optimize.CommonsChunkPlugin('vendor', '/scripts/vendor.min.js'),
    new webpack.DefinePlugin({
      'process.env': { NODE_ENV:  JSON.stringify('production') }
    }),
    new webpack.optimize.UglifyJsPlugin({minimize: true}),
  ],
};

module.exports = config;