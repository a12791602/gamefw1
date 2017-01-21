import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import {loadUserInfo} from '../actions/UserAction';

class BaseComponent extends Component {

  constructor(props) {
    super(props);

    const {dispatch} = this.props;
    dispatch(loadUserInfo());
  }

  render() {
    return null;
  }
};

BaseComponent.propTypes = {
  user: PropTypes.object
};

export default BaseComponent;