import React, {Component, PropTypes} from 'react';
import {absUrl} from '../utils/url';
import {Link} from 'react-router';

class HeaderNav extends Component {

  constructor(props) {
    super(props);
    this.links = [{
      title: '真人视讯',
      to: '/live',
      self: true,
    }, {
      title: '电子游戏',
      to: '/slot',
      self: true,
    }, {
      title: '体育赛事',
      to: '/sport',
      self: true,
    }, {
      title: '彩票游戏',
      to: absUrl('/m/wap'),
      self: false,
    }, {
      title: 'BBIN',
      to: '/bbin',
      self: true,
    }];
  }

  render() {
    return (
      <div className="header-links">
        <ul className="items clearfix">
          {this.links.map((link, index)=> {
            let renderLink = link => {
              if (link['self']) {
                return <Link  activeClassName="active" to={link.to}>{link.title}</Link>
              }
              else {
                return <a href={link.to}>{link.title}</a>
              }
            };
            return <li key={index} className="item">{renderLink(link)}</li>
          })}
        </ul>
      </div>
    );
  }
}

export default HeaderNav;