if (typeof Object.assign != 'function') {
  Object.assign = function (target, varArgs) { // .length of function is 2
    'use strict';
    if (target == null) { // TypeError if undefined or null
      throw new TypeError('Cannot convert undefined or null to object');
    }

    var to = Object(target);

    for (var index = 1; index < arguments.length; index++) {
      var nextSource = arguments[index];

      if (nextSource != null) { // Skip over if undefined or null
        for (var nextKey in nextSource) {
          // Avoid bugs when hasOwnProperty is shadowed
          if (Object.prototype.hasOwnProperty.call(nextSource, nextKey)) {
            to[nextKey] = nextSource[nextKey];
          }
        }
      }
    }
    return to;
  };
}

import Promise from 'promise-polyfill'; 

// To add to window
if (!window.Promise) {
  window.Promise = Promise;
}


import "../scss/main.scss";

import 'isomorphic-fetch';
import ReactDom from 'react-dom';
import React from 'react';
import {Provider} from 'react-redux';

import RouterContainer from './containers/RouterContainer';
import appStore from './stores/appStore';

const store = appStore()

document.addEventListener('DOMContentLoaded', () => {
  ReactDom.render(
    <Provider store={store}>
      <RouterContainer />
    </Provider>,
    document.getElementById('mobile-app')
  );
});

