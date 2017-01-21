var webpack = require('webpack');
var path = require('path');
var websiteDir = path.resolve(__dirname);
var nodeModuleDir = path.resolve(__dirname, 'node_modules');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var Ignore = new webpack.IgnorePlugin(/\.svg$/);

var sassExtractor = new ExtractTextPlugin('css/main.min.css')

var config = {
  devtool: 'source-map',
  entry: {
    main: [path.resolve(websiteDir, 'scripts', 'main.js')],
    vendor: ['lodash', 'react', 'redux', 'react-slick', 'slick-carousel', 'isomorphic-fetch'],
  },
  output: {
    path: path.resolve(websiteDir, 'built'),
    filename: 'scripts/[name].min.js',
    publicPath: 'http://127.0.0.1:8889/built/',
  },
  module: {
    loaders: [{
      test: /\.scss$/,
      loader: sassExtractor.extract(['css?sourceMap', 'resolve-url', 'sass?sourceMap'])
    }, {
      test: /\.js$/,
      loaders: ['react-hot', 'babel?' + JSON.stringify({presets: ['react', 'es2015', 'stage-0']})],
      exclude: [nodeModuleDir]
    }, {
            test: /\.(eot|svg|ttf|woff|woff2)$/,
            loader: 'file?name=fonts/[name].[ext]'
        }, {
          test: /\.(gif|png|jpg)$/,
          loader: 'file-loader?name=[name].[ext]&publicPath=../&outputPath=images',
        }],
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify('development')
    }),
    sassExtractor,
    new webpack.optimize.CommonsChunkPlugin('vendor', '/scripts/vendor.min.js'),
    new webpack.optimize.UglifyJsPlugin({minimize: true}),
  ],
  devServer: {
	  host: '0.0.0.0',
  }
};

module.exports = config;