import React, {PropTypes, Component} from 'react';
import {Link} from 'react-router';
import {absUrl,resourceUrl} from '../utils/url';

class Footer extends Component {
  render() {
    return (
      <div className="footer">
        <div className="inner">
          <div className="flinks">
            <a href={ absUrl( '/m/help?code=m_duty')}>博彩责任</a>
            <a href={ absUrl( '/m/help?code=m_fair')}>公平与责任</a>
            <a href={ absUrl( '/m/help?code=m_rule')}>规则与条款</a>
          </div> 
          <div className="flogo"><img src={ resourceUrl('/images/flogo.png') } /></div>
          <p className="copyright">{window.copyright}</p>
        </div>
      </div>
    );
  }
};

export default Footer;