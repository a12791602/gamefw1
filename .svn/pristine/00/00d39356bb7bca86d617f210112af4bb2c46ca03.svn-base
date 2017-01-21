import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router';

import RegisterForm from '../components/RegisterForm';
import BaseComponent from '../components/BaseComponent';

class RegisterContainer extends BaseComponent {

  render() {
    return (
      <div className="login-page register-page page">
        <RegisterForm {...this.props} />

        <div className="login-actions">
          <Link className="btn btn-reg" to={'/register'}>注册帐号</Link>
          <Link className="btn btn-home" to={'/'}>返回首页</Link>
        </div>
      </div>

    );
  }
}

function mapStateToProps(state) {
  const {user} = state;
  return {user};
}

export default connect(mapStateToProps)(RegisterContainer);