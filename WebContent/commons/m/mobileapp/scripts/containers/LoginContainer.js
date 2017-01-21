import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router';

import LoginForm from '../components/LoginForm';

class LoginContainer extends Component {
  render() {
    return (
      <div className="login-page page">
        <LoginForm {...this.props} />

        <div className="login-actions">
          <Link className="btn btn-reg" to={'/register'}>注册帐号</Link>
          <Link className="btn btn-home" to={'/'}>返回首页</Link>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const {user, messages} = state;
  return {user, messages};
}

export default connect(mapStateToProps)(LoginContainer);