import React, {Component, PropTypes} from 'react';
import {Link} from 'react-router';
import {absUrl} from '../utils/url';
import {userIsLogin} from '../utils/user';

class BBIN extends Component {

  constructor(props) {
    super(props);
    this.brands = [ {
      id: 'bbin-live',
      title: 'BB视讯',
      url: absUrl('/m/game/forwardGame?typeCode=live&gameType=bbin'),
    }, {
      id: 'bbin-sport',
      title: 'BB体育',
      url: absUrl('/m/game/forwardGame?typeCode=ball&gameType=bbin'),
    }, {
      id: 'bbin-slot',
      title: 'BB电子',
      url: absUrl('/m/game/forwardGame?typeCode=game&gameType=bbin'),
    }, {
      id: 'bbin-lottery',
      title: 'BB彩票',
      url: absUrl('/m/game/forwardGame?typeCode=ltlottery&gameType=bbin'),
    }];
  }

  alertToLogin() {
    alert('请先登录');
  }

  bbinLink(bbin) {
    const {user} = this.props;
    if (userIsLogin(user)) {
      return <a href={bbin.url}>{bbin.title}</a>;
    }
    else {
      return <a href="javascript:void(0);" onClick={this.alertToLogin}>{bbin.title}</a>;
    }
  }

  render() {
    return (
      <div className="slot-type-links bbin-type-links">
        <ul className="clearfix items">
          {this.brands.map((bbin, index) => {
            return (
              <li key={index} className={"slot-item-" + bbin.id}>
                <div className="slot-link-inner">{ this.bbinLink(bbin) }</div>
              </li>);
          })}
        </ul>
      </div>
    );
  }
};

export default BBIN;