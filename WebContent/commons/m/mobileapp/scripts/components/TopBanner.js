import React, {Component, PropTypes} from 'react';
import {Link} from 'react-router';
import {absUrl, resourceUrl} from '../utils/url';
import {userIsLogin} from '../utils/user';
import {userLogout} from '../actions/UserAction';

class TopBanner extends Component {

  constructor(props) {
    super(props);
    this.logout = this.logout.bind(this);
  }

  logout() {
    const {dispatch} = this.props;
    dispatch(userLogout());
  }

  renderTbLinks() {
    const {user} = this.props;
    if (userIsLogin(user)) {
      return (
        <ul className="items clearfix">
          <li><Link to={'/'} className="icon icon-home"/></li>
          <li><a href={ absUrl( '/m/wap/member')}><i className="link icon-member">会员中心</i></a></li>
          <li><a href="javascript:void(0)" onClick={this.logout} className="icon icon-logout"/></li>
        </ul>
      );
    }
    else {
      return (
        <ul className="items clearfix">
          <li><Link to={'/'} className="icon icon-home"></Link></li>
          <li><Link to={'/login'} className="link link-login">登录</Link></li>
          <li><Link to={'/register'} className="link link-register">注册</Link></li>
        </ul>
      );
    }
  }

  render() {
    return (
      <div className="top-banner-banner">
        <div className="logo"><span className="helper"></span><img src={resourceUrl('/images/logo.png')} alt="" className="logo" /></div>
        <div className="tb-links">
          {this.renderTbLinks()}
        </div>
      </div>
    );
  };
};

TopBanner.propTypes = {
  user: PropTypes.object.isRequired
};

export default TopBanner;