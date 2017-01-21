import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import BaseComponent from '../components/BaseComponent';

class AppContainer extends BaseComponent {

  componentWillMount() {
    const {router} = this.context;
    if (typeof window.defaultPath == 'string' ) {
      router.push(window.defaultPath);
    }
  }

  render() {
    return (
      <div className="app-container">
        {this.props.children}
      </div>
    );
  }
}

AppContainer.contextTypes = {
  router: PropTypes.object.isRequired
};

function mapStateForProps(state) {
  return {};
}

export default connect(mapStateForProps)(AppContainer);